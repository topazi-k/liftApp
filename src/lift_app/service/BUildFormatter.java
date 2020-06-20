package lift_app.service;

import lift_app.entities.Building;
import lift_app.entities.Floor;
import lift_app.entities.Lift.LiftDirection;
import lift_app.entities.Passenger;

public class BUildFormatter {
    
    public static int cct=-1;
    private Building b;
    
    public BUildFormatter(Building building) {
        this.b = building;
    }

    public String createStiring() {
        cct++;
        StringBuilder sb = new StringBuilder();
        sb.append(b.getNumberOfFloors() + " number of floors ||step" +cct);
        sb.append("\n\n");
        sb.append("");
        for (Floor floor : b.getFloors()) {
            sb.append("n=" + floor.getNumber() + "   :");
            for (Passenger p : floor.getAllPassengers()) {
                sb.append(p.getDestination()+", ");
            }
            if (b.getLift().getCurrentFloor() == floor.getNumber()) {
                if(b.getLift().getDirection()==LiftDirection.UP) {
                    sb.append("||v||");
                }
                else{
                    sb.append("||^||");
                }
                sb.append(" lift here: "+ "liftFloor "+b.getLift().getCurrentFloor()+"||");
                for (Passenger p : b.getLift().getPassengers()) {
                    sb.append(p.getDestination() + ",");
                }
            }
            sb.append("\n\n");
        }

        return sb.toString();
    }
}
