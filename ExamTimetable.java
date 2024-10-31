import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import classes.CourseInfo;
import classes.Slots;
import classes.StudentCourses;
import classes.StudentRecord;
import classes.Utils;

public class ExamTimetable {
    
    //Parameters
    static boolean ENDTERM_MODE = false;   //FOR ENDTERM SET TO TRUE
    static boolean FIXED_BREAK = true;
    static int SLOTS_PER_DAY = 3;          //FOR ENDTERM SET TO 2
    static int EXAMS_PER_SLOT = 2;         //FOR ENDTERM SET TO 1
    static int STUDENTS_PER_SLOT = 5700;  //6912      (No. of classrooms * (classroom capacity))



    public static void assignCoursesToSlots(Slots slots, Map<String, StudentCourses> studentCoursesMap, List<CourseInfo> leftoverCourses, List<CourseInfo> courseInfoList) {
        int slotIndex = 1;
        int clashedCourses = 0;

        ArrayList<String> blacklist = new ArrayList<>();
        try (BufferedReader brr = new BufferedReader(new FileReader("./metadata/blacklist.txt"))) {
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
                slotIndex = (courseInfo.getProposedDay()*SLOTS_PER_DAY)-(SLOTS_PER_DAY-1);
            }
            String initialCourse = courseInfo.getCourseCode();

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
                Set<String> combinedSlotCourses = new HashSet<>();
                Set<String> currentSlotCourses = new HashSet<>();
                Set<String> directSlotCourses = new HashSet<>();

                int modIndex = (slotIndex-1)%SLOTS_PER_DAY;
                int mainslotIndex = slotIndex - 1;
                int altslotIndex1 = 100;
                int altslotIndex2 = 100;

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
                
                if(!ENDTERM_MODE && FIXED_BREAK){
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

                if(!ENDTERM_MODE && altslotIndex2 < slots.getNumberOfSlots()) {
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
                    if (currentSlotCourses.contains(matchedCourse) || directSlotCourses.contains(matchedCourse) || totalStudentCount > STUDENTS_PER_SLOT) {
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
                        if (clashedCourses > EXAMS_PER_SLOT-1) {
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

    public static void main(String[] args) {

        List<StudentRecord> studentRecords = new ArrayList<>();
        Map<String, CourseInfo> courseInfoMap = new LinkedHashMap<>();
        Map<String, StudentCourses> studentCoursesMap = new LinkedHashMap<>();
        Map<List<String>, StudentCourses> programMap = new LinkedHashMap<>();

        Utils.fetchStudentData(studentRecords, courseInfoMap, studentCoursesMap, programMap);

        List<CourseInfo> courseInfoList = new ArrayList<>(courseInfoMap.values());
        courseInfoList = Utils.mergeSort(courseInfoList, Comparator.comparing(CourseInfo::getStudentCount).reversed());
        courseInfoList = Utils.mergeSort(courseInfoList, Comparator.comparing(CourseInfo::getProposedDay));

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
        System.out.println("No. of days: " + ((slots.getNumberOfSlots() + (SLOTS_PER_DAY-1)) / SLOTS_PER_DAY));
        //System.out.println("LeftOverCourses: " + leftoverCourses);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("./documents/Exam_Timetable.tsv"))) {
            //for each key in programMap get the courses
            writer.write("Day" + "\t" + "Slot" + "\t" +  "CourseCode" + "\t" +  "CourseTitle" + "\t" +  "Program" + "\t" +  "Semester" + "\t" +  "School" + "\n");

            for(Map.Entry<List<String>, StudentCourses> entry : programMap.entrySet()){
                List<String> course = entry.getValue().getCourses();

                for(int i = 0; i < slots.getNumberOfSlots(); i++){
                    for(String mainCourse : course){
                        if(slots.getSlot(i).contains(mainCourse)){
                            writer.write(((i/SLOTS_PER_DAY)+1) + "\t" + (i%SLOTS_PER_DAY+1) + "\t" + mainCourse + "\t" + courseInfoMap.get(mainCourse).getCourseTitle() + "\t" + entry.getKey().get(0) + "\t" + entry.getKey().get(1) + "\t" + courseInfoMap.get(mainCourse).getSchool() + "\n");
                        }
                    }

                }
            }
            


        } catch (IOException e) {
            e.printStackTrace();
        }


        try (BufferedWriter writer = new BufferedWriter(new FileWriter("./metadata/exam_slots.txt"))) {
              
            for (int i = 0; i < slots.getNumberOfSlots(); i++) {
                //if not empty
                if(!slots.getSlot(i).isEmpty())
                {
                    writer.write(slots.getSlot(i).toString().substring(1, slots.getSlot(i).toString().length()-1));
                }
                else{
                    writer.write("Empty");
                }
                writer.newLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
