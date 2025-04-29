class Solution {
    public int[] solution(String[] keymap, String[] targets) {
        int[] answer = {};
        /*
        idx별로 문자열의 문자 하나하나 비교(keymap과 targets // keymap의 모든 idx로 target 하나의 idx),
        같을때 idx번호+1 = 횟수 -> cnt 로 합산
        target 없을때 [0] = -1;
        횟수 배열 반환
        */
        answer = new int[targets.length]; // targets 개수만큼 크기 재할당

        for (int i = 0; i < targets.length; i++) {
            String target = targets[i];
            int count = 0;
            boolean possible = true;

            for (int j = 0; j < target.length(); j++) {
                char c = target.charAt(j);
                int minPress = Integer.MAX_VALUE;

                for (int k = 0; k < keymap.length; k++) {
                    int idx = keymap[k].indexOf(c);
                    if (idx != -1) {
                        minPress = Math.min(minPress, idx + 1);
                    }
                }

                if (minPress == Integer.MAX_VALUE) {
                    possible = false;
                    break;
                } else {
                    count += minPress;
                }
            }

            answer[i] = possible ? count : -1;
        }

        
        return answer;
    }
}