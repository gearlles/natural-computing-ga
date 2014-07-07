package com.gearlles.ga.core.selection;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.gearlles.ga.core.Chromosome;

public class Roulette<T extends Comparable<T>> implements SelectionInterface<T> {
	private Random rand = new Random();

	public List<Chromosome<T>> select(List<Chromosome<T>> population) {
		List<Chromosome<T>> parents = new ArrayList<Chromosome<T>>();

		double fitnessSum = 0;
		for (int i = 0; i < population.size(); i++) {
			fitnessSum += population.get(i).getFitness();
		}

		// execute the roulette two times to select two individuals
		for (int i = 0; i < 2; i++) {
			double random = rand.nextDouble();
			double accumulatedSum = 0;

			for (int j = 0; j < population.size(); j++) {
				double proportion = population.get(j).getFitness() / fitnessSum;
				accumulatedSum += proportion;

				if (accumulatedSum > random) {
					parents.set(i, population.get(j));
					break;
				}
			}
		}

		return parents;
	}
}
