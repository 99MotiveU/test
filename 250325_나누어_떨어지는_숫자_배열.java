import java.util.Arrays;

class Solution throws ArrayIndexOutOfBoundsException {
    public int[] solution(int[] arr1, int divisor) {
        int[] arr2 = new int[arr1.length];
        Arrays.sort(arr1);
        int j =0;
        for(int i = 0; i<arr1.length; i++){
            if(arr1[i] % divisor ==0){
                 arr2[j] = arr1[i]; 
                j++;
            }
        }
        if(j==0){
            arr2[j] = -1;
            j++;
        }
        int[] answer = Arrays.copyOf(arr2, j);
        return answer;
    }
}
