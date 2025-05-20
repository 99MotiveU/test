class Solution {
    public int[] solution(String[] park, String[] routes) {
        int[] answer = {};
        answer = new int[2];

        int h = park.length;
        int w = park[0].length();
        char[][] map = new char[h][w];

        for (int i = 0; i < h; i++) {
            map[i] = park[i].toCharArray();
            if (park[i].contains("S")) {
                answer[0] = i;
                answer[1] = park[i].indexOf('S');
            }
        }

        for (String route : routes) {
            String[] split = route.split(" ");
            String dir = split[0];
            int dist = Integer.parseInt(split[1]);

            int dy = 0, dx = 0;
            if (dir.equals("N")) dy = -1;
            else if (dir.equals("S")) dy = 1;
            else if (dir.equals("W")) dx = -1;
            else if (dir.equals("E")) dx = 1;

            boolean canMove = true;
            int ny = answer[0], nx = answer[1];

            for (int i = 0; i < dist; i++) {
                ny += dy;
                nx += dx;

                if (ny < 0 || ny >= h || nx < 0 || nx >= w || map[ny][nx] == 'X') {
                    canMove = false;
                    break;
                }
            }

            if (canMove) {
                answer[0] = ny;
                answer[1] = nx;
            }
        }
        return answer;
    }
}
