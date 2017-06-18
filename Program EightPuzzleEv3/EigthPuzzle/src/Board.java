import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Board {
    public int[][] goalState = {{ 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 9 }};//state when end algoritm
    public int[][] tiles={{ 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 9 }};//temp state to solve
    public Board(int[][] tiles){// build board from matrix 3x3
        this.tiles=tiles;
    }
    public Board(Board board){// build form board (clone)
        for(int f=0; f<3; f++){
            for(int c=0; c<3; c++){
                this.tiles[f][c]=board.tiles[f][c];
            }
        }
    }
    //Heuristic value from state board
    public int manhattan(){
        int manhattan=0;//initial value for distance (heuristic value)
        boolean encontrado=false;//initial state for search
        /*
         * evaluate for value 1 
         * # is the position where number 1 should be solution board, numbers are 
         * amount of steps that need to get the solution position 
         * rowX columnY (manhattan)
         * <pre>
         * #12
         * 123
         * 234
         * </pre>
         */
        if(this.tiles[0][0]==1){//case number 1 is in solution position
                encontrado=true;
        }
        if(!encontrado){
            for(int f=0; f<3; f++){
                for(int c=0; c<3; c++){
                    if(this.tiles[f][c]==1){
                    	//distance for 1 step to solution position
                        if((f==1 && c==0) || (f==0 && c==1)){
                            manhattan=manhattan+1;
                            encontrado=true;
                            break;
                        }
                    	//distance for 2 steps to solution position
                        if((f==2 && c==0) || (f==1 && c==1) || (f==0 && c==2)){
                            manhattan=manhattan+2;
                            encontrado=true;
                            break;
                        }
                        //distance for 3 steps to solution position
                        if((f==2 && c==1) || (f==1 && c==2)){
                            manhattan=manhattan+3;
                            encontrado=true;
                            break;
                        }
                        //distance for 4 steps to solution position
                        if(f==2 && c==2){
                            manhattan=manhattan+4;
                            encontrado=true;
                            break;
                        }

                    }
                }
                if(encontrado){//break loop when value 1 is found (Optimization)
                    break;
                }
            }   
        }
        encontrado=false;//reset search state
        /*
         * evaluate for value 2
         * # is the position where number 2 should be solution board, numbers are 
         * amount of steps that need to get the solution position 
         * rowX columnY (manhattan)
         * <pre>
         * 1#1
         * 212
         * 323
         * </pre>
         */
        if(this.tiles[0][1]==2){//case number 2 is in solution position
            encontrado=true;
        }
        if(!encontrado){
            for(int f=0; f<3; f++){
                for(int c=0; c<3; c++){
                    if(this.tiles[f][c]==2){
                        //distance for 1 step to solution position
                        if((f==0 && c==0) || (f==1 && c==1) || (f==0 && c==2)){
                            manhattan=manhattan+1;
                            encontrado=true;
                            break;
                        }
                        //distance for 2 steps to solution position
                        if((f==1 && c==0) || (f==2 && c==1) || (f==1 && c==2)){
                            manhattan=manhattan+2;
                            encontrado=true;
                            break;
                        }
                        //distance for 3 steps to solution position
                        if((f==2 && c==0) || (f==2 && c==2)){
                            manhattan=manhattan+3;
                            encontrado=true;
                            break;
                        }
                    }
                }
                if(encontrado){//break loop when value 2 is found (Optimization)
                    break;
                }
            }
        }
        encontrado=false;//reset search state
        /*
         * evaluate for value 3
         * # is the position where number 3 should be solution board, numbers are 
         * amount of steps that need to get the solution position 
         * rowX columnY (manhattan)
         * <pre>
         * 21#
         * 321
         * 432
         * </pre>
         */
        if(this.tiles[0][2]==3){//case number 3 is in solution position
            encontrado=true;
        }
        if(!encontrado){
            for(int f=0; f<3; f++){
                for(int c=0; c<3; c++){
                    if(this.tiles[f][c]==3){
                        //distance for 1 step to solution position
                        if((f==0 && c==1) || (f==1 && c==2)){
                            manhattan=manhattan+1;
                            encontrado=true;
                            break;
                        }
                        //distance for 2 steps to solution position
                        if((f==0 && c==0) || (f==1 && c==1) || (f==2 && c==2)){
                            manhattan=manhattan+2;
                            encontrado=true;
                            break;
                        }
                        //distance for 3 steps to solution position
                        if((f==1 && c==0) || (f==2 && c==1)){
                            manhattan=manhattan+3;
                            encontrado=true;
                            break;
                        }
                        //distance for 4 steps to solution position
                        if((f==2 && c==0)){
                            manhattan=manhattan+4;
                            encontrado=true;
                            break;
                        }


                    }
                }
                if(encontrado){//break loop when value 3 is found (Optimization)
                    break;
                }
            }
        }
        
        encontrado=false;//reset search state
        
        /*
         * evaluate for value 4
         * # is the position where number 4 should be solution board, numbers are 
         * amount of steps that need to get the solution position 
         * rowX columnY (manhattan)
         * <pre>
         * 123
         * #12
         * 123
         * </pre>
         */
        if(this.tiles[1][0]==4){//case number 4 is in solution position
            encontrado=true;
        }
        if(!encontrado){
            for(int f=0; f<3; f++){
                for(int c=0; c<3; c++){
                    if(this.tiles[f][c]==4){
                        //distance for 1 step to solution position
                        if((f==0 && c==0) || (f==2 && c==0) || (f==1 && c==1)){
                            manhattan=manhattan+1;
                            encontrado=true;
                            break;
                        }
                        //distance for 2 steps to solution position
                        if((f==0 && c==1) || (f==2 && c==1) || (f==1 && c==2)){
                            manhattan=manhattan+2;
                            encontrado=true;
                            break;
                        }
                        //distance for 3 steps to solution position
                        if((f==0 && c==2) || (f==2 && c==2)){
                            manhattan=manhattan+3;
                            encontrado=true;
                            break;
                        }
                    }
                }
                if(encontrado){//break loop when value 4 is found (Optimization)
                    break;
                }
            }
        }
        encontrado=false;//reset search state
        /*
         * evaluate for value 5
         * # is the position where number 5 should be solution board, numbers are 
         * amount of steps that need to get the solution position 
         * rowX columnY (manhattan)
         * <pre>
         * 212
         * 1#1
         * 212
         * </pre>
         */
        if(this.tiles[1][1]==5){//case number 5 is in solution position
            encontrado=true;
        }
        if(!encontrado){
            for(int f=0; f<3; f++){
                for(int c=0; c<3; c++){
                    if(this.tiles[f][c]==5){
                        //distance for 1 step to solution position
                        if((f==1 && c==0) || (f==0 && c==1) || (f==2 && c==1) || 
                                (f==1 && c==2)){
                            manhattan=manhattan+1;
                            encontrado=true;
                            break;
                        }
                        //distance for 2 steps to solution position
                        if((f==0 && c==0) || (f==2 && c==0) || (f==0 && c==2) || 
                                (f==2 && c==2)){
                            manhattan=manhattan+2;
                            encontrado=true;
                            break;
                        }

                    }
                }
                if(encontrado){//break loop when value 5 is found (Optimization)
                    break;
                }
            }
        }
        
        encontrado=false;//reset search state
        /*
         * evaluate for value 6
         * # is the position where number 6 should be solution board, numbers are 
         * amount of steps that need to get the solution position 
         * rowX columnY (manhattan)
         * <pre>
         * 321
         * 21#
         * 321
         * </pre>
         */
        if(this.tiles[1][2]==6){//case number 6 is in solution position
            encontrado=true;
        }
        if(!encontrado){
            for(int f=0; f<3; f++){
                for(int c=0; c<3; c++){
                    if(this.tiles[f][c]==6){
                        //distance for 1 step to solution position
                        if((f==1 && c==1) || (f==0 && c==2) || (f==2 && c==2)){
                            manhattan=manhattan+1;
                            encontrado=true;
                            break;
                        }
                        //distance for 2 steps to solution position
                        if((f==1 && c==0) || (f==0 && c==1) || (f==2 && c==1)){
                            manhattan=manhattan+2;
                            encontrado=true;
                            break;
                        }
                        //distance for 3 steps to solution position
                        if((f==0 && c==0) || (f==2 && c==0)){
                            manhattan=manhattan+3;
                            encontrado=true;
                            break;
                        }

                    }
                }
                if(encontrado){//break loop when value 6 is found (Optimization)
                    break;
                }
            }
        }
        encontrado=false;//reset search state
        /*
         * evaluate for value 7
         * # is the position where number 7 should be solution board, numbers are 
         * amount of steps that need to get the solution position 
         * rowX columnY (manhattan)
         * <pre>
         * 234
         * 123
         * #12
         * </pre>
         */
        if(this.tiles[2][0]==7){//case number 7 is in solution position
            encontrado=false;
        }
        if(!encontrado){
            for(int f=0; f<3; f++){
                for(int c=0; c<3; c++){
                    if(this.tiles[f][c]==7){
                        //distance for 1 step to solution position
                        if((f==1 && c==1) || (f==2 && c==1)){
                            manhattan=manhattan+1;
                            encontrado=true;
                            break;
                        }
                        //distance for 2 steps to solution position
                        if((f==0 && c==0) || (f==1 && c==1) || (f==2 && c==2)){
                            manhattan=manhattan+2;
                            encontrado=true;
                            break;
                        }
                        //distance for 3 steps to solution position
                        if((f==0 && c==1) || (f==1 && c==2)){
                            manhattan=manhattan+3;
                            encontrado=true;
                            break;
                        }
                        //distance for 4 steps to solution position
                        if(f==0 && c==2){
                            manhattan=manhattan+4;
                            encontrado=true;
                            break;
                        }

                    }
                }
                if(encontrado){//break loop when value 7 is found (Optimization)
                    break;
                }
            }
        }
        encontrado=false;//reset search state
        /*
         * evaluate for value 8
         * # is the position where number 8 should be solution board, numbers are 
         * amount of steps that need to get the solution position 
         * rowX columnY (manhattan)
         * <pre>
         * 323
         * 212
         * 1#1
         * </pre>
         */
        if(this.tiles[2][1]==8){//case number 8 is in solution position
            encontrado=true;
        }
        if(!encontrado){
            for(int f=0; f<3; f++){
                for(int c=0; c<3; c++){
                    if(this.tiles[f][c]==8){
                        //distance for 1 step to solution position
                        if((f==2 && c==0) || (f==1 && c==1) || (f==2 && c==2)){
                            manhattan=manhattan+1;
                            encontrado=true;
                            break;
                        }
                        //distance for 2 steps to solution position
                        if((f==1 && c==0) || (f==0 && c==1) || (f==2 && c==2)){
                            manhattan=manhattan+2;
                            encontrado=true;
                            break;
                        }
                        //distance for 3 steps to solution position
                        if((f==0 && c==0) || (f==0 && c==2)){
                            manhattan=manhattan+3;
                            encontrado=true;
                            break;
                        }
                    }
                }
                if(encontrado){//break loop when value 8 is found (Optimization)
                    break;
                }
            }
        }
        return manhattan;   
    }// return sum of Manhattan distances between blocks and solution
    
    /*
     * hamming add 1 for every cell (different to empty cell) that not in solution position 
     *
     */
    public int hamming(){
    	int hamming=0;
    	for(int f=0; f<3; f++){
    		for(int c=0; c<3; c++){
    			if(this.tiles[f][c]!=this.goalState[f][c]){
    				hamming++;
    			}
    			
    		}
    	}
    	return hamming;
    }
        
    
    
    public void moveBoard(int move){
    	/*
    	 * move board (memory) wit move (decoded with number)
    	 * 0-Up
    	 * 1-Right
    	 * 2-Down
    	 * 3-Left
    	 * that moves is the empty cell example
    	 * <pre>
    	 * 123
    	 * 456
    	 * 789
    	 * </pre>
    	 * number 9 is empty cell if do up result this board
    	 * <pre>
    	 * 123
    	 * 459
    	 * 786
    	 */
        int fila=-1;//initial value for row
        int columna=-1;//initial value for column
        int temp=0;//temp value to swap
        if(move==0){//Case up
            for(int f=0; f<3; f++){//search empty cell
                for(int c=0; c<3; c++){
                    if(this.tiles[f][c]==9){
                        fila=f;
                        columna=c;
                        break;
                    }
                }
                if(fila!=-1){//when found empty cell break (optimization)
                    break;
                }
            }
            
            if(fila==0){//in arrow 0 can't do movement up
                //System.out.println("No es posible realizar acción arriba");
                return;
            }
            temp=this.tiles[fila-1][columna];//value to move
            this.tiles[fila-1][columna]=9;//replace where empty cell end when do move
            this.tiles[fila][columna]=temp;//replace value to move in last position empty cell (before move)
            return;
        }
        if(move==1){//Case right
            for(int f=0; f<3; f++){//search empty cell
                for(int c=0; c<3; c++){
                    if(this.tiles[f][c]==9){
                        fila=f;
                        columna=c;
                        break;
                    }
                }
                if(fila!=-1){//when found empty cell break (optimization)
                    break;
                }
            }
            
            if(columna==2){//in column 2 can't do movement right
                //System.out.println("No es posible realizar acción derecha");
                return;
            }
            temp=this.tiles[fila][columna+1];//value to move
            this.tiles[fila][columna+1]=9;//replace where empty cell end when do move
            this.tiles[fila][columna]=temp;//replace value to move in last position empty cell (before move)
            return;
        }
        if(move==2){//Case Down
            for(int f=0; f<3; f++){//search empty cell
                for(int c=0; c<3; c++){
                    if(this.tiles[f][c]==9){
                        fila=f;
                        columna=c;
                        break;
                    }
                }
                if(fila!=-1){//when found empty cell break (optimization)
                    break;
                }
            }
            
            if(fila==2){//in row 2 can't do movement right
                //System.out.println("No es posible realizar acción abajo");
                return;
            }
            temp=this.tiles[fila+1][columna];//value to move
            this.tiles[fila+1][columna]=9;//replace where empty cell end when do move
            this.tiles[fila][columna]=temp;//replace value to move in last position empty cell (before move)
            return;
        }
        if(move==3){//Case Left
            for(int f=0; f<3; f++){//search empty cell
                for(int c=0; c<3; c++){
                    if(this.tiles[f][c]==9){
                        fila=f;
                        columna=c;
                        break;
                    }
                }
                if(fila!=-1){//when found empty cell break (optimization)
                    break;
                }
            }
            
            if(columna==0){//in row 2 can't do movement right
                //System.out.println("No es posible realizar acción izquierda");
                return;
            }
            temp=this.tiles[fila][columna-1];//value to move
            this.tiles[fila][columna-1]=9;//replace where empty cell end when do move
            this.tiles[fila][columna]=temp;//replace value to move in last position empty cell (before move)
            return;
        }        
    }
	
    public boolean isSolvable(){
    	/*
    	 * check if is possible to solve example
    	 * in board
    	 * <pre>
    	 * 123
    	 * 459
    	 * 786
    	 * </pre>
    	 * take how vector 123459786, take every number and check if
    	 * value is greater than next positions except 9 (empty cell) and plus one
    	 * Case 1
    	 * check 2, 3, 4 ,5, 7, 8, 6
    	 * all greater -> plus 7
    	 * Case 2
    	 * check 3, 4, 5, 7, 8, 6
    	 * all greater -> plus 6
    	 * Case 3
    	 * check 4, 5, 7, 8, 6
    	 * all greater plus 5
    	 * Case 4
    	 * check 5, 7, 8, 6
    	 * all greater plus 4
    	 * Case 5
    	 * check 7, 8, 6
    	 * all greater plus 3
    	 * Case 7
    	 * check 8, 6
    	 * 8>7 -> is greater plus 1
    	 * 7>6 -> not greater
    	 * Case 8
    	 * check 6
    	 * 8>6 -> not greater
    	 * add all and...
    	 * 7+6+5+4+3+1=26 -> if result is even is solvable
    	 */
    	List<Integer> linealTablero= new ArrayList<Integer>();
    	for(int i=0; i<3; i++){
    		for(int j=0; j<3; j++){
    			linealTablero.add(this.tiles[i][j]);//take board how vector
    		}
    	}
    	int temp=0;//temp value to check greater
    	int cantidad=0;//initial result value
    	for(int i=0; i<linealTablero.size(); i++){
    		temp=linealTablero.get(i);
    		for(int j=i+1; j<linealTablero.size(); j++){//check the next values to temp value except empty
    			if(linealTablero.get(j)<temp && linealTablero.get(j)!=9 && temp!=9){
    				cantidad++;
    			}
    		}
    	}
    	//System.out.println(cantidad);
    	return cantidad%2==0;//result even return true if not even return false
    }
	
    public void toPrint(){// print representation of the board
        System.out.println("<------->");
        for(int f=0; f<3; f++){
            System.out.print("   ");
            for(int c=0; c<3; c++){
                if(this.tiles[f][c]==9){
                    System.out.print("_");
                }else{
                    System.out.print(this.tiles[f][c]);
                }
            }
            System.out.println("");
        }
        System.out.println("<------->");
    }
    @Override
    public String toString(){// print representation of the board
        String x="";
        char k = (char)34;
        x=x.concat(Character.toString(k));
        for(int f=0; f<3; f++){
            for(int c=0; c<3; c++){
                if(this.tiles[f][c]==9){
                    x=x.concat("_");
                }else{
                    x=x.concat(""+this.tiles[f][c]);
                }
            }
            if(f!=2){
                k = (char)92;
                x=x.concat(Character.toString(k)+"n");
            }
        }
        k = (char)34;
        x=x.concat(Character.toString(k));
        return x;
    }
    //compare input board with actual board (object memory) is iqual
    public boolean isIqual(Board x){
        for(int f=0; f<3; f++){
            for(int c=0; c<3; c++){
                if(x.tiles[f][c]!=this.tiles[f][c]){
                    return false;
                }
            }
        }
        return true;
    }
  //compare solution board with actual board (object memory) is ready
    public boolean isGoal(){
        for(int f=0; f<3; f++){
            for(int c=0; c<3; c++){
                if(this.goalState[f][c]!=this.tiles[f][c]){
                    return false;
                }
            }
        }
        return true;
    }
    /*
     * make a solution tree from initial board using a Heuristic that input
     * if input a heuristic that not define this not use a heuristic value and do a amplitude search
     */
    public Graph TreeSolution(String Heuristica){
        Graph g = new Graph(Heuristica);//new graph
        Node puzzNode = new Node(this);//first node (tree head, initial state)
        g.addNode(puzzNode);//add first node
        long expandido=0;
        while(true){
            puzzNode.expanded=true;//set state in current node
            g.expand(puzzNode);
            int costecamino=Integer.MAX_VALUE;//infinite value
            for(Node n : g.nodes){
                if(!n.expanded){//check in all nodes min path cost value (heristic+steps)
                    //n.value.toPrint();
                    if(costecamino>(n.caminoCost+n.heuristicavalue)){
                        costecamino=n.caminoCost+n.heuristicavalue;
                    }
                }
            }
            for(Node n : g.nodes){//check in tree is already solution and return tree
                if(n.value.isGoal()){
					System.out.println("veces expandido "+expandido);
                    return g;
                }
            }
            for(Node n : g.nodes){//find node that not expanded with min value heuristic+steps
                if(costecamino==(n.caminoCost+n.heuristicavalue) && !n.expanded){
                    puzzNode=n;
                    break;
                }
            }
            expandido++;
        }
    }

}
