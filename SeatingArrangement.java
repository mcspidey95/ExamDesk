import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

import classes.CourseInfo;
import classes.InvigilatorRoom;
import classes.Room;
import classes.Slots;
import classes.Staff;
import classes.StudentCourses;
import classes.StudentRecord;

public class SeatingArrangement {

    //CHANGE VALUES IN /metadata/parameters.txt FILE
    static boolean ENDTERM_MODE;    //FOR ENDTERM SET TO TRUE
    static int SLOTS_PER_DAY;       //FOR ENDTERM SET TO 2

    
    public static void generateSeatingArrangement(Slots slots, Map<String, StudentCourses> studentCoursesMap, int slotIndex, List<Room> rooms, Map<String, List<String>> seatingArrangement){

        try (BufferedReader brrr = new BufferedReader(new FileReader("./sources/Rooms.txt"))) {
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

    public static void assignInvigilators(Map<String, List<String>> seatingArrangement, List<Staff> invigilators, List<InvigilatorRoom> invigilatorRooms, int slotIndex, Slots slots) {
        Map<String, Set<String>> courseInRoom = new LinkedHashMap<>();

        if(slotIndex%SLOTS_PER_DAY == 0){
            for(Staff invigilator : invigilators){
                invigilator.setusedLastSlot("No");
            }
        }

        for(Staff invigilator : invigilators){
            if(invigilator.getusedLastSlot().equals("Yes") || invigilator.getusedLastSlot().equals("No_r")) invigilator.setusedLastSlot("No");
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
            String relieverAssigned = "NA";

            if(entry.getValue().size() == 1){

                for(Staff invigilator: invigilators){
                    if(invigilator.getStaffDept().equals(getDepartment(course[0].substring(0,3))) && invigilator.getusedLastSlot().equals("No") && invigilator.getTotalSlots()>0 && invigilator.canInvigilate(slots.getSlot(slotIndex))){
                        invigilatorAssigned = invigilator.getStaffID() + "/" + invigilator.getStaffName() + "@" + invigilator.getStaffDept();
                        if(!ENDTERM_MODE){
                            invigilator.setusedLastSlot("Yes_n");
                        }
                        else{
                            invigilator.setusedLastSlot("Yes");
                        }
                        invigilator.setTotalSlots(invigilator.getTotalSlots() - 1);
                        break;
                    }
                }

                for(Staff invigilator: invigilators){
                    if(ENDTERM_MODE && relieverAssigned.equals("NA") && invigilator.getusedLastSlot().equals("No") && invigilator.getRelieverSlots()>0 && invigilator.canInvigilate(slots.getSlot(slotIndex))){
                        relieverAssigned = invigilator.getStaffID() + "/" + invigilator.getStaffName();
                        invigilator.setRelieverSlots(invigilator.getRelieverSlots() - 1);
                        invigilator.setusedLastSlot("No_r");
                        break;
                    }
                }
                invigilatorRooms.add(new InvigilatorRoom(roomId, invigilatorAssigned, relieverAssigned));
            }
            else if(entry.getValue().size() > 1){
                int index = 0;

                while(index < course.length){
                    for(Staff invigilator: invigilators){
                        if(invigilator.getStaffDept().equals(getDepartment(course[index].substring(0,3))) && invigilator.getusedLastSlot().equals("No") && invigilator.getTotalSlots()>0 && invigilator.canInvigilate(slots.getSlot(slotIndex))){
                            invigilatorAssigned = invigilator.getStaffID() + "/" + invigilator.getStaffName() + "@" + invigilator.getStaffDept();
                            if(!ENDTERM_MODE){
                                invigilator.setusedLastSlot("Yes_n");
                            }
                            else{
                                invigilator.setusedLastSlot("Yes");
                            }
                            invigilator.setTotalSlots(invigilator.getTotalSlots() - 1);
                            break;
                        }
                    }

                    for(Staff invigilator: invigilators){
                        if(ENDTERM_MODE && relieverAssigned.equals("NA") && invigilator.getusedLastSlot().equals("No") && invigilator.getRelieverSlots()>0 && invigilator.canInvigilate(slots.getSlot(slotIndex))){
                            relieverAssigned = invigilator.getStaffID() + "/" + invigilator.getStaffName();
                            invigilator.setRelieverSlots(invigilator.getRelieverSlots() - 1);
                            invigilator.setusedLastSlot("No_r");
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
                invigilatorRooms.add(new InvigilatorRoom(roomId, invigilatorAssigned, relieverAssigned));
            }
        }

        for(InvigilatorRoom invigilatorRoom : invigilatorRooms){
            if(invigilatorRoom.getInvigilator().equals("NA")){
                for(Staff invigilator: invigilators){
                    if(invigilator.getusedLastSlot().equals("No") && invigilator.getTotalSlots()>0){
                        invigilatorRoom.setInvigilator((invigilator.getStaffID() + "/" + invigilator.getStaffName() + "@" + invigilator.getStaffDept()));
                        if(!ENDTERM_MODE){
                            invigilator.setusedLastSlot("Yes_n");
                        }
                        else{
                            invigilator.setusedLastSlot("Yes");
                        }
                        invigilator.setTotalSlots(invigilator.getTotalSlots() - 1);
                        break;
                    }
                }
            }
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("./documents/Invigilator_Assignment.tsv", true))) {

            for(InvigilatorRoom room: invigilatorRooms){
                String invigilator = room.getInvigilator();
                String invigilatorId = invigilator.substring(0, invigilator.indexOf("/"));
                String invigilatorName = invigilator.substring(invigilator.indexOf("/")+1, invigilator.indexOf("@"));
                String invigilatorDept = invigilator.substring(invigilator.indexOf("@")+1);
                String reliever = room.getReliever();

                if(!ENDTERM_MODE){

                    writer.write(((slotIndex/SLOTS_PER_DAY)+1) + "\t" + (slotIndex%SLOTS_PER_DAY+1) + "\t" + room.getLocationId() + "\t" + invigilatorId + "\t" + invigilatorName + "\t" + invigilatorDept + "\n");
                }
                else{
                    String relieverId = reliever.substring(0, reliever.indexOf("/"));
                    String relieverName = reliever.substring(reliever.indexOf("/")+1);

                    writer.write(((slotIndex/SLOTS_PER_DAY)+1) + "\t" + (slotIndex%SLOTS_PER_DAY+1) + "\t" + room.getLocationId() + "\t" + invigilatorId + "\t" + invigilatorName + "\t" + invigilatorDept + "\t" + relieverId + "\t" + relieverName + "\n");
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        /*for(InvigilatorRoom room: invigilatorRooms){
            System.out.println("Room: "+room.getLocationId()+" ---> "+room.getInvigilator());
        }*/
    }

    public static void main(String[] args) {

        List<StudentRecord> studentRecords = new ArrayList<>();
        Map<String, CourseInfo> courseInfoMap = new LinkedHashMap<>();
        Map<String, StudentCourses> studentCoursesMap = new LinkedHashMap<>();
        Map<List<String>, StudentCourses> programMap = new LinkedHashMap<>();
        Slots slots = Utils.fetchSlots();

        //System.out.println("Slots after assigning courses: " + slots);

        Utils.fetchParameters();
        Utils.fetchStudentData(studentRecords, courseInfoMap, studentCoursesMap, programMap);

        List<Staff> invigilators = new ArrayList<>();
        try (BufferedReader brrr = new BufferedReader(new FileReader("./sources/Invigilators.txt"))) {
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
                int relieverSlots = 6;

                invigilators.add(new Staff(staffID, staffName, staffDept, staffSlots, usedLastSlot, notToInvigilate, relieverSlots));
            }

            brrr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("./documents/Invigilator_Assignment.tsv"))) {

            if(!ENDTERM_MODE){
                writer.write("Day" + "\t" + "Slot" + "\t" +  "Room" + "\t" +  "Invigilator ID" + "\t" +  "Invigilator Name" + "\t" +  "Invigilator Dept" + "\n");
            }
            else{
                writer.write("Day" + "\t" + "Slot" + "\t" +  "Room" + "\t" +  "Invigilator ID" + "\t" +  "Invigilator Name" + "\t" +  "Invigilator Dept" + "\t" +  "Reliever ID" + "\t" +  "Reliever Name" + "\n");
            }
            

        } catch (IOException e) {
            e.printStackTrace();
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("./documents/Seating_Arrangement.tsv"))) {

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
            assignInvigilators(seatingArrangement, invigilators, invigilatorRooms, i, slots);

            // Print the seating arrangement
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("./documents/Seating_Arrangement.tsv", true))) {
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
}
