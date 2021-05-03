import java.util.LinkedList;
/**

  Here is the definition of the board for Connect4
  * We also implement heuristic function for the AI
 */

public class Hfunction {

    static final int X = 1;     //Player 
    static final int O = -1;    //AI 
    int NULL = 0;              //Blank space

    Fgame PreviousMove;
    int lastAction; 
    int victory;
    int [][] mainBoard;
    String wMode;      


    //Constructor  for a board
    public Hfunction() {
        PreviousMove = new Fgame();
        lastAction = O; // Player starts first
        victory = 0;
        mainBoard = new int[6][7];
        for(int i=0; i<6; i++) {
            for(int j=0; j<7; j++) {
                mainBoard[i][j] = NULL;
            }
        }
    }//end Constructor
	
    public void setVictory(int victory) {
        this.victory = victory;
    }

    public void setWMode(String wMode) {
        this.wMode = wMode;
    }//end setWMode
    
    // move based on a column and a player
    public void makeMove(int col, int letter) {
        PreviousMove = PreviousMove.muvDone(getRowPosition(col), col);
        mainBoard[getRowPosition(col)][col] = letter;
        lastAction = letter;
    }
	
    //Checks whether a move is eligible
    public boolean isValidMove(int col) {
        int row = getRowPosition(col);
        if ((row == -1) || (col == -1) || (row > 5) || (col > 6)) {
            return false;
        }
        if(mainBoard[row][col] != NULL) {
            return false;
        }
        return true;
    }
	
    // This is used when  need to make a move
    public boolean canMove(int row, int col) {
     
        if ((row <= -1) || (col <= -1) || (row > 5) || (col > 6)) {
            return false;
        }
        return true;
    }
	
    //look for column is full 
    public boolean checkFullColumn(int col) {
        if (mainBoard[0][col] == NULL)
            return false;
        else{
            System.out.println("The column "+(col+1)+" have no space. Go for another column.");
            return true;
        }
    }
	
    //look for a blank space in the board to put 'X' or 'O'
    public int getRowPosition(int col) {
        int rowPosition = -1;
        for (int row=0; row<6; row++) {
            if (mainBoard[row][col] == NULL) {
                    rowPosition = row;
            }
        }
        return rowPosition;
    } 
    
    //It help us to expand the board 
    public Hfunction boardWithExpansion(Hfunction board) {
        Hfunction expansion = new Hfunction();
        expansion.PreviousMove = board.PreviousMove;
        expansion.lastAction = board.lastAction;
        expansion.victory = board.victory;
        expansion.mainBoard = new int[6][7];
        for(int i=0; i<6; i++) {
            for(int j=0; j<7; j++) {
                expansion.mainBoard[i][j] = board.mainBoard[i][j];
            }
        }
        return expansion;
    }
	
    //create the children. The maximum of the children is 7 because here on;y 7 columns
    public LinkedList<Hfunction> getChildren(int letter) {
        LinkedList<Hfunction> children = new LinkedList<Hfunction>();
        for(int col=0; col<7; col++) {
            if(isValidMove(col)) {
                Hfunction child = boardWithExpansion(this);
                child.makeMove(col, letter);
                children.add(child);
            }
        }
        return children;
    }
	
    public int utilityFunction() {
        //max go for 'O'
        // +90  'O' is winner, -90 'X' is winner,
        // +10 if three 'O' in a row, -5 three 'X' in a row,
        // +4 if two 'O' in a row, -1 two 'X' in a row
        int Xlines = 0;
        int Olines = 0;
        if (checkWinState()) {
            if(victory == X) {
                Xlines = Xlines + 90;
            } else {
                Olines = Olines + 90;
            }
        }	
        Xlines  = Xlines + checkThree(X)*10 + check2In(X)*4;
        Olines  = Olines + checkThree(O)*5 + check2In(O);
	return Olines - Xlines;
    }
	
    // possible victory movement
    public boolean checkWinState() {
    //Case of four row
        for (int i=5; i>=0; i--) {
            for (int j=0; j<4; j++) {
                if (mainBoard[i][j] == mainBoard[i][j+1] && mainBoard[i][j] == mainBoard[i][j+2] && mainBoard[i][j] == mainBoard[i][j+3] && mainBoard[i][j] != NULL) {
                    setVictory(mainBoard[i][j]);
                    setWMode("Victory by row.");
                    return true;
                }
            }
        }
		
    //Case for 4-column
        for (int i=5; i>=3; i--) {
            for (int j=0; j<7; j++) {
                if (mainBoard[i][j] == mainBoard[i-1][j] && mainBoard[i][j] == mainBoard[i-2][j] && mainBoard[i][j] == mainBoard[i-3][j] && mainBoard[i][j] != NULL) {
                    setVictory(mainBoard[i][j]);
                    setWMode("Victory by column.");
                    return true;
                }
            }
        }
		
    // for ascendent four diagonal
        for (int i=0; i<3; i++) {
            for (int j=0; j<4; j++) {
                if (mainBoard[i][j] == mainBoard[i+1][j+1] && mainBoard[i][j] == mainBoard[i+2][j+2] && mainBoard[i][j] == mainBoard[i+3][j+3] && mainBoard[i][j] != NULL) {
                    setVictory(mainBoard[i][j]);
                    setWMode("Victory by diagonal.");
                    return true;
                }
            }
        }
		
    // for an descendent four diagonal
        for (int i=0; i<6; i++) {
            for (int j=0; j<7; j++) {
                if (canMove(i-3,j+3)) {
                    if (mainBoard[i][j] == mainBoard[i-1][j+1] && mainBoard[i][j] == mainBoard[i-2][j+2] && mainBoard[i][j] == mainBoard[i-3][j+3]  && mainBoard[i][j] != NULL) {
                        setVictory(mainBoard[i][j]);
                        setWMode("Victory by diagonal.");
                        return true;
                    }
                }
            }
        }
        // no winner found yet :(
        setVictory(0);
        return false;
    }
	
    //search for   3 pieces of a same player
    public int checkThree(int player) {	
        int times = 0;
        // row
        for (int i = 5; i >= 0; i--) {
            for (int j = 0; j < 7; j++) {
                if (canMove(i, j + 2)) {
                    if (mainBoard[i][j] == mainBoard[i][j + 1] && mainBoard[i][j] == mainBoard[i][j + 2] && mainBoard[i][j] == player) {
                        times++;
                    }
                }
            }
        }

        // column
        for (int i = 5; i >= 0; i--) {
            for (int j = 0; j < 7; j++) {
                if (canMove(i - 2, j)) {
                    if (mainBoard[i][j] == mainBoard[i - 1][j] && mainBoard[i][j] == mainBoard[i - 2][j] && mainBoard[i][j] == player) {
                        times++;
                    }
                }
            }
        }

        // diagonal ascendent
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                if (canMove(i + 2, j + 2)) {
                    if (mainBoard[i][j] == mainBoard[i + 1][j + 1] && mainBoard[i][j] == mainBoard[i + 2][j + 2] && mainBoard[i][j] == player) {
                        times++;
                    }
                }
            }
        }

        // diagonal descendent
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                if (canMove(i - 2, j + 2)) {
                    if (mainBoard[i][j] == mainBoard[i - 1][j + 1] && mainBoard[i][j] == mainBoard[i - 2][j + 2] && mainBoard[i][j] == player) {
                        times++;
                    }
                }
            }
        }
        return times;				
    }

    //search for  2 pieces of a same player
    public int check2In(int player) {	
        int times = 0;
        //In a row
        for (int i = 5; i >= 0; i--) {
            for (int j = 0; j < 7; j++) {
                if (canMove(i, j + 1)) {
                    if (mainBoard[i][j] == mainBoard[i][j + 1] && mainBoard[i][j] == player) {
                        times++;
                    }
                }
            }
        }

        // column
        for (int i = 5; i >= 0; i--) {
            for (int j = 0; j < 7; j++) {
                if (canMove(i - 1, j)) {
                    if (mainBoard[i][j] == mainBoard[i - 1][j] && mainBoard[i][j] == player) {
                        times++;
                    }
                }
            }
        }

        // diagonal ascendent
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                if (canMove(i + 1, j + 1)) {
                    if (mainBoard[i][j] == mainBoard[i + 1][j + 1] && mainBoard[i][j] == player) {
                        times++;
                    }
                }
            }
        }

        // diagonal descendent
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                if (canMove(i - 1, j + 1)) {
                    if (mainBoard[i][j] == mainBoard[i - 1][j + 1] && mainBoard[i][j] == player) {
                        times++;
                    }
                }
            }
        }
        return times;
    }
    
    public boolean checkGameOver() {
        //If there is a winner
        if (checkWinState()) {
            return true;
        }
        //look for null 
        for(int row=0; row<6; row++) {
            for(int col=0; col<7; col++) {
                if(mainBoard[row][col] == NULL) {
                    return false;
                }
            }
        }
        return true;
    }

    // display board
    public void printBoard() {
        System.out.println("| 1 | 2 | 3 | 4 | 5 | 6 | 7 |");
        System.out.println();
        for (int i=0; i<6; i++) {
            for (int j=0; j<7; j++) {
                    if (mainBoard[i][j] == 1) {
                        System.out.print("| " + "\u001B[36mX" + "\u001B[30m "); //green for player
                    } else if (mainBoard[i][j] == -1) {
                        System.out.print("| " + "\u001B[35mO" + "\u001B[30m "); //pink for AI
                    } else {
                        System.out.print("| " + "*" + " ");
                    }
            }
            System.out.println("|"); //End of each row
        }
    }
}
