import io.jbotsim.core.Message;
import io.jbotsim.core.Node;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Connexite extends Node{

    List<Node> interdit = new ArrayList<>();
    List<Node> visited = new ArrayList<>();




public boolean est_connexe_sans(Node s, Node t, List<Node> interdit){

    Stack<Node> pile = new Stack<>();
    pile.add(s);
    Node tmp;
    while (!pile.empty()){
        tmp= pile.pop();
        List<Node> voisins = tmp.getNeighbors();
        for (Node v:voisins){
            if (!visited.contains(v)|| !pile.contains(v)||!interdit.contains(v) || v!=t){
                pile.add(v);
            }
            else if (v==t){
                return true;
            }
        }
        visited.add(tmp);
    }
    return false;
}


    @Override
    public void onMessage(Message message) {
        super.onMessage(message);


    }


}
