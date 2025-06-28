import java.util.*;

class Solution {
    public int solution(int[] elements) {
        int answer = 0;
        int n = elements.length;
        
        HashSet<Integer> sums = new HashSet<>();
        
        int[] extended = new int[n * 2];
        for (int i = 0; i < n * 2; i++) {
            extended[i] = elements[i % n];
        }

        for (int length = 1; length <= n; length++) {
            for (int start = 0; start < n; start++) {
                int sum = 0;
                for (int i = start; i < start + length; i++) {
                    sum += extended[i];
                }
                sums.add(sum);
            }
        }
        answer = sums.size();
        return answer;
    }
}
