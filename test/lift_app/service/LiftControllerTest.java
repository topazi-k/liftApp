package lift_app.service;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;

import lift_app.entities.BuildingState;
import lift_app.entities.Floor;
import lift_app.entities.Lift;
import lift_app.entities.Lift.Direction;
import lift_app.entities.Passenger;

class LiftControllerTest {

    private static BuildingState createTestBuilding(Direction direction, int currentFloor) {
        BuildingState building = new BuildingState();
        Floor[] floors = new Floor[5];
        for (int i = 0; i < floors.length; i++) {
            Floor floor = new Floor(i + 1);
            floors[i] = floor;
        }
        building.setFloors(floors);
        Lift lift = new Lift(building.getNumberOfFloors());
        lift.setCurrentFloor(currentFloor);
        lift.setDirection(direction);

        building.setLift(lift);

        return building;
    }

    private static void setPassengersOnFloor(BuildingState building, int floorNumber, int numberOfPassengers,
            int destination) {
        for (int i = 0; i < numberOfPassengers; i++) {
            Passenger passenger = new Passenger();
            passenger.setDestination(destination);
            building.getFloorByNumber(floorNumber).addPassenger(passenger);
        }
    }

    @Test
    public void shouldStopOnNextFloorWhenLiftIsNotFullAndLiftPassengersNeedFurther() {
        int currentFloor = 2;
        int nextExpectedFloor = 3;

        BuildingState building = createTestBuilding(Direction.UP, currentFloor);
        building.incrementStep();

        Passenger passengInLift = new Passenger();
        passengInLift.setDestination(5);
        building.getLift().setPassenger(passengInLift);
        setPassengersOnFloor(building, 3, 3, 5);
        LiftController controller = new LiftController(building);
        controller.init();

        assertEquals(nextExpectedFloor, controller.nextStep().getLift().getCurrentFloor());
    }

    @Test
    public void shouldTakeTwoPassengersToLiftWhenThreePassengersOnFloorButOnePassHasOpposedDirection() {
        int currentFloor = 2;

        BuildingState building = createTestBuilding(Direction.UP, currentFloor);
        building.incrementStep();
        Passenger passengInLift = new Passenger();
        passengInLift.setDestination(5);
        building.getLift().setPassenger(passengInLift);
        setPassengersOnFloor(building, 3, 2, 5);
        setPassengersOnFloor(building, 3, 1, 1);
        LiftController controller = new LiftController(building);
        controller.init();

        int expectedPassengers = building.getLift().getPassengers().size() + 2;
        assertEquals(expectedPassengers, (controller.nextStep().getLift().getPassengers().size()));
    }

    @Test
    public void shouldChangeDirectionOnLastFloor() {
        int currentFloor = 4;

        BuildingState building = createTestBuilding(Direction.UP, currentFloor);
        building.incrementStep();
        Passenger passengInLift = new Passenger();
        passengInLift.setDestination(5);
        building.getLift().setPassenger(passengInLift);
        setPassengersOnFloor(building, 3, 2, 5);

        LiftController controller = new LiftController(building);
        controller.init();

        assertEquals(Direction.DOWN, controller.nextStep().getLift().getDirection());
    }
    
    @Test
    public void shouldChangeDirectionInNonLastFloorsWhenLiftEmptyAndFloorsInDirectionIsEmpty() {
        int currentFloor = 3;

        BuildingState building = createTestBuilding(Direction.UP, currentFloor);
        building.incrementStep();
        
        setPassengersOnFloor(building, 2, 2, 5);

        LiftController controller = new LiftController(building);
        controller.init();
        
        assertEquals(2, (controller.nextStep().getLift().getCurrentFloor()));
        assertEquals(Direction.DOWN, controller.nextStep().getLift().getDirection());
    }

    @Test
    public void shouldStayOnSameFloorWhenOtherFloorsEmptyAndLiftIsEmpty() {
        int currentFloor = 3;

        BuildingState building = createTestBuilding(Direction.UP, currentFloor);
        building.incrementStep();

        setPassengersOnFloor(building, 3, 2, 5);
        setPassengersOnFloor(building, 3, 1, 1);
        LiftController controller = new LiftController(building);
        controller.init();

        assertEquals(currentFloor, (controller.nextStep().getLift().getCurrentFloor()));
    }

    @Test
    public void shouldGoWithNotFullLiftThroughAllFloorsWhenPassengersOnFloorsHasOpposedDirection() {
        int currentFloor = 1;
        int nextExpectedFloor = 5;

        BuildingState building = createTestBuilding(Direction.UP, currentFloor);
        building.incrementStep();

        Passenger passengInLift = new Passenger();
        passengInLift.setDestination(5);
        building.getLift().setPassenger(passengInLift);
        setPassengersOnFloor(building, 2, 3, 1);
        setPassengersOnFloor(building, 3, 3, 2);
        setPassengersOnFloor(building, 4, 3, 3);
        LiftController controller = new LiftController(building);
        controller.init();

        assertEquals(nextExpectedFloor, controller.nextStep().getLift().getCurrentFloor());
    }
    
    
}
