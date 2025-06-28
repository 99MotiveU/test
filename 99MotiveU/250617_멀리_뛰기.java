class Solution {
    public long solution(int n) {
        long answer = 0;
        //피보나치 수열
        long a = 1, b = 1;

        for (int i = 2; i <= n; i++) {
            long temp = (a + b) % 1234567;
            a = b;
            b = temp;
        }

        answer = b;
        return answer;
    }
}
