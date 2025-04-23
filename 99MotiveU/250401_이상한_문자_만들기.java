class Solution {
    public String solution(String s) {
        String answer = "";
        StringBuilder sw = new StringBuilder();
        String[] stwords = s.split(" ", -1);
    
        for (int i = 0; i < stwords.length; i++) {
            StringBuilder stword = new StringBuilder();
            for (int j = 0; j < stwords[i].length(); j++) {
                if (j % 2 == 0) {
                    stword.append(Character.toUpperCase(stwords[i].charAt(j))); 
                }else {
                    stword.append(Character.toLowerCase(stwords[i].charAt(j)));
                }
            }
            sw.append(stword);
            if (i < stwords.length - 1) {
                sw.append(" "); 
            }
        }
        answer = sw.toString();
        return answer;
    }
}
