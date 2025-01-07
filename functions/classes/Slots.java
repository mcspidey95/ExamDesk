package classes;
import java.util.List;
import java.util.ArrayList;

public class Slots {
    private List<List<String>> slots;


    public Slots(int n) {
        slots = new ArrayList<>(n);
        // Initialize each slot with an empty list
        for (int i = 0; i < n; i++) {
            slots.add(new ArrayList<>());
        }
    }

    public void addCourseToSlot(int index, String courseCode) {
        if (index >= 0 && index < slots.size()) {
            slots.get(index).add(courseCode);
        } else {
            System.out.println("Index out of bounds");
        }
    }

    public List<String> getSlot(int index) {
        if (index >= 0 && index < slots.size()) {
            return slots.get(index);
        } else {
            System.out.println("Index out of bounds");
            return new ArrayList<>();
        }
    }

    public int getNumberOfSlots() {
        return slots.size();
    }

    public void addNewSlot() {
        slots.add(new ArrayList<>()); // Add a new empty slot
    }

    @Override
    public String toString() {
        return slots.toString();
    }
}
