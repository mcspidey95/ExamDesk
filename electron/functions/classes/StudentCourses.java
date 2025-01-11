package classes;
import java.util.List;
import java.util.ArrayList;

public class StudentCourses {
    private String rollNumber;
    private List<String> courses;

    public StudentCourses(String rollNumber) {
        this.rollNumber = rollNumber;
        this.courses = new ArrayList<>();
    }

    public void addCourse(String courseCode) {
        courses.add(courseCode);
    }

    public List<String> getCourses() {
        return courses;
    }

    @Override
    public String toString() {
        return rollNumber + "\t" + courses;
    }
}
