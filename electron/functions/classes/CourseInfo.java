package classes;

public class CourseInfo {
    private String school;
    private String courseCode;
    private String courseTitle;
    private int studentCount;
    private int proposedDay;

    public CourseInfo(String school, String courseCode, String courseTitle, int studentCount, int proposedDay) {
        this.school = school;
        this.courseCode = courseCode;
        this.courseTitle = courseTitle;
        this.studentCount = studentCount;
        this.proposedDay = proposedDay;
    }

    public String getSchool() { return school; }

    public int getStudentCount() { return studentCount; }

    public String getCourseCode() { return courseCode; }

    public String getCourseTitle() { return courseTitle; }

    public int getProposedDay() { return proposedDay; }

    public void setStudentCount(int studentCount){
        this.studentCount = studentCount;
    }

    @Override
    public String toString() {
        return courseCode + "\t" + courseTitle + "\t" + studentCount;
    }
}