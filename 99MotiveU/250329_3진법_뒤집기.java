class Solution {
    public int solution(int n) {
        int answer = 0;
        StringBuilder base3 = new StringBuilder();
        while(n > 0) {
            base3.append(n % 3);
            n /= 3;
        }
        answer = Integer.parseInt(base3.toString(), 3);   
        return answer;
    }
}
