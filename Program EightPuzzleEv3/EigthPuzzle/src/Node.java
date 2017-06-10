


import java.util.ArrayList;

/**
 *
 * @author carlo
 */
public class Node{
        public Board value;//name
        public int caminoCost=0; //Weith
        public int heuristicavalue=0;
        public ArrayList<Edge> salientes = new ArrayList<Edge>(); //Arcos que salen del nodo
        public ArrayList<Edge> entrantes= new ArrayList<Edge>(); //Arcos que entran al nodo
        public Node parent;//de quien es padre
        public int action=-1;
        public boolean expanded=false;
        public Node(Board value){
            this.value=value;
            this.heuristicavalue=value.manhattan();
        }
        
                
        public Node(Node x){
            this.value=x.value;
            this.caminoCost=x.caminoCost;
            this.salientes=x.salientes;
            this.entrantes=x.entrantes;
            this.parent=x.parent;
        }
}
