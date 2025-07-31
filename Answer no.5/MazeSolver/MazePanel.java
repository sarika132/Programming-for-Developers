import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MazePanel extends JPanel {
    private final Cell[][] maze;
    private final List<Cell> path;
    private final int cellSize = 25;

    public MazePanel(Cell[][] maze, List<Cell> path) {
        this.maze = maze;
        this.path = path;
        setPreferredSize(new Dimension(maze[0].length * cellSize, maze.length * cellSize));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (int r = 0; r < maze.length; r++) {
            for (int c = 0; c < maze[0].length; c++) {
                int x = c * cellSize;
                int y = r * cellSize;
                Cell cell = maze[r][c];

                g.setColor(Color.BLACK);
                if (cell.topWall) g.drawLine(x, y, x + cellSize, y);
                if (cell.leftWall) g.drawLine(x, y, x, y + cellSize);
                if (cell.bottomWall) g.drawLine(x, y + cellSize, x + cellSize, y + cellSize);
                if (cell.rightWall) g.drawLine(x + cellSize, y, x + cellSize, y + cellSize);
            }
        }

        // draw path
        g.setColor(Color.GREEN);
        for (Cell cell : path) {
            g.fillRect(cell.col * cellSize + 5, cell.row * cellSize + 5, cellSize - 10, cellSize - 10);
        }
    }
}

    

