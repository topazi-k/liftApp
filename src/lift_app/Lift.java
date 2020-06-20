package lift_app;

import lift_app.entities.Building;
import lift_app.entities.Floor;
import lift_app.entities.Passenger;
import lift_app.service.BUildFormatter;
import lift_app.service.LiftController;
import lift_app.service.StartDataGenerator;

public class Lift {

    public static void main(String[] args) {
        StartDataGenerator dg = new StartDataGenerator();

        Building b = dg.generateStartData();

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
        BUildFormatter bf=new BUildFormatter(b);
        System.out.println(bf.createStiring());
        int i=0;
        while(i<15) {
            controller.nextStep();
            System.out.println(bf.createStiring());
            i++;
        }
    }

}
