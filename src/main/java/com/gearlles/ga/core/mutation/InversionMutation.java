package com.gearlles.ga.core.mutation;

import java.util.Collections;
import java.util.List;
import java.util.Random;

import com.gearlles.ga.core.Chromosome;
import com.gearlles.ga.core.Population;
import com.gearlles.ga.core.Route;

public class InversionMutation implements MutationInterface {
	private static Random rand = new Random();

	public Chromosome mutate(Chromosome chromosome) {
		List<Route> gene = chromosome.getGene();

		for (int i = 0; i < gene.size(); i++) {
			if (rand.nextDouble() > Population.mutationRatio) {
				continue;
			}
			
			Route r = gene.get(i);
			Collections.reverse(r.getNodes());
		}

		return new Chromosome(gene);
	}

}
