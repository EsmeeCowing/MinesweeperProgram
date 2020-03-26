//Esmee Cowing
import java.util.Random;

public class MyMineModel implements MineModel{

    //initializes necessary variables - why are these automatically this.variables?
    private int numRows, numCols, numMines, numFlags;
    private long startTimeMillis;
    private boolean gameStarted, gameOver, playerLife;
    private MyCell[][] cellCoordinates;

    @Override
    public void newGame(int numRows, int numCols, int numMines) {
        //setting starting values for all local variables
        this.gameStarted = true;
        this.gameOver = false;
        this.playerLife = true;
        this.numCols = numCols;
        this.numRows = numRows;
        this.numMines = numMines;
        this.numFlags = 0;
        this.cellCoordinates = new MyCell[this.getNumCols()][this.getNumRows()];
        this.startTimeMillis = (System.currentTimeMillis());

        //putting all cell coordinates into array with cells
        for (int col = 0; col < this.numCols; col += 1) {
            for (int row = 0; row < this.numRows; row += 1) {
                cellCoordinates[col][row] = new MyCell(col, row);
            }
        }

        // making random cells mines
        Random Randomizer = new Random();
        int mineCount = 0;
        while (mineCount < numMines) {
            int cellRow = Randomizer.nextInt(numRows);
            int cellCol = Randomizer.nextInt(numCols);
            if (!this.cellCoordinates[cellCol][cellRow].isMine()) {
                this.cellCoordinates[cellCol][cellRow].makeMine();
                mineCount++;
            }
        }

        //finding neighbour mines for each cell
        for (int col = 0; col < this.numCols; col += 1) {
            for (int row = 0; row < this.numRows; row += 1) {
                int[] colAdds = new int[3], rowAdds = new int[3];
                int neighbourMines = 0, rowAddsCount = 0, colAddsCount = 0;
                colAdds[colAddsCount++] = 0;
                rowAdds[rowAddsCount++] = 0;
                if (row != this.numRows - 1) {
                    rowAdds[rowAddsCount++] = 1;
                }
                if (row != 0) {
                    rowAdds[rowAddsCount++] = -1;
                }
                if (col != this.numCols - 1) {
                    colAdds[colAddsCount++] = 1;
                }
                if (col != 0) {
                    colAdds[colAddsCount++] = -1;
                }
                //counting up the number of neighbor mines for each cell and recording each of its neighbour cells
                for (int i = 0; i < colAddsCount; i++) {
                    for (int i2 = 0; i2 < rowAddsCount; i2++) {
                        cellCoordinates[col][row].appendToNeighborCellsArray(cellCoordinates[col + colAdds[i]][row + rowAdds[i2]]);
                        if (cellCoordinates[col + colAdds[i]][row + rowAdds[i2]].isMine()) {
                            neighbourMines+=1;
                        }
                    }
                }
                //making sure that if the cell itself is a mine, that's not added to its neihgbor mine count
                if (cellCoordinates[col][row].isMine()){
                    neighbourMines -= 1;
                }
                // telling the cell how many neighbor mines it has
                cellCoordinates[col][row].setNumNeighbourMines(neighbourMines);
            }

        }
    }


    @Override
    public int getNumRows() {
        return numRows;
    }

    @Override
    public int getNumCols() {
        return numCols;
    }

    @Override
    public int getNumMines() {
        return numMines;
    }

    @Override
    public int getNumFlags() {
        return numFlags;
    }

    @Override
    public int getElapsedSeconds() {
        return ((int)((System.currentTimeMillis()-this.startTimeMillis)/1000));
    }

    @Override
    public Cell getCell(int row, int col) {
        return cellCoordinates[col][row];
    }

    @Override
    public void stepOnCell(int row, int col) {
        this.cellCoordinates[col][row].makeVisible();
        if (this.cellCoordinates[col][row].isMine()) {
            this.playerLife = false;
            gameOver = true;
        }
        else if (cellCoordinates[col][row].getNeighborMines() == 0) {
            MyCell[] neighborCellArray = cellCoordinates[col][row].getNeighborCellsArray();
            for (int i = 0; i < cellCoordinates[col][row].getNeighborCellsCount(); i++) {
                if (neighborCellArray[i].getNeighborMines() == 0 && !neighborCellArray[i].isVisible() && !neighborCellArray[i].isFlagged() && !neighborCellArray[i].isMine()) {
                        stepOnCell(neighborCellArray[i].getRow(), neighborCellArray[i].getCol());
                }
                neighborCellArray[i].makeVisible();
                }
        }
    }


    @Override
    public void placeOrRemoveFlagOnCell(int row, int col) {
        if (this.cellCoordinates[col][row].isFlagged()) {
            this.cellCoordinates[col][row].makeUnflagged();
            numFlags -= 1;
        } else {
            this.cellCoordinates[col][row].makeFlagged();
                numFlags += 1;
        }
    }


    @Override
    public boolean isGameStarted() {
        return gameStarted;
    }

    @Override
    public boolean isGameOver() {
        return gameOver;
    }

    @Override
    public boolean isPlayerDead() {
        if (!playerLife) {
            return true;
        }
        return false;
    }

    @Override
    //the winning scenario is stepping on all of the non-mine cells
    public boolean isGameWon() {
        if (playerLife){
            for (int col = 0; col < this.numCols; col++){
                for (int row = 0; row < this.numRows; row++){
                    if (!cellCoordinates[col][row].isMine() && !cellCoordinates[col][row].isVisible()){
                        return false;
                    }
                }
            }
            return true;
        }
        return false;
    }

}
