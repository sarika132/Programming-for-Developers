import java.util.*;

public class MazeGenerator {
    private final int rows, cols;
    private final Cell[][] maze;
    private final Random rand = new Random();

    public MazeGenerator(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        maze = new Cell[rows][cols];

        for (int r = 0; r < rows; r++)
            for (int c = 0; c < cols; c++)
                maze[r][c] = new Cell(r, c);
    }

    public Cell[][] generate() {
        generateMazeFrom(0, 0);
        return maze;
    }

    private void generateMazeFrom(int r, int c) {
        maze[r][c].visited = true;

        List<int[]> directions = Arrays.asList(
            new int[]{-1, 0}, // Up
            new int[]{1, 0},  // Down
            new int[]{0, -1}, // Left
            new int[]{0, 1}   // Right
        );
        Collections.shuffle(directions, rand);

        for (int[] dir : directions) {
            int newR = r + dir[0];
            int newC = c + dir[1];

            if (newR >= 0 && newR < rows && newC >= 0 && newC < cols && !maze[newR][newC].visited) {
                if (dir[0] == -1) { // Moving up
                    maze[r][c].topWall = false;
                    maze[newR][newC].bottomWall = false;
                } else if (dir[0] == 1) { // Moving down
                    maze[r][c].bottomWall = false;
                    maze[newR][newC].topWall = false;
                } else if (dir[1] == -1) { // Moving left
                    maze[r][c].leftWall = false;
                    maze[newR][newC].rightWall = false;
                } else if (dir[1] == 1) { // Moving right
                    maze[r][c].rightWall = false;
                    maze[newR][newC].leftWall = false;
                }

                generateMazeFrom(newR, newC);
            }
        }
    }
}
