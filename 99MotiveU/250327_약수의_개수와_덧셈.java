  class Solution {
    public int solution(int left, int right) {
        int answer = 0;

        for (int i = left; i <= right; i++) {
            answer += (Math.sqrt(i) == (int) Math.sqrt(i)) ? -i : i;
        }
        return answer;
    }
}
