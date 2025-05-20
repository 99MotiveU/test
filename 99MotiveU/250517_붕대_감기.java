class Solution {
    public int solution(int[] bandage, int health, int[][] attacks) {
        int answer = 0;

        int t = bandage[0];    
        int x = bandage[1]; 
        int y = bandage[2];

        int maxHealth = health;
        int time, healCount = 0;
        int attackIdx = 0;
        
        for (int i = 1; i <= attacks[attacks.length - 1][0]; i++) {
            if (attackIdx < attacks.length && attacks[attackIdx][0] == i) {
                health -= attacks[attackIdx][1];
                if (health <= 0) {
                    answer = -1;
                    return answer;
                }
                healCount = 0;
                attackIdx++;
            } else {
                healCount++;
                health += x;

                if (healCount == t) {
                    health += y;
                    healCount = 0;
                }

                if (health > maxHealth) {
                    health = maxHealth;
                }
            }
        }
        answer = health;
        return answer;
    }
}
