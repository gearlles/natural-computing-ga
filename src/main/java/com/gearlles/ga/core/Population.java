package com.gearlles.ga.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import com.gearlles.ga.core.crossover.CrossoverInterface;
import com.gearlles.ga.core.fitness.FitnessFunction;
import com.gearlles.ga.core.mutation.MutationInterface;
import com.gearlles.ga.core.selection.SelectionInterface;

public class Population<T extends Comparable<T>> {
	public static double crossoverRatio;
	public static double elitismRatio;
	public static double mutationRatio;

	private int chromosomeSize;
	private int populationSize;

	private List<Chromosome<T>> population;
	private Random rand = new Random();

	public SelectionInterface<T> selection;
	private CrossoverInterface<T> crossover;
	private MutationInterface<T> mutation;
	private FitnessFunction<T> fitnessFunction;

	public Population(int chromosomeSize, int populationSize, SelectionInterface<T> selection, CrossoverInterface<T> crossover, MutationInterface<T> mutation, FitnessFunction<T> fitnessFunction) {
		this.chromosomeSize = chromosomeSize;
		this.populationSize = populationSize;
		
		this.selection = selection;
		this.crossover = crossover;
		this.mutation = mutation;
		this.fitnessFunction = fitnessFunction;

		// 1. Initialize population
		this.population = new ArrayList<Chromosome<T>>(populationSize);
		
		for (int i = 0; i < populationSize; i++) {
			this.population.set(i, new Chromosome<T>(this.chromosomeSize, fitnessFunction));
		}

		Collections.sort(population);
	}

	public Population(int chromosomeSize, int populationSize, List<Chromosome<T>> population) {
		this.chromosomeSize = chromosomeSize;
		this.populationSize = populationSize;
		this.population = population;

		Collections.sort(this.population);
	}

	public void evolve() {
		List<Chromosome<T>> nextPopulation = new ArrayList<Chromosome<T>>();

		/* Elitism */
		int idx = (int) Math.round(population.size() * elitismRatio);
		System.arraycopy(population, 0, nextPopulation, 0, idx);

		for (int i = idx; i < nextPopulation.size(); i++) {
			if (rand.nextDouble() <= crossoverRatio) {
				List<Chromosome<T>> parents = selection.select(population);
				List<Chromosome<T>> children = crossover.mate(parents.get(0), parents.get(1));

				nextPopulation.set(i, mutation.mutate(children.get(0)));

				// Repeat for the second child, if there is enough space in the
				// population.
				if (i + 1 < nextPopulation.size()) {
					nextPopulation.set(++i, mutation.mutate(children.get(1)));
				}
			} else {
				nextPopulation.set(i, mutation.mutate(population.get(i)));
			}
		}

		Collections.sort(nextPopulation);

		population = nextPopulation;
	}

	public List<Chromosome<T>> getPopulation() {
		return population;
	}
}
