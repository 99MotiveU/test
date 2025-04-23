class Solution {
    public String solution(String s) {
//         String answer = "";
//         int len = s.length();
//         if (len % 2 == 1) {
//             answer = String.valueOf(s.charAt(len / 2)); 
//         } else {
//             answer = s.substring(len / 2 - 1, len / 2 + 1); 
//         }
//         return answer;
        return  (s.length() % 2 == 1) ? String.valueOf(s.charAt(s.length() / 2)) : s.substring(s.length() / 2 - 1, s.length() / 2 + 1);
    }
}
