/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */




import java.util.ArrayList;
import java.util.List;

import lejos.hardware.BrickFinder;
import lejos.hardware.lcd.GraphicsLCD;
import lejos.utility.Delay;

/**
 *
 * @author carlo
 */
public class Board {
    public int[][] goalState = {{ 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 9 }};//9 vacio
    public int[][] tiles={{ 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 9 }};//9 vacio
    public Board(int[][] tiles){// Toma una matriz 3x3 para formar 8-puzzle
        this.tiles=tiles;
    }
    public Board(Board board){// Duplicar tablero
        //int[][] nuevoTiles= {{ 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 9 }};
        for(int f=0; f<3; f++){
            for(int c=0; c<3; c++){
                this.tiles[f][c]=board.tiles[f][c];
            }
        }
    }

    public int manhattan(){
        int manhattan=0;
        boolean encontrado=false;//Estableciendo estado de busqueda
        //evaluando posición del 1
        /**
         * tablero 8-puzzle (3x3) evaluando el 1 partiendo de la siguiente forma; 
         * siendo el # la posición solución para 1 y los números es la cantidad 
         * de pasos de la solución en caso de que se encuentre en dicha posición 
         * filaXcolumna (manhatan)
         * <pre>
         *  #12
         *  123
         *  234
         * </pre>
         */
        if(this.tiles[0][0]==1){//caso de estar ubicado
                encontrado=true;
        }
        if(!encontrado){
            for(int f=0; f<3; f++){
                for(int c=0; c<3; c++){
                    if(this.tiles[f][c]==1){
                        //distancia de 1 paso a la casilla correcta
                        if((f==1 && c==0) || (f==0 && c==1)){
                            manhattan=manhattan+1;
                            encontrado=true;
                            break;
                        }
                        //distancia de 2 pasos a la casilla correcta
                        if((f==2 && c==0) || (f==1 && c==1) || (f==0 && c==2)){
                            manhattan=manhattan+2;
                            encontrado=true;
                            break;
                        }
                        //distancia de 3 pasos a la casilla correcta
                        if((f==2 && c==1) || (f==1 && c==2)){
                            manhattan=manhattan+3;
                            encontrado=true;
                            break;
                        }
                        //distancia de 4 pasos a la casilla correcta
                        if(f==2 && c==2){
                            manhattan=manhattan+4;
                            encontrado=true;
                            break;
                        }

                    }
                }
                if(encontrado){//para no iterar mas sin necesidad
                    break;
                }
            }
            
        }//fin de evaluando posición del 1
        encontrado=false;//reinicnado estado de busqueda
        
        //evaluando posición del 2
        /**
         * tablero 8-puzzle (3x3) evaluando el 2 partiendo de la siguiente forma; 
         * siendo el # la posición solución para 2 y los números es la cantidad 
         * de pasos de la solución en caso de que se encuentre en dicha posición 
         * filaXcolumna (manhatan)
         * <pre>
         *  1#1
         *  212
         *  323
         * </pre>
         */
        if(this.tiles[0][1]==2){//caso de estar bien ubicado
            encontrado=true;
        }
        if(!encontrado){
            for(int f=0; f<3; f++){
                for(int c=0; c<3; c++){
                    if(this.tiles[f][c]==2){
                        //distancia de 1 paso a la casilla correcta
                        if((f==0 && c==0) || (f==1 && c==1) || (f==0 && c==2)){
                            manhattan=manhattan+1;
                            encontrado=true;
                            break;
                        }
                        //distancia de 2 pasos a la casilla correcta
                        if((f==1 && c==0) || (f==2 && c==1) || (f==1 && c==2)){
                            manhattan=manhattan+2;
                            encontrado=true;
                            break;
                        }
                        //distancia de 3 pasos a la casilla correcta
                        if((f==2 && c==0) || (f==2 && c==2)){
                            manhattan=manhattan+3;
                            encontrado=true;
                            break;
                        }


                    }
                }//fin columnas
                if(encontrado){//para no iterar mas sin necesidad
                    break;
                }
            }//fin filas
        }
        encontrado=false;//reinicnado estado de busqueda
        
        //evaluando posición del 3
        /**
        * tablero 8-puzzle (3x3) evaluando el 3 partiendo de la siguiente forma; 
        * siendo el # la posición solución para 3 y los números es la cantidad 
        * de pasos de la solución en caso de que se encuentre en dicha posición 
        * filaXcolumna (manhatan)
        * <pre>
        *  21#
        *  321
        *  432
        * </pre>
        */
        if(this.tiles[0][2]==3){//caso de estar bien ubicado
            encontrado=true;
        }
        if(!encontrado){
            for(int f=0; f<3; f++){
                if(this.tiles[0][2]==3){//caso de estar bien ubicado
                        break;
                }
                for(int c=0; c<3; c++){
                    if(this.tiles[f][c]==3){
                        //distancia de 1 paso a la casilla correcta
                        if((f==0 && c==1) || (f==1 && c==2)){
                            manhattan=manhattan+1;
                            encontrado=true;
                            break;
                        }
                        //distancia de 2 pasos a la casilla correcta
                        if((f==0 && c==0) || (f==1 && c==1) || (f==2 && c==2)){
                            manhattan=manhattan+2;
                            encontrado=true;
                            break;
                        }
                        //distancia de 3 pasos a la casilla correcta
                        if((f==1 && c==0) || (f==2 && c==1)){
                            manhattan=manhattan+3;
                            encontrado=true;
                            break;
                        }
                        //distancia de 4 pasos a la casilla correcta
                        if((f==2 && c==0)){
                            manhattan=manhattan+4;
                            encontrado=true;
                            break;
                        }


                    }
                }//fin columnas
                if(encontrado){//para no iterar mas sin necesidad
                    break;
                }
            }//fin filas
        }
        
        encontrado=false;//reinicnado estado de busqueda
        
        //evaluando posición del 4
        /**
        * tablero 8-puzzle (3x3) evaluando el 4 partiendo de la siguiente forma; 
        * siendo el # la posición solución para 4 y los números es la cantidad 
        * de pasos de la solución en caso de que se encuentre en dicha posición 
        * filaXcolumna (manhatan)
        * <pre>
        *  123
        *  #12
        *  123
        * </pre>
        */
        if(this.tiles[1][0]==4){//caso de estar bien ubicado
            encontrado=true;
        }
        if(!encontrado){
            for(int f=0; f<3; f++){
                for(int c=0; c<3; c++){
                    if(this.tiles[f][c]==4){
                        //distancia de 1 paso a la casilla correcta
                        if((f==0 && c==0) || (f==2 && c==0) || (f==1 && c==1)){
                            manhattan=manhattan+1;
                            encontrado=true;
                            break;
                        }
                        //distancia de 2 pasos a la casilla correcta
                        if((f==0 && c==1) || (f==2 && c==1) || (f==1 && c==2)){
                            manhattan=manhattan+2;
                            encontrado=true;
                            break;
                        }
                        //distancia de 3 pasos a la casilla correcta
                        if((f==0 && c==2) || (f==2 && c==2)){
                            manhattan=manhattan+3;
                            encontrado=true;
                            break;
                        }
                    }
                }//fin columnas
                if(encontrado){//para no iterar mas sin necesidad
                    break;
                }
            }//fin filas
        }
        encontrado=false;//reinicnado estado de busqueda
        
        //evaluando posición del 5
        /**
        * tablero 8-puzzle (3x3) evaluando el 5 partiendo de la siguiente forma; 
        * siendo el # la posición solución para 5 y los números es la cantidad 
        * de pasos de la solución en caso de que se encuentre en dicha posición 
        * filaXcolumna (manhatan)
        * <pre>
        *  212
        *  1#1
        *  212
        * </pre>
        */
        if(this.tiles[1][1]==5){//caso de estar bien ubicado
            encontrado=true;
        }
        if(!encontrado){
            for(int f=0; f<3; f++){
                for(int c=0; c<3; c++){
                    if(this.tiles[f][c]==5){
                        //distancia de 1 paso a la casilla correcta
                        if((f==1 && c==0) || (f==0 && c==1) || (f==2 && c==1) || 
                                (f==1 && c==2)){
                            manhattan=manhattan+1;
                            encontrado=true;
                            break;
                        }
                        //distancia de 2 pasos a la casilla correcta
                        if((f==0 && c==0) || (f==2 && c==0) || (f==0 && c==2) || 
                                (f==2 && c==2)){
                            manhattan=manhattan+2;
                            encontrado=true;
                            break;
                        }

                    }
                }//fin columnas
                if(encontrado){//para no iterar mas sin necesidad
                    break;
                }
            }//fin filas
        }
        
        encontrado=false;//reinicnado estado de busqueda
        
        //evaluando posición del 6
        /**
        * tablero 8-puzzle (3x3) evaluando el 6 partiendo de la siguiente forma; 
        * siendo el # la posición solución para 6 y los números es la cantidad 
        * de pasos de la solución en caso de que se encuentre en dicha posición 
        * filaXcolumna (manhatan)
        * <pre>
        *  321
        *  21#
        *  321
        * </pre>
        */
        if(this.tiles[1][2]==6){//caso de estar bien ubicado
            encontrado=true;
        }
        if(!encontrado){
            for(int f=0; f<3; f++){
                for(int c=0; c<3; c++){
                    if(this.tiles[f][c]==6){
                        //distancia de 1 paso a la casilla correcta
                        if((f==1 && c==1) || (f==0 && c==2) || (f==2 && c==2)){
                            manhattan=manhattan+1;
                            encontrado=true;
                            break;
                        }
                        //distancia de 2 pasos a la casilla correcta
                        if((f==1 && c==0) || (f==0 && c==1) || (f==2 && c==1)){
                            manhattan=manhattan+2;
                            encontrado=true;
                            break;
                        }
                        //distancia de 3 pasos a la casilla correcta
                        if((f==0 && c==0) || (f==2 && c==0)){
                            manhattan=manhattan+3;
                            encontrado=true;
                            break;
                        }

                    }
                }//fin columnas
                if(encontrado){//para no iterar mas sin necesidad
                    break;
                }
            }//fin filas
        }
        encontrado=false;//reinicnado estado de busqueda
        
        //evaluando posición del 7
        /**
        * tablero 8-puzzle (3x3) evaluando el 7 partiendo de la siguiente forma; 
        * siendo el # la posición solución para 7 y los números es la cantidad 
        * de pasos de la solución en caso de que se encuentre en dicha posición 
        * filaXcolumna (manhatan)
        * <pre>
        *  234
        *  123
        *  #12
        * </pre>
        */
        if(this.tiles[2][0]==7){//caso de estar bien ubicado
            encontrado=false;
        }
        if(!encontrado){
            for(int f=0; f<3; f++){
                for(int c=0; c<3; c++){
                    if(this.tiles[f][c]==7){
                        //distancia de 1 paso a la casilla correcta
                        if((f==1 && c==1) || (f==2 && c==1)){
                            manhattan=manhattan+1;
                            encontrado=true;
                            break;
                        }
                        //distancia de 2 pasos a la casilla correcta
                        if((f==0 && c==0) || (f==1 && c==1) || (f==2 && c==2)){
                            manhattan=manhattan+2;
                            encontrado=true;
                            break;
                        }
                        //distancia de 3 pasos a la casilla correcta
                        if((f==0 && c==1) || (f==1 && c==2)){
                            manhattan=manhattan+3;
                            encontrado=true;
                            break;
                        }
                        //distancia de 3 pasos a la casilla correcta
                        if(f==0 && c==2){
                            manhattan=manhattan+4;
                            encontrado=true;
                            break;
                        }

                    }
                }//fin columnas
                if(encontrado){//para no iterar mas sin necesidad
                    break;
                }
            }//fin filas
        }
        encontrado=false;//reinicnado estado de busqueda
        
        //evaluando posición del 8
        /**
        * tablero 8-puzzle (3x3) evaluando el 8 partiendo de la siguiente forma; 
        * siendo el # la posición solución para 8 y los números es la cantidad 
        * de pasos de la solución en caso de que se encuentre en dicha posición 
        * filaXcolumna (manhatan)
        * <pre>
        *  234
        *  123
        *  #12
        * </pre>
        */
        if(this.tiles[2][1]==8){//caso de estar bien ubicado
            encontrado=true;
        }
        if(!encontrado){
            for(int f=0; f<3; f++){
                for(int c=0; c<3; c++){
                    if(this.tiles[f][c]==8){
                        //distancia de 1 paso a la casilla correcta
                        if((f==2 && c==0) || (f==1 && c==1) || (f==2 && c==2)){
                            manhattan=manhattan+1;
                            encontrado=true;
                            break;
                        }
                        //distancia de 2 pasos a la casilla correcta
                        if((f==1 && c==0) || (f==0 && c==1) || (f==2 && c==2)){
                            manhattan=manhattan+2;
                            encontrado=true;
                            break;
                        }
                        //distancia de 3 pasos a la casilla correcta
                        if((f==0 && c==0) || (f==0 && c==2)){
                            manhattan=manhattan+3;
                            encontrado=true;
                            break;
                        }
                    }
                }//fin columnas
                if(encontrado){//para no iterar mas sin necesidad
                    break;
                }
            }//fin filas
        }
        return manhattan;
        
        
        
    }             // return sum of Manhattan distances between blocks and goal
    
    
    
    public void moveBoard(int move){/*mueve dado un tablero 
        *y retona la movida de ese tablero sin modificar el que se lleva en memoria
        *movimientos apartir de la posición del vacío (9) y se desplaza hacia 
        *ese lado el vacío (9)
        *0-Arriba
        *1-Derecha
        *2-Abajo
        *3-Izquierda
        */
        int fila=-1;
        int columna=-1;
        int temp=0;//dato temporal para hacer "swap"
        if(move==0){
            for(int f=0; f<3; f++){
                for(int c=0; c<3; c++){
                    if(this.tiles[f][c]==9){
                        fila=f;
                        columna=c;
                        break;
                    }
                }
                if(fila!=-1){
                    break;
                }
            }
            
            if(fila==0){
                //System.out.println("No es posible realizar acción arriba");
                return;
            }
            temp=this.tiles[fila-1][columna];
            this.tiles[fila-1][columna]=9;
            this.tiles[fila][columna]=temp;
            return;
        }
        if(move==1){
            for(int f=0; f<3; f++){
                for(int c=0; c<3; c++){
                    if(this.tiles[f][c]==9){
                        fila=f;
                        columna=c;
                        break;
                    }
                }
                if(fila!=-1){
                    break;
                }
            }
            
            if(columna==2){
                //System.out.println("No es posible realizar acción derecha");
                return;
            }
            temp=this.tiles[fila][columna+1];
            this.tiles[fila][columna+1]=9;
            this.tiles[fila][columna]=temp;
            return;
        }
        if(move==2){
            for(int f=0; f<3; f++){
                for(int c=0; c<3; c++){
                    if(this.tiles[f][c]==9){
                        fila=f;
                        columna=c;
                        break;
                    }
                }
                if(fila!=-1){
                    break;
                }
            }
            
            if(fila==2){
                //System.out.println("No es posible realizar acción abajo");
                return;
            }
            temp=this.tiles[fila+1][columna];
            this.tiles[fila+1][columna]=9;
            this.tiles[fila][columna]=temp;
            return;
        }
        if(move==3){
            for(int f=0; f<3; f++){
                for(int c=0; c<3; c++){
                    if(this.tiles[f][c]==9){
                        fila=f;
                        columna=c;
                        break;
                    }
                }
                if(fila!=-1){
                    break;
                }
            }
            
            if(columna==0){
                //System.out.println("No es posible realizar acción izquierda");
                return;
            }
            temp=this.tiles[fila][columna-1];
            this.tiles[fila][columna-1]=9;
            this.tiles[fila][columna]=temp;
            return;
        }        
    }
	
    public boolean isSolvable(){
    	List<Integer> linealTablero= new ArrayList<Integer>();;
    	for(int i=0; i<3; i++){
    		for(int j=0; j<3; j++){
    			linealTablero.add(this.tiles[i][j]);
    		}
    	}
    	int temp=0;
    	int cantidad=0;
    	for(int i=0; i<linealTablero.size(); i++){
    		temp=linealTablero.get(i);
    		for(int j=i+1; j<linealTablero.size(); j++){
    			if(linealTablero.get(j)<temp && linealTablero.get(j)!=9 && temp!=9){
    				cantidad++;
    			}
    		}
    	}
    	//System.out.println(cantidad);
    	return cantidad%2==0;
    }
	
    public void toPrint(){// pring representation of the board
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
    public String toString(){// pring representation of the board
        //System.out.println("<------->");
        String x="";
        char k = (char)34;
        x=x.concat(Character.toString(k));
        for(int f=0; f<3; f++){
            //System.out.print("   ");
            
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
            
            //System.out.println("");
        }
        k = (char)34;
        x=x.concat(Character.toString(k));
        //System.out.println("<------->");
        return x;
    }
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
    public Graph TreeSolution(){// find a solution to the initial board
        Graph g = new Graph();
        Node puzzNode = new Node(this);
        g.addNode(puzzNode);
        int paso=0;
        while(true){
            puzzNode.expanded=true;
            g.expand(puzzNode);
            int costecamino=Integer.MAX_VALUE;//infinito
            for(Node n : g.nodes){
                if(!n.expanded){
                    //n.value.toPrint();
                    if(costecamino>(n.caminoCost+n.heuristicavalue)){
                        costecamino=n.caminoCost+n.heuristicavalue;
                    }
                }
            }
            for(Node n : g.nodes){
                if(n.value.isGoal()){
					System.out.println("pasos hechos "+paso);
                    return g;
                }
            }
            for(Node n : g.nodes){
                if(costecamino==(n.caminoCost+n.heuristicavalue) && !n.expanded){
                    //System.out.println("--------------------------------------------");
                    //n.value.toPrint();
                    puzzNode=n;
                    break;
                }
            }
            paso++;
        }
        //return g;
        
    }

}
