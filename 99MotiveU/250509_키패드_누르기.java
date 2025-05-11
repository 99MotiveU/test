class Solution {
    public String solution(int[] numbers, String hand) {
        String answer = "";

        int[][] pos = { 
            {3, 1}, 
            {0, 0}, {0, 1}, {0, 2},
            {1, 0}, {1, 1}, {1, 2},
            {2, 0}, {2, 1}, {2, 2} 
        };

        int[] left = {3, 0};  
        int[] right = {3, 2};  

        StringBuilder sb = new StringBuilder();

        for (int num : numbers) {
            int[] target = pos[num];

            if (num == 1 || num == 4 || num == 7) {
                sb.append("L");
                left = target;
            } else if (num == 3 || num == 6 || num == 9) {
                sb.append("R");
                right = target;
            } else {
                int leftDist = Math.abs(left[0] - target[0]) + Math.abs(left[1] - target[1]);
                int rightDist = Math.abs(right[0] - target[0]) + Math.abs(right[1] - target[1]);

                if (leftDist < rightDist) {
                    sb.append("L");
                    left = target;
                } else if (leftDist > rightDist) {
                    sb.append("R");
                    right = target;
                } else {
                    if (hand.equals("right")) {
                        sb.append("R");
                        right = target;
                    } else {
                        sb.append("L");
                        left = target;
                    }
                }
            }
        }

        answer = sb.toString();
        return answer;
    }
}
// 각 숫자의 키패드 위치를 좌표처럼 설정하고 (1,4,7)은 왼손, (3,6,9)는 오른손으로 고정한후 (2,5,8,0)은 왼손과 오른손의 현재 위치를 추적하면서 어떤 손이 더 가까운지 판단하며, 거리가 같을 경우 주손을 사용하도록하고 StringBuilder로 결과를 조립하여 answer를 return 했습니다.