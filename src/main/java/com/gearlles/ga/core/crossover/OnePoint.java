package com.gearlles.ga.core.crossover;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import com.gearlles.ga.core.Chromosome;
import com.gearlles.ga.core.fitness.FitnessFunction;

public class OnePoint<T extends Comparable<T>> implements CrossoverInterface<T> {

	private static Random rand = new Random();

	public List<Chromosome<T>> mate(Chromosome<T> dad, Chromosome<T> mom) {
		// Convert the genes to arrays to make thing easier.
		List<T> dadGene = dad.getGene();
		List<T> momGene = mom.getGene();

		// Select a random pivot point for the mating
		int pivot = rand.nextInt(dadGene.size());

		// Provide a container for the child gene data
		List<T> child1 = new ArrayList<T>();
		List<T> child2 = new ArrayList<T>();

		// Copy the data from each gene to the first child.
		System.arraycopy(dadGene, 0, child1, 0, pivot);
		System.arraycopy(momGene, pivot, child1, pivot, (child1.size() - pivot));

		// Repeat for the second child, but in reverse order.
		System.arraycopy(momGene, 0, child2, 0, pivot);
		System.arraycopy(dadGene, pivot, child2, pivot, (child2.size() - pivot));
		
		FitnessFunction<T> fitnessFunction = dad.getFitnessFunction();

		return Arrays.asList(new Chromosome<T>(child1, fitnessFunction), new Chromosome<T>(child2, fitnessFunction));
	}

}
