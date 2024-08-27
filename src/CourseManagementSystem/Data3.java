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
public class Data3 {
    private final Integer T_ID;
    private final String first;
    private final String last;
    private final Integer age;
    private final String sex;
    private final String course;
    
    
    Data3(Integer T_ID,String first,String last,Integer age,String sex,String course){
        
        this.T_ID=T_ID;
        this.first=first;
        this.last=last;
        this.age=age;
        this.sex=sex;
        this.course=course;
    }
    public int getT_ID(){
        return T_ID;
    }
    
    public String getFirst(){
        return first;
    }
    
    public String getLast(){
        return last;
    }
    
    public int getAge(){
        return age;
    }
    
    public String getSex(){
        return sex;
    }
    
    public String getCourse(){
        return course;
    }
}
