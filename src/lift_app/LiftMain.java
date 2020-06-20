package lift_app;

import lift_app.entities.BuildingState;
import lift_app.service.LiftController;
import lift_app.service.StartDataGenerator;
import lift_app.service.StringFormatter;

public class LiftMain {

    public static void main(String[] args) {
        StartDataGenerator dg = new StartDataGenerator();

        BuildingState b = dg.generateStartData();

        LiftController controller=new LiftController(b);
        controller.init();
        StringFormatter bf=new StringFormatter();
        System.out.println(bf.formatData(b));
        int i=0;
        while(i<15) {
            controller.nextStep();
            System.out.println(bf.formatData(b));
            i++;
        }
    }

}
