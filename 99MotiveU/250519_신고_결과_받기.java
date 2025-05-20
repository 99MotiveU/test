class Solution {
    public int[] solution(String[] id_list, String[] report, int k) {
        int[] answer = {};
        int n = id_list.length;
        answer = new int[n]; 
        int[][] reportMatrix = new int[n][n];
        int[] reportCount = new int[n];

        for (String r : report) {
            String[] parts = r.split(" ");
            String fromUser = parts[0];
            String toUser = parts[1];

            int fromIdx = indexOf(id_list, fromUser);
            int toIdx = indexOf(id_list, toUser);

            if (reportMatrix[fromIdx][toIdx] == 0) {
                reportMatrix[fromIdx][toIdx] = 1;
                reportCount[toIdx]++;
            }
        }

        for (int reported = 0; reported < n; reported++) {
            if (reportCount[reported] >= k) {
                for (int reporter = 0; reporter < n; reporter++) {
                    if (reportMatrix[reporter][reported] == 1) {
                        answer[reporter]++;
                    }
                }
            }
        }
        return answer;
    }

    private int indexOf(String[] arr, String target) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i].equals(target)) return i;
        }
        return -1;
    }
}
