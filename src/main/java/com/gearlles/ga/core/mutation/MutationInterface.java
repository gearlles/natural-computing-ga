package com.gearlles.ga.core.mutation;

import com.gearlles.ga.core.Chromosome;

public interface MutationInterface<T extends Comparable<T>> {
	public Chromosome<T> mutate(Chromosome<T> chromosome);
}
