package lift_app.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import lift_app.entities.Building;
import lift_app.entities.Constants;
import lift_app.entities.Floor;
import lift_app.entities.Lift;
import lift_app.entities.Lift.LiftDirection;
import lift_app.entities.Passenger;

public class LiftController {

    private int stepsCounter = 0;
    private Building building;
    Lift lift;

    Floor currentFloor;
    Floor nextFloor;
    Random random = new Random();

    public LiftController(Building building) {
        this.building = building;
    }

    public void init() {
        lift = building.getLift();
        currentFloor = building.getFloorByNumber(lift.getCurrentFloor());

    };

    public void nextStep() {
        stepsCounter++;
        if (stepsCounter != 1) {
            currentFloor = building.getFloorByNumber(findNextFloor());
        }
        lift.setCurrentFloor(currentFloor.getNumber());
        List<Passenger> passengersThatOut = lift.getPassengersOut();
        if (!currentFloor.isEmpty()) {
            getPassengersIn();
        }
        createNewDestination(passengersThatOut);
        currentFloor.setPassengers(passengersThatOut);
    }

    private int findNextFloor() {
        if (lift.isEmpty()) {
            nextFloor = findNextFloorForEmptyLift();
            return nextFloor.getNumber();
        }
        int nextFloorInsideLift = lift.getNextFloorNumber();
        if (lift.getPassengers().size() == Constants.MAX_PASSANGERS_LIFT) {
            return nextFloorInsideLift;
        }
        int travelCompanionFloor = findFloorWithTravelCompanions(lift.getCurrentFloor());
        return chooseTheNearest(nextFloorInsideLift, travelCompanionFloor);
    }

    public int chooseTheNearest(int nextFloorInsideLift, int travelCompanionFloor) {
        if (travelCompanionFloor == 0) {
            return nextFloorInsideLift;
        } else if (nextFloorInsideLift < travelCompanionFloor && lift.getDirection() == LiftDirection.UP) {
            return nextFloorInsideLift;
        } else if (nextFloorInsideLift > travelCompanionFloor && lift.getDirection() == LiftDirection.DOWN) {
            return nextFloorInsideLift;
        }
        return travelCompanionFloor;
    }

    private Floor findNextFloorForEmptyLift() {
        Floor nonEmptyFloor = checkFloorsInCurrentDirection();
        if (nonEmptyFloor == null) {
            lift.changeDirection();
            nonEmptyFloor = checkFloorsInCurrentDirection();
        }
        return nonEmptyFloor;
    }

    public Floor checkFloorsInCurrentDirection() {
        Floor nextFloor = null;
        int nextFloorN = currentFloor.getNumber();
        do {
            nextFloorN = getNextNumberInDirection(nextFloorN);
            if (building.isFloorExist(nextFloorN)) {
                nextFloor = building.getFloorByNumber(nextFloorN);
            } else {
                return nextFloor;
            }
        } while (nextFloor.isEmpty());
        return nextFloor;
    }

    private int getNextNumberInDirection(int currentFloor) {
        return (lift.getDirection() == LiftDirection.UP) ? ++currentFloor : --currentFloor;
    }

    private int findFloorWithTravelCompanions(int currentFloor) {
        int nextFloorNumber = getNextNumberInDirection(currentFloor);
        while (building.isFloorExist(nextFloorNumber)) {
            Floor nextFloor = building.getFloorByNumber(nextFloorNumber);
            if (nextFloor.hasPassengersInDirection(lift.getDirection())) {
                return nextFloorNumber;
            }
            nextFloorNumber++;
        }
        return 0;
    }

    private List<Passenger> getPassengersIn() {

        if (lift.isEmpty() && (!currentFloor.getAllPassengers().isEmpty()) && (!hasPassInCurrDirect())) {
            lift.changeDirection();
        }
        List<Passenger> passengersOnFloor = currentFloor.getPassengersInDirection(lift.getDirection());
        List<Passenger> passengersThatIn = new ArrayList<>();
        for (Passenger passenger : passengersOnFloor) {
            if (lift.isFull()) {
                break;
            }
            lift.setPassenger(passenger);
            passengersThatIn.add(passenger);
        }
        for (Passenger p : passengersThatIn) {
            currentFloor.removePassengerInDirection(p);
        }
        return passengersThatIn;
    }

    private List<Passenger> createNewDestination(List<Passenger> passengers) {
        for (Passenger p : passengers) {
            int newDestination;
            do {
                newDestination = generateFloorNumber();
            } while (newDestination == currentFloor.getNumber());
            p.setDestination(newDestination);
        }
        return passengers;
    }

    private boolean hasPassInCurrDirect() {
        if (lift.getDirection() == LiftDirection.UP) {
            return !(currentFloor.getPassengersUpDirection().isEmpty());
        }
        return !(currentFloor.getPassengersDownDirection().isEmpty());
    }

    private int generateFloorNumber() {
        return random.nextInt(building.getNumberOfFloors()) + 1;
    }
}
