package control;

import model.EndOfGameException;
import model.GameBoard;
import visual.VisualBoard;

import java.util.Scanner;

public class GameController {
    private GameBoard board;
    private Scanner commandsReader = new Scanner(System.in);

    public GameController(int height, int length, int bombsNumber){
        board = new GameBoard(height, length, bombsNumber);
    }

    public void start(){
        System.out.println("Welcome to saper!\nYou can open the cell (\"open x y\") or mark it as a bomb (\"mark x y\")\nOpen any cell to start");
        board.start = true;
        while(true){
            try {
                processCommand();
            }
            catch (EndOfGameException e) {
                System.out.println(e.getMessage());
                end();
            }
        }
    }

    public void processCommand()throws EndOfGameException {
        String command = commandsReader.next().toLowerCase();
        int heightCoordinate;
        int lengthCoordinate;
        switch (command) {
            case "open":
                heightCoordinate = commandsReader.nextInt();
                lengthCoordinate = commandsReader.nextInt();
                try {
                    board.open(heightCoordinate, lengthCoordinate);
                    board.refreshView();
                }
                catch (RuntimeException e) {
                    System.out.println(e.getMessage());
                }
                break;
            case "mark":
                heightCoordinate = commandsReader.nextInt();
                lengthCoordinate = commandsReader.nextInt();
                try {
                    board.mark(heightCoordinate, lengthCoordinate);
                    board.refreshView();
                }
                catch (RuntimeException e){
                    System.out.println(e.getMessage());
                }
                break;
            default:
                System.out.println("Unknown command");
        }
    }

    private void end() {
        System.out.println("Thanks for game B)");
    }
}
