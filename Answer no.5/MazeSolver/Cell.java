public class Cell {
    int row, col;
    boolean visited = false;
    boolean topWall = true, bottomWall = true, leftWall = true, rightWall = true;

    public Cell(int row, int col) {
        this.row = row;
        this.col = col;
    }
}
