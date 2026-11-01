class Solution {
    public int minMoves(int[] nums) {
        int min = nums[0];
        long sum = 0; // Using long to prevent integer overflow during summation

        // Find the minimum value and calculate the total sum
        for (int num : nums) {
            sum += num;
            if (num < min) {
                min = num;
            }
        }

        // Apply the formula: Total Sum - (n * min)
        return (int) (sum - (long) min * nums.length);
    }
}