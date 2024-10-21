package classes;

public class InvigilatorRoom {
    String locationId;
    String invigilator;
    String reliever;

    public InvigilatorRoom(String locationId, String invigilator, String reliever) {
        this.locationId = locationId;
        this.invigilator = invigilator;
        this.reliever = reliever;
    }

    public String getLocationId() {
        return locationId;
    }

    public String getInvigilator() {
        return invigilator;
    }

    public String getReliever() {
        return reliever;
    }

    public void setInvigilator(String invigilator) {
        this.invigilator = invigilator;
    }

    public void setReliever(String reliever) {
        this.reliever = reliever;
    }
}
