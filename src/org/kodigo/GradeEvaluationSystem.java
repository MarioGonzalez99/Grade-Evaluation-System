package org.kodigo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class GradeEvaluationSystem {
    private static final BufferedReader IN = new BufferedReader(new InputStreamReader(System.in));
    private static final byte STUDENTS = 20; //Number of students per section
    private static final Map<Float,Byte> HISTOGRAM = new HashMap<>(); //Frequency of grades (Map<grade,frequency>)
    private static final Map<String,Float> STUDENT_MAP = new HashMap<>(); //Map <Student,Grade>
    private static final List<Float> GRADES = new ArrayList<>(); //List of all grades


    public static void main(String[] args) {
        String welcomeMessage = "Welcome to the grade evaluation system\n" +
                "Please enter the name of the student followed by the grade\n" +
                "For the grades make sure to enter a valid value between 0-10 inclusive\n" +
                "The number of students per section is "+ STUDENTS;
        System.out.println(welcomeMessage);

        //Receive input from user and store it in data structures
        byte currentStudent = 1;
        while(currentStudent<=STUDENTS) {
            String name = Utilities.getStudentName(IN,currentStudent, STUDENTS);
            System.out.printf("Student name %s added successfully\n",name);
            float grade = Utilities.getStudentGrade(IN,currentStudent, STUDENTS);
            System.out.printf("The grade %.2f for student %s was added successfully\n",grade,name);

            //If histogram already contains the grade, increment the count by 1; else, add grade to the histogram
            if(HISTOGRAM.containsKey(grade)){
                HISTOGRAM.put(grade, (byte)(HISTOGRAM.get(grade)+1));
            }else{
                HISTOGRAM.put(grade, (byte) 1);
            }

            STUDENT_MAP.put(name, grade);
            GRADES.add(grade);
            currentStudent++;
        }

        //Get the statistic information
        float min = StatisticalFunctions.getMinimumGrade(GRADES);
        float max = StatisticalFunctions.getMaximumGrade(GRADES);
        float avg = StatisticalFunctions.getAverage(GRADES);
        Map<Byte, List<Float>> mostRepeated = StatisticalFunctions.getMostRepeated(HISTOGRAM);
        Map<Byte, List<Float>> leastRepeated = StatisticalFunctions.getLessRepeated(HISTOGRAM);

        //Create the strings used to create the text file
        String tableOfStudents = Utilities.getTableOfStudents(STUDENT_MAP);
        String statisticalModule = String.format("""
                        The minimum grade is: %.2f
                        The maximum grade is: %.2f
                        The average grade is: %.2f
                        """, min,max,avg);
        String mostRepeatedString = "The most repeated grade(s): "+Utilities.getStringWithGrades(mostRepeated) +"\n";
        String leastRepeatedString = "The least repeated grade(s): "+Utilities.getStringWithGrades(leastRepeated)+"\n";

        //Print information to the console
        System.out.println(tableOfStudents);
        System.out.print(statisticalModule);
        System.out.print(mostRepeatedString);
        System.out.print(leastRepeatedString);

        //Create the file using the strings containing statistical information
        Utilities.createFile(tableOfStudents, statisticalModule, mostRepeatedString, leastRepeatedString);
        System.out.println("The data has been saved successfully");

        //Close the buffered reader
        try {
            IN.close();
        } catch (IOException e) {
            System.out.println("Couldn't close BufferedReader instance");
            e.printStackTrace();
        }
    }
}
