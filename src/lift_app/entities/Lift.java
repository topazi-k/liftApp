package lift_app.entities;

import java.util.ArrayList;
import java.util.List;

import lift_app.service.PassengerComparator;

public class Lift {

    private List<Passenger> sortedPassengers = new ArrayList<>();
    private Direction currentDirection=Direction.UP;
    private int currentFloor;
    private int numberOfFloors;

    public Lift(int numberOfFloors) {
        this.numberOfFloors = numberOfFloors;
        this.currentFloor = Constants.FIRST_FLOOR;
    }

    
    
    private PassengerComparator comparator = new PassengerComparator();

    public enum Direction {
        UP, DOWN
    };

    public boolean isEmpty() {
        return (sortedPassengers.size() == 0) ? true : false;
    }

    public boolean isFull() {
        return (sortedPassengers.size() == Constants.MAX_PASSANGERS_LIFT) ? true : false;
    }

    public Passenger getFirstPassenger() {
        return sortedPassengers.get(0);
    }

    public Passenger getLastPassenger() {
        return sortedPassengers.get(sortedPassengers.size() - 1);
    }

    public List<Passenger> getPassengers() {
        return this.sortedPassengers;
    }

    public void setPassenger(Passenger passenger) {
        if (sortedPassengers.size() > Constants.MAX_PASSANGERS_LIFT) {
            throw new RuntimeException("Unexpected passengers amount");
        } else {
            this.sortedPassengers.add(passenger);
            sortedPassengers.sort(comparator);
        }
    }
    
    public void removePassenger(Passenger passenger) {
        sortedPassengers.remove(passenger);
    }

    public int getNextDestination() {
        if (currentDirection == Direction.UP) {
            return sortedPassengers.get(0).getDestination();
        }
        return sortedPassengers.get(sortedPassengers.size() - 1).getDestination();
    }


    public Direction getDirection() {
        return currentDirection;
    }

    public void setDirection(Direction direction) {
        this.currentDirection = direction;
    }

    public int getCurrentFloor() {
        return currentFloor;
    }

    public void setCurrentFloor(int currentFloor) {
        this.currentFloor = currentFloor;
        if (currentFloor == numberOfFloors) {
            setDirection(Direction.DOWN);

        } else if (currentFloor == 1) {
            setDirection(Direction.UP);
        }
    }

    public void changeDirection() {
        if (currentDirection == Direction.UP) {
            currentDirection = Direction.DOWN;
        } else {
            currentDirection = Direction.DOWN;
        }
    }
}
