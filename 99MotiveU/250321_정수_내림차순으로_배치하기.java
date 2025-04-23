import java.util.Arrays;
class Solution {
    public long solution(long n) {
        long answer = 0;
        String str = Long.toString(n);
        char[] arr = str.toCharArray();
        Arrays.sort(arr);
        String result = "";
        for(int i = arr.length-1; i>=0; i--){
            result += arr[i];
        }
        answer = Long.parseLong(result);
        return answer;
    }
}
