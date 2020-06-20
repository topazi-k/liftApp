package lift_app;

import lift_app.entities.BuildingState;
import lift_app.service.LiftController;
import lift_app.service.StartDataGenerator;
import lift_app.service.StringFormatter;

public class Lift {

    public static void main(String[] args) {
        StartDataGenerator dg = new StartDataGenerator();

        BuildingState b = dg.generateStartData();

        //System.out.println("all floors" + b.getFloors().length + " :  \n\n");

    /*    for (Floor f : b.getFloors()) {
            System.out.println(
                    "\n\n\nflore : nomber " + f.getNumber() + "\n passengers count : " + f.getAllPassengers().size());

            for (Passenger p : f.getAllPassengers()) {
                System.out.println(p.getDestination());
            }
        }
        /*
         * for(int i=0;i<50; i++) { System.out.println( dg.generateRandomInRange(1,
         * 10)); }
         */
        
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
