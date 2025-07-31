public class AnswerNo3B {

    public static int maxMagicalPower(String M) {
        int n = M.length();
        // Arrays to store maximum odd-length palindrome ending at or before i, and starting at or after i
        int[] leftMax = new int[n];
        int[] rightMax = new int[n];

        // Step 1: Find all odd-length palindromes centered at each character
        for (int center = 0; center < n; center++) {
            int l = center, r = center;

            // Expand around the center while characters match
            while (l >= 0 && r < n && M.charAt(l) == M.charAt(r)) {
                int len = r - l + 1;

                // Only consider odd-length palindromes
                if (len % 2 == 1) {
                    leftMax[r] = Math.max(leftMax[r], len);
                    rightMax[l] = Math.max(rightMax[l], len);
                }
                l--; r++;
            }
        }

        // Step 2: Convert to prefix max
        for (int i = 1; i < n; i++) {
            leftMax[i] = Math.max(leftMax[i], leftMax[i - 1]);
        }

        // Step 3: Convert to suffix max
        for (int i = n - 2; i >= 0; i--) {
            rightMax[i] = Math.max(rightMax[i], rightMax[i + 1]);
        }

        // Step 4: Try every split and take max product
        int maxProduct = 0;
        for (int i = 0; i < n - 1; i++) {
            maxProduct = Math.max(maxProduct, leftMax[i] * rightMax[i + 1]);
        }

        return maxProduct;
    }

    public static void main(String[] args) {
        System.out.println("Output 1: " + maxMagicalPower("xyzyxabc"));           // Expected Output: 5
        System.out.println("Output 2: " + maxMagicalPower("levelwowracecar"));    // Expected Output: 35
    }
}
