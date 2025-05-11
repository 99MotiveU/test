class Solution {
    public int[] solution(String[] wallpaper) {
        int[] answer = {}; 

        int lux = Integer.MAX_VALUE, luy = Integer.MAX_VALUE;
        int rdx = Integer.MIN_VALUE, rdy = Integer.MIN_VALUE;

        for (int i = 0; i < wallpaper.length; i++) {
            for (int j = 0; j < wallpaper[i].length(); j++) {
                if (wallpaper[i].charAt(j) == '#') {
                    lux = Math.min(lux, i);
                    luy = Math.min(luy, j);
                    rdx = Math.max(rdx, i);
                    rdy = Math.max(rdy, j);
                }
            }
        }

        answer = new int[] { lux, luy, rdx + 1, rdy + 1 };
        return answer;
    }
}
//문제 설명에 적힌 내용을 그대로 적용해 풀이했습니다.