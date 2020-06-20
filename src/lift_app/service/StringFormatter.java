package lift_app.service;

import lift_app.entities.BuildingState;
import lift_app.entities.Floor;
import lift_app.entities.Passenger;

public class StringFormatter {

    public String formatData(BuildingState building) {
        StringBuilder data = new StringBuilder();

        data.append("*Step - " + building.getCurrentStep() + "*\n\n");
        int numberOfFloors = building.getNumberOfFloors();
        for (int i = numberOfFloors - 1; i > 0; i--) {
            Floor floor = building.getFloorByNumber(i);
            for (Passenger passenger : floor.getAllPassengers()) {
                data.append(passenger.getDestination() + ",");
            }
            data.append("||floor "+floor.getNumber());
            if (building.getLift().getCurrentFloor() == floor.getNumber()) {
                data.append("lift here: " + building.getLift().getDirection()+"|");
                for(Passenger passenger: building.getLift().getPassengers()){
                    data.append(passenger.getDestination()+",");
                }
            }
            data.append("\n\n");
        }
        return data.toString();
    }
}
