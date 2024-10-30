// THIS IS QUESTION 2.2

public class MissingNumber {
    public static int findMissingNumber(int[] nums) {
        int n = nums.length;
        int expectedSum = (n + 1) * (n + 2) / 2;
        int actualSum = 0;

        for (int num : nums) {
            actualSum += num;
        }

        return expectedSum - actualSum;
    }

    public static void main(String[] args) {
        int[] nums = {3, 7, 1, 2, 6, 4};
        int missingNumber = findMissingNumber(nums);
        System.out.println("The missing number is: " + missingNumber);
    }
}