import java.util.*;

public class AnswerNo1A {

    //  Calculates the maximum capital by completing up to k most profitable projects
    //  that are affordable with current capital.
    //  k     Maximum number of projects to complete
    //  c     Initial capital
    //  revenues      Array of profits from each project
    //  investments    Array of required capital for each project
  
    public static int maximizeCapital(int k, int c, int[] revenues, int[] investments) {
        int n = revenues.length;

        // Store all projects as (investment, revenue) pairs
        List<int[]> projects = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            projects.add(new int[]{investments[i], revenues[i]});
        }

        // Sort projects by required investment (ascending)
        projects.sort(Comparator.comparingInt(a -> a[0]));

        // Max-heap to store the most profitable affordable projects
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());

        // To track selected project revenues for display
        List<Integer> selectedRevenues = new ArrayList<>();

        int i = 0;

        while (k-- > 0) {
            // To add all projects we can afford right now to the max-heap
            while (i < n && projects.get(i)[0] <= c) {
                maxHeap.offer(projects.get(i)[1]);
                i++;
            }

            // If no projects are affordable, stop
            if (maxHeap.isEmpty()) break;

            // Choose the most profitable project and increase capital
            int profit = maxHeap.poll();
            c += profit;
            selectedRevenues.add(profit); // For Tracking selected project
        }

        System.out.println("Selected project revenues (in order): " + selectedRevenues);

        return c;
    }

    public static void main(String[] args) {
        // Example 1
        int[] rev1 = {2, 5, 8};
        int[] inv1 = {0, 2, 3};
        System.out.println("Example 1 Final Capital: " + maximizeCapital(2, 0, rev1, inv1));
        // Expected: Final Capital: 7

        // Example 2
        int[] rev2 = {3, 6, 10};
        int[] inv2 = {1, 3, 5};
        System.out.println("Example 2 Final Capital: " + maximizeCapital(3, 1, rev2, inv2));
        // Expected: Final Capital: 20
    }
}
