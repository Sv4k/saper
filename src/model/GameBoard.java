package model;

import visual.VisualBoard;

import java.util.concurrent.ThreadLocalRandom;

public class GameBoard {
    private VisualBoard visualBoard;
    private int bombsNumber;
    private Cell[][] cells;
    private int[] bombs;
    private int winConditionCounter;
    public boolean start;

    public GameBoard(int height, int length, int bombsNumber) {
        start = true;
        this.bombsNumber = bombsNumber;
        bombs = new int[height];
        cells = new Cell[height][length];
        for (int i = 0; i < height; i++)
            for (int j = 0; j < length; j++)
                cells[i][j] = new Cell();
        winConditionCounter = height * length;
        visualBoard = new VisualBoard(height, length);
        visualBoard.refreshView();
    }

    public void fillBoard(int startHeightCoordinate, int startLengthCoordinate) throws EndOfGameException{
        int cnt = bombsNumber;
        while(cnt > 0) {
            int heightCoordinate = ThreadLocalRandom.current().nextInt(0, cells.length);
            int lengthCoordinate = ThreadLocalRandom.current().nextInt(0, cells[0].length);
            Cell tmp = cells[heightCoordinate][lengthCoordinate];
            if((startHeightCoordinate == heightCoordinate && startLengthCoordinate == lengthCoordinate) || tmp.containsBomb())
                continue;
            tmp.setBomb();
            bombs[heightCoordinate] = lengthCoordinate;
            countBomb(heightCoordinate, lengthCoordinate);
            cnt--;
        }
    }

    private void countBomb(int heightCoordinate, int lengthCoordinate){
        if (heightCoordinate > 0) {
            cells[heightCoordinate - 1][lengthCoordinate].increaseCounter();
            if (lengthCoordinate > 0) {
                cells[heightCoordinate - 1][lengthCoordinate - 1].increaseCounter();
            }
            if (lengthCoordinate < cells[0].length - 1) {
                cells[heightCoordinate - 1][lengthCoordinate + 1].increaseCounter();
            }
        }
        if (heightCoordinate < cells.length - 1) {
            cells[heightCoordinate + 1][lengthCoordinate].increaseCounter();
            if(lengthCoordinate > 0) {
                cells[heightCoordinate + 1][lengthCoordinate - 1].increaseCounter();
            }
            if(lengthCoordinate < cells[0].length - 1) {
                cells[heightCoordinate + 1][lengthCoordinate + 1].increaseCounter();
            }
        }
        if(lengthCoordinate > 0)
            cells[heightCoordinate][lengthCoordinate - 1].increaseCounter();
        if(lengthCoordinate < cells[0].length - 1)
            cells[heightCoordinate][lengthCoordinate + 1].increaseCounter();
    }

    public void open(int heightCoordinate, int lengthCoordinate) throws EndOfGameException{
        if(heightCoordinate < 0 || lengthCoordinate < 0 || heightCoordinate >= cells.length || lengthCoordinate >= cells[0].length)
            throw new RuntimeException("Illegal coordinates");

        if(start) {
            start = false;
            fillBoard(heightCoordinate, lengthCoordinate);
        }

        Cell current = cells[heightCoordinate][lengthCoordinate];
        int content = current.open();
        if(content == -1)
            bombOpened();
        visualBoard.changeCellView(heightCoordinate, lengthCoordinate, content);
        winConditionCounter --;
        if(winConditionCounter == 0)
            throw new EndOfGameException("Congratulations!!! You won!");
        if(content == 0) {
            if(heightCoordinate != 0) {
                try {
                    open(heightCoordinate - 1, lengthCoordinate);
                } catch (RuntimeException e) {
                }
            }
            if(lengthCoordinate != 0) {
                try {
                    open(heightCoordinate, lengthCoordinate - 1);
                } catch (RuntimeException e) {
                }
            }
            if(heightCoordinate != cells.length - 1) {
                try {
                    open(heightCoordinate + 1, lengthCoordinate);
                } catch (RuntimeException e) {
                }
            }
            if(lengthCoordinate != cells[0].length) {
                try {
                    open(heightCoordinate, lengthCoordinate + 1);
                } catch (RuntimeException e) {
                }
            }
        }
    }

    public void refreshView(){
        visualBoard.refreshView();
    }

    public void mark(int heightCoordinate, int lengthCoordinate) throws EndOfGameException{
        if(heightCoordinate < 0 || lengthCoordinate < 0)
            throw new RuntimeException("Illegal coordinates");
        cells[heightCoordinate][lengthCoordinate].mark();
        visualBoard.changeCellView(heightCoordinate, lengthCoordinate, -2);
        winConditionCounter --;
        if(winConditionCounter == 0) {
            throw new EndOfGameException("Congratulations!!! You won the game!");
        }
    }

    private void bombOpened() throws EndOfGameException{
        for(int i = 0; i < bombs.length; i++) {
            visualBoard.changeCellView(i, bombs[i], cells[i][bombs[i]].isMarked() ? -3 : -1);
        }
        throw new EndOfGameException("*BOOOM* You died! Game over");
    }
}
