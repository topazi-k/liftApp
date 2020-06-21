package lift_app;

import lift_app.entities.BuildingState;
import lift_app.entities.Constants;
import lift_app.service.DataGenerator;
import lift_app.service.LiftController;
import lift_app.service.StringFormatter;

public class LiftMain {

    public static void main(String[] args) {

        DataGenerator dataGenerator = new DataGenerator();
        BuildingState building = dataGenerator.generateStartData();
        StringFormatter formatter =new StringFormatter();
        LiftController controller = new LiftController(building);
        controller.init();

        for (int i = 0; i < Constants.DEFAULT_NUMBER_OF_STEPS; i++) {
            System.out.println(formatter.formatData(controller.nextStep()));

        }
    }
}
