class Solution {
    public int solution(int[] schedules, int[][] timelogs, int startday) {
        int answer = 0;
        for (int i = 0; i < schedules.length; i++) {
            boolean ok = true;
            for (int j = 0; j < 7; j++) {
                int d = (startday - 1 + j) % 7 + 1;
                if (d == 6 || d == 7) continue;
                int h = schedules[i] / 100, m = schedules[i] % 100 + 10;
                int limit = (m >= 60 ? (h + 1) * 100 + (m - 60) : h * 100 + m);
                if (timelogs[i][j] > limit) {
                    ok = false;
                    break;
                }
            }
            if (ok) answer++;
        }
        return answer;
    }
}
