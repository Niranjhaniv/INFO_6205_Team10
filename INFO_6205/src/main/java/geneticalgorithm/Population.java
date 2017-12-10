/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geneticalgorithm;

import java.util.ArrayList;
import java.util.stream.IntStream;

/**
 *
 * @author Niranjanii
 */
public class Population implements Cloneable {

    private ArrayList<ClassSchedule> schedules;

    public Population(int size, Data data) {
        schedules = new ArrayList<ClassSchedule>(size);
        IntStream.range(0, size).forEach(x -> schedules.add(new ClassSchedule(data).initialize()));
    }

    public Population(ArrayList<ClassSchedule> schedules1, ArrayList<ClassSchedule> schedules2) {

        schedules = new ArrayList<ClassSchedule>(schedules1.size() + schedules2.size());
        schedules.addAll(schedules1);
        schedules.addAll(schedules2);

    }

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public ArrayList<ClassSchedule> getSchedules() {
        return this.schedules;
    }

    public void setSchedules(ArrayList<ClassSchedule> schedules) {
        this.schedules = schedules;
    }

    public Population sortByFitness() {
        schedules.sort((schedule1, schedule2) -> {
            int returnValue = 0;
            if (schedule1.getFitness() > schedule2.getFitness()) {
                returnValue = -1;
            } else if (schedule1.getFitness() < schedule2.getFitness()) {
                returnValue = 1;
            }
            return returnValue;
        });
        return this;
    }

public boolean equals(Object obj) {
    System.out.println("test");
 return (this.schedules.equals(((Population)obj).getSchedules()));    
}
}
