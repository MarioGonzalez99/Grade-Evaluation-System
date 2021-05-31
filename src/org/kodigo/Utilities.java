package org.kodigo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class Utilities {
    private static String studentName;
    private static float studentGrade;
    private static boolean validInput;

    /*
     * A method to validate and retrieve the grade of the student
     */
    public static String getStudentName(BufferedReader br, byte i, byte n){
        validInput = false;
        do{
            System.out.printf("Enter the student name (%d/%d):\n", i, n);
            try {
                studentName = br.readLine().trim();
                if(studentName.isEmpty()) throw new IOException();
                validInput = true; //Exit loop if valid input
            } catch (IOException e) {
                System.out.println("The name is not a valid, please enter a valid input");
            }
        }while(!validInput);
        return studentName;
    }

    /*
     * A method to validate and retrieve the name of the student
     */
    public static float getStudentGrade(BufferedReader br, byte i, byte n){
        validInput = false;
        do{
            System.out.printf("Enter the student grade (%d/%d):\n", i, n);
            try {
                studentGrade = Float.parseFloat(br.readLine());
                if(studentGrade <0 || studentGrade > 10) throw new IOException(); // Throw exception if grade is not between a valid range
                validInput = true; //Exit loop if valid input
            } catch (NumberFormatException | IOException e) {
                System.out.println("The grade is not a valid number (0-10 inclusive), please enter a valid number");
            }
        }while(!validInput);
        return studentGrade;
    }

    /*
     * A method that returns a string with a list of grades with same frequency
     */
    public static String getStringWithGrades(Map<Byte, List<Float>> repeatedGrades){
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<Byte, List<Float>> entry : repeatedGrades.entrySet()) {
            sb.append(entry.getValue());
            sb.append(" (freq: ");
            sb.append(entry.getKey());
            sb.append(")\t");
        }
        return sb.toString().trim();
    }

    /*
     * A method that returns a string representing a table of students and their grades
     */
    public static String getTableOfStudents(Map<String, Float> studentMap){

        String leftAlignFormat = "| %-20s | %-7.2f |%n";
        StringBuilder table = new StringBuilder(String.format("+----------------------+---------+%n"));
        table.append(String.format("| Student name         | Grade   |%n"));
        table.append(String.format("+----------------------+---------+%n"));
        for (Map.Entry<String, Float> entry : studentMap.entrySet()) {
            table.append(String.format(leftAlignFormat, entry.getKey(), entry.getValue()));
        }
        table.append(String.format("+----------------------+---------+%n"));

        return table.toString();
    }

    /*
     * A method to create the student database as plain text file
     */
    public static void createFile(String table, String statistics, String mostRepeated, String leastRepeated){
        File file = new File("StudentsDB.txt");
        FileWriter fr = null;
        try {
            fr = new FileWriter(file);
            fr.write(table);
            fr.write(statistics);
            fr.write(mostRepeated);
            fr.write(leastRepeated);
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            //close resources
            try {
                if (fr != null) {
                    fr.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
