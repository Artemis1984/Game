package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Gameboard extends JFrame {

    static int dimension = 3;
    static int cellSize = 150;
    private char[][] gameField;
    private GameButton[] gameButtons;
    static char nullSymbol = '\u0000';

    private Game game;

    public Gameboard(Game currentGame){

        this.game = currentGame;
        initField();

    }


    private void initField(){


        setBounds(cellSize*dimension, cellSize*dimension,300,400);
        setTitle("Крестики Нолики");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel controlpanel = new JPanel();
        JButton newGameButton = new JButton("Новая игра");
        newGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                emptyField();
            }
        });

        controlpanel.setLayout(new BoxLayout(controlpanel, BoxLayout.X_AXIS));
        controlpanel.add(newGameButton);
        controlpanel.setSize(cellSize * dimension, 150);

        JPanel gameFieldPanel = new JPanel();
        gameFieldPanel.setLayout(new GridLayout(dimension, dimension));
        gameFieldPanel.setSize(cellSize * dimension,cellSize * dimension);

        gameField = new char[dimension][dimension];
        gameButtons = new GameButton[dimension*dimension];

        for (int i = 0; i < dimension*dimension; i++) {
            GameButton fieldButton = new GameButton(i,this);
            gameFieldPanel.add(fieldButton);
            gameButtons[i] = fieldButton;
        }

        getContentPane().add(controlpanel, BorderLayout.NORTH);
        getContentPane().add(gameFieldPanel, BorderLayout.CENTER);
        setVisible(true);

    }

    void emptyField(){

        for (int i = 0; i < dimension * dimension ; i++) {
            gameButtons[i].setText("");
            int x = i / Gameboard.dimension;
            int y = i % Gameboard.dimension;
            gameField[x][y] = nullSymbol;


        }

    }


    Game getGame(){return game;}

    boolean isTurnable(int x, int y){



        if (gameField[y][x] == nullSymbol){
            return  true;
        }

        return false;
    }

    void updateGameField(int x, int y){

        gameField[y][x] = game.GetCurrentPlayer().GetPlayerSign();

    }

    boolean checkWin(){

        boolean result = false;
        char playerSymbol = getGame().GetCurrentPlayer().GetPlayerSign();
        if (checkWinDiagonals(playerSymbol)||checkWinLines(playerSymbol))
            result = true;

        return result;

    }

    private boolean checkWinLines(char playerSymbol){

        boolean rows, cols, result;
        result = false;

        for (int i = 0; i < dimension; i++) {
            cols = true;
            rows = true;
            for (int j = 0; j < dimension; j++) {

                cols &= (gameField[i][j] == playerSymbol);
                rows &= (gameField[j][i] == playerSymbol);
            }
            if (cols || rows){
                result = true;
                break;
            }

            if (result)
                break;
        }

        return result;
    }

    private boolean checkWinDiagonals(char playerSymbol){

        boolean rightLeft, leftRight, result;
        result = false;
        rightLeft = true;
        leftRight = true;
        for (int i = 0; i < dimension; i++) {

            rightLeft &= (gameField[i][i] == playerSymbol);
            leftRight &= (gameField[dimension-i-1][i] == playerSymbol);
        }
        if (rightLeft||leftRight){
            result = true;
        }

        return result;
    }

    boolean isFull(){

        boolean result = true;
        for (int i = 0; i < dimension; i++) {

            for (int j = 0; j < dimension; j++) {
                if (gameField[i][j] == nullSymbol){
                    result = false;
                    break;
                }

            }

            if (!result)
                break;
        }
        return result;

    }

    public GameButton getButton(int buttonIndex){

        return gameButtons[buttonIndex];
    }


}
