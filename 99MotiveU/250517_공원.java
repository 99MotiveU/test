class Solution {
    public int solution(int[] mats, String[][] park) {
        int answer = 0;
        java.util.Arrays.sort(mats);
        
        for (int k = mats.length - 1; k >= 0; k--) {
            int size = mats[k];

            for (int i = 0; i <= park.length - size; i++) {
                for (int j = 0; j <= park[0].length - size; j++) {
                    boolean canPlace = true;

                    for (int x = 0; x < size; x++) {
                        for (int y = 0; y < size; y++) {
                            
                            if (!park[i + x][j + y].equals("-1")) {
                                canPlace = false;
                                break;
                            }
                        }
                        if (!canPlace) break;
                    }
                    if (canPlace) return size; 
                }
            }
        }
        answer = answer -1;
        return answer;
    }
}
