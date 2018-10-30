package game;

import javax.swing.*;

public class GameButton extends JButton {

    private int buttonIndex;
    private Gameboard board;


    public GameButton(int gameButtonIndex, Gameboard currentGameBoard){


        buttonIndex = gameButtonIndex;
        board = currentGameBoard;
        int rowNum = buttonIndex / Gameboard.dimension;
        int cellNum = buttonIndex % Gameboard.dimension;
        setSize(Gameboard.cellSize - 5, Gameboard.cellSize - 5);
        addActionListener(new GameActionListener(rowNum, cellNum, this));


    }

    public Gameboard getBoard(){return board;}

}