import java.util.*;

public class AnswerNo2B {

    public static void main(String[] args) {
        System.out.println("Testing: STAR + MOON = NIGHT");
        solve("STAR", "MOON", "NIGHT");  // Try solving this equation

        System.out.println("\nTesting: CODE + BUG = DEBUG");
        solve("CODE", "BUG", "DEBUG");  // Try solving this equation too
    }

    static void solve(String word1, String word2, String result) {
        /// Step 1: Extract all unique letters from the equation
        Set<Character> uniqueLetters = new HashSet<>();
        for (char c : (word1 + word2 + result).toCharArray()) {
            uniqueLetters.add(c);
        }

        // Step 2: Check if letters exceed digit limit
        if (uniqueLetters.size() > 10) {
            System.out.println("Too many unique letters. Can't assign digits (max 10).");
            return;   // Only digits 0–9, so max 10 unique letters allowed
        }

        // Step 3: Start backtracking 
        List<Character> letters = new ArrayList<>(uniqueLetters);
        boolean[] usedDigits = new boolean[10];
        int[] letterToDigit = new int[letters.size()];

        // Step 4: Start backtracking to try all combinations
        backtrack(0, letters, letterToDigit, usedDigits, word1, word2, result);
    }

    static void backtrack(int index, List<Character> letters, int[] mapping, boolean[] usedDigits,
                          String word1, String word2, String result) {
        // Base case: all letters have a digit
        if (index == letters.size()) {
            Map<Character, Integer> charDigitMap = new HashMap<>();
            for (int i = 0; i < letters.size(); i++) {
                charDigitMap.put(letters.get(i), mapping[i]);
            }

            // No leading zeros
            if (charDigitMap.get(word1.charAt(0)) == 0 || charDigitMap.get(word2.charAt(0)) == 0 || charDigitMap.get(result.charAt(0)) == 0)
                return;

            // Step 5: Convert each word to a number using the mapping
            long num1 = toNumber(word1, charDigitMap);
            long num2 = toNumber(word2, charDigitMap);
            long numRes = toNumber(result, charDigitMap);

            // Step 6: Check if equation holds
            if (num1 + num2 == numRes) {
                System.out.println("Valid Mapping Found:");
                System.out.println(word1 + " = " + num1);
                System.out.println(word2 + " = " + num2);
                System.out.println(result + " = " + numRes);
                System.out.println("Mapping: " + charDigitMap);
                return;
            }
            return;
        }

        // Step 7: Try assigning digits from 0 to 9
        for (int d = 0; d <= 9; d++) {
            if (!usedDigits[d]) {
                usedDigits[d] = true;
                mapping[index] = d;
                backtrack(index + 1, letters, mapping, usedDigits, word1, word2, result);
                usedDigits[d] = false; // backtrack
            }
        }
    }

    // Utility method to convert a word to number using the digit map
    static long toNumber(String word, Map<Character, Integer> map) {
        long value = 0;
        for (char c : word.toCharArray()) {
            value = value * 10 + map.get(c);
        }
        return value;
    }
}
   

// The program start with identifying all unique letters used across the three words. It then generates all
 //possible digit permutations for those letters, ensuring that each letter is mapped to a distinct digit. For 
 //each permutation, the program converts the words into numbers and checks if the arithmetic equation is 
 //satisfied. It also ensures that none of the numbers begin with zero, which would be invalid. If a valid mapping
 // is found, the program prints the numeric values of the words and the corresponding letter-digit mapping.
 // If no valid mapping exists for a puzzle, it simply moves on without printing a result. Overall, 
 //this program demonstrates a systematic way to solve letter-based number puzzles by combining combinatorics and 
 //validation logic.
