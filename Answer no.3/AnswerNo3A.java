public class AnswerNo3A {

    // Given:
    //Two strings: p1 and p2
    //Two integers: t1 and t2
    // P1 rapeat t1 times to make a long sequence A.
    // p2 repeat t2 times to make another sequence B.

    //This method calculates how many full repetitions of (p2 * t2)can be extracted as a subsequence from (p1 * t1).
    
    public static int getMaxRepetitions(String p1, int t1, String p2, int t2) {
        // Step 1: Build full string from p1 repeated t1 times
        StringBuilder seqA = new StringBuilder();
        for (int i = 0; i < t1; i++) {
            seqA.append(p1);
        }
        String fullSeqA = seqA.toString();

        // Step 2: Try to find as many subsequences of p2 in fullSeqA
        int countP2 = 0;
        int indexA = 0;  // Where we are in seqA

        while (indexA < fullSeqA.length()) {
            int indexP2 = 0;  // Where we are in p2

            // Try to match full p2 once
            for (int i = indexA; i < fullSeqA.length(); i++) {
                if (fullSeqA.charAt(i) == p2.charAt(indexP2)) {
                    indexP2++;
                    if (indexP2 == p2.length()) {
                        // Found full p2 once
                        countP2++;
                        indexA = i + 1;
                        break;
                    }
                }
            }

            // If we couldn't complete p2 in remaining seqA, stop
            if (indexP2 != p2.length()) {
                break;
            }
        }

        // Step 3: Return how many times p2 × t2 can fit in countP2
        return countP2 / t2;
    }

    public static void main(String[] args) {
        String p1 = "bca";
        int t1 = 6;
        String p2 = "ba";

        int t2_1 = 1;
        int result1 = getMaxRepetitions(p1, t1, p2, t2_1);
        System.out.println("For t2 = " + t2_1 + ", Result: " + result1);  //output : 6

        int t2_2 = 3;
        int result2 = getMaxRepetitions(p1, t1, p2, t2_2);
        System.out.println("For t2 = " + t2_2 + ", Result: " + result2); //output : 2
    }
}

//The final result tells us the maximum number of full p2 * t2 sequences that can be extracted from p1 * t1.