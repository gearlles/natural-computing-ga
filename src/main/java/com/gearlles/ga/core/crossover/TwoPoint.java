package com.gearlles.ga.core.crossover;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import com.gearlles.ga.core.Chromosome;
import com.gearlles.ga.core.fitness.FitnessFunction;

public class TwoPoint<T extends Comparable<T>> implements CrossoverInterface<T> {

	private static Random rand = new Random();

	public List<Chromosome<T>> mate(Chromosome<T> dad, Chromosome<T> mom) {
		// Convert the genes to arrays to make thing easier.
		List<T> dadGene = dad.getGene();
		List<T> momGene = mom.getGene();

		// Select a random pivot point for the mating
		int pivot1 = rand.nextInt(dadGene.size());
		int pivot2 = rand.nextInt(dadGene.size());

		int tmp;
		if (pivot1 > pivot2) {
			tmp = pivot1;
			pivot1 = pivot2;
			pivot2 = tmp;
		}

		// Provide a container for the child gene data
		List<T> child1 = new ArrayList<T>();
		List<T> child2 = new ArrayList<T>();

		System.arraycopy(dadGene, 0, child1, 0, pivot1);
		System.arraycopy(momGene, pivot1, child1, pivot1, (pivot2 - pivot1));
		System.arraycopy(dadGene, pivot2, child1, pivot2, (dadGene.size() - pivot2));

		System.arraycopy(momGene, 0, child2, 0, pivot1);
		System.arraycopy(dadGene, pivot1, child2, pivot1, (pivot2 - pivot1));
		System.arraycopy(momGene, pivot2, child2, pivot2, (dadGene.size() - pivot2));
		
		FitnessFunction<T> fitnessFunction = dad.getFitnessFunction();

		return Arrays.asList(new Chromosome<T>(child1, fitnessFunction), new Chromosome<T>(child2, fitnessFunction));
	}

}
