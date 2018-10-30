package game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class GameActionListener implements ActionListener {

    private int row;
    private int cell;
    private GameButton button;


    public GameActionListener(int row, int cell, GameButton gbutton){

        this.row = row;
        this.cell = cell;
        this.button = gbutton;

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        Gameboard board = button.getBoard();
        if (board.isTurnable(row, cell)){

            boolean Norm = updateByPlayersData(board);

            if (board.isFull()){
                GameOver();
            }
            else if (!Norm){
                updateByAiData(board);
            }
        }
        else {
            GameOver();
        }
    }

    private boolean updateByPlayersData(Gameboard board){

        board.updateGameField(row, cell);
        button.setText(Character.toString(board.getGame().GetCurrentPlayer().GetPlayerSign()));
        if (board.checkWin()){
            GameOver();
            return true;
        }

        else {
            board.getGame().PassTurn();
        }

        return false;
    }

    private void updateByAiData(Gameboard board) {

        int x = 0;
        int y = 0;
        int cellIndex;

        boolean smartmode = false;
        int maxscore = 0;

        for (int i = 0; i < Gameboard.dimension * Gameboard.dimension ; i++) {

            int fieldscore = 0;

            if (board.getButton(i).getText().equals("")) {


                if (i - 1 >= 0 && board.getButton(i - 1).getText().equals(Character.toString('O'))) {

                    fieldscore++; // лево
                }

                if (i + 1 < Gameboard.dimension * Gameboard.dimension && board.getButton(i + 1).getText().equals(Character.toString('O'))) {

                    fieldscore++; // право
                }

                if (i - 3 >= 0 && board.getButton(i - 3).getText().equals(Character.toString('O'))) {

                    fieldscore++; // верх
                }

                if (i - 4 >= 0 && board.getButton(i - 4).getText().equals(Character.toString('O'))) {

                    fieldscore++; // лево верх
                }

                if (i - 2 >= 0 && board.getButton(i - 2).getText().equals(Character.toString('O'))) {

                    fieldscore++; // право верх
                }

                if (i + 3 < Gameboard.dimension * Gameboard.dimension && board.getButton(i + 3).getText().equals(Character.toString('O'))) {

                    fieldscore++; // вниз
                }

                if (i + 4 < Gameboard.dimension * Gameboard.dimension && board.getButton(i + 4).getText().equals(Character.toString('O'))) {

                    fieldscore++; //  право вниз
                }

                if (i + 2 < Gameboard.dimension * Gameboard.dimension && board.getButton(i + 2).getText().equals(Character.toString('O'))) {

                    fieldscore++; // лево вниз
                }

                if (fieldscore > maxscore) {
                    maxscore = fieldscore;
                    smartmode = true;
                    x = i / Gameboard.dimension;
                    y = i % Gameboard.dimension;
                    System.out.println("smart");

                }
            }
        }

        if (!smartmode) {

            Random rnd = new Random();
            do {
                x = rnd.nextInt(Gameboard.dimension);
                y = rnd.nextInt(Gameboard.dimension);
            } while (!board.isTurnable(x, y));
            System.out.println("Random");

        }

        board.updateGameField(x, y);
        cellIndex = Gameboard.dimension * x + y;
        board.getButton(cellIndex).setText("O");
        if (board.checkWin()) {
            GameOver();
        }
        else {
            board.getGame().PassTurn();
        }
    }

    public void GameOver() {

        if (button.getBoard().checkWin()) {
            if (button.getBoard().getGame().GetCurrentPlayer().GetPlayerSign() == 'O') {
                button.getBoard().getGame().ShowMessagetext("компьютер выиграл");
                button.getBoard().emptyField();
                button.getBoard().getGame().PassTurn();

            }
            else {
                button.getBoard().getGame().ShowMessagetext("Игрок выиграл");
                button.getBoard().emptyField();


            }
        }

        else if (button.getBoard().isFull()){
            button.getBoard().getGame().ShowMessagetext("Ничья");
            button.getBoard().emptyField();
            button.getBoard().getGame().PassTurn();
        }

        else
            button.getBoard().getGame().ShowMessagetext("Некоректный ход");

    }

}
