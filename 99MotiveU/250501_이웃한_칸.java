class Solution {
    public int solution(String[][] board, int h, int w) {
        int answer = 0;               
        int n = board.length;         
        
        int[] hd = {0, 1, -1, 0};     
        int[] wh = {1, 0, 0, -1};     

        for (int i = 0; i < 4; i++) {
            int hcheck = h + hd[i];
            int wcheck = w + wh[i];

            if (hcheck >= 0 && hcheck < n && wcheck >= 0 && wcheck < n) {
                if (board[h][w].equals(board[hcheck][wcheck])) {
                    answer++;
                }
            }
        }
        return answer;
    }
}
