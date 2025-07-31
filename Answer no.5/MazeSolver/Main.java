public class Main {
    public static void main(String[] args) {
        MazeGenerator generator = new MazeGenerator(5, 5);
        Cell[][] maze = generator.generate();

        // Print maze as visited cells for demo (not graphical)
        for (int r = 0; r < maze.length; r++) {
            for (int c = 0; c < maze[0].length; c++) {
                System.out.print(maze[r][c].visited ? "  " : "X ");
            }
            System.out.println();
        }
    }
}
