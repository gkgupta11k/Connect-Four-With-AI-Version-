import java.util.Scanner;
/**


  In this class I have build the players and the interaction between player and AI.
 */

public class ConncetFour {
    
    static int columnP;
    static Hfunction board;
    static AlgMinMax AIplayer;
  
    
    public static void main(String[] args) {	
        // here define the AI player "O" 
	AIplayer = new AlgMinMax(Hfunction.O);
        
	board = new Hfunction();
	System.out.println("Welcome to Connect Four Game with AI\n");
        System.out.println(" AI: '\u001B[35mO\u001B[30m' and Player: '\u001B[36mX\u001B[30m' \n");
	board.printBoard();
        //While the game is running 
	while(!board.checkGameOver()) {
            System.out.println();
            switch (board.lastAction) {
            //If O played last, then X plays now 
                case Hfunction.O:
                    System.out.print("Player '\u001B[36mX\u001B[30m' turn.");
                    try {
                        do {
                            System.out.print("\nChoose your column for your next move (1-7): ");
                            Scanner in = new Scanner(System.in);
                            columnP = in.nextInt();
                        } while (board.checkFullColumn(columnP-1));
                    } catch (Exception e){
                        System.out.println("\nRequired numbers are 1,2,3,4,5,6 or 7. Try again\n");
                        break;
                    }
                    //activity of the player
                    board.makeMove(columnP-1, Hfunction.X);
                    System.out.println();
                    break;
            //If O played last, then X plays now 
                case Hfunction.X:
                    Fgame AIMove = AIplayer.getNextMove(board);
                    board.makeMove(AIMove.col, Hfunction.O);
                    System.out.println("AI '\u001B[35mO\u001B[30m' chosen column "+(AIMove.col+1)+".");
                    System.out.println();
                    break;

                default:
                    break;
            }
            board.printBoard();
        }
        // Conclusion after the game finished 
        System.out.println();
        if (board.victory == Hfunction.X) {
            System.out.println("Player '\u001B[36mX\u001B[30m' won the Game :)");
            System.out.println(board.wMode);
        } else if (board.victory == Hfunction.O) {
            System.out.println("AI '\u001B[35mO\u001B[30m' won the Game :)");
            System.out.println(board.wMode);
        } else {
            System.out.println("Game is draw!");
        }
        System.out.println("Game over.");
    }
}
