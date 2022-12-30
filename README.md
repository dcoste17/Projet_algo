# Projet_algo
Sur un graphe donné, on choisit d'abord la source avec le clic molette, puis la destination, puis les sommets interdits. Si on clic de nouveau sur un sommet interdit, il est réinitialisé.
La source est en blanc, la target en noir et les sommets interdits sont plus gros.
Une fois la source et la target définient, on peut utiliser quatre commandes (avec clic gauche sur un emplacemnet vide):

    -There is one path : pour savoir si en évitant le/les noeuds interdits la source peut rejoindre la destination.

    -It is k-connexe : pour savoir si, pour un k donné, ici le nombre de sommets interdit, la graphe est k-connexe.

    -Find Connexite : pour trouver le plus grand k.

    -Nodes who are critical : qui met les noeuds critiques en rouge en fonction du nombre de noeud interdit.

La fonction nb_chemin, ainsi que les deux foncitions qu'elle utilise (parpours_p et elagage) ne sont pas fonctionnelles et ne sont donc pas à prendre en compte, l'explication de l'idée derrière ces fonctions sera bien-sûr données en détail dans le rendu.
