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
public class GeneticAlgorithm {
  private Data data;
	public GeneticAlgorithm(Data data) { this.data = data; }
	public Population evolve(Population population) { return mutatePopulation(crossoverPopulation(population)); }
	Population crossoverPopulation(Population population) {
		Population crossoverPopulation = new Population(population.getSchedules().size(), data);
		IntStream.range(0, Main.NUMB_OF_ELITE_SCHEDULES).forEach(x -> crossoverPopulation.getSchedules().set(x, 
				                                                                                    population.getSchedules().get(x)));
		IntStream.range(Main.NUMB_OF_ELITE_SCHEDULES, population.getSchedules().size()).forEach(x -> {
			if (Main.CROSSOVER_RATE > Math.random()) {
				ClassSchedule schedule1 = selectTournamentPopulation(population).sortByFitness().getSchedules().get(0);
				ClassSchedule schedule2 = selectTournamentPopulation(population).sortByFitness().getSchedules().get(0);
				crossoverPopulation.getSchedules().set(x, crossoverSchedule(schedule1, schedule2));
			} else crossoverPopulation.getSchedules().set(x, population.getSchedules().get(x));
		});
		return crossoverPopulation;
	}
	ClassSchedule crossoverSchedule(ClassSchedule schedule1, ClassSchedule schedule2) { 
		ClassSchedule crossoverSchedule = new ClassSchedule(data).initialize();
		IntStream.range(0, crossoverSchedule.getClasses().size()).forEach(x -> {
			if (Math.random() > 0.5) crossoverSchedule.getClasses().set(x, schedule1.getClasses().get(x));
			else crossoverSchedule.getClasses().set(x, schedule2.getClasses().get(x));
		});
		return crossoverSchedule;
	}
	Population mutatePopulation(Population population) {
		Population mutatePopulation = new Population(population.getSchedules().size(), data);
		ArrayList<ClassSchedule> schedules = mutatePopulation.getSchedules();
		IntStream.range(0, Main.NUMB_OF_ELITE_SCHEDULES).forEach(x -> schedules.set(x, population.getSchedules().get(x)));
		IntStream.range(Main.NUMB_OF_ELITE_SCHEDULES, population.getSchedules().size()).forEach(x -> {
			schedules.set(x, mutateSchedule(population.getSchedules().get(x)));
		});
		return mutatePopulation;
	}
	ClassSchedule mutateSchedule(ClassSchedule mutateSchedule) { 
		ClassSchedule schedule = new ClassSchedule(data).initialize();
		IntStream.range(0, mutateSchedule.getClasses().size()).forEach(x -> {
			if (Main.MUTATION_RATE > Math.random()) mutateSchedule.getClasses().set(x, schedule.getClasses().get(x));
		});
		return mutateSchedule;
	}
	Population selectTournamentPopulation(Population population) {
		Population tournamentPopulation = new Population(Main.TOURNAMENT_SELECTION_SIZE, data);
		IntStream.range(0, Main.TOURNAMENT_SELECTION_SIZE).forEach(x -> {
			tournamentPopulation.getSchedules().set(x, 
					                  population.getSchedules().get((int)(Math.random() * population.getSchedules().size())));
		});
		return tournamentPopulation;
	}
}
