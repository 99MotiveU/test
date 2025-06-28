import java.util.*;

class Solution {
    public int[] solution(int n, String[] words) {
        int[] answer = {};
        Set<String> used = new HashSet<>();
        used.add(words[0]);

        for (int i = 1; i < words.length; i++) {
            String prev = words[i - 1];
            String curr = words[i];

            if (used.contains(curr) || prev.charAt(prev.length() - 1) != curr.charAt(0)) {
                answer = new int[]{(i % n) + 1, (i / n) + 1};
                break;
            }

            used.add(curr);
        }

        if (answer.length == 0) {
            answer = new int[]{0, 0};
        }
        return answer;
    }
}
