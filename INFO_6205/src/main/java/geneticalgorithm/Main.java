/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geneticalgorithm;

import java.util.ArrayList;
import geneticalgorithm.domain.Class;
import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import org.apache.log4j.Logger;
import org.apache.log4j.BasicConfigurator;

/**
 *
 * @author Niranjanii
 */
public class Main {
 	public static final int POPULATION_SIZE = 10;
	public static final double MUTATION_RATE = 0.1;
	public static final double CROSSOVER_RATE = 0.9;
	public static final int TOURNAMENT_SELECTION_SIZE = 3;
	public static final int NUMB_OF_ELITE_SCHEDULES = 1;
        public static final int CUT_OFF = 10;

  

 
	private int scheduleNumb = 0;
	private int classNumb = 1;
	private Data data;
        
        static Population population = null;
        
        final static Logger logger = Logger.getLogger(Main.class);
        
	public static void main(String[] args) {
            BasicConfigurator.configure();
            logger.debug("Entered Main Class");
		Main driver = new Main();
		driver.data = new Data();
		int generationNumber = 0;
		driver.printAvailableData();
                Population population = null;
                
        System.out.println("> Generation # "+generationNumber);
    	System.out.print("  Schedule # |                                           ");
    	System.out.print("Classes [dept,class,room,instructor,meeting-time]       ");
    	System.out.println( "                                  | Fitness | Conflicts");
    	System.out.print("-----------------------------------------------------------------------------------");
    	System.out.println("-------------------------------------------------------------------------------------");

        int mid = (POPULATION_SIZE-0)/2;
         
         CompletableFuture<Population> colonies1 = populationgenerate(mid-0, driver.data); 
            CompletableFuture<Population> colonies2 = populationgenerate(POPULATION_SIZE-mid, driver.data);
            
                 CompletableFuture< Population> populationCombine = colonies1.
                    thenCombine(colonies2, (xs1, xs2) -> {
                        ArrayList<ClassSchedule> schedules = new  ArrayList<ClassSchedule>(xs1.getSchedules().size()+xs1.getSchedules().size());
                        schedules.addAll(xs1.getSchedules());
                        schedules.addAll(xs2.getSchedules());
                        Population p=new Population(xs1.getSchedules(),xs2.getSchedules());
                     
                        return p;
                    });
                
               populationCombine.whenComplete((p, throwable) -> {
            
                 
                   combine(p);
                   
                
            }); 
               
              CompletableFuture.allOf(populationCombine).join();
               
               
           populationCombine.thenRun(()->{
                  call(driver);
                });
           
 
	}
	private void printScheduleAsTable(ClassSchedule schedule, int generation,Data data) {
        ArrayList<Class> classes = schedule.getClasses();
        System.out.print("\n                       ");
        System.out.println("Class # | Dept | Course (number, max # of students) | Room (Capacity) |   Instructor (Id)   |  Meeting Time (Id)"); 
        System.out.print("                       ");
        System.out.print("------------------------------------------------------");
        System.out.println("---------------------------------------------------------------");
        
        
        classes.forEach(x -> {
           
        	int majorIndex = data.getDepts().indexOf(x.getDept());
        	int coursesIndex = data.getCourses().indexOf(x.getCourse());
        	int roomsIndex = data.getRooms().indexOf(x.getRoom());
        	int instructorsIndex = data.getProfessors().indexOf(x.getProfessor());
        	int meetingTimeIndex = data.getMeetingTimes().indexOf(x.getMeetingTime());
        	System.out.print("                       ");
        	System.out.print(String.format("  %1$02d  ", classNumb) + "  | ");
        	System.out.print(String.format("%1$4s", data.getDepts().get(majorIndex).getName()) + " | ");
            System.out.print(String.format("%1$21s", data.getCourses().get(coursesIndex).getName() + 
            		                       " ("+data.getCourses().get(coursesIndex).getNumber()+", "+
            		                       x.getCourse().getMaxNumberOfStudents())+")             | ");
            System.out.print(String.format("%1$10s", data.getRooms().get(roomsIndex).getNumber() + 
            		                       " ("+x.getRoom().getSeatCapacity()) + ")     | ");
            System.out.print(String.format("%1$15s", data.getProfessors().get(instructorsIndex).getName()+
            		                       " ("+data.getProfessors().get(instructorsIndex).getId()+")") + "  | ");
            System.out.println(data.getMeetingTimes().get(meetingTimeIndex).getTime()+ 
            		        " ("+data.getMeetingTimes().get(meetingTimeIndex).getId()+")");
            classNumb++;
        });
         
        if (schedule.getFitness() == 1) System.out.println("> Solution Found in "+ (generation + 1) +" generations");
        System.out.print("-----------------------------------------------------------------------------------");
    	System.out.println("-------------------------------------------------------------------------------------");
    }
	private void printAvailableData() {
    	System.out.println("Available Departments ==>");
    	data.getDepts().forEach(x->System.out.println("name: "+x.getName()+", courses: "+x.getCourses()));
    	System.out.println("\nAvailable Courses ==>");
    	data.getCourses().forEach(x->System.out.println("course #: "+x.getNumber()+", name: "+x.getName()+", max # of students: "
    	                                                + x.getMaxNumberOfStudents()+", instructors: "+ x.getProfessors()));
    	System.out.println("\nAvailable Rooms ==>");
    	data.getRooms().forEach(x->System.out.println("room #: "+x.getNumber()+", max seating capacity: "+x.getSeatCapacity()));
    	System.out.println("\nAvailable Instructors ==>");
    	data.getProfessors().forEach(x->System.out.println("id: "+x.getId()+", name: "+x.getName()));
    	System.out.println("\nAvailable Meeting Times ==>");
    	data.getMeetingTimes().forEach(x->System.out.println("id: "+x.getId()+", Meeting Time: "+x.getTime()));
    	System.out.print("-----------------------------------------------------------------------------------");
    	System.out.println("-------------------------------------------------------------------------------------");
    }
        
        
            private static CompletableFuture<Population> populationgenerate(int populationSize, Data data) {
        //CompletableFuture<int[]> part1 = null;
        return CompletableFuture.supplyAsync(
                () -> {
                	
                   Population population = new Population(populationSize, data).sortByFitness();
                    return population;
                }
                
                
        );
        
        
    }
            
               private  static void combine(Population p) {
                   
                   population= p;
                  
               }
               
               
               
    private static void call(Main driver) {
         System.out.print("Came here");
                
                   int generationNumber = 0;
                    GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm(driver.data);
                    System.out.println(driver.data);
                    population.getSchedules().forEach(schedule -> System.out.println("       "+driver.scheduleNumb++ +
                                                                                                                                         "     | "+ schedule + " | " +
                                                                                                                                         String.format("%.5f",schedule.getFitness()) +
                                                                                                                                         " | "+schedule.getNumbOfConflicts()));
                    driver.printScheduleAsTable(population.getSchedules().get(0), generationNumber,driver.data);
                    
                    driver.classNumb = 1;
                 
                    while (population.getSchedules().get(0).getFitness() != 1.0) {
                            System.out.println("> Generation # "+ ++generationNumber);
                            System.out.print("  ClassSchedule # |                                           ");
                            System.out.print("Classes [dept,class,room,instructor,meeting-time]       ");
                            System.out.println("                                  | Fitness | Conflicts");
                            System.out.print("-----------------------------------------------------------------------------------");
                            System.out.println("-------------------------------------------------------------------------------------");

                            population = geneticAlgorithm.evolve(population).sortByFitness();
                       driver.scheduleNumb = 0;
                        population.getSchedules().forEach(schedule -> System.out.println("       "+driver.scheduleNumb++ +
                                                                                                                                         "     | "+ schedule + " | " +
                                                                                                                                                 String.format("%.5f",schedule.getFitness()) +
                                                                                                                                                 " | "+schedule.getNumbOfConflicts()));
                        driver.printScheduleAsTable(population.getSchedules().get(0), generationNumber,driver.data);
                        driver.classNumb = 1;
                      }
                    
    }

                   
}
