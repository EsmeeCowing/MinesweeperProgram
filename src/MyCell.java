//Esmee Cowing
public class MyCell implements Cell {

    private int row, col, numNeighbourMines, neighborCellsCount;
    private boolean visible, mine, flagged;
    private MyCell[] neighborCellsArray;


    public MyCell(int col, int row){
        this.col = col;
        this.row = row;
        this.visible = false;
        this.mine = false;
        this.flagged = false;
        this.neighborCellsCount = 0;
        this.neighborCellsArray = new MyCell[9];
    }

    @Override
    public int getRow() {
        return row;
    }

    @Override
    public int getCol() {
        return col;
    }

    @Override
    public boolean isVisible() {
        return visible;
    }

    @Override
    public boolean isMine() {
        return mine;
    }

    @Override
    public boolean isFlagged() {
        return flagged;
    }

    @Override
    public int getNeighborMines() {
        return numNeighbourMines;
   }

   public void makeVisible(){
        this.visible = true;
   }

   public void makeFlagged(){
        this.flagged = true;
   }

    public void makeUnflagged(){
        this.flagged = false;
    }

    public void makeMine(){
        this.mine = true;
    }

    public void makeNormalCell(){
        this.mine = false;
    }

    public void setNumNeighbourMines(int neighbourMines){
        this.numNeighbourMines = neighbourMines;
    }

    public MyCell[] getNeighborCellsArray(){
        return neighborCellsArray;
    }

    public int getNeighborCellsCount(){
        return neighborCellsCount;
    }

    public void appendToNeighborCellsArray(MyCell cell){
        neighborCellsArray[neighborCellsCount++] = cell;
    }

}
