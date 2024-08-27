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
public class Data {

    private final Integer S_ID;
    private final String first;
    private final String last;
    private final Integer age;
    private final String sex;
    private final Integer phone;
    private final String department;
    private final String course;

    public Data(Integer S_ID, String first,String last, Integer age, String sex, Integer phone, String department, String course) {
        this.S_ID = S_ID;
        this.first = first;
        this.last=last;
        this.age = age;
        this.sex = sex;
        this.phone = phone;
        this.department = department;
        this.course = course;
    }

    public Integer getS_ID() {
        return S_ID;
    }

    public String getFirst() {
        return first;
    }

    public String getLast(){
        return last;
    }
    
    public Integer getAge() {
        return age;
    }

    public String getSex() {
        return sex;
    }

    public Integer getPhone() {
        return phone;
    }

    public String getDepartment() {
        return department;
    }

    public String getCourse() {
        return course;
    }
}
