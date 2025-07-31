public class AnswerNo2A {

    // Counts the number of continuous periods where the total temperature change
    // falls within the range [lowThreshold, highThreshold].
    public static int countValidPeriods(int[] temperatureChanges, int lowThreshold, int highThreshold) {
        int count = 0;

        // Outer loop to select the start index of the period
        for (int start = 0; start < temperatureChanges.length; start++) {
            int sum = 0;

            // Inner loop to extend the period from start to end
            for (int end = start; end < temperatureChanges.length; end++) {
                sum += temperatureChanges[end];

                // Check if sum falls within the anomaly range
                if (sum >= lowThreshold && sum <= highThreshold) {
                    count++;    // Valid period found, increment count
                }
            }
        }

        return count;    // Return total number of valid periods found
    } 

    public static void main(String[] args) {
        int[] changes1 = {3, -1, -4, 6, 2};
        int low1 = 2, high1 = 5;

        int[] changes2 = {-2, 3, 1, -5, 4};
        int low2 = -1, high2 = 2;

        int result1 = countValidPeriods(changes1, low1, high1);
        int result2 = countValidPeriods(changes2, low2, high2);

        System.out.println("Example 1 Output: " + result1); // Expected : 7
        System.out.println("Example 2 Output: " + result2); // Expected: 7
    }
}

//After adding each day's change, check if the sum is within the required range.If yes, increment the count.
//After checking all periods, the method returns the total count of valid periods.