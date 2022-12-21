import io.jbotsim.core.*;
import io.jbotsim.core.event.SelectionListener;
import io.jbotsim.core.event.StartListener;
import io.jbotsim.ui.JViewer;
import io.jbotsim.ui.icons.Icons;

public class Main implements SelectionListener, StartListener{

    Topology tp;
    Node sourceNode;
    Node targetNode;
    public Main() {
        tp = new Topology();
        tp.setTimeUnit(200); // slow down for visualization
        tp.setDefaultNodeModel(Connexite.class);
        tp.addSelectionListener(this);
        tp.addStartListener(this);
        new JViewer(tp);
        tp.start();
    }
    @Override
    public void onSelection(Node node) {

        Node selectedNode = (Node) node;

        //la source devient bleu
        if (sourceNode == null) {
            sourceNode = selectedNode;
            sourceNode.setColor(Color.white);

            //la destination devient noir
        } else if (targetNode == null){
            targetNode = selectedNode;
            targetNode.setColor(Color.black);

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
}


