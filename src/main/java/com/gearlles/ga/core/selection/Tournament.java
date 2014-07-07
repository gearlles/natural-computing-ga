package com.gearlles.ga.core.selection;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.gearlles.ga.core.Chromosome;

public class Tournament<T extends Comparable<T>> implements SelectionInterface<T> {
	private int tournamentSize;
	private Random rand = new Random();

	public Tournament(int tournamentSize) {
		this.tournamentSize = tournamentSize;
	}

	public List<Chromosome<T>> select(List<Chromosome<T>> population) {
		List<Chromosome<T>> parents = new ArrayList<Chromosome<T>>();

		// select two individuals
		for (int i = 0; i < 2; i++) {
			parents.set(i, population.get(rand.nextInt(population.size())));
			for (int j = 0; j < tournamentSize; j++) {
				int idx = rand.nextInt(population.size());
				if (population.get(idx).compareTo(parents.get(i)) < 0) {
					parents.set(i, population.get(idx));
				}
			}
		}

		return parents;
	}
}
