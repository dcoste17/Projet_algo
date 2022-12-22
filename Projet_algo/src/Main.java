import io.jbotsim.core.*;
import io.jbotsim.core.event.CommandListener;
import io.jbotsim.core.event.SelectionListener;
import io.jbotsim.core.event.StartListener;
import io.jbotsim.ui.JViewer;
import io.jbotsim.ui.icons.Icons;

import java.util.ArrayList;
import java.util.List;

public class Main implements SelectionListener, StartListener, CommandListener {

    public static final String HAS_ONE_PATH = "There is one path";
    public static final String FIND_CONNEX = "Find Connexite";
    public static final String K_CONNEX = "It is k-Connexite";
    Topology tp;
    Node sourceNode;
    Node targetNode;
    List<Node> allNodes = new ArrayList<>();
    List<Node> interdit = new ArrayList<>();
    public Main() {
        tp = new Topology();
        tp.setTimeUnit(500);
        tp.setDefaultNodeModel(Connexite.class);
        tp.addSelectionListener(this);
        tp.addStartListener(this);
        tp.addCommand(HAS_ONE_PATH);
        tp.addCommand(K_CONNEX);
        tp.addCommand(FIND_CONNEX);
        tp.addCommandListener(this);
        new JViewer(tp);
        tp.start();
    }
    @Override
    public void onSelection(Node node) {
        //la source devient bleu
        if (sourceNode == null) {
            sourceNode = node;
            sourceNode.setColor(Color.white);
            //la destination devient noir
        } else if (targetNode == null){
            targetNode = node;
            targetNode.setColor(Color.black);
        } else if (interdit.contains(node)) {
            interdit.remove(node);
            node.setIconSize(Node.DEFAULT_ICON_SIZE);
        } else {
            interdit.add(node);
            node.setIconSize(16);
        }
    }

    @Override
    public void onStart() {
        if (targetNode != null) {
            targetNode.setIcon(Icons.DEFAULT_NODE_ICON);
            targetNode.setIconSize(10);
            targetNode = null;
        }
        sourceNode = null;
        for (Node node : tp.getNodes()){
            node.setColor(null);
        }


        for (Link link : tp.getLinks())
            link.setWidth(1);
    }


    public static void main(String[] args) {
        new Main();
    }

    @Override
    public void onCommand(String command) {
        Connexite c = new Connexite();
        allNodes= tp.getNodes();
        boolean is_connexe;
        int nb_interdit=interdit.size();
        if (command.equals(HAS_ONE_PATH)){

            boolean co;
            co = c.est_connexe_sans(sourceNode,targetNode,interdit);
            System.out.println(co);

        }
        if (command.equals(FIND_CONNEX)){
            is_connexe=true;
            int i =0;
            while (is_connexe){
                i+=1;
                is_connexe =c.k_connexe(i,sourceNode,targetNode,allNodes);

            }
            System.out.println("i="+i);

        }
        if (command.equals(K_CONNEX)){
           is_connexe =c.k_connexe(nb_interdit,sourceNode,targetNode,allNodes);
            System.out.println(is_connexe);
        }

    }
}


