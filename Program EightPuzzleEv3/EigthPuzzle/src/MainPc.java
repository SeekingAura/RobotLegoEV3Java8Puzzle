
import java.util.ArrayList;
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
		
		int iterations=0;
		List<Long> times = new ArrayList<Long>();
		
			//long startTime = CPUUtils.getSystemTime();
			long startTime = System.nanoTime();
			int[][] tiles= {{ 8, 5, 6 }, { 7, 2, 3 }, { 4, 1, 9 }};//9 is empty cell;
		       
		       Board puzz = new Board(tiles);       
		       List<Integer> actions;
		       List<String> actions2;
		       
		       Graph gx = new Graph();
		       //Abajo, Abajo, Izquierda, Arriba, Derecha, Abajo, Derecha
		       
		       if(puzz.isSolvable()){//check if is possible to get a solution
		    	   
		    	   System.out.println("Start Search");
		    	   
		    	   gx=puzz.TreeSolution("manhattan");//build solution tree
		    	   
		    	   actions=gx.chainActions();//actions to do (numbers)
		    	   //long endTime = CPUUtils.getSystemTime();
		    	   //long endTime = System.nanoTime();
		    	   //times.add(endTime - startTime);
		    	   actions2=gx.showActions(actions);//actions to do (literal)
		    	   System.out.println("Actions (literal values) "+actions2);
		    	   System.out.println("Num actions "+actions.size());
		       }
		    	   //System.out.println("execute time (mili seconds) "+elapsedTime);
		    	   System.out.println("end Search");
		    	   
			       //System.out.println("Actions (number values) "+actions);
			       //
			       //gx.printDOT();//Export the graph in DOT format.
		    //iterations++;   
		
		//for (Long x : times){
		//	System.out.println(x);
		//}
		
	}
		

	



}