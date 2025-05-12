import java.util.*;

class Solution {
    public int[] solution(String today, String[] terms, String[] privacies) {
        int[] answer = {};

        Map<String, Integer> termMap = new HashMap<>();
        for (String term : terms) {
            String[] parts = term.split(" ");
            termMap.put(parts[0], Integer.parseInt(parts[1]));
        }

        int todayDays = convertToDays(today);

        List<Integer> result = new ArrayList<>();

        for (int i = 0; i < privacies.length; i++) {
            String[] parts = privacies[i].split(" ");
            String date = parts[0];
            String type = parts[1];
            int collectedDays = convertToDays(date);
            int validMonths = termMap.get(type);
            int expireDays = collectedDays + (validMonths * 28) - 1;

            if (expireDays < todayDays) {
                result.add(i + 1);
            }
        }
        
        answer = new int[result.size()];
        for (int i = 0; i < result.size(); i++) {
            answer[i] = result.get(i);
        }

        return answer;
    }

    // 날짜를 총 일수로 변환하는 함수
    private int convertToDays(String date) {
        String[] parts = date.split("\\.");
        int year = Integer.parseInt(parts[0]);
        int month = Integer.parseInt(parts[1]);
        int day = Integer.parseInt(parts[2]);

        return (year * 12 * 28) + (month * 28) + day;
    }
}

//개인정보 수집 유효기간은 약관별 유효기간을 Map에 저장한 뒤, 각 개인정보 수집일에 유효기간을 더해 만료일을 계산하고, 오늘 날짜와 비교하여 만료된 개인정보의 번호를 리스트에 담아 배열로 변환해 해결했습니다.