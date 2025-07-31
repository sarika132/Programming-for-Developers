public class AnswerNo1B {

    public static int strongPinChanges(String pin) {
        int n = pin.length();
        boolean hasLower = false, hasUpper = false, hasDigit = false;

        //Ensuring the PIN contains:
        //at least one lowercase
        //at least one uppercase
        //at least one digit

        // Check for required character types
        for (char ch : pin.toCharArray()) {
            if (Character.isLowerCase(ch)) hasLower = true;
            else if (Character.isUpperCase(ch)) hasUpper = true;
            else if (Character.isDigit(ch)) hasDigit = true;
        }

        // Count how many character types are missing
        int missingTypes = 0;
        if (!hasLower) missingTypes++;
        if (!hasUpper) missingTypes++;
        if (!hasDigit) missingTypes++;

        // Count repeating characters (3 or more in a row)
        int replacements = 0;
        int[] repeatBuckets = new int[3]; // For optimizing deletes in long PINs

        for (int i = 2; i < n;) {
            if (pin.charAt(i) == pin.charAt(i - 1) && pin.charAt(i - 1) == pin.charAt(i - 2)) {
                int len = 2;
                while (i < n && pin.charAt(i) == pin.charAt(i - 1)) {
                    len++;
                    i++;
                }
                replacements += len / 3;
                repeatBuckets[len % 3]++;
            } else {
                i++;
            }
        }

        // If PIN is too short
        if (n < 6) {
            return Math.max(6 - n, missingTypes);
        }

        // If PIN is acceptable length
        if (n <= 20) {
            return Math.max(replacements, missingTypes);
        }

        // If PIN is too long
        int deletes = n - 20;
        int remainingDeletes = deletes;

        // Use deletions to reduce repeating character replacements
        for (int i = 0; i < 3; i++) {
            while (remainingDeletes > 0 && repeatBuckets[i] > 0) {
                int toDelete = i + 1;
                remainingDeletes -= toDelete;
                repeatBuckets[i]--;
                replacements--; // one less replacement needed
            }
        }

        // If still have extra deletes, remove them directly from remaining sequences
        replacements -= remainingDeletes / 3;

        // Final result = deletes + max(replacements, missing character types)
        return deletes + Math.max(replacements, missingTypes);
    }

    public static void main(String[] args) {
        System.out.println("Example 1 Output: " + strongPinChanges("X1!"));      // 3
        System.out.println("Example 2 Output: " + strongPinChanges("123456"));   // 2
        System.out.println("Example 3 Output: " + strongPinChanges("Aa1234!"));  // 0
        System.out.println("Example 4 Output: " + strongPinChanges("aaaaaaaAAAAAAA1111111")); // 8
    }
}
