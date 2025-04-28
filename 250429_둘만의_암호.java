class Solution {
    public String solution(String s, String skip, int index) {
        String answer = "";
        
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i); // s의 한 글자 꺼내기
            int count = 0;
            
            while (count < index) { // index만큼 이동할 때까지
                ch++;
                if (ch > 'z') { // z를 넘으면 a로
                    ch = 'a';
                }
                if (skip.indexOf(ch) == -1) { // skip에 없으면 count 증가
                    count++;
                }
            }
            answer += ch; // 변환된 문자 answer에 추가
        }
        return answer;
    }
}
