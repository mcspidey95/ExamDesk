import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

public class StudentData {

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

    static class StudentRecord {
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

    static class CourseInfo {
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

        @Override
        public String toString() {
            return courseCode + "\t" + courseTitle + "\t" + studentCount;
        }
    }

    static class StudentCourses {
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

    public static class Slots {
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

    public static class Room {
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

    public static class Staff{
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

        public boolean isNotToInvigilate(String courseCode) {
            for (String course : notToInvigilate) {
                if (course.equals(courseCode)) {
                    return false;
                }
            }
            return true;
        }
    }

    public static class InvigilatorRoom {
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

    public static void assignCoursesToSlots(Slots slots, Map<String, StudentCourses> studentCoursesMap, List<CourseInfo> leftoverCourses, List<CourseInfo> courseInfoList) {
        int slotIndex = 1;
        int clashedCourses = 0;

        ArrayList<String> blacklist = new ArrayList<>();
        try (BufferedReader brr = new BufferedReader(new FileReader("blacklist.txt"))) {
            String line;
            while ((line = brr.readLine()) != null) {
                blacklist.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    
        for (CourseInfo courseInfo : leftoverCourses) {
            slotIndex = 1;
            if(courseInfo.getProposedDay() != 999){
                slotIndex = (courseInfo.getProposedDay()*3)-2;
            }
            String initialCourse = courseInfo.courseCode;

            if(blacklist.contains(initialCourse)){
                continue;
            }
            
            
            //Saves all Courses that cant be together with the initial course
            List<String> matchedCourses = new ArrayList<>();
            matchedCourses.add(initialCourse);
    
            // Find all students who are enrolled in the initial course and collect all their courses
            for (Map.Entry<String, StudentCourses> entry : studentCoursesMap.entrySet()) {
                List<String> studentCourses = entry.getValue().getCourses();
                if (studentCourses.contains(initialCourse)) {
                    // Append all the student's courses to matchedCourses if they aren't already there
                    for (String course : studentCourses) {
                        if (!matchedCourses.contains(course)) {
                            matchedCourses.add(course);
                        }
                    }
                }
            }

            
            // Check if any of the matched courses are in the current, previous, or next slot
            boolean conflict = true;
            while (conflict) {
                conflict = false;
                Boolean fixedBreak = true;
                Set<String> combinedSlotCourses = new HashSet<>();
                Set<String> currentSlotCourses = new HashSet<>();
                Set<String> directSlotCourses = new HashSet<>();

                int modIndex = (slotIndex-1)%3;
                int mainslotIndex = slotIndex - 1;
                int altslotIndex1 = 20;
                int altslotIndex2 = 20;

                if(modIndex == 0){
                    altslotIndex1 = slotIndex;
                    altslotIndex2 = slotIndex + 1;
                }
                else if(modIndex == 1){
                    altslotIndex1 = slotIndex - 2;
                    altslotIndex2 = slotIndex;
                }
                else if(modIndex == 2){
                    altslotIndex1 = slotIndex - 2;
                    altslotIndex2 = slotIndex - 3;
                }
                
                if(fixedBreak){
                    if(modIndex == 0 && altslotIndex1 < slots.getNumberOfSlots()){
                        directSlotCourses.addAll(slots.getSlot(altslotIndex1));
                    }
                    else if(modIndex == 1 && altslotIndex2 < slots.getNumberOfSlots()){
                        directSlotCourses.addAll(slots.getSlot(altslotIndex1));
                        directSlotCourses.addAll(slots.getSlot(altslotIndex2));
                    }
                    else if(modIndex == 2){
                        directSlotCourses.addAll(slots.getSlot(altslotIndex1));
                    }
                }

                combinedSlotCourses.addAll(slots.getSlot(mainslotIndex)); // Current slot
                currentSlotCourses.addAll(slots.getSlot(mainslotIndex)); // Current slot

                if(altslotIndex1 < slots.getNumberOfSlots()) {
                    combinedSlotCourses.addAll(slots.getSlot(altslotIndex1));
                }

                if(altslotIndex2 < slots.getNumberOfSlots()) {
                    combinedSlotCourses.addAll(slots.getSlot(altslotIndex2));
                }

                int totalStudentCount = 0;
                for (String courseCode : currentSlotCourses) {
                    // Iterate through the courseInfoList to find matching course codes
                    for (CourseInfo course : courseInfoList) {
                        if (course.getCourseCode().equals(courseCode) || course.getCourseCode().equals(initialCourse)) {
                            totalStudentCount += course.getStudentCount();
                        }
                    }
                }

                for (String matchedCourse : matchedCourses) {
                    if (currentSlotCourses.contains(matchedCourse) || directSlotCourses.contains(matchedCourse) || totalStudentCount > 5000) { //6912
                        // Conflict found, increment the slot index and try again
                        slotIndex++;
                        if (slotIndex > slots.getNumberOfSlots()) {
                            slots.addNewSlot();
                        }
                        conflict = true;
                        break;
                    }
                    if (combinedSlotCourses.contains(matchedCourse)) {
                        clashedCourses++;
                        if (clashedCourses > 1) {
                            // Conflict found, increment the slot index and try again
                            slotIndex++;
                            clashedCourses = 0;
                            if (slotIndex > slots.getNumberOfSlots()) {
                                slots.addNewSlot();
                            }
                            conflict = true;
                            break;
                        }
                    }
                }

                /*System.out.println("Initial Course: " + initialCourse);
                System.out.println("Combined Slot Courses: " + combinedSlotCourses);
                System.out.println("Matched Courses: " + matchedCourses);
                System.out.println("clashedCourses: "+ clashedCourses);
                System.out.println();*/
                
                /*if(initialCourse.equals("CSE3128")){
                    System.out.println(currentSlotCourses);
                    System.out.println(combinedSlotCourses);
                }
                if(initialCourse.equals("CSE3005") || initialCourse.equals("CSE3128")){
                    System.out.println(initialCourse);
                }*/
            }
            // Add the initial course to the current slot once there are no conflicts
            slots.addCourseToSlot(slotIndex - 1, initialCourse);
        }
    }

    public static void generateSeatingArrangement(Slots slots, Map<String, StudentCourses> studentCoursesMap, int slotIndex, List<Room> rooms, Map<String, List<String>> seatingArrangement){

        try (BufferedReader brrr = new BufferedReader(new FileReader("Rooms.txt"))) {
            String line;

            brrr.readLine();

            while ((line = brrr.readLine()) != null) {
                String[] details = line.split("\t");  // Split by whitespace
                String locationId = details[1];
                int seatingCapacity = Integer.parseInt(details[2]);
                int subjectCapacity = Integer.parseInt(details[3]);
                rooms.add(new Room(locationId, seatingCapacity, subjectCapacity));
            }

            brrr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<String> coursesInSlot = slots.getSlot(slotIndex);

        //Add courses to a stack in reverse order
        Stack<String> courseStack = new Stack<>();
        for (int i = coursesInSlot.size() - 1; i >= 0; i--) {
            courseStack.push(coursesInSlot.get(i));
        }

        //Initialize variables for the two courses
        String courseA = courseStack.isEmpty() ? null : courseStack.pop();
        String courseB = courseStack.isEmpty() ? null : courseStack.pop();

        //Create a map to keep track of students for each course
        Map<String, Queue<String>> courseStudentMap = new LinkedHashMap<>();
        for (String course : coursesInSlot) {
            Queue<String> studentsInCourse = new LinkedList<>();
            for (Map.Entry<String, StudentCourses> entry : studentCoursesMap.entrySet()) {
                String studentRollNumber = entry.getKey();
                List<String> studentCourses = entry.getValue().getCourses();
                if (studentCourses.contains(course)) {
                    studentsInCourse.add(studentRollNumber);
                }
            }
            courseStudentMap.put(course, studentsInCourse);
        }

        
        for (Room room : rooms) {
            List<String> assignedStudents = new ArrayList<>();
            int deskCount = 0;



            while (deskCount < room.getSeatingCapacity()) {

                if(courseA == null && courseB == null) {
                    courseA = courseStack.isEmpty() ? null : courseStack.pop();
                    courseB = courseStack.isEmpty() ? null : courseStack.pop();
                }

                if(courseStack.isEmpty() && courseA == null && courseB == null) {
                    break;
                }

                // Assign one student from courseA if available
                if (courseA != null && courseB != null && deskCount < room.getSeatingCapacity()) {
                    //System.out.println("courseA: "+courseA + "studentsA: "+courseStudentMap.get(courseA).size());
                    Queue<String> studentsA = courseStudentMap.get(courseA);
                    if (studentsA != null && !studentsA.isEmpty() && courseB != null) {
                        assignedStudents.add(studentsA.poll() + " (" + courseA + ")");
                        deskCount++;
                    }

                    if (studentsA == null || studentsA.isEmpty()) {
                        //System.out.println("gandu");
                        courseA = courseStack.isEmpty() ? null : courseStack.pop();
                    }
                }

                // Assign one student from courseB if available
                if (courseB != null && courseA != null && deskCount < room.getSeatingCapacity()) {
                    //System.out.println("courseB: "+courseB + "studentsB: "+courseStudentMap.get(courseB).size());
                    Queue<String> studentsB = courseStudentMap.get(courseB);
                    if (studentsB != null && !studentsB.isEmpty()) {
                        assignedStudents.add(studentsB.poll() + " (" + courseB + ")");
                        deskCount++;
                    }

                    if (studentsB == null || studentsB.isEmpty()) {
                        courseB = courseStack.isEmpty() ? null : courseStack.pop();
                    }
                }

                // If only one course is left, add students with a seat gap
                if (courseA != null && courseB == null && deskCount < room.getSeatingCapacity()) {
                    //System.out.println("Only one course left: " + courseA + "\n");
                    Queue<String> studentsA = courseStudentMap.get(courseA);
                    boolean assignSeat = true;
                
                    while (deskCount < room.getSeatingCapacity()) {
                        if (assignSeat && studentsA != null && !studentsA.isEmpty()) {
                            assignedStudents.add(studentsA.poll() + " (" + courseA + ")");
                        } else {
                            assignedStudents.add("PRN//EMPTY (CSE6969)");
                        }
                        assignSeat = !assignSeat;
                        deskCount++;
                    }
                
                    if (studentsA == null || studentsA.isEmpty()) {
                        courseA = courseStack.isEmpty() ? null : courseStack.pop();
                    }
                }

                
                if(courseB != null && !courseStudentMap.get(courseB).isEmpty()){
                    //System.out.println("Pushing course: "+courseB + "students: "+courseStudentMap.get(courseB).size());
                    courseStack.push(courseB);
                }
                courseB = null;

                if(courseA != null && !courseStudentMap.get(courseA).isEmpty()){
                    courseStack.push(courseA);
                }
                courseA = null;
            }

            // Print the assigned students for each room
            //System.out.println("Room: " + room.getLocationId() + " Students: " + assignedStudents);
            seatingArrangement.put(room.getLocationId(), assignedStudents);
        }
    }
    //human computer interaction, optoelectronics, devops tools, database managent
    public static String getDepartment(String course){

        if(course.equals("BBA")) return "SOM";
        if(course.equals("BDC")) return "SOD";
        if(course.equals("MAT")) return "MAT";
        if(course.equals("CHE")) return "CHE";
        if(course.equals("FRL")) return "French";
        if(course.equals("LAW")) return "SOL";
        if(course.equals("BBB")) return "SOM";
        if(course.equals("BBE")) return "SOM";
        if(course.equals("BDF")) return "SOD";
        if(course.equals("MBA")) return "SOM";
        if(course.equals("BBL")) return "SOL";
        if(course.equals("CSA")) return "SOIS";
        if(course.equals("CSE")) return "SOCSE";
        if(course.equals("BSE")) return "SOM";
        if(course.equals("BSM")) return "SOD";
        if(course.equals("PHY")) return "PHY";
        if(course.equals("COM")) return "SOC";
        if(course.equals("EEE")) return "EEE";
        if(course.equals("ECE")) return "ECE";
        if(course.equals("SOC")) return "SOC";
        if(course.equals("BCH")) return "SOC";
        if(course.equals("MEC")) return "MEC";
        if(course.equals("BCL")) return "SOL";
        if(course.equals("BAJ")) return "SOMS";
        if(course.equals("BAL")) return "SOL";
        if(course.equals("CIV")) return "CIV";
        if(course.equals("DES")) return "SOD";
        if(course.equals("MAH")) return "SOC";
        if(course.equals("BAV")) return "SOM";
        if(course.equals("KAN")) return "Kannada";
        if(course.equals("MGT")) return "SOM";
        if(course.equals("PET")) return "PET";
        if(course.equals("ENG")) return "English";

        return course;
    }

    public static void assignInvigilators(Map<String, List<String>> seatingArrangement, List<Staff> invigilators, List<InvigilatorRoom> invigilatorRooms, int slotIndex) {
        Map<String, Set<String>> courseInRoom = new LinkedHashMap<>();

        if(slotIndex%3 == 0){
            for(Staff invigilator : invigilators){
                invigilator.setusedLastSlot("No");
            }
        }

        for(Staff invigilator : invigilators){
            if(invigilator.getusedLastSlot().equals("Yes")) invigilator.setusedLastSlot("No");
        }

        for(Staff invigilator : invigilators){
            if(invigilator.getusedLastSlot().equals("Yes_n")) invigilator.setusedLastSlot("Yes");
        }
        
    
        for (Map.Entry<String, List<String>> entry : seatingArrangement.entrySet()) {
            String roomID = entry.getKey();
            List<String> studentsInRoom = entry.getValue();
            Set<String> uniqueStudents = new HashSet<>();
    
            // Append each unique student name to the set
            for (String student : studentsInRoom) {

                int index = student.indexOf("(");
                int indexEnd = student.indexOf(")");
                uniqueStudents.add(student.substring(index + 1, indexEnd));
            }

            courseInRoom.put(roomID, uniqueStudents);
        }

        for(Map.Entry<String, Set<String>> entry : courseInRoom.entrySet()){
            String roomId = entry.getKey();
            String[] course = entry.getValue().toArray(new String[0]);
            String invigilatorAssigned = "NA";

            if(entry.getValue().size() == 1){

                for(Staff invigilator: invigilators){
                    if(invigilator.getStaffDept().equals(getDepartment(course[0].substring(0,3))) && invigilator.getusedLastSlot().equals("No") && invigilator.getTotalSlots()>0 && invigilator.isNotToInvigilate(course[0])){
                        invigilatorAssigned = invigilator.getStaffID() + "/" + invigilator.getStaffName() + "@" + invigilator.getStaffDept();
                        invigilator.setusedLastSlot("Yes_n");
                        invigilator.setTotalSlots(invigilator.getTotalSlots() - 1);
                        break;
                    }
                }
                invigilatorRooms.add(new InvigilatorRoom(roomId, invigilatorAssigned, "NA"));
            }
            else if(entry.getValue().size() > 1){
                int index = 0;

                while(index < course.length){
                    for(Staff invigilator: invigilators){
                        if(invigilator.getStaffDept().equals(getDepartment(course[index].substring(0,3))) && invigilator.getusedLastSlot().equals("No") && invigilator.getTotalSlots()>0 && invigilator.isNotToInvigilate(course[index])){
                            invigilatorAssigned = invigilator.getStaffID() + "/" + invigilator.getStaffName() + "@" + invigilator.getStaffDept();
                            invigilator.setusedLastSlot("Yes_n");
                            invigilator.setTotalSlots(invigilator.getTotalSlots() - 1);
                            break;
                        }
                    }

                    if(invigilatorAssigned.equals("NA")){
                        index++;
                    }
                    else{
                        break;
                    }
                }
                invigilatorRooms.add(new InvigilatorRoom(roomId, invigilatorAssigned, "NA"));
            }
        }

        for(InvigilatorRoom invigilatorRoom : invigilatorRooms){
            if(invigilatorRoom.getInvigilator().equals("NA")){
                for(Staff invigilator: invigilators){
                    if(invigilator.getusedLastSlot().equals("No") && invigilator.getTotalSlots()>0){
                        invigilatorRoom.setInvigilator((invigilator.getStaffID() + "/" + invigilator.getStaffName() + "@" + invigilator.getStaffDept()));
                        invigilator.setusedLastSlot("Yes_n");
                        invigilator.setTotalSlots(invigilator.getTotalSlots() - 1);
                        break;
                    }
                }
            }
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("Invigilator_Assignment.tsv", true))) {

            for(InvigilatorRoom room: invigilatorRooms){
                String invigilator = room.getInvigilator();
                String invigilatorId = invigilator.substring(0, invigilator.indexOf("/"));
                String invigilatorName = invigilator.substring(invigilator.indexOf("/")+1, invigilator.indexOf("@"));
                String invigilatorDept = invigilator.substring(invigilator.indexOf("@")+1);

                writer.write(((slotIndex/3)+1) + "\t" + (slotIndex%3+1) + "\t" + room.getLocationId() + "\t" + invigilatorId + "\t" + invigilatorName + "\t" + invigilatorDept + "\n");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        /*for(InvigilatorRoom room: invigilatorRooms){
            System.out.println("Room: "+room.getLocationId()+" ---> "+room.getInvigilator());
        }*/
    }

    public static void main(String[] args) {
        String filePath = "StudentRegistration.txt";
        List<StudentRecord> studentRecords = new ArrayList<>();
        Map<String, CourseInfo> courseInfoMap = new LinkedHashMap<>();
        Map<String, StudentCourses> studentCoursesMap = new LinkedHashMap<>();
        Map<List<String>, StudentCourses> programMap = new LinkedHashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split("\t");
                StudentRecord record = new StudentRecord(
                        data[3], // school
                        data[5], // programName
                        data[6], // sem
                        data[1], // rollNumber
                        data[2], // studentName
                        data[7], // section
                        data[8] // courseCode
                        //Faculty details + day + slot + room
                );
                studentRecords.add(record);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (BufferedReader br = new BufferedReader(new FileReader("FinalCourseData.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] details = line.split("\t");

                String school = details[0];
                String courseCode = details[3];
                String courseTitle = details[4];
                int proposedDay = 999;
                if (details.length > 5) {
                    proposedDay = Integer.parseInt(details[5]);
                }

                courseInfoMap.putIfAbsent(courseCode, new CourseInfo(school, courseCode, courseTitle, 0, proposedDay));

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Student Records Created!");

        studentRecords = mergeSort(studentRecords, Comparator.comparing(StudentRecord::getRollNumber));

        System.out.println("Student Records Sorted!");

        for (StudentRecord record : studentRecords) {
            String courseCode = record.courseCode;

            // Update course info       
            if(courseInfoMap.get(courseCode) != null)
            {
                courseInfoMap.get(courseCode).studentCount++;
            }      
    
            // Update student courses info
            String rollNumber = record.rollNumber + "//" + record.studentName;
            studentCoursesMap.putIfAbsent(rollNumber, new StudentCourses(rollNumber));
            studentCoursesMap.get(rollNumber).addCourse(courseCode);

            // Update program map
            String programCode = record.programName;
            String semNo = record.sem;
            programMap.putIfAbsent(Arrays.asList(programCode, semNo), new StudentCourses(programCode));
            //only add course if unique
            if(!programMap.get(Arrays.asList(programCode, semNo)).getCourses().contains(courseCode)){
                programMap.get(Arrays.asList(programCode, semNo)).addCourse(courseCode);
            }
        }
    

        List<CourseInfo> courseInfoList = new ArrayList<>(courseInfoMap.values());
        courseInfoList = mergeSort(courseInfoList, Comparator.comparing(CourseInfo::getStudentCount).reversed());
        courseInfoList = mergeSort(courseInfoList, Comparator.comparing(CourseInfo::getProposedDay));

        //remove entries in courseInfoList if studentCount is 0
        Iterator<CourseInfo> iterator = courseInfoList.iterator();
        while (iterator.hasNext()) {
            CourseInfo courseInfo = iterator.next();
            if (courseInfo.getStudentCount() == 0) {
                iterator.remove(); // Safely removes the current element
            }
        }
    
        // Print sorted course info
        /*System.out.println("Course Info (sorted by student count):");
        for (CourseInfo courseInfo : courseInfoList) {
            System.out.println(courseInfo);
        }
    
        // Print student courses info
        /*System.out.println("\nStudent Courses:");
        for (StudentCourses studentCourses : studentCoursesMap.values()) {
            System.out.println(studentCourses);
        }*/

        //Print program map info
        /*System.out.println("\nProgram Map:");
        for (Map.Entry<List<String>, StudentCourses> entry : programMap.entrySet()) {
            if(entry.getKey().get(0).equals("CAI") && entry.getKey().get(1).equals("5")){
                System.out.println(entry.getKey() + ": " + entry.getValue());
            }
        }*/

        System.out.println("Other Objects Created!");

        List<CourseInfo> leftoverCourses = new ArrayList<>(courseInfoList);
        Slots slots = new Slots(13);

        assignCoursesToSlots(slots, studentCoursesMap, leftoverCourses, courseInfoList);
        System.out.println("Slots after assigning courses: " + slots);
        System.out.println("Slots created: " + slots.getNumberOfSlots());
        System.out.println("No. of days: " + ((slots.getNumberOfSlots() + 2) / 3));
        //System.out.println("LeftOverCourses: " + leftoverCourses);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("Exam_Timetable.tsv"))) {
          //for each key in programMap get the courses
          writer.write("Day" + "\t" + "Slot" + "\t" +  "CourseCode" + "\t" +  "CourseTitle" + "\t" +  "Program" + "\t" +  "Semester" + "\t" +  "School" + "\n");

            for(Map.Entry<List<String>, StudentCourses> entry : programMap.entrySet()){
                List<String> course = entry.getValue().getCourses();

                for(int i = 0; i < slots.getNumberOfSlots(); i++){
                    for(String mainCourse : course){
                        if(slots.getSlot(i).contains(mainCourse)){
                            writer.write(((i/3)+1) + "\t" + (i%3+1) + "\t" + mainCourse + "\t" + courseInfoMap.get(mainCourse).getCourseTitle() + "\t" + entry.getKey().get(0) + "\t" + entry.getKey().get(1) + "\t" + courseInfoMap.get(mainCourse).school + "\n");
                        }
                    }

                }
            }
            


        } catch (IOException e) {
            e.printStackTrace();
        }

        List<Staff> invigilators = new ArrayList<>();
        try (BufferedReader brrr = new BufferedReader(new FileReader("Invigilators.txt"))) {
            String line;

            brrr.readLine();

            while ((line = brrr.readLine()) != null) {
                String[] details = line.split("\t");

                String staffID = details[0];
                String staffDept = details[1];
                String staffName = details[2];
                int staffSlots = Integer.parseInt(details[3]);
                String[] notToInvigilate = new String[0];
                String usedLastSlot = "No";
                if (details.length > 4) {
                    notToInvigilate = details[4].split(",");
                }

                invigilators.add(new Staff(staffID, staffName, staffDept, staffSlots, usedLastSlot, notToInvigilate));
            }

            brrr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("Invigilator_Assignment.tsv"))) {

            writer.write("Day" + "\t" + "Slot" + "\t" +  "Room" + "\t" +  "Invigilator ID" + "\t" +  "Invigilator Name" + "\t" +  "Invigilator Dept" + "\n");

        } catch (IOException e) {
            e.printStackTrace();
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("Seating_Arrangement.tsv"))) {

            // date, slot, roomNo, rollNo, Name, SeatNo, CourseCode, CourseName
            writer.write("Day" + "\t" + "Slot" + "\t" +  "RoomNo" + "\t" +  "Student RollNo" + "\t" +  "Student Name" + "\t" +  "SeatNo" + "\t" +  "CourseCode" +  "\t" +  "CourseName" + "\n");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


        for (int i = 0; i < slots.getNumberOfSlots(); i++) {
            //int i = 0;    
            List<Room> rooms = new ArrayList<>();
            Map<String, List<String>> seatingArrangement = new LinkedHashMap<>();
            generateSeatingArrangement(slots, studentCoursesMap, i, rooms, seatingArrangement);

            List<InvigilatorRoom> invigilatorRooms = new ArrayList<>();
            assignInvigilators(seatingArrangement, invigilators, invigilatorRooms, i);

            // Print the seating arrangement
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("Seating_Arrangement.tsv", true))) {
                int[] seatRange = {1,3,5,6,8,10,11,13,15,16,18,20,21,23,25,26,28,20,31,33,35,36,38,40,41,43,45,46,48,50,51,53,55,56,58,60,61,63,65,66,68,70};
                
                for (Map.Entry<String, List<String>> entry : seatingArrangement.entrySet()) {
                    String roomNo = entry.getKey();
                    List<String> students = entry.getValue();
                    int seatIndex = 0;

                    for (String student : students) {
                        String rollNo = student.substring(0, student.indexOf("//"));
                        String name = student.substring(student.indexOf("//") + 2, student.indexOf("(")-1);
                        String courseCode = student.substring(student.indexOf("(") + 1, student.indexOf(")"));
                        String seatNo = Integer.toString(seatRange[seatIndex]);
                        seatIndex++;

                        for (String course : courseInfoMap.keySet()) {
                            if (course.equals(courseCode)) {
                                String courseName = courseInfoMap.get(courseCode).getCourseTitle();

                                writer.write(((i/3)+1) + "\t" + (i%3+1) + "\t" + roomNo + "\t" + rollNo + "\t" + name + "\t" + seatNo + "\t" + courseCode + "\t" + courseName + "\n");
                            }
                        }
                    }
                    
                }
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            
        } 
        

        /*Set<String> uniqueCourseCodes = new HashSet<>();

        for (CourseInfo courseInfo : courseInfoList) {
            uniqueCourseCodes.add(courseInfo.getCourseCode().substring(0, 3));
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("course_list.txt"))) {
            for (String code : uniqueCourseCodes) {
                writer.write(code);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        
    }

    // Merge Sort
    public static <T> List<T> mergeSort(List<T> list, Comparator<? super T> comparator) {
        if (list.size() <= 1) {
            return list;
        }

        int mid = list.size() / 2;
        List<T> left = mergeSort(list.subList(0, mid), comparator);
        List<T> right = mergeSort(list.subList(mid, list.size()), comparator);

        return merge(left, right, comparator);
    }

    // Merge
    private static <T> List<T> merge(List<T> left, List<T> right, Comparator<? super T> comparator) {
        List<T> result = new ArrayList<>();
        int i = 0, j = 0;

        while (i < left.size() && j < right.size()) {
            if (comparator.compare(left.get(i), right.get(j)) <= 0) {
                result.add(left.get(i++));
            } else {
                result.add(right.get(j++));
            }
        }

        while (i < left.size()) {
            result.add(left.get(i++));
        }

        while (j < right.size()) {
            result.add(right.get(j++));
        }

        return result;
    }
}