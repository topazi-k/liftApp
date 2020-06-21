package lift_app.entities;

import java.util.ArrayList;
import java.util.List;

import lift_app.entities.Lift.Direction;

public class Floor {
    private int floorNumber;
    private List<Passenger> passengersUp = new ArrayList<>();
    private List<Passenger> passengersDown = new ArrayList<>();

    public Floor(int floorNumber) {
        this.floorNumber = floorNumber;
    }

    public int getNumber() {
        return floorNumber;
    }

    public List<Passenger> getPassengersUpDirection() {
        return passengersUp;
    }

    public List<Passenger> getPassengersDownDirection() {
        return passengersDown;
    }

    public List<Passenger> getPassengersInDirection(Direction direction) {
        if (direction == Direction.UP) {
            return passengersUp;
        }
        return passengersDown;
    }

    public boolean hasPassengersInDirection(Direction direction) {
        if (direction == Direction.UP) {
            return !(passengersUp.isEmpty());
        }
        return !(passengersDown.isEmpty());
    }

    public boolean isEmpty() {
        return (passengersUp.isEmpty() && passengersDown.isEmpty()) ? true : false;
    }

    public void removePassenger(Passenger passenger) {
        if (passenger.getDestination() > floorNumber) {
            passengersUp.remove(passenger);
            return;
        }
        passengersDown.remove(passenger);
    }

    public List<Passenger> getAllPassengers() {
        List<Passenger> allPassengers = new ArrayList<>();
        allPassengers.addAll(passengersDown);
        allPassengers.addAll(passengersUp);
        return allPassengers;

    }

    public void addPassenger(Passenger passenger) {
        if (passenger.getDestination() > floorNumber) {
            passengersUp.add(passenger);
        } else {
            passengersDown.add(passenger);
        }
    }

    public void setPassengers(List<Passenger> passengers) {
        for (Passenger passenger : passengers) {
            if (passenger.getDestination() > floorNumber) {
                passengersUp.add(passenger);
            } else {
                passengersDown.add(passenger);
            }
        }
    }

}
