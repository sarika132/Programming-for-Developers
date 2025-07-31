import java.util.*;

public class SecureTransmission {
    // Adjacency list representation of the graph
    // Each node maps to a list of int[] {neighbor, strength}
    private List<List<int[]>> graph;

     // Constructor to build the graph from number of nodes and links
    public SecureTransmission(int n, int[][] links) {
        graph = new ArrayList<>();

        // Initialize empty adjacency lists for each node
        for (int i = 0; i < n; i++) graph.add(new ArrayList<>());

        // Add each link as bidirectional edge in the graph
        for (int[] link : links) {
            int u = link[0], v = link[1], strength = link[2];
            graph.get(u).add(new int[]{v, strength});
            graph.get(v).add(new int[]{u, strength}); // Undirected
        }
    }

    
     //Check if secure transmission is possible from sender to receiver
     //using only links with strength less than maxStrength.
     //Uses Depth-First Search (DFS).
    public boolean canTransmit(int sender, int receiver, int maxStrength) {
        boolean[] visited = new boolean[graph.size()];
        return dfs(sender, receiver, visited, maxStrength);
    }

    // Helper DFS method to search for path meeting strength constraint
    private boolean dfs(int current, int target, boolean[] visited, int maxStrength) {
        if (current == target) return true;
        visited[current] = true;

        // Explore all neighbors of current node
        for (int[] neighbor : graph.get(current)) {
            int next = neighbor[0], strength = neighbor[1];

            // Visit next node only if not visited and link strength is less than maxStrength
            if (!visited[next] && strength < maxStrength) {
                if (dfs(next, target, visited, maxStrength)) return true;
            }
        }
        return false;
    }

    // Sample main method to test
    public static void main(String[] args) {
        int[][] links = {
            {0, 2, 4}, {2, 3, 1}, {3,0,2}, {4, 5, 5}
        };
        SecureTransmission st = new SecureTransmission(6, links);
        System.out.println(st.canTransmit(2, 3, 2)); // true
        System.out.println(st.canTransmit(1, 3, 3)); // false
        System.out.println(st.canTransmit(3, 0, 2)); // false
        System.out.println(st.canTransmit(0, 5, 6)); // false
    }
}
    
