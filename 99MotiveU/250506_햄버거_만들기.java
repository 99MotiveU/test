import java.util.Stack;

class Solution {
    public int solution(int[] ingredient) {
        int answer = 0;
        Stack<Integer> stack = new Stack<>();

        for (int i : ingredient) {
            stack.push(i);
            
            if (stack.size() >= 4 &&
                stack.get(stack.size() - 4) == 1 &&
                stack.get(stack.size() - 3) == 2 &&
                stack.get(stack.size() - 2) == 3 &&
                stack.get(stack.size() - 1) == 1) {

                for (int j = 0; j < 4; j++) {
                    stack.pop();
                }
                answer++;
            }
        }
        return answer;
    }
}
//스택에 재료를 쌓고, [1, 2, 3, 1] 패턴이 있을 때 햄버거를 포장해 카운트하고, pop()을 사용하여 포장된 재료를 제거하는 식으로 해결