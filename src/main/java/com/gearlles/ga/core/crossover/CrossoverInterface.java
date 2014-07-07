package com.gearlles.ga.core.crossover;

import java.util.List;

import com.gearlles.ga.core.Chromosome;

public interface CrossoverInterface<T extends Comparable<T>> {
	public List<Chromosome<T>> mate(Chromosome<T> dad, Chromosome<T> mom);
}
