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
