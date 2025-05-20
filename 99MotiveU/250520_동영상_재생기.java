class Solution {
    public String solution(String video_len, String pos, String op_start, String op_end, String[] commands) {
        String answer = "";
        int videoLength = Integer.parseInt(video_len.substring(0, 2)) * 60 + Integer.parseInt(video_len.substring(3));
        int position = Integer.parseInt(pos.substring(0, 2)) * 60 + Integer.parseInt(pos.substring(3));
        int opStart = Integer.parseInt(op_start.substring(0, 2)) * 60 + Integer.parseInt(op_start.substring(3));
        int opEnd = Integer.parseInt(op_end.substring(0, 2)) * 60 + Integer.parseInt(op_end.substring(3));

        if (position >= opStart && position <= opEnd) {
            position = opEnd;
        }

        for (String command : commands) {
            if (command.equals("prev")) {
                position = Math.max(0, position - 10);
            } else if (command.equals("next")) {
                position = Math.min(videoLength, position + 10);
            }

            if (position >= opStart && position <= opEnd) {
                position = opEnd;
            }
        }

        int mm = position / 60;
        int ss = position % 60;
        answer = String.format("%02d:%02d", mm, ss);
        return answer;
    }
}

// 먼저 문자열 형태의 시간을 parseInt를 사용해 초 단위로 변환한 뒤, 시작 위치가 오프닝 구간에 해당하면 점프 하도록 조건을 걸었고, "prev"와 "next" 명령어를 처리하며 재생 위치를 갱신하고, 명령어 실행 후 위치가 다시 오프닝 구간에 들어오면 자동으로 점프하도록 했습니다. 최종 위치는 다시 "mm:ss" 형식으로 변환하여 return하도록 했습니다.
