
import java.util.List;

public class MainPc {
	public static void main(String[] args) {
		/*
		 * Board
		 * <pre>
		 * 193
		 * 726
		 * 548
		 * </pre>
		 */
		int[][] tiles= {{ 1, 9, 3 }, { 7, 2, 6 }, { 5, 4, 8 }};//9 is empty cell;
	       
	       Board puzz = new Board(tiles);       
	       List<Integer> actions;
	       List<String> actions2;
	       
	       Graph gx = new Graph();
	       
	       if(puzz.isSolvable()){//check if is possible to get a solution
	    	   gx=puzz.TreeSolution();//build solution tree
	    	   actions=gx.chainActions();//actions to do (numbers)
		       actions2=gx.showActions(actions);//actions to do (literal)
		       System.out.println("Actions (number values) "+actions);
		       System.out.println("Actions (literal values) "+actions2);
		       //gx.printDOT();//Export the graph in DOT format.
		       
	       }
		}

	



}