package lift_app.entities;

public class BuildingState {

    private Floor[] floors;
    private Lift lift;
    
    private int currentStep;
    
    public BuildingState() {

    }
    
    
    public int getCurrentStep() {
        return currentStep;
    }


    public void setCurrentStep(int currentStep) {
        this.currentStep = currentStep;
    }


    public int getNumberOfFloors() {
        return floors.length;
    }
    
    public boolean isFloorExist(int number) {
        if(number>=1&& number<=(floors.length)){
            return true;
        }
        return false;
    }
    public Floor getFloorByNumber(int number) {
        return floors[number-1];
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
}
