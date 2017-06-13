import java.util.ArrayList;

public class Node{
        public Board value;//node content
        public int caminoCost=0; //Weight
        public int heuristicavalue=0;
        public int num=0;
        public ArrayList<Edge> salientes = new ArrayList<Edge>(); //edges that get out from node
        public ArrayList<Edge> entrantes= new ArrayList<Edge>(); //edges that get in from node
        public Node parent;
        public boolean isStepSolution=false;
        public int action=-1;//last action to obtain this board 
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
