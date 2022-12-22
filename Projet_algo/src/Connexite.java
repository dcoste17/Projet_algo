import io.jbotsim.core.Color;
import io.jbotsim.core.Message;
import io.jbotsim.core.Node;
import io.jbotsim.core.Topology;
import io.jbotsim.core.Link;

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

    public Topology elagage(Node s, Node t) {
        Stack<Node> pile = new Stack<>();
        Stack<Node> pile_tmp = new Stack<>();
        Topology graphe_elage = new Topology();
        pile.add(s);
        Node tmp;
        Node noeud_marqué = s;
        int modif_tour = 0;
        int noeuds_ajoute = 0;
        int nb_voisins = 0;
        int nb_voisins_niv_suivant = 0;
        int voisins_visités = 0;
        while (!pile.empty()) {
            tmp = pile.pop();
            List<Node> voisins = tmp.getNeighbors();
            nb_voisins = s.getNeighbors().size();
            do {
                if (modif_tour != 0) {
                    int position = 0;
                    for (Node v : voisins) {
                        if (position+1 == nb_voisins && !pile.contains(noeud_marqué)){
                            position++;
                            noeud_marqué = v;
                        }
                        if (!visited.contains(v) || !pile.contains(v) || v!=t){
                            pile.add(v);
                            //pile_tmp.add(v);
                        }
                        //si le noeud correspond au noeud de départ, on garde le lien
                        if (tmp == s) {
                            Link lien = new Link(tmp, v);
                            graphe_elage.addNode(v);
                            graphe_elage.addLink(lien);
                            modif_tour++;
                            noeuds_ajoute++;
                        } else {
                            //si le noeud voisin correspond au noeud d'arrivée, on garde le lien
                            if (v == t) {
                                Link lien = new Link(tmp, v);
                                graphe_elage.addNode(v);
                                graphe_elage.addLink(lien);
                                modif_tour++;
                                noeuds_ajoute++;
                                nb_voisins_niv_suivant++;
                            } else {
                                //si le noeud voisin n'a pas déjà été visité ET qu'il n'est lié qu'à son
                                //parent + un autre noeud
                                if (!visited.contains(v) && v.getNeighbors().size() == 2) {
                                    //si un voisin du noeud voisin est déjà visité, on n'ajoute pas le noeud
                                    for (Node v_visited : visited) {
                                        if (v.hasNeighbor(v_visited)) {
                                            break;
                                        }
                                        Link lien = new Link(tmp, v);
                                        graphe_elage.addNode(v);
                                        graphe_elage.addLink(lien);
                                        modif_tour++;
                                        noeuds_ajoute++;
                                        nb_voisins_niv_suivant++;
                                    }

                                }
                            }
                        }
                    }
                    visited.add(tmp);
                } else {
                    Node choix = voisins.get(0);
                    Link lien = new Link(tmp, choix);
                    graphe_elage.addNode(choix);
                    graphe_elage.addLink(lien);
                    modif_tour++;
                    noeuds_ajoute++;
                }
            } while (Math.min(nb_voisins, nb_voisins_niv_suivant) != noeuds_ajoute || pile.contains(noeud_marqué));
        }
        return graphe_elage;
    }


    @Override
    public void onMessage(Message message) {
        super.onMessage(message);


    }


}
