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
    public boolean calcul_combinaisons(int p, List<Node> n, Node s, Node t){
        List<Node> indices = new ArrayList<>();

        for (Node node:n.subList(0,p))
            indices.add(node);

        int i = p-1;

        while (i != -1){
            int indexi=n.indexOf(indices.get(i))+1;
            Node node = n.get(indexi);
            indices.set(i, node);
            if (i < p-1) {
                for (int j = i + 1; j < p; j++) {
                    indexi =n.indexOf(indices.get(j - 1)) + 1;
                    node=n.get(indexi);
                    indices.set(j, node);
                }
            }
            if (indices.get(i).getID() == n.size()-p+i){
                i--;
            }else{
                i = p-1;
            }

            boolean a = est_connexe_sans(s,t,indices);
            if (!a){
                return false;
            }
        }

        return true;
    }

    public List<Node> noeuds_critiques(int p, List<Node> n, Node s, Node t){
        List<Node> indices = new ArrayList<>();
        List<Node> critiques = new ArrayList<>();
        for (Node node:n.subList(0,p))
            indices.add(node);

        int i = p-1;

        while (i != -1){
            int indexi=n.indexOf(indices.get(i))+1;
            Node node = n.get(indexi);
            indices.set(i, node);
            if (i < p-1) {
                for (int j = i + 1; j < p; j++) {
                    indexi =n.indexOf(indices.get(j - 1)) + 1;
                    node=n.get(indexi);
                    indices.set(j, node);
                }
            }
            if (indices.get(i).getID() == n.size()-p+i){
                i--;
            }else{
                i = p-1;
            }

            boolean a = est_connexe_sans(s,t,indices);
            if (!a){
                for (Node node_critic : indices){
                    if (!critiques.contains(node_critic)){
                        critiques.add(node_criticgit);
                    }
                }
            }
        }
        return critiques;
    }


    public Topology elagage(Node s, Node t) {
        Stack<Node> pile = new Stack<>();
        List<Node> added = new ArrayList<>();
        Topology graphe_elage = new Topology();
        Node candidat = new Node();
        pile.add(s);
        Node tmp = new Node();
        while (!pile.empty()) {
            List<Node> voisins = tmp.getNeighbors();
            for (Node v : voisins) {
                //si le noeud p??re correspond au noeud source OU si le noeud voisin correspond au noeud d'arriv??e
                //on garde le lien
                if(!added.contains(v)) {
                    if ((tmp == s || v == t)) {
                        Link lien = new Link(tmp, v);
                        graphe_elage.addNode(v);
                        graphe_elage.addLink(lien);
                        added.add(v);
                        candidat = null;
                        break;
                    }
                    //si le noeud voisin n'est li?? qu'?? son
                    //parent + un autre noeud
                    if (v.getNeighbors().size() <= 2) {
                        //si un voisin du noeud voisin est d??j?? ajout??, on n'ajoute pas le noeud
                        for(Node neighbor_v : v.getNeighbors()){
                            //si le noeud voisin du noeud voisin (qui n'est pas le noeud source)
                            //n'est reli?? ?? aucun noeud d??j?? ajout??, on garde le lien
                            if(neighbor_v != tmp && !added.contains(neighbor_v)){
                                Link lien = new Link(tmp, v);
                                graphe_elage.addNode(v);
                                graphe_elage.addLink(lien);
                                added.add(v);
                                candidat = null;
                                break;
                            }
                        }
                    }else{
                        candidat = v;
                    }
                }
                if (candidat != null){
                    added.add(candidat);
                    Link lien = new Link(tmp, v);
                    graphe_elage.addNode(v);
                    graphe_elage.addLink(lien);
                    candidat = null;
                }
            }
        }return graphe_elage;
    }
    int parcours_p (Node t, Node tmp){
        int cpt = 0;
        List<Node> voisins = tmp.getNeighbors();
        for (Node v : voisins) {
            if (v == t){
                return 1;
            }
            cpt = cpt + parcours_p(t, v);
        }
        return cpt;
    }

    int nb_chemin (Node s, Node t) {
        int nb = 0;
        Topology graphe_mod = elagage(s, t);
        List<Node> noeuds = graphe_mod.getNodes();
        int taille = noeuds.size();
        Node s_mod = noeuds.get(0);
        Node tmp = s_mod;
        Node t_mod = noeuds.get(taille-1);
        nb = parcours_p(t_mod, tmp);
        return nb;
    }

    @Override
    public void onMessage(Message message) {
        super.onMessage(message);


    }


}
