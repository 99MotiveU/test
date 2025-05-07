import java.util.Arrays;

class Solution {
    
    //필드명-인덱스 매핑 메서드
    private int getIndex(String field) {
        if (field.equals("code")) {return 0;}
        if (field.equals("date")) {return 1;}
        if (field.equals("maximum")) {return 2;}
        return 3;
    }
    
    public int[][] solution(int[][] data, String ext, int val_ext, String sort_by) {
        int[][] answer = {};
        
        int extIdx = getIndex(ext);
        int sortIdx = getIndex(sort_by);

        // 조건에 맞는 데이터만 세기
        int count = 0;
        for (int i = 0; i < data.length; i++) {
            if (data[i][extIdx] < val_ext) count++;
        }

        // 조건에 맞는 데이터만 복사
        int[][] filtered = new int[count][4];
        int idx = 0;
        for (int i = 0; i < data.length; i++) {
            if (data[i][extIdx] < val_ext) {
                filtered[idx++] = data[i];
            }
        }

        for (int i = 0; i < filtered.length - 1; i++) {
            for (int j = i + 1; j < filtered.length; j++) {
                if (filtered[i][sortIdx] > filtered[j][sortIdx]) {
                    int[] temp = filtered[i];
                    filtered[i] = filtered[j];
                    filtered[j] = temp;
                }
            }
        }
        answer = filtered;
        return answer;
    }
}
