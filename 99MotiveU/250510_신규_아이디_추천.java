class Solution {
    public String solution(String new_id) {
        String answer = "";
        answer = new_id;
        
        answer = answer.toLowerCase();
        
        answer = answer.replaceAll("[^a-z0-9-_.]", "");

        answer = answer.replaceAll("\\.{2,}", ".");
        
        answer = answer.replaceAll("^\\.|\\.$", "");
        
        if (answer.isEmpty()) {
            answer = "a";
        }
        
        if (answer.length() > 15) {
            answer = answer.substring(0, 15);
            answer = answer.replaceAll("\\.$", "");
        }
        
        while (answer.length() < 3) {
            answer += answer.charAt(answer.length() - 1);
        }
        
        return answer;
    }
}

//문제 설명에 적힌 1-7단계를 코드로 그대로 구현했습니다.