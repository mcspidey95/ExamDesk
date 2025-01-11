package classes;

/*static class StudentRecord {
    private String academicYear;
    private String school;
    private String examination;
    private String programCode;
    private String programName;
    private String semNo;
    private String semRoman;
    private String uid;
    private String rollNumber;
    private String studentName;
    private String section;
    private String courseEligibility;
    private String courseCode;
    private String courseTitle;

    public StudentRecord(String academicYear, String school, String examination, String programCode, String programName, String semNo, String semRoman, String uid, String rollNumber, String studentName, String section, String courseEligibility, String courseCode, String courseTitle) {
        this.academicYear = academicYear;
        this.school = school;
        this.examination = examination;
        this.programCode = programCode;
        this.programName = programName;
        this.semNo = semNo;
        this.semRoman = semRoman;
        this.uid = uid;
        this.rollNumber = rollNumber;
        this.studentName = studentName;
        this.section = section;
        this.courseEligibility = courseEligibility;
        this.courseCode = courseCode;
        this.courseTitle = courseTitle;
    }

    public String getAcademicYear() { return academicYear; }
    public String getSchool() { return school; }
    public String getExamination() { return examination; }
    public String getProgramCode() { return programCode; }
    public String getProgramName() { return programName; }
    public String getSemNo() { return semNo; }
    public String getSemRoman() { return semRoman; }
    public String getUid() { return uid; }
    public String getRollNumber() { return rollNumber; }
    public String getStudentName() { return studentName; }
    public String getSection() { return section; }
    public String getCourseEligibility() { return courseEligibility; }
    public String getCourseCode() { return courseCode; }
    public String getCourseTitle() { return courseTitle; }

    @Override
    public String toString() {
        return academicYear + "\t" + school + "\t" + examination + "\t" + programCode + "\t" + programName + "\t" + semNo + "\t" + semRoman + "\t" + uid + "\t" + rollNumber + "\t" + studentName + "\t" + section + "\t" + courseEligibility + "\t" + courseCode + "\t" + courseTitle;
    }
}*/

public class StudentRecord {
    private String school;
    private String programName;
    private String sem;
    private String rollNumber;
    private String studentName;
    private String section;
    private String courseCode;

    public StudentRecord(String school, String programName, String sem, String rollNumber, String studentName, String section, String courseCode) {
        this.school = school;
        this.programName = programName;
        this.sem = sem;
        this.rollNumber = rollNumber;
        this.studentName = studentName;
        this.section = section;
        this.courseCode = courseCode;
    }

    public String getSchool() { return school; }
    public String getProgramName() { return programName; }
    public String getSemNo() { return sem; }
    public String getRollNumber() { return rollNumber; }
    public String getStudentName() { return studentName; }
    public String getSection() { return section; }
    public String getCourseCode() { return courseCode; }

    @Override
    public String toString() {
        return school + "\t" + programName + "\t" + sem + "\t" + "\t" + rollNumber + "\t" + studentName + "\t" + section + "\t" + courseCode;
    }
}
