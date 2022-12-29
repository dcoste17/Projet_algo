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
    public List<List<Integer>> calcul_combinaisons(int p, int n){
        List<List<Integer>> liste_combinaisons = new ArrayList<>();
        List<Integer> indices = new ArrayList<>();
        liste_combinaisons.add(indices);

        for(int i = 0; i < p; i++){
            indices.add(i);
        }
        if(p == n){
            return liste_combinaisons;
        }
        int i = p-1;

        while (i != -1){
            indices.set(i, indices.get(i) + 1);
            if (i < p-1) {
                for (int j = i + 1; i < p; j++) {
                    indices.set(j, indices.get(j - 1) + 1);
                }
            }
            if (indices.get(i) == n-p+i){
                i--;
            }else{
                i = p-1;
            }
            liste_combinaisons.add(indices);
        }
        return liste_combinaisons;
    }

    public boolean k_connexe(int k, Node s, Node t, List<Node> allNodes){
        List<Node> interdit=new ArrayList<>();
        List<List<Integer>> combinaisons = new ArrayList<>();
        combinaisons = calcul_combinaisons(k, allNodes.size());
        for(List<Integer> uplets : combinaisons){
            for(Integer position : uplets){
                if(allNodes.get(position) == s || allNodes.get(position) == t){
                    break;
                }
                interdit.add(allNodes.get(position));
            }
            if(interdit.size() == k){
                boolean a = est_connexe_sans(s,t,interdit);
                if (!a){
                    return false;
                }
            }
            interdit.clear();
        }
        return true;
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
                //si le noeud père correspond au noeud source OU si le noeud voisin correspond au noeud d'arrivée
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
                    //si le noeud voisin n'est lié qu'à son
                    //parent + un autre noeud
                    if (v.getNeighbors().size() <= 2) {
                        //si un voisin du noeud voisin est déjà ajouté, on n'ajoute pas le noeud
                        for(Node neighbor_v : v.getNeighbors()){
                            //si le noeud voisin du noeud voisin (qui n'est pas le noeud source)
                            //n'est relié à aucun noeud déjà ajouté, on garde le lien
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
