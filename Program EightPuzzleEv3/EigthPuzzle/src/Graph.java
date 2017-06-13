import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Graph {
    public ArrayList<Node> nodes = new ArrayList<Node>();
    public ArrayList<Edge> edges = new ArrayList<Edge>();
    public Graph(){
    }
    
    public void addNode(Board value){
        Node x = new Node(value);
        this.nodes.add(x);
    }
    public void addNode(Node x){
        this.nodes.add(x);
    }
    
    public void addDirectedEdge(Node start, Node end){
        Edge Arc1 = new Edge(start, end);
        this.edges.add(Arc1);
        start.salientes.add(Arc1);
        end.entrantes.add(Arc1);
    }
    public void expand(Node base){
        for(int i=0; i<4; i++){//every iteration is an action (0-up, 1-right, 2-Down, 3-left) 
            Board temp = new Board(base.value);//copy of input board
            temp.moveBoard(i);
            boolean existe=false;//initial state
            if(!temp.isIqual(base.value)){//if move is impossible temp board not change, dont want make alredy exist boards
                for(Node x : this.nodes){//check if exist on tree new board state
                    if(x.value.isIqual(temp)){
                        existe=true;
                    }
                }
                if(!existe){//only expand if dont exist on tree the new board state
                    Node hijo = new Node(temp);
                    hijo.parent=base;
                    hijo.action=i;
                    hijo.expanded=false;
                    hijo.caminoCost=base.caminoCost+1;
                    this.addNode(hijo);
                    this.addDirectedEdge(base, hijo);
                }    
                
            }
        }
    }
    public List<Integer> chainActions(){
        Node temp = null;
        List<Integer> actions = new ArrayList<Integer>();
        for(Node x : this.nodes){
            if(x.value.isGoal()){//take node with solution state
                temp=x;
            }
        }
        while(true){//add to action value and go to parent node until parent null (tree head)
            if(temp.parent==null){
                break;
            }
            actions.add(temp.action);
            temp=temp.parent;
        }
        /*
         * because initial in the end of tree the first action is the last action
         * with reverse list the actions are in order
         */
        Collections.reverse(actions);
        return actions;
    }
    
    public List<String> showActions(List<Integer> x){//actions decoder (int number to literal)
        List<String> actions = new ArrayList<String>();
        for(int i : x){
            if(i==0){
                actions.add("Arriba");
            }
            if(i==1){
                actions.add("Derecha");
            }
            if(i==2){
                actions.add("Abajo");
            }
            if(i==3){
                actions.add("Izquierda");
            }
        }
        return actions;
        
    }
    
    /*
     * @brief Export the graph in DOT format.
     *
     * Visit http://www.webgraphviz.com/ to visualize it.
     */
    public void printDOT(){
        System.out.println("graph ethane {");
	for(Edge e : this.edges){
		System.out.print("\t"+e.start.value.toString()+" -- "+e.end.value.toString());
    	System.out.println(";");
    }
	System.out.println("}");
    }
    

    
    
}