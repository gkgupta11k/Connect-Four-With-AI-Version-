/**

 In this class I have implement to make a move and get a specific position
 */

public class Fgame {	
    int row;
    int col;
    private int value;     


    public Fgame() {
        row = -1;
        col = -1;
        value = 0;
    } 

    //Move done
    public Fgame muvDone(int row, int col) {
        Fgame moveDone = new Fgame();
        moveDone.row = row;
        moveDone.col = col;
        moveDone.value = -1;
        return moveDone;
    }// end muvDone
    
    //Move for expansion (with utility function)
    public Fgame possibleMuv(int row, int col, int value) {
        Fgame posisibleMuv = new Fgame();
        posisibleMuv.row = row;
        posisibleMuv.col = col;
        posisibleMuv.value = value;
        return posisibleMuv;
    }//end possibleMuv

    //Move used to compare in MinMax algorithm
    public Fgame Compare(int value) {
        Fgame Compare = new Fgame();
        Compare.row = -1;
        Compare.col = -1;
        Compare.value = value;
        return Compare;
    }//end moveCOmpare

    public int getValue() {
        return value;
    }//end getValue

    public void setRow(int aRow) {
        row = aRow;
    }//end setRow

    public void setCol(int aCol) {
        col = aCol;
    }//end setCol

    public void setValue(int aValue) {
        value = aValue;
    }//end setValue
}//end class Fgame
