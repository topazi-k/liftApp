package lift_app.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import lift_app.entities.BuildingState;
import lift_app.entities.Constants;
import lift_app.entities.Floor;
import lift_app.entities.Lift;
import lift_app.entities.Lift.Direction;
import lift_app.entities.Passenger;

public class DataGenerator {
    Random random = new Random();

    public BuildingState generateStartData() {

        BuildingState building = new BuildingState();
        Floor[] floors = createFloors();
        for (Floor floor : floors) {
            floor.setPassengers(generatePassengerOnFloor(floor, floors.length));
        }
        building.setFloors(floors);
        Lift lift = new Lift(building.getNumberOfFloors());
        lift.setDirection(Direction.UP);
        building.setLift(lift);
        return building;
    }

    private Floor[] createFloors() {
        int numberOfFloors = generateRandomInRange(Constants.MIN_FLOORS, Constants.MAX_FLOORS);
        Floor[] floors = new Floor[numberOfFloors];
        for (int i = 0; i < numberOfFloors; i++) {
            Floor floor = new Floor(i+1);
            floors[i] = floor;
        }
        return floors;
    }

    private List<Passenger> generatePassengerOnFloor(Floor floor, int numberOfFloors) {
        List<Passenger> passengers = new ArrayList<>();
        int numberOfPassengers = random.nextInt(Constants.MAX_PASS_ON_FLOOR_ON_START + 1);
        for (int i = 0; i < numberOfPassengers; i++) {
            Passenger passenger = new Passenger();
            int destination;
            do {
                destination = generateRandomInRange(1, numberOfFloors);
            } while (destination == floor.getNumber());
            passenger.setDestination(destination);
            passengers.add(passenger);
        }
        return passengers;
    }

    public int generateRandomInRange(int min, int max) {
        return random.nextInt((max - min) + 1) + min;
    }
}
