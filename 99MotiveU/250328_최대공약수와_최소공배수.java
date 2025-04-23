class Solution {
    public int[] solution(int n, int m) {
        int[] answer = {}; 
        //mnum : 최대공, nnum : 최소공;
        int mnum = 1;
        for (int i = 1; i <= n; i++) {
            if (n % i == 0 && m % i == 0) { mnum = i;}
        }
        int nnum = (n * m) / mnum;
        answer = new int[]{mnum, nnum};
        
        return answer;
    }
}
