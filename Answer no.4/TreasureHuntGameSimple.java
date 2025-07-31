import java.util.*;

public class TreasureHuntGameSimple {
    static final int DRAW = 0;
    static final int PLAYER1_WIN = 1;
    static final int PLAYER2_WIN = 2;

    private int[][] graph;
    private int[][][] memo;
    private Set<String> visited;

    public TreasureHuntGameSimple(int[][] graph) {
        this.graph = graph;
        int n = graph.length;
        this.memo = new int[n][n][2];  // memo[p1][p2][turn]
        this.visited = new HashSet<>();
    }

    public int playGame(int p1, int p2) {
        return dfs(p1, p2, 0);
    }

    private int dfs(int p1, int p2, int turn) {
        // Base cases
        if (p1 == 0) return PLAYER1_WIN;     // Player 1 reaches treasure
        if (p1 == p2) return PLAYER2_WIN;    // Player 2 catches Player 1

        String state = p1 + "," + p2 + "," + turn;

        // Cycle detection: if we see this state again, it's a draw
        if (visited.contains(state)) return DRAW;

        // Return cached result if available
        if (memo[p1][p2][turn] != 0) return memo[p1][p2][turn];

        visited.add(state);

        int result;
        if (turn == 0) {
            // Player 1's turn - try all moves
            result = PLAYER2_WIN; // Assume worst case (opponent wins)
            for (int next : graph[p1]) {
                int res = dfs(next, p2, 1);
                if (res == PLAYER1_WIN) {
                    result = PLAYER1_WIN; // Found winning move
                    break;
                }
                if (res == DRAW && result != PLAYER1_WIN) {
                    result = DRAW; // Draw if no winning move found yet
                }
            }
        } else {
            // Player 2's turn - try all moves except treasure (node 0)
            result = PLAYER1_WIN; // Assume worst case (opponent wins)
            for (int next : graph[p2]) {
                if (next == 0) continue; // Player 2 can't go to treasure
                int res = dfs(p1, next, 0);
                if (res == PLAYER2_WIN) {
                    result = PLAYER2_WIN; // Found winning move
                    break;
                }
                if (res == DRAW && result != PLAYER2_WIN) {
                    result = DRAW; // Draw if no winning move found yet
                }
            }
        }

        visited.remove(state);
        memo[p1][p2][turn] = result; // Cache result
        return result;
    }

    public static void main(String[] args) {
        int[][] graph = {
            {2, 5},      // Node 0 (treasure)
            {3},         // Node 1 (Player 1 start)
            {0, 4, 5},   // Node 2 (Player 2 start)
            {1, 4, 5},   // Node 3
            {2, 3},      // Node 4
            {0, 2, 3}    // Node 5
        };

        TreasureHuntGameSimple game = new TreasureHuntGameSimple(graph);

        int result = game.playGame(1, 2);
        System.out.println("Game Result: " + result);
        // Output meanings: 0=Draw, 1=Player1 wins, 2=Player2 wins
    }
}
