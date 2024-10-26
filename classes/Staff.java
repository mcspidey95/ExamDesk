package classes;

import java.util.List;

public class Staff{
    String staffID;
    String staffName;
    String staffDept;
    int totalSlots;
    String usedLastSlot;
    String[] notToInvigilate;

    public Staff(String staffID, String staffName, String staffDept, int totalSlots, String usedLastSlot, String[] notToInvigilate) {
        this.staffID = staffID;
        this.staffName = staffName;
        this.staffDept = staffDept;
        this.totalSlots = totalSlots;
        this.usedLastSlot = usedLastSlot;
        this.notToInvigilate = notToInvigilate;
    }

    public String getStaffID() {
        return staffID;
    }

    public String getStaffName() {
        return staffName;
    }

    public String getStaffDept() {
        return staffDept;
    }

    public int getTotalSlots() {
        return totalSlots;
    }

    public String getusedLastSlot(){
        return usedLastSlot;
    }

    public void setusedLastSlot(String usedLastSlot){
        this.usedLastSlot = usedLastSlot;
    }

    public void setTotalSlots(int totalSlots) {
        this.totalSlots = totalSlots;
    }

    public boolean isNotToInvigilate(List<String> slots) {
        for (String course : notToInvigilate) {
            if (slots.contains(course)) {
                return false;
            }
        }
        return true;
    }
}
