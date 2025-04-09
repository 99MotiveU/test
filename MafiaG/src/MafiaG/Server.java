package MafiaG;

import MafiaG.ConGemini;
import java.io.*;
import java.net.*;
import java.util.*;

public class Server {
    static List<ClientHandler> clients = new ArrayList<>();
    static final int MAX_CLIENTS = 7;
    static int anonymousCounter = 1;
    static int readyCount = 0;

    static Map<String, Integer> voteMap = new HashMap<>();
    static Set<String> votedUsers = new HashSet<>();
    static Map<String, String> answers = new HashMap<>();
    static int questionCount = 0;
    static final int MAX_QUESTIONS = 5;

    static List<String> questionList = Arrays.asList(
        "오늘 점심으로 뭘 먹을까요?",
        "당신이 제일 좋아하는 동물은?",
        "주말에 뭐하면 좋을까요?",
        "가장 기억에 남는 여행지는 어디인가요?",
        "요즘 즐겨 듣는 음악은 뭔가요?",
        "어릴 때 꿈은 무엇이었나요?",
        "요즘 빠진 취미는?",
        "혼자 여행 간다면 어디로 가고 싶나요?"
    );
    static Random random = new Random();
    static String currentQuestion = "";

    static boolean resultRevealed = false;
    static boolean gameStarted = false;

    static ClientHandler geminiBot = null;

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(3579)) {
            System.out.println("서버가 시작되었습니다");

            geminiBot = new GeminiBot("익명" + anonymousCounter++, getRandomColor());
            clients.add(geminiBot);

            while (true) {
                Socket socket = serverSocket.accept();
                if (clients.size() >= MAX_CLIENTS) {
                    socket.close();
                    continue;
                }

                ClientHandler handler = new ClientHandler(socket);
                handler.colorCode = getRandomColor();
                handler.nickname = "익명" + anonymousCounter++;
                clients.add(handler);

                handler.start();
                broadcastParticipants();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void broadcast(String msg) {
        for (ClientHandler client : clients) {
            try {
                client.send(msg);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    static void broadcastParticipants() {
        StringBuilder sb = new StringBuilder();
        sb.append("{\"type\":\"PARTICIPANTS\",\"list\":[");
        for (int i = 0; i < clients.size(); i++) {
            sb.append("{\"nickname\":\"").append(clients.get(i).nickname)
              .append("\",\"color\":\"").append(clients.get(i).colorCode).append("\"}");
            if (i != clients.size() - 1) sb.append(",");
        }
        sb.append("]}");
        broadcast(sb.toString());
    }
    
    static Set<String> usedColors = new HashSet<>();
    static String getRandomColor() {
        List<String> colors = new ArrayList<>(Arrays.asList(
            "#FF6B6B", "#6BCB77", "#4D96FF", "#FFC75F", "#A66DD4", "#FF9671", "#00C9A7"
        ));

        // 사용된 색상 제거
        colors.removeAll(usedColors);

        if (colors.isEmpty()) {
            // 색상 부족할 경우 예외 처리 또는 기본 색상 사용
            return "#000000";
        }

        String color = colors.get(random.nextInt(colors.size()));
        usedColors.add(color);
        return color;
    }

    static void startNextQuestion() {
        if (questionCount >= MAX_QUESTIONS) {
            broadcastFinalVoteResult();
            return;
        }

        currentQuestion = questionList.get(random.nextInt(questionList.size()));
        questionCount++;
        resultRevealed = false;

        broadcast("{\"type\":\"QUESTION_PHASE\",\"question\":\"" + currentQuestion + "\"}");
        broadcast("{\"type\":\"chat\",\"color\":\"#888888\",\"message\":\"⏱️ 타이머 시작! 20초 후 답변이 공개됩니다.\"}");

        answers.clear();
        votedUsers.clear();
        voteMap.clear();

        new Timer().schedule(new TimerTask() {
            public void run() {
                String geminiAnswer = generateGeminiAnswer(currentQuestion);
                answers.put(geminiBot.nickname, geminiAnswer);
                System.out.println("[서버] Gemini 답변 등록: " + geminiAnswer);
                checkAndRevealIfReady();
            }
        }, 1000);

        new Timer().schedule(new TimerTask() {
            public void run() {
                if (!resultRevealed) {
                    revealAnswers();
                    resultRevealed = true;
                }
            }
        }, 20000);
    }

    static void revealAnswers() {
        StringBuilder sb = new StringBuilder();
        sb.append("{\"type\":\"REVEAL_RESULT\",\"question\":\"")
          .append(currentQuestion).append("\",\"answers\":[");

        List<ClientHandler> shuffledClients = new ArrayList<>(clients);
        Collections.shuffle(shuffledClients);

        int i = 0;
        for (ClientHandler client : shuffledClients) {
            String answer = answers.get(client.nickname);
            if (answer == null) answer = "응답 없음";

            sb.append("{\"color\":\"").append(client.colorCode)
              .append("\",\"message\":\"").append(answer).append("\"}");

            if (++i < shuffledClients.size()) sb.append(",");
        }

        sb.append("]}");
        broadcast(sb.toString());

        new Timer().schedule(new TimerTask() {
            public void run() {
                broadcast("{\"type\":\"VOTE_PHASE\"}");
            }
        }, 1000);
    }

    static void checkAndRevealIfReady() {
        if (answers.size() == clients.size() && !resultRevealed) {
            System.out.println("[서버] 모든 답변 제출됨 (하지만 20초 타이머까지 대기)");
        }
    }

    static void broadcastVoteResult() {
        String topColor = null;
        int maxVotes = 0;

        for (Map.Entry<String, Integer> entry : voteMap.entrySet()) {
            if (entry.getValue() > maxVotes) {
                maxVotes = entry.getValue();
                topColor = entry.getKey();
            }
        }

        broadcast("{\"type\":\"chat\",\"color\":\"#000000\",\"message\":\"💡 투표 결과: " + topColor + " 유저가 " + maxVotes + "표를 받았습니다.\"}");

        new Timer().schedule(new TimerTask() {
            public void run() {
                startNextQuestion();
            }
        }, 3000);
    }

 // 최종 투표 결과 발표 메서드 수정 (동점자 전원 승리 처리)
    static void broadcastFinalVoteResult() {
        int maxVotes = 0;
        List<String> topNicknames = new ArrayList<>();

        for (Map.Entry<String, Integer> entry : voteMap.entrySet()) {
            int votes = entry.getValue();
            if (votes > maxVotes) {
                maxVotes = votes;
                topNicknames.clear();
                topNicknames.add(entry.getKey());
            } else if (votes == maxVotes) {
                topNicknames.add(entry.getKey());
            }
        }

        StringBuilder message = new StringBuilder("🏁 최종 투표 결과: ");
        for (String name : topNicknames) {
            message.append(name).append(" ");
        }
        message.append("유저가 ").append(maxVotes).append("표를 받아 승리했습니다.");

        broadcast("{\"type\":\"FINAL_RESULT\",\"message\":\"" + message + "\"}");

        updateScores(topNicknames);  // 동점자 모두 전달
    }

    // 점수 반영 메서드 수정: 다수 승자 처리
    static void updateScores(List<String> winners) {
        List<String> participants = new ArrayList<>();

        for (ClientHandler client : clients) {
            if (client.nickname != null && !client.nickname.equals("Gemini")) {
                participants.add(client.nickname);
            }
        }

        try {
            DB.DatabaseManager.updateScoresAfterGame(winners, participants);
            System.out.println("[서버] 게임 점수 반영 완료!");
        } catch (Exception e) {
            System.out.println("[서버] 점수 반영 중 오류 발생: " + e.getMessage());
        }
    }


    static String generateGeminiAnswer(String question) {
        try {
            return ConGemini.getResponse(question);
        } catch (IOException e) {
            e.printStackTrace();
            return "Gemini 응답 실패: " + e.getMessage();
        }
    }

    static class ClientHandler extends Thread {
        Socket socket;
        BufferedReader br;
        BufferedWriter bw;
        String nickname;
        String colorCode;
        boolean isReady = false;

        public ClientHandler(Socket socket) {
            this.socket = socket;
            if (socket != null) {
                try {
                    br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        public void run() {
            try {
                send("{\"type\":\"INIT\",\"nickname\":\"" + nickname + "\",\"color\":\"" + colorCode + "\"}");

                String msg;
                while ((msg = br.readLine()) != null) {
                    if (msg.contains("\"type\":\"start\"")) {
                        isReady = true;
                        readyCount++;
                        int realPlayers = clients.size() - 1;
                        if (readyCount == realPlayers && !gameStarted) {
                            if (realPlayers >= 3) {
                                gameStarted = true;
                                broadcast("{\"type\":\"GAME_START\"}");
                                startNextQuestion();
                            } else {
                                for (ClientHandler client : clients) {
                                    try {
                                        if (!(client instanceof GeminiBot)) {
                                            client.send("{\"type\":\"chat\",\"color\":\"#FF0000\",\"message\":\"⚠️ 최소 3명 이상이 준비해야 게임을 시작할 수 있습니다.\"}");
                                        }
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }
                    } else if (msg.contains("\"type\":\"ANSWER_SUBMIT\"")) {
                        String answer = extractValue(msg, "message");
                        System.out.println("[서버] " + nickname + " 의 답변 수신: " + answer);
                        answers.put(nickname, answer);
                        checkAndRevealIfReady();
                    } else if (msg.contains("\"type\":\"vote\"")) {
                        String target = extractValue(msg, "target");
                        if (!target.equals(nickname)) {
                            voteMap.put(target, voteMap.getOrDefault(target, 0) + 1);
                            votedUsers.add(nickname);
                        }
                        if (votedUsers.size() == clients.size() - 1) {
                            broadcastVoteResult();
                        }
                    } else if (!msg.trim().startsWith("{")) {
                        String chatJson = "{\"type\":\"chat\",\"color\":\"" + colorCode + "\",\"message\":\"" + msg + "\"}";
                        broadcast(chatJson);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    clients.remove(this);
                    broadcastParticipants();
                    if (br != null) br.close();
                    if (bw != null) bw.close();
                    if (socket != null) socket.close();
                    usedColors.remove(colorCode);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        void send(String msg) throws IOException {
            if (bw != null) {
                bw.write(msg);
                bw.newLine();
                bw.flush();
            }
        }
    }

    static class GeminiBot extends ClientHandler {
        public GeminiBot(String nickname, String colorCode) {
            super(null);
            this.nickname = nickname;
            this.colorCode = colorCode;
        }

        @Override public void run() {}
        @Override void send(String msg) {}
    }

    static String extractValue(String json, String key) {
        int idx = json.indexOf(key);
        if (idx == -1) return "";
        int start = json.indexOf("\"", idx + key.length() + 2);
        int end = json.indexOf("\"", start + 1);
        return json.substring(start + 1, end);
    }
}
