package lift_app.entities;

public class BuildingState {

    private Floor[] floors;
    private Lift lift;
    private int currentStep;
    private int numberGonesFromLift;

    public int getNumberGonesFromLift() {
        return numberGonesFromLift;
    }

    public void setNumberGonesFromLift(int numberGonesFromLift) {
        this.numberGonesFromLift = numberGonesFromLift;
    }
    
    public void incrementStep() {
        currentStep++;
    }
    public int getCurrentStep() {
        return currentStep;
    }

    public void setCurrentStep(int currentStep) {
        this.currentStep = currentStep;
    }

    public Floor[] getFloors() {
        return floors;
    }

    public void setFloors(Floor[] floors) {
        this.floors = floors;
    }

    public Lift getLift() {
        return lift;
    }

    public void setLift(Lift lift) {
        this.lift = lift;
    }

    public int getNumberOfFloors() {
        return floors.length;
    }

    public boolean isFloorExist(int number) {
        if (number >= 1 && number <= (floors.length)) {
            return true;
        }
        return false;
    }

    public Floor getFloorByNumber(int number) {
        return floors[number - 1];
    }

}
