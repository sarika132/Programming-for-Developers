import javax.swing.*;
import java.util.List;

public class MazeSolverApp {
    public static void main(String[] args) {
        int rows = 10;
        int cols = 10;

        MazeGenerator generator = new MazeGenerator(rows, cols);
        Cell[][] maze = generator.generate();

        // Reset visited before solving
        for (Cell[] row : maze)
            for (Cell cell : row)
                cell.visited = false;

        Cell start = maze[0][0];
        Cell end = maze[rows - 1][cols - 1];

        List<Cell> path = MazeSolver.solveDFS(maze, start, end);

        JFrame frame = new JFrame("Maze Solver");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new MazePanel(maze, path));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
