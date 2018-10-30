package game;

import javax.swing.*;

public class Game {

    private Gameboard board;
    private GamePlayer[] gameplayers = new GamePlayer[2];
    int playersTurn = 0;


    public Game(){

        this.board = new Gameboard(this);

    }

    public void initGame(){

        gameplayers[0] = new GamePlayer(true, 'X');
        gameplayers[1] = new GamePlayer(false, 'O');

    }

    void PassTurn(){

        if (playersTurn == 0){
            playersTurn = 1;
        }
        else {
            playersTurn = 0;
        }
    }

    GamePlayer GetCurrentPlayer(){return gameplayers[playersTurn];}

    void ShowMessagetext(String messageText){

        JOptionPane.showMessageDialog(board, messageText);

    }

}
