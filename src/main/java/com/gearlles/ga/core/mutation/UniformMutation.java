package com.gearlles.ga.core.mutation;

import java.util.List;
import java.util.Random;

import com.gearlles.ga.core.Chromosome;
import com.gearlles.ga.core.Population;
import com.gearlles.ga.core.Route;

public class UniformMutation implements MutationInterface {
	private static Random rand = new Random();

	public Chromosome mutate(Chromosome chromosome) {
		double[] lowerLimit = Chromosome.fitnessFunction.LOWER_LIMIT;
		double[] upperLimit = Chromosome.fitnessFunction.UPPER_LIMIT;

		List<Route> gene = chromosome.getGene();

		for (int i = 0; i < gene.size(); i++) {
			if (rand.nextDouble() > Population.mutationRatio) {
				continue;
			}
			// FIXME dentro do intervalo do mapa
			//gene[i] = lowerLimit[i] + (upperLimit[i] - lowerLimit[i]) * rand.nextDouble();
			gene.add(new Route());
		}

		return new Chromosome(gene);
	}

}
