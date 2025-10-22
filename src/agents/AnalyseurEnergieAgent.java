package agents;

import jade.core.Agent;
import jade.core.behaviours.SimpleBehaviour;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AnalyseurEnergieAgent extends Agent {
    private final List<Double> historique = new ArrayList<>();
    private final int TAILLE_HISTORIQUE = 5;
    private int nombreAnalyses = 0;
    private final Random random = new Random();

    @Override
    protected void setup() {
        System.out.println(getLocalName() + " démarré...");

        // Comportement principal : analyse périodique de la consommation
        addBehaviour(new SimpleBehaviour() {

            @Override
            public void action() {
                // Simuler une valeur de consommation entre 50 et 200 kW
                double consommation = 50 + random.nextDouble() * 150;

                // Ajouter à l’historique et maintenir une taille maximale de 5
                historique.add(consommation);
                if (historique.size() > TAILLE_HISTORIQUE) {
                    historique.remove(0);
                }

                // Calcul de la moyenne mobile
                double moyenne = calculerMoyenne();

                // Affichage des résultats
                System.out.printf("[AnalyseurEnergie] Mesure %d : %.2f kW | Moyenne: %.2f kW%n",
                        nombreAnalyses + 1, consommation, moyenne);

                // Vérifier et suggérer des économies si nécessaire
                suggererEconomies(consommation);

                nombreAnalyses++;

                // Attendre 7 secondes avant la prochaine analyse
                try {
                    Thread.sleep(7000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public boolean done() {
                return nombreAnalyses >= 15; // arrêt après 15 analyses
            }

            @Override
            public int onEnd() {
                System.out.println(getLocalName() + " a terminé les analyses.");
                return super.onEnd();
            }
        });
    }

    // Calcul de la moyenne des dernières mesures
    private double calculerMoyenne() {
        double somme = 0.0;
        for (double valeur : historique) {
            somme += valeur;
        }
        return historique.isEmpty() ? 0.0 : somme / historique.size();
    }

    // Suggestion d’économie d’énergie
    private void suggererEconomies(double consommation) {
        if (consommation > 150) {
            System.out.println("[AnalyseurEnergie] ⚠ RECOMMANDATION : Réduire la consommation !");
        }
    }

    @Override
    protected void takeDown() {
        System.out.println(getLocalName() + " terminé.");
    }
}
