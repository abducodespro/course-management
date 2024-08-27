/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CourseManagementSystem;

/**
 *
 * @author Nurusan
 */
public class Data2 {
    private final Integer C_ID;
    private final String courseName;
    private final Integer creditHour;
    
    public Data2(Integer C_ID, String courseName,Integer creditHour){
        this.C_ID=C_ID;
        this.courseName=courseName;
        this.creditHour=creditHour;
    }
    
    public Integer getC_ID(){
     return C_ID;   
    }
    
    public String getCourseName(){
        return courseName;
    } 
    
    public Integer getCreditHour(){
        return creditHour;
    }
}
