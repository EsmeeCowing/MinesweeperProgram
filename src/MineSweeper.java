//Esmee Cowing
class MineSweeper
{
  public static void main(String[] args) {
    MineModel mineModel = new MyMineModel();
    MineView mineView = new MineView(mineModel, 600, 400);
  }
}
