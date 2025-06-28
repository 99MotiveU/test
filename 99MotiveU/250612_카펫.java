class Solution {
    public int[] solution(int brown, int yellow) {
        int[] answer = {};
        int total = brown + yellow;

        for (int height = 3; height <= total; height++) {
            
            if (total % height != 0) {
                continue;
            }
            int width = total / height;

            if (width < height) {
                continue;
            }

            int inWidth = width - 2;
            int inHeight = height - 2;
            int yellowArea = inWidth * inHeight;

            if (yellowArea == yellow) {
                answer = new int[]{width, height};
                break;
            }
        }

        return answer;
    }
}
