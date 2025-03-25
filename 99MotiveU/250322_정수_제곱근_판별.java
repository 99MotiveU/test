class Solution {
    public long solution(long n) {
        long answer = -1;
        long d = (long)Math.sqrt(n);
        if(d*d==n){answer = (d+1)*(d+1);}
        return answer;
        
    }
}
