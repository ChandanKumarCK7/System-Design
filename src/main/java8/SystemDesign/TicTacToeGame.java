package SystemDesign;









import java.util.Arrays;
import java.util.Scanner;

interface Board{
    int size();
}

class SmallBoard implements Board{
    public int size(){
        return 3;
    }
}

class LargeBoard implements Board{
    public int size(){
        return 4;
    }
}

class GameManager{
    Board b;
    char[][] board;
    boolean pickedStatus = false;
    boolean someoneWon = false;
    GameManager(Board b){
        this.b = b;
        board = new char[b.size()][b.size()];
    }


    public void play(char p1, char p2, Scanner in){
        printBoard();

        int c = 0;

        while(c!=board.length * board[0].length && someoneWon == false){
            if (pickedStatus){
                while(true && someoneWon == false){
                    boolean picked = pickACellOnBoardP1(p1, p2, in);
                    if (picked)
                        break;
                }
            }
            else{
                while(true && someoneWon == false){
                    boolean picked = pickACellOnBoardP2(p1, p2, in);
                    if (picked)
                        break;
                }
            }
            c+=1;

        }

    }

    public boolean pickACellOnBoardP1(char p1, char p2, Scanner in){
        System.out.println("player "+ p1 +" to choose a row column");
        int ro = in.nextInt();
        int co = in.nextInt();

        if (board[ro][co] != p1 && board[ro][co] != p2){
            board[ro][co] = p1;
            if (evalBoard(p1, ro, co)){
                System.out.println("player  "+ p1 + " wins");
                someoneWon = true;
                return true;
            }
        }
        else{
            return false;
        }
        pickedStatus = !pickedStatus;
        printBoard();
        return true;
    }

    public boolean pickACellOnBoardP2(char p1, char p2, Scanner in){
        System.out.println("player "+ p2 +" to choose a row column");
        int ro = in.nextInt();
        int co = in.nextInt();

        if (board[ro][co] != p1 && board[ro][co] != p2){
            board[ro][co] = p2;
            if (evalBoard(p2, ro, co)){
                System.out.println("player  "+ p2 + " wins");
                someoneWon = true;
                return true;
            }
        }
        else{
            return false;
        }
        pickedStatus = !pickedStatus;
        printBoard();
        return true;
    }

    public boolean evalBoard(char sign, int i, int j){
        // TODO - need to just complete cross also
        if (i != 0 && j != 0 && i!= board.length-1 && j!= board[0].length && (untilTop(sign, i,  j) && untilBelow(sign, i, j)) || (untilRight(sign, i, j) && untilLeft(sign, i, j))){
            System.out.println("true");
            return true;
        }
        else {
            if (i ==0){
                // TODO - need to just complete cross also
                return (untilBelow(sign, i,  j) || (untilLeft(sign, i, j) && untilRight(sign , i , j) ));
            }

            else if (i == board.length-1){
                return (untilTop(sign, i,  j) || (untilLeft(sign, i, j) && untilRight(sign , i , j) ));
            }

            else if (j ==0){
                return (untilRight(sign, i,  j) || (untilTop(sign, i, j) && untilBelow(sign , i , j) ));
            }

            else{
                return (untilLeft(sign, i,  j) || (untilTop(sign, i, j) && untilBelow(sign , i , j) ));
            }
        }


    }

    public boolean untilLeft(char sign, int i, int j){
        while(j>=0){
            if (board[i][j--] == sign){
                continue;
            }
            else{
                System.out.println("untilLeft false ");
                return false;
            }
        }

        System.out.println("untilLeft true");
        return true;
    }

    public boolean untilTop(char sign, int i, int j){
        while(i>=0){
            if (board[i--][j] == sign){
                continue;
            }
            else{
                System.out.println("untilTop false ");
                return false;
            }
        }

        System.out.println("untilTop true");
        return true;
    }

    public boolean untilRight(char sign, int i, int j){
        while(j<board[0].length){
            if (board[i][j++] == sign){
                continue;
            }
            else{
                System.out.println("untilRight false ");
                return false;
            }
        }

        System.out.println("untilRight true ");
        return true;
    }

    public boolean untilBelow(char sign, int i, int j){
        while(i < board.length){
            if (board[i++][j] == sign){
                continue;
            }
            else{
                System.out.println("untilBelow false ");
                return false;
            }
        }

        System.out.println("untilBelow true");
        return true;
    }

    public void printBoard(){
        for(char[] b1: board)
            System.out.println(Arrays.toString(b1));
    }
}



public class TicTacToeGame {





    public static void main(String[] args){
        Board b = new LargeBoard();

        GameManager g = new GameManager(b);

        Scanner in = new Scanner(System.in);
        System.out.println(" player1 to choose either X or O");
        String p1_choice = in.nextLine();
        char p1 = p1_choice.charAt(0);
        char p2;

        if (p1 == 'X'){
            p2 = 'O';}
        else{
            p2 = 'X';}

        g.play(p1, p2, in);

    }
}
