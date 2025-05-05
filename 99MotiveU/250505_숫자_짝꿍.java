import java.util.*;

class Solution {
    public String solution(String X, String Y) {
        String answer = "";
        StringBuilder sb = new StringBuilder();

        for (char ch = '9'; ch >= '0'; ch--) {
            int countX = countChar(X, ch);
            int countY = countChar(Y, ch);

            int min = Math.min(countX, countY);
            for (int i = 0; i < min; i++) {
                sb.append(ch);
            }
        }

        if (sb.length() == 0) {
            answer = "-1";
        } else if (sb.charAt(0) == '0') {
            answer = "0";
        } else {
            answer = sb.toString();
        }
        return answer;
    }
// 특정 문자가 문자열에 몇 번 나오는지 세는 메서드
    private int countChar(String s, char target) {
        int count = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == target) count++;
        }
        return count;
    }
}
