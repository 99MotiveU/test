import java.util.Stack;

class Solution {
    public int solution(int[][] board, int[] moves) {
        int answer = 0;
        Stack<Integer> basket = new Stack<>();

        int n = board.length;

        for (int move : moves) {
            int column = move - 1; 
            for (int row = 0; row < n; row++) {
                int doll = board[row][column];
                if (doll != 0) {
                    board[row][column] = 0;
                    if (!basket.isEmpty() && basket.peek() == doll) {
                        basket.pop();
                        answer += 2;
                    } else {
                        basket.push(doll);
                    }
                    break;
                }
            }
        }
        return answer;
    }
}
