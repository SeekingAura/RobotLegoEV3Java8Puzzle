/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author carlo
 */
public class Graph {
    public ArrayList<Node> nodes = new ArrayList<Node>();
    public ArrayList<Edge> edges = new ArrayList<Edge>();
    public Graph(){               // constructor
      //
      //
    }  // end constructor
    

    
    //-----------------------------//
    //Methods//
    public void addNode(Board value){
        Node x = new Node(value);
        this.nodes.add(x);
    }
    public void addNode(Node x){
        this.nodes.add(x);
    }
    
    
    public void addDirectedEdge(Node start, Node end){
        //for (Vertex v : myVertices.values())
        
        Edge Arc1 = new Edge(start, end);
        
        
        this.edges.add(Arc1);
        start.salientes.add(Arc1);
        end.entrantes.add(Arc1);
        
    }
    public void expand(Node base){
        for(int i=0; i<4; i++){
            Board temp = new Board(base.value);//copia de Board base
            temp.moveBoard(i);
            boolean existe=false;
            if(!temp.isIqual(base.value)){
                for(Node x : this.nodes){
                    if(x.value.isIqual(temp)){
                        existe=true;
                    }
                }
                if(!existe){
                    Node hijo = new Node(temp);
                    hijo.parent=base;
                    hijo.action=i;
                    hijo.expanded=false;
                    hijo.caminoCost=base.caminoCost+1;
                    this.addNode(hijo);
                    this.addDirectedEdge(base, hijo);
                    //temp.toPrint();
                    //System.out.println("---------------------");
                    //base.value.toPrint();
                    //this.printDOT();
                }    
                
            }
        }
    }
    public List<Integer> chainActions(){
        Node temp = null;
        List<Integer> actions = new ArrayList<Integer>();
        for(Node x : this.nodes){
            if(x.value.isGoal()){
                temp=x;
            }
        }
        while(true){
            if(temp.parent==null){
                break;
            }
            actions.add(temp.action);
            temp=temp.parent;
        }
        //System.out.println("pase aca");
        Collections.reverse(actions);//invertir
        return actions;
    }
    
    public List<String> showActions(List<Integer> x){
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
    
    /**
    * @brief Export the graph in DOT format.
    *
    * Visit http://sandbox.kidstrythisathome.com/erdos/ to visualize it.
    */
    
    public void printDOT(){
        System.out.println("graph ethane {");

	//for(Node n: this.nodes){
            
	//	System.out.println("\t"+n.value+";");
	//}

	for(Edge e : this.edges){
		
		System.out.print("\t"+e.start.value.toString()+" -- "+e.end.value.toString());
    	//if(weighted){
    		
			//System.out.print("[label="+e.cost+"]");
	//	}
    	System.out.println(";");
    }
	System.out.println("}");
    }
    

    
    
}