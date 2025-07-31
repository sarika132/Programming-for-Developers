import java.util.*;

public class MazeSolver {
    public static List<Cell> solveDFS(Cell[][] maze, Cell start, Cell end) {
        Stack<Cell> stack = new Stack<>();
        Map<Cell, Cell> cameFrom = new HashMap<>();
        List<Cell> path = new ArrayList<>();

        stack.push(start);
        start.visited = true;

        while (!stack.isEmpty()) {
            Cell current = stack.pop();
            if (current == end) break;

            for (Cell neighbor : getNeighbors(maze, current)) {
                if (!neighbor.visited) {
                    neighbor.visited = true;
                    cameFrom.put(neighbor, current);
                    stack.push(neighbor);
                }
            }
        }

        // backtrack
        Cell step = end;
        while (step != null && step != start) {
            path.add(step);
            step = cameFrom.get(step);
        }
        if (step == start) path.add(start);
        Collections.reverse(path);
        return path;
    }

    private static List<Cell> getNeighbors(Cell[][] maze, Cell cell) {
        int r = cell.row, c = cell.col;
        List<Cell> neighbors = new ArrayList<>();

        if (!cell.topWall && r > 0) neighbors.add(maze[r - 1][c]);
        if (!cell.bottomWall && r < maze.length - 1) neighbors.add(maze[r + 1][c]);
        if (!cell.leftWall && c > 0) neighbors.add(maze[r][c - 1]);
        if (!cell.rightWall && c < maze[0].length - 1) neighbors.add(maze[r][c + 1]);

        return neighbors;
    }
}

