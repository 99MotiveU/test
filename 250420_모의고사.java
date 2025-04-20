import java.util.*;

class Solution {
    public int[] solution(int[] answers) {
        int[] answer = {};
        
        int[] m1 = {1, 2, 3, 4, 5};
        int[] m2 = {2, 1, 2, 3, 2, 4, 2, 5};
        int[] m3 = {3, 3, 1, 1, 2, 2, 4, 4, 5, 5};
        // 하나씩 검사하면서 수포자의 답과 비교
        int[] scores = new int[3];
        for (int i = 0; i < answers.length; i++) {
            if (answers[i] == m1[i % m1.length]) scores[0]++;
            if (answers[i] == m2[i % m2.length]) scores[1]++;
            if (answers[i] == m3[i % m3.length]) scores[2]++;
        }

        int max = Math.max(scores[0], Math.max(scores[1], scores[2]));

        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            if (scores[i] == max) list.add(i + 1);
        }

        answer = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            answer[i] = list.get(i);
        }

        return answer;
    }
}
