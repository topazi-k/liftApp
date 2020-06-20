package lift_app.service;

import java.util.Comparator;

import lift_app.entities.Passenger;

public class PassengerComparator implements Comparator<Passenger> {

    public int compare(Passenger p1, Passenger p2) {
        if (p1.getDestination() == p2.getDestination()) {
            return 0;
        }
        if (p1.getDestination() > p2.getDestination()) {
            return 1;
        } else {
            return -1;
        }
    }

}
