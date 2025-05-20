import java.util.*;

class Solution {
    public String[] solution(String[] players, String[] callings) {
        String[] answer = {};
        
        Map<String, Integer> map = new HashMap<>();

        for (int i = 0; i < players.length; i++) {
            map.put(players[i], i);
        }

        for (String name : callings) {
            int i = map.get(name);
            String front = players[i - 1];

            players[i - 1] = name;
            players[i] = front;

            map.put(name, i - 1);
            map.put(front, i);
        }
        answer = players;
        return answer;
    }
}
