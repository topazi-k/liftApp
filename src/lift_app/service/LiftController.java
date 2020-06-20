package lift_app.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import lift_app.entities.BuildingState;
import lift_app.entities.Constants;
import lift_app.entities.Floor;
import lift_app.entities.Lift;
import lift_app.entities.Lift.LiftDirection;
import lift_app.entities.Passenger;

public class LiftController {

    private int stepsCounter = 0;
    private BuildingState building;
    private Lift lift;
    private Floor currentFloor;

    private Random random = new Random();

    public LiftController(BuildingState building) {
        this.building = building;
    }

    public void init() {
        lift = building.getLift();
        currentFloor = building.getFloorByNumber(lift.getCurrentFloor());

    };

    public void nextStep() {
        stepsCounter++;
        building.setCurrentStep(stepsCounter);
        if (stepsCounter != 1) {
            currentFloor = building.getFloorByNumber(findNextFloor());
        }
        lift.setCurrentFloor(currentFloor.getNumber());

        List<Passenger> passengersFromLift = takePassengersOutOfLift();
        building.setNumberGonesFromLift(passengersFromLift.size());
        takePassengersToLift();
        createNewDestination(passengersFromLift);
        currentFloor.setPassengers(passengersFromLift);
    }

    private int findNextFloor() {
        if (lift.isEmpty()) {
            return findFloorForEmptyLift().getNumber();
        }
        int nearestDestinationInLift = lift.getNextDestination();
        if (lift.getPassengers().size() == Constants.MAX_PASSANGERS_LIFT) {
            return nearestDestinationInLift;
        }
        int nearestPassengerOutside = findNearestByPassengersDirection(lift.getCurrentFloor());
        return chooseTheNearest(nearestDestinationInLift, nearestPassengerOutside);
    }

    private Floor findFloorForEmptyLift() {
        Floor floorInDirection = findNearestNonEmptyByDirection(currentFloor.getNumber());
        if (floorInDirection == null) {
            lift.changeDirection();
            floorInDirection = findNearestNonEmptyByDirection(currentFloor.getNumber());
        }
        return floorInDirection;
    }

    public Floor findNearestNonEmptyByDirection(int currentFloor) {
        int next = getNextNumberInDirection(currentFloor);
        while (next != Constants.NON_EXISTING_FLOOR) {
            Floor nextFloor = building.getFloorByNumber(next);
            if (nextFloor.isEmpty()) {
                next = getNextNumberInDirection(next);
                continue;
            } else {
                return nextFloor;
            }
        }
        return null;
    }

    private int findNearestByPassengersDirection(int currentFloor) {
        int next = getNextNumberInDirection(currentFloor);
        while (next != Constants.NON_EXISTING_FLOOR) {
            Floor nextFloor = building.getFloorByNumber(next);
            if (nextFloor.hasPassengersInDirection(lift.getDirection())) {
                return next;
            }
            next = getNextNumberInDirection(next);
        }
        return 0;
    }

    private int getNextNumberInDirection(int currentFloor) {
        int nexNumberInDirection = (lift.getDirection() == LiftDirection.UP) ? ++currentFloor : --currentFloor;
        if (building.isFloorExist(nexNumberInDirection)) {
            return nexNumberInDirection;
        }
        return Constants.NON_EXISTING_FLOOR;
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

    public List<Passenger> takePassengersOutOfLift() {
        List<Passenger> droppedPassengers = new ArrayList<>();
        for (Passenger passenger : lift.getPassengers()) {
            if (passenger.getDestination() == currentFloor.getNumber()) {
                droppedPassengers.add(passenger);
            }
        }
        for (Passenger passenger : droppedPassengers) {
            lift.removePassenger(passenger);
        }
        return droppedPassengers;
    }

    private void takePassengersToLift() {
        if (currentFloor.isEmpty()) {
            return;
        }
        if (lift.isEmpty() && (!currentFloor.hasPassengersInDirection(lift.getDirection()))) {
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
            currentFloor.removePassenger(p);
        }
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

   

    private int generateFloorNumber() {
        return random.nextInt(building.getNumberOfFloors()) + 1;
    }
}
