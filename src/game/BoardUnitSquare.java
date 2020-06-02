package game;

public class BoardUnitSquare {
    private Chessman currentChessman;
    private int[] position;
    private game.BoardUnitSquare[][] board;

    public BoardUnitSquare(int posX, int posY, game.BoardUnitSquare[][] board) {
        this.position = new int[2];
        this.position[0] = posX;
        this.position[1] = posY;
        this.board = board;
    }

    @Override
    public String toString() {
        return (position[0] + 1) + "," + (position[1] + 1);
    }

    public int[] getPosition() {
        return position;
    }

    public void setCurrentChessman(Chessman currentChessman) {
        this.currentChessman = currentChessman;
    }


    public Chessman getCurrentChessman() {
        return currentChessman;
    }

    public game.Player getChessmanPlayer() {
        if (this.getCurrentChessman() == null)
            return null;
        return this.currentChessman.getOwner();
    }

    public game.BoardUnitSquare getSquareRightRow(int count) { // negative number for back
        if (this.position[1] + count > 7 || this.position[1] + count < 0)
            return null;
        return board[this.position[0]][this.position[1] + count];
    }

    public game.BoardUnitSquare getSquareFrontRow(int count) { // negative number for back
        if (this.position[0] + count > 7 || this.position[0] + count < 0)
            return null;
        return board[this.position[0] + count][this.position[1]];
    }

    public game.BoardUnitSquare getSquareVerticalUpRight(int count) { // negative number for down-left
        return this.getSquareFrontRow(count).getSquareRightRow(count);
    }

    public game.BoardUnitSquare getSquareVerticalUpLeft(int count) { // negative number for down-right
        return this.getSquareFrontRow(count).getSquareRightRow(-count);
    }
}
