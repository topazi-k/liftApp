package lift_app.service;

import java.util.Formatter;

import lift_app.entities.BuildingState;
import lift_app.entities.Floor;
import lift_app.entities.Lift;
import lift_app.entities.Lift.LiftDirection;
import lift_app.entities.Passenger;

public class StringFormatter {

    public String formatData(BuildingState building) {
        StringBuilder buildingState = new StringBuilder();
        Formatter formatter = new Formatter(buildingState);

        formatter.format("%19s %2d %1s\n\n", "* Step - ", building.getCurrentStep(), "*");
        int numberOfFloors = building.getNumberOfFloors();
        Lift lift = building.getLift();
        
        
        for (int i = numberOfFloors; i > 0; i--) {
            Floor floor = building.getFloorByNumber(i);
            formatter.format("%s %-2d %s", "¹:", floor.getNumber(), "||");

            if (lift.getCurrentFloor() == floor.getNumber()) {
                StringBuilder liftData = new StringBuilder();
                for (Passenger passenger : lift.getPassengers()) {
                    liftData.append(passenger.getDestination() + ",");
                }
                String sign = getDirectionSign(lift.getDirection());
                formatter.format("%s%17s%s||", sign, liftData.toString(), sign);
                formatter.format("%s%d||", "out:", building.getNumberGonesFromLift());
            } else {

                formatter.format("%22s", "                   ||     ||");
            }
            
            StringBuilder passOnFloor=new StringBuilder();
            for (Passenger passenger : floor.getAllPassengers()) {
                passOnFloor.append(passenger.getDestination() + ",");
            }
            formatter.format(passOnFloor.toString()+"\n");
        }
        
        
        formatter.close();
        return buildingState.toString();
    }

    private String getDirectionSign(LiftDirection direction) {
        if (direction == LiftDirection.UP) {
            return "^";
        }
        return "v";
    }
}
