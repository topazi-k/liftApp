package lift_app.entities;

import java.util.ArrayList;
import java.util.List;

import lift_app.entities.Lift.LiftDirection;

public class Floor {
    private int floorNumber;
    private List<Passenger> passengersUp = new ArrayList<>();
    private List<Passenger> passengersDown = new ArrayList<>();

    public List<Passenger> getPassengersUpDirection() {
        return passengersUp;
    }

//   public List<Passenger> getMostPassengers() {
 //        return (passengersUp.size() > passengersDown.size()) ? passengersUp : passengersDown;
 //   }

    public List<Passenger> getPassengersDownDirection() {
        return passengersDown;
    }
    
    public List<Passenger> getPassengersInDirection(LiftDirection direction) {
        if(direction==LiftDirection.UP) {
            return passengersUp;
        }
        return passengersDown;
    }
    
    public boolean hasPassengersInDirection(LiftDirection direction) {
        if(direction==LiftDirection.UP) {
            return !(passengersUp.isEmpty());
        }
        return !(passengersDown.isEmpty());
    }
    
    public boolean isEmpty() {
        return (passengersUp.isEmpty() && passengersDown.isEmpty()) ? true : false;
    }

    public int getNumber() {
        return floorNumber;
    }
    
    
    public void setNumber(int floorNumber) {
        this.floorNumber = floorNumber;
    }

   
    public void removePassengerInDirection(Passenger passenger) {
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
