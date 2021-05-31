package org.kodigo;

import java.util.*;

public class StatisticalFunctions {

    /*
    * A method that returns the minimum grade of a list
    */
    public static float getMinimumGrade(List<Float> grades){
        return grades.stream().min(Float::compareTo).orElse(0f); //minimum grade
    }

    /*
     * A method that returns the maximum grade of a list
     */
    public static float getMaximumGrade(List<Float> grades){
        return grades.stream().max(Float::compareTo).orElse(0f);
    }

    /*
     * A method that returns the average grade of a list
     */
    public static float getAverage(List<Float> grades){
        return (float) grades.stream().mapToDouble(Float::floatValue).summaryStatistics().getAverage();
    }

    /*
     * A method that returns a list of grades with the most frequency
     */
    public static Map<Byte,List<Float>> getMostRepeated(Map<Float, Byte> histogram){
        //Search the max freq
        byte maxFreq = 0; //Start with the minimum possible frequency
        for (Map.Entry<Float, Byte> entry : histogram.entrySet()) {
            if(entry.getValue()>maxFreq) {
                maxFreq = entry.getValue();
            }
        }

        return getGradeMap(maxFreq, histogram);
    }

    /*
     * A method that returns a list of grades with the least frequency
     */
    public static Map<Byte,List<Float>> getLessRepeated(Map<Float, Byte> histogram){
        //Search the min freq
        byte minFreq = (byte) histogram.size(); //Start with the maximum possible frequency
        for (Map.Entry<Float, Byte> entry : histogram.entrySet()) {
            if(entry.getValue()<minFreq) {
                minFreq = entry.getValue();
            }
        }

        return getGradeMap(minFreq, histogram);

    }

    /*
    * Helper methods
    */

    private static Map<Byte, List<Float>> getGradeMap(byte freq, Map<Float, Byte> histogram){
        List<Float> gradeList = new ArrayList<>();
        //Get a grade list with same frequency
        for(Map.Entry<Float, Byte> entry : histogram.entrySet()){
            if(entry.getValue()==freq) {
                gradeList.add(entry.getKey());
            }
        }
        //Create a map with the frequency as key and the list of grades as value
        Map<Byte, List<Float>> repeatedGrades = new HashMap<>();
        repeatedGrades.put(freq, gradeList);

        return repeatedGrades;
    }
}
