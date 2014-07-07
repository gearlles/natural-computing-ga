package com.gearlles.ga.core.mutation;

import java.util.List;
import java.util.Random;

import com.gearlles.ga.core.Chromosome;
import com.gearlles.ga.core.Population;

public class UniformMutation<T extends Comparable<T>> implements MutationInterface<T> {
	
	private static Random rand = new Random();

	public Chromosome<T> mutate(Chromosome<T> chromosome) {
		List<T> gene = chromosome.getGene();
		List<T> randomGene = chromosome.getFitnessFunction().getRandom();
		for (int i = 0; i < gene.size(); i++) {
			if (rand.nextDouble() > Population.mutationRatio) {
				continue;
			}
			gene.set(i, randomGene.get(i));
		}

		return new Chromosome<T>(gene, chromosome.getFitnessFunction());
	}

}
