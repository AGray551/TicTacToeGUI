import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class TicTacToeFrame extends JFrame {

    private final int ROW = 3;
    private final int COL = 3;
    private String board[][] = new String[ROW][COL];
    private JButton visBoard[][] = new JButton[ROW][COL];
    private String currPlayer = "X";
    private boolean gameRunning = true;
    private int numTurns = 0;

    private JPanel mainPnl;
    private JPanel moveSltPnl;


    public TicTacToeFrame(){
        mainPnl = new JPanel();
        mainPnl.setLayout(new BorderLayout());

        clearBoard();

        createMoveSltPnl();
        mainPnl.add(moveSltPnl);
        display();

        JButton quit = new JButton("Quit");
        quit.setSize(100, 50);
        quit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        add(quit, BorderLayout.AFTER_LAST_LINE);

        add(mainPnl);
        setTitle("Tic-Tac-Toe Game!");
        setLocationRelativeTo(null);
        setLocation(500, 125);
        setSize(new Dimension(800, 800));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }


    private void createMoveSltPnl(){
        moveSltPnl = new JPanel();
        moveSltPnl.setLayout(new GridLayout(3, 3));
        moveSltPnl.setSize(400, 400);
        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < COL; j++) {
                visBoard[i][j] = new JButton("");
                final int finalI = i;
                final int finalJ = j;
                visBoard[i][j].addActionListener(e -> playTurn(finalI, finalJ));
                moveSltPnl.add(visBoard[i][j]);
            }
        }

    }

    private void clearBoard(){
        currPlayer = "X";
        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < COL; j++) {
                board[i][j] = " ";
            }
        }
        numTurns = 0;
        gameRunning = true;
    }

    private void display(){
        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < COL; j++) {
                if(board[i][j] == "X"){
                    visBoard[i][j].setText("X");
                }
                else if(board[i][j] == "O"){
                    visBoard[i][j].setText("O");
                }
                else{
                    visBoard[i][j].setText("");
                }
                visBoard[i][j].setFont(new Font("Arial", Font.PLAIN, 84));
            }
        }
    }

    private void playTurn(int inRow, int inCol){
        if (isValidMove(inRow, inCol) && gameRunning){
            board[inRow][inCol] = currPlayer;
            display();

            if(numTurns >= 7 && isTie()){
                gameRunning = false;
                int cont = JOptionPane.showConfirmDialog(this, "The game ended in a tie!\nWould you like to play again?");
                if(cont == JOptionPane.YES_OPTION){
                    clearBoard();
                    display();
                }
                else if(cont == JOptionPane.NO_OPTION){
                    System.exit(0);
                }
            }
            else if(isWin(currPlayer)) {
                gameRunning = false;
                int cont = JOptionPane.showConfirmDialog(this, "Player " + currPlayer + " won!\nWould you like to play again?");
                if(cont == JOptionPane.YES_OPTION){
                    clearBoard();
                    display();
                }
                else if(cont == JOptionPane.NO_OPTION){
                    System.exit(0);
                }
            }
            else{
                numTurns++;

                if(currPlayer == "X"){
                    currPlayer = "O";
                }
                else{
                    currPlayer = "X";
                }
            }
        }
        else
            JOptionPane.showMessageDialog(this, "Not a valid move!");


    }

    private boolean isValidMove(int row, int col){
        return board[row][col].equals(" ");
    }

    private boolean isWin(String player){
        return (isColWin(player)||isRowWin(player)||isDiagonalWin(player));
    }

    private boolean isColWin(String player){
        for (int i = 0; i < COL; i++) {
            if(board[0][i].equals(player) && board[1][i].equals(player) && board[2][i].equals(player)){
                return true;
            }
        }
        return false;
    }

    private boolean isRowWin(String player){
        for (int i = 0; i < ROW; i++) {
            if(board[i][0].equals(player) && board[i][1].equals(player) && board[i][2].equals(player)){
                return true;
            }
        }
        return false;
    }

    private boolean isDiagonalWin(String player){
        return (board[0][0].equals(player) && board[1][1].equals(player) && board[2][2].equals(player)) || (board[0][2].equals(player) && board[1][1].equals(player) && board[2][0].equals(player));
    }

    private boolean isTie(){
        for(int i = 0; i < ROW; i++){
            for(int j = 0; j < COL; j++){
                if(board[i][j].equals(" ")){
                    return false;
                }
            }
        }

        for(int i = 0; i < 8; i++){
            if(lineBoth(i)){
                return false;
            }
        }
        return true;
    }

    private boolean lineBoth(int lineIndex){
        String line = "";
        switch(lineIndex){
            case 0: line = board[0][0] + board[0][1] + board[0][2]; break;
            case 1: line = board[1][0] + board[1][1] + board[1][2]; break;
            case 2: line = board[2][0] + board[2][1] + board[2][2]; break;
            case 3: line = board[0][0] + board[1][0] + board[2][0]; break;
            case 4: line = board[0][1] + board[1][1] + board[2][1]; break;
            case 5: line = board[0][2] + board[1][2] + board[2][2]; break;
            case 6: line = board[0][0] + board[1][1] + board[2][2]; break;
            case 7: line = board[0][2] + board[1][1] + board[2][0]; break;
        }
        return !(line.contains("X") && line.contains("O"));
    }
}
