class Solution {
    public int solution(int[][] sizes) {
        int answer = 0;
        int w = 0, h = 0;
        for(int []size : sizes){
            w = Math.max(w,Math.max(size[0],size[1]));
            h = Math.max(h,Math.min(size[0],size[1]));              
        }
        answer = w * h;
        return answer;
    }
}
