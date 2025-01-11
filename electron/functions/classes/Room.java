package classes;

public class Room {
    String locationId;
    int seatingCapacity;
    int subjectCapacity;

    public Room(String locationId, int seatingCapacity, int subjectCapacity) {
        this.locationId = locationId;
        this.seatingCapacity = seatingCapacity;
        this.subjectCapacity = subjectCapacity;
    }

    public String getLocationId() {
        return locationId;
    }

    public int getSeatingCapacity() {
        return seatingCapacity;
    }

    public int getSubjectCapacity() {
        return subjectCapacity;
    }
}