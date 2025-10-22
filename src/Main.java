import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;

public class Main {
    public static void main(String[] args) {
        // Créer le runtime JADE
        Runtime rt = Runtime.instance();
        Profile p = new ProfileImpl();
        p.setParameter(Profile.MAIN_HOST, "localhost");
        p.setParameter(Profile.GUI, "true"); // Active l'interface RMA

        // Créer le conteneur principal
        AgentContainer mainContainer = rt.createMainContainer(p);

        try {
            // === Agent 1 : Capteur de Température ===
            /*AgentController tempAgent = mainContainer.createNewAgent(
                    "CapteurTemp",
                    "agents.CapteurTemperatureAgent",
                    null
            );
            tempAgent.start();*/

            // === Agent 3 : Compteur de Personnes ===
            /*AgentController compteurAgent = mainContainer.createNewAgent(
                    "CompteurPersonnes",
                    "agents.CompteurPersonnesAgent",
                    null
            );
            compteurAgent.start();*/

            // === Agent 4 : Analyseur d'Énergie ===
            AgentController energieAgent = mainContainer.createNewAgent(
                    "AnalyseurEnergie",
                    "agents.AnalyseurEnergieAgent",
                    null
            );
            energieAgent.start();


            System.out.println("Tous les agents ont été démarrés avec succès !");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
