package agents;

import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class CompteurPersonnesAgent extends Agent {
    private int personnesPresentes = 0;
    private int totalEntrees = 0;
    private int totalSorties = 0;
    private int operations = 0;
    private final Random random = new Random();
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    @Override
    protected void setup() {
        System.out.println(getLocalName() + " démarré...");

        // Comportement périodique : simulation d’entrées/sorties toutes les 4 secondes
        addBehaviour(new TickerBehaviour(this, 4000) {
            @Override
            protected void onTick() {
                boolean entree = random.nextBoolean(); // entrée ou sortie
                if (entree) {
                    personnesPresentes++;
                    totalEntrees++;
                    afficherAction("+1 entrée");
                } else if (personnesPresentes > 0) {
                    personnesPresentes--;
                    totalSorties++;
                    afficherAction("-1 sortie");
                } else {
                    afficherAction("(aucune sortie, compteur à 0)");
                }

                operations++;

                // Afficher les statistiques toutes les 10 opérations
                if (operations % 10 == 0) {
                    afficherStatistiques();
                }
            }
        });
    }

    // Méthode pour afficher l’action courante
    private void afficherAction(String action) {
        String heure = LocalTime.now().format(formatter);
        System.out.println("[CompteurPersonnes] " + heure +
                " - Personnes présentes: " + personnesPresentes +
                " (" + action + ")");
    }

    // Méthode d’affichage des statistiques globales
    private void afficherStatistiques() {
        System.out.println("\n=== STATISTIQUES ===");
        System.out.println("Personnes actuelles : " + personnesPresentes);
        System.out.println("Total entrées       : " + totalEntrees);
        System.out.println("Total sorties       : " + totalSorties);
        System.out.println("====================\n");
    }

    @Override
    protected void takeDown() {
        System.out.println(getLocalName() + " terminé.");
    }
}
