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
        int nextFloor = findFloorWithPass(floor -> !floor.hasPassengersInDirection(lift.getDirection()));
        return chooseTheNearest(nearestDestinationInLift, nextFloor);
    }

    private Floor findFloorForEmptyLift() {
        int floorNumber = findFloorWithPass(floor -> floor.isEmpty());
        if (floorNumber == Constants.NON_EXISTING_FLOOR) {
            lift.changeDirection();
            floorNumber = findFloorWithPass(floor -> floor.isEmpty());
        }
        if (floorNumber == Constants.NON_EXISTING_FLOOR) {
            return currentFloor;
        }
        return building.getFloorByNumber(floorNumber);
    }

    private int findFloorWithPass(FloorChecker checker) {
        int next = getNextNumberInDirection(currentFloor.getNumber());
        while (next != Constants.NON_EXISTING_FLOOR) {
            Floor nextFloor = building.getFloorByNumber(next);
            if (checker.isEmpty(nextFloor)) {
                next = getNextNumberInDirection(next);
                continue;
            } else {
                return next;
            }
        }
        return Constants.NON_EXISTING_FLOOR;
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
