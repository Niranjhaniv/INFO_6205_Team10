/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geneticalgorithm.domain;

import java.util.ArrayList;

/**
 *
 * @author Niranjanii
 */
public class Course {
   private String number = null;
   private String name = null;
   private int maxNumberOfStudents;
   private ArrayList<Professor> professors;
   public Course(String number,String name,ArrayList<Professor> professors,int maxNumberOfStudents)
   {
       this.number = number;
       this.name =name;
       this.professors = professors;
       this.maxNumberOfStudents = maxNumberOfStudents; 
   }

    public String getNumber() {
        return number;
    }

    public String getName() {
        return name;
    }

    public int getMaxNumberOfStudents() {
        return maxNumberOfStudents;
    }

    public ArrayList<Professor> getProfessors() {
        return professors;
    }
   
    public String toString() { return name; }
    
    @Override
public boolean equals(Object obj) {
    return ((this.name==((Course)obj).name)&&(this.number==((Course)obj).number)&&(this.professors.equals(((Course)obj).getProfessors())));
}
    
}
