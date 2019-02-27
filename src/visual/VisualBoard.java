package visual;

public class VisualBoard {
    private String[][] cellValues;

    public VisualBoard(int height, int length){
        cellValues = new String[height][length];
        for(int i = 0; i < height; i++)
            for(int j = 0; j < length; j++)
                cellValues[i][j] = "?";
    }

    public void changeCellView(int heightCoordinate, int lengthCoordinate, int value){
        cellValues[heightCoordinate][lengthCoordinate] = getValueView(value);
    }

    public void refreshView(){
        System.out.print("\033[H\033[2J");
        System.out.flush();
        for(int i = 0; i < cellValues.length; i++) {
            for(int j = 0; j < cellValues[i].length; j++) {
                System.out.print(cellValues[i][j] + " ");
            }
            System.out.println();
        }
    }

    private String getValueView(int value){
       switch(value) {
           case -3 :
               return "+";
           case -2 :
               return "!";
           case -1 :
               return "*";
           case 0 :
               return "#";
           default :
               return Integer.toString(value);
       }
    }
}
