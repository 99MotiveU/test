import java.util.*;

public class Solution {
    public int[] solution(int[] arr) {
        /* 
        int[] answer = {};
        int[] temp = new int[arr.length];
        int idx = 0, cp = -1;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] != cp) {
                temp[idx++] = arr[i];
            }
            cp = arr[i];
        }
        answer = new int[idx];
        System.arraycopy(temp, 0, answer, 0, idx);
	return answer;
        */
        
        /* Stack
        int[] answer = {}; 
        Stack stack = new Stack();  
        int cp = -1;

        for (int i = 0; i < arr.length; i++) {
            if (arr[i] != cp) {
                stack.push(arr[i]);
            }
            cp = arr[i];
        }
        answer = new int[stack.size()];
        for (int i = answer.length - 1; i >= 0; i--) {
            answer[i] = (int) stack.pop();  
        }
	return answer;
        */

        // Queue
        int[] answer = {};
        Queue que = new LinkedList();
        int cp = -1;
        
        for(int i = 0; i<arr.length; i++){
            if(arr[i] !=cp){
                que.add(arr[i]);
            }
            cp = arr[i];
        }
        answer = new int[que.size()];
        int idx = 0;
        while(!que.isEmpty()){
            answer[idx++] = (int)que.poll();
        }
        return answer; 
    }
}
