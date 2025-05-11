import java.util.*;

class Solution {
    public String solution(String[] survey, int[] choices) {
        String answer = "";

        Map<Character, Integer> scoreMap = new HashMap<>();
        for (char type : "RTCFJMAN".toCharArray()) {
            scoreMap.put(type, 0);
        }

        for (int i = 0; i < survey.length; i++) {
            String s = survey[i];
            int choice = choices[i];

            char disagree = s.charAt(0);
            char agree = s.charAt(1);   

            if (choice < 4) {
                scoreMap.put(disagree, scoreMap.get(disagree) + (4 - choice));
            } else if (choice > 4) {
                scoreMap.put(agree, scoreMap.get(agree) + (choice - 4));
            }
        }

        answer += (scoreMap.get('R') >= scoreMap.get('T')) ? 'R' : 'T';
        answer += (scoreMap.get('C') >= scoreMap.get('F')) ? 'C' : 'F';
        answer += (scoreMap.get('J') >= scoreMap.get('M')) ? 'J' : 'M';
        answer += (scoreMap.get('A') >= scoreMap.get('N')) ? 'A' : 'N';

        return answer;
    }
}
//HashMap을 사용해 각 성격 유형(R, T, C, F, J, M, A, N)의 점수를 저장하고, charAt()으로 설문 문자열의 각 문자를 추출해 점수를 계산한 후, 선택지에 따라 동의 또는 비동의 쪽에 점수를 부여하며, 마지막에 각 성격 지표쌍에서 더 높은 점수를 가진 유형을 비교해 결과 문자열을 만드는 식으로 해결했습니다.
