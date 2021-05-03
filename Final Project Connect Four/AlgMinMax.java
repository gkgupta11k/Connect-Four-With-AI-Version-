import java.util.LinkedList;
import java.util.Random;
/**

 in this class I have implement the MinMax algorithm
 */

public class AlgMinMax {
    //  it holds the maximum depth 
    int maximumDepth;
    //  it holds which letter the AI controls
    int cLetter;
  

    public AlgMinMax(int thePlayerLetter) {
        maximumDepth = 5; // it help to make better decsion about more depth, more accurate decision, more time
        cLetter = thePlayerLetter;
    }

    //Initiates MinMax algorithm
    public Fgame getNextMove(Hfunction board) {
        //We select the lowest  values in order to choose the greatest
        return max(board.boardWithExpansion(board), 0);
    }


    public Fgame min(Hfunction board, int depth) { //Min plays 'X' for player
        Random r = new Random();
        // If Min is called on a state after a maximum depth is reached, then a heuristic evaluated on HFunction and the returned.
        if((board.checkGameOver()) || (depth == maximumDepth)){
            Fgame baseMove = new Fgame();
            baseMove = baseMove.possibleMuv(board.PreviousMove.row, board.PreviousMove.col, board.utilityFunction());
            return baseMove;
        }else{
            //The moves are calculated here
            LinkedList<Hfunction> children = new LinkedList<Hfunction>(board.getChildren(Hfunction.X));
            Fgame minMove = new Fgame();
            minMove = minMove.Compare(Integer.MAX_VALUE);
            for (int i =0; i < children.size();i++) {
                Hfunction child = children.get(i);
                // each child max is called, on a low depth
                Fgame move = max(child, depth + 1);
                // child move with the low value is chosen and returned by max
                if(move.getValue() <= minMove.getValue()) {
                    if ((move.getValue() == minMove.getValue())) {
                        //If heuristic contain same value then randomly choose one of the two moves
                        if (r.nextInt(2) == 0) { //If 0, here then reconsider the maxMove. Else, the maxMove is the same
                            minMove.setRow(child.PreviousMove.row);
                            minMove.setCol(child.PreviousMove.col);
                            minMove.setValue(move.getValue());
                        }
                    }
                    else {
                        minMove.setRow(child.PreviousMove.row);
                        minMove.setCol(child.PreviousMove.col);
                        minMove.setValue(move.getValue());
                    }
                }
            }
            return minMove;
        }
    }


    public Fgame max(Hfunction board, int depth) { //MAX plays 'O'
        Random r = new Random();
    // If max is called on a state after a maximum depth is reached, then a heuristic evaluated on HFunction and the returned.
        if((board.checkGameOver()) || (depth == maximumDepth)) {
            Fgame baseMove = new Fgame();
            baseMove = baseMove.possibleMuv(board.PreviousMove.row, board.PreviousMove.col, board.utilityFunction());
            return baseMove;
        }else{
            LinkedList<Hfunction> children = new LinkedList<Hfunction>(board.getChildren(cLetter));
            Fgame maxMove = new Fgame();
            maxMove = maxMove.Compare(Integer.MIN_VALUE);
            for (int i =0; i < children.size();i++) {
                Hfunction child = children.get(i);
                Fgame move = min(child, depth + 1);
                //The greatest value is chosen here 
                if(move.getValue() >= maxMove.getValue()) {
                    if ((move.getValue() == maxMove.getValue())) {
                        if (r.nextInt(2) == 0) {
                            maxMove.setRow(child.PreviousMove.row);
                            maxMove.setCol(child.PreviousMove.col);
                            maxMove.setValue(move.getValue());
                        }
                    }
                else {
                    maxMove.setRow(child.PreviousMove.row);
                    maxMove.setCol(child.PreviousMove.col);
                    maxMove.setValue(move.getValue());
                    }
                }
            }
            return maxMove;
        }
    }//end max
}
