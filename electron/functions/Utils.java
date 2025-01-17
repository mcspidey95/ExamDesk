
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import classes.CourseInfo;
import classes.Slots;
import classes.StudentCourses;
import classes.StudentRecord;

import java.util.Comparator;

public class Utils {

    public static void fetchParameters(){
        try (BufferedReader reader = new BufferedReader(new FileReader("./electron/functions/metadata/parameters.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(": ");
                if (parts.length == 2) {
                    String variableName = parts[0].trim();
                    String variableValue = parts[1].trim();

                    // Assign the value to the corresponding global variable
                    switch (variableName) {
                        case "ENDTERM_MODE":
                            ExamTimetable.ENDTERM_MODE = Boolean.parseBoolean(variableValue);
                            SeatingArrangement.ENDTERM_MODE = Boolean.parseBoolean(variableValue);
                            break;

                        case "FIXED_BREAK":
                            ExamTimetable.FIXED_BREAK = Boolean.parseBoolean(variableValue);
                            break;

                        case "SLOTS_PER_DAY":
                            ExamTimetable.SLOTS_PER_DAY = Integer.parseInt(variableValue);
                            SeatingArrangement.SLOTS_PER_DAY = Integer.parseInt(variableValue);
                            break;

                        case "EXAMS_PER_SLOT":
                            ExamTimetable.EXAMS_PER_SLOT = Integer.parseInt(variableValue);
                            break;

                        case "STUDENTS_PER_SLOT":
                            ExamTimetable.STUDENTS_PER_SLOT = Integer.parseInt(variableValue);
                            break;

                        default:
                            
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void fetchStudentData(List<StudentRecord> studentRecords, Map<String, CourseInfo> courseInfoMap, Map<String, StudentCourses> studentCoursesMap, Map<List<String>, StudentCourses> programMap){

        String filePath = "./electron/functions/sources/StudentRegistration.txt";

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
                );
                studentRecords.add(record);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (BufferedReader br = new BufferedReader(new FileReader("./electron/functions/sources/FinalCourseData.txt"))) {
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

        studentRecords = Utils.mergeSort(studentRecords, Comparator.comparing(StudentRecord::getRollNumber));

        System.out.println("Student Records Sorted!");

        for (StudentRecord record : studentRecords) {
            String courseCode = record.getCourseCode();

            // Update course info       
            if(courseInfoMap.get(courseCode) != null)
            {
                courseInfoMap.get(courseCode).setStudentCount(courseInfoMap.get(courseCode).getStudentCount()+1);
            }      
    
            // Update student courses info
            String rollNumber = record.getRollNumber() + "//" + record.getStudentName();
            studentCoursesMap.putIfAbsent(rollNumber, new StudentCourses(rollNumber));
            studentCoursesMap.get(rollNumber).addCourse(courseCode);

            // Update program map
            String programCode = record.getProgramName();
            String semNo = record.getSemNo();
            programMap.putIfAbsent(Arrays.asList(programCode, semNo), new StudentCourses(programCode));
            //only add course if unique
            if(!programMap.get(Arrays.asList(programCode, semNo)).getCourses().contains(courseCode)){
                programMap.get(Arrays.asList(programCode, semNo)).addCourse(courseCode);
            }
        }
    }

    public static String dayToDate(int day){

        try(BufferedReader reader = new BufferedReader(new FileReader("./electron/functions/metadata/days.txt"))) {
            String line;
            int lineNo = 0;

            while ((line = reader.readLine()) != null) {
                if (lineNo == day-1) {
                    return line;
                }
                lineNo++;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return "Invalid Day";
    }

    public static String slotToTime(int slot){

        try(BufferedReader reader = new BufferedReader(new FileReader("./electron/functions/metadata/slots.txt"))) {
            String line;
            int lineNo = 0;

            while ((line = reader.readLine()) != null) {
                if (lineNo == slot-1) {
                    return line;
                }
                lineNo++;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return "Invalid Day";
    }

    public static Slots fetchSlots(){
        
        Slots slots = new Slots(13);
        
        try (BufferedReader reader = new BufferedReader(new FileReader("./electron/functions/metadata/exam_slots.txt"))) {
            String line;
            int lineNo = 0;
    
            while ((line = reader.readLine()) != null) {

                if (lineNo >= slots.getNumberOfSlots()) {
                    slots.addNewSlot();
                }
                
                if(!line.equals("Empty"))
                {
                    List<String> slot = new ArrayList<>(Arrays.asList(line.split(", ")));
                    for(String course : slot){
                        slots.addCourseToSlot(lineNo, course);
                    }
                }
                lineNo++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return slots;
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
