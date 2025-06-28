class Solution {
    public int solution(int[] arr) {
        int answer = 0;

        int lcm = arr[0];
        for (int i = 1; i < arr.length; i++) {
            int a = lcm;
            int b = arr[i];

            while (b != 0) {
                int temp = a % b;
                a = b;
                b = temp;
            }
            lcm = lcm * arr[i] / a;
        }

        answer = lcm;
        return answer;
    }
}
