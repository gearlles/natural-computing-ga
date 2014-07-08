package com.gearlles.ga.core.crossover;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.gearlles.ga.core.Chromosome;
import com.gearlles.ga.core.Route;

public class TwoPoint implements CrossoverInterface {

	private static Random rand = new Random();

	public Chromosome[] mate(Chromosome dad, Chromosome mom) {
		// Convert the genes to arrays to make thing easier.
		List<Route> dadGene = dad.getGene();
		List<Route> momGene = mom.getGene();

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
		List<Route> child1 = new ArrayList<Route>(dadGene.size());
		List<Route> child2 = new ArrayList<Route>(dadGene.size());

		System.arraycopy(dadGene, 0, child1, 0, pivot1);
		System.arraycopy(momGene, pivot1, child1, pivot1, (pivot2 - pivot1));
		System.arraycopy(dadGene, pivot2, child1, pivot2, (dadGene.size() - pivot2));

		System.arraycopy(momGene, 0, child2, 0, pivot1);
		System.arraycopy(dadGene, pivot1, child2, pivot1, (pivot2 - pivot1));
		System.arraycopy(momGene, pivot2, child2, pivot2, (dadGene.size() - pivot2));

		return new Chromosome[]{new Chromosome(child1, dad.getMap()), new Chromosome(child2, dad.getMap())};
	}

}
