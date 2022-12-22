import io.jbotsim.core.*;
import io.jbotsim.core.event.CommandListener;
import io.jbotsim.core.event.SelectionListener;
import io.jbotsim.core.event.StartListener;
import io.jbotsim.ui.JViewer;
import io.jbotsim.ui.icons.Icons;

import java.util.List;

public class Main implements SelectionListener, StartListener, CommandListener {

    public static final String K_CONNEXITE = "k-Connexe?";
    public static final String FIND_CONNEX = "Find Connexite";
    Topology tp;
    Node sourceNode;
    Node targetNode;
    Mode mode = new Mode();
    List<Node> interdit= mode.interdit;
    public Main() {
        tp = new Topology();
        tp.setTimeUnit(500);
        tp.setDefaultNodeModel(Connexite.class);
        tp.addSelectionListener(this);
        tp.addStartListener(this);
        tp.addCommand(K_CONNEXITE);
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
        for (Node node : tp.getNodes())
            node.setColor(null);
        for (Link link : tp.getLinks())
            link.setWidth(1);
    }


    public static void main(String[] args) {
        new Main();
    }

    @Override
    public void onCommand(String command) {
        if (command.equals(K_CONNEXITE)){

            for (Node n: interdit)
                System.out.println(n.getID());
        }
        if (command.equals(FIND_CONNEX)){

        }

    }
}


