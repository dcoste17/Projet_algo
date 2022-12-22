import io.jbotsim.core.Color;
import io.jbotsim.core.Message;
import io.jbotsim.core.Node;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Connexite extends Node{

    List<Node> visited = new ArrayList<>();

    public boolean est_connexe_sans(Node s, Node t, List<Node> interdit){
        Stack<Node> pile = new Stack<>();
        pile.add(s);
        Node tmp;
        while (!pile.empty()){
            tmp= pile.pop();
            List<Node> voisins = tmp.getNeighbors();
            for (Node v:voisins){
                if (!visited.contains(v)&& !pile.contains(v)&&!interdit.contains(v) && v!=t){
                    pile.add(v);
                }
                else if (v == t) {
                    visited.clear();
                    return true;
                }
            }
            visited.add(tmp);
        }
        return false;
    }
    public boolean k_connexe(int k,Node s, Node t, List<Node> allNodes){
        List<Node> interdit=new ArrayList<>();
        for (Node node: allNodes) {
            if (node!=s && node!=t){
                interdit.add(node);
                for (int i = 1; i < k; i++) {
                    int n = 0;
                    boolean stop =false;
                    while (!stop){
                        if (!interdit.contains(allNodes.get(n)) && allNodes.get(n)!=s && allNodes.get(n)!=t){
                            interdit.add(allNodes.get(n));
                            stop =true;
                        }
                        n+=1;
                    }
                }
                boolean a = est_connexe_sans(s,t,interdit);
                interdit.clear();
                if (!a){
                    return false;
                }
            }
        }
        return true;
    }



    @Override
    public void onMessage(Message message) {
        super.onMessage(message);


    }


}
