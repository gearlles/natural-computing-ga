package com.gearlles.ga.core.selection;

import java.util.List;

import com.gearlles.ga.core.Chromosome;

public interface SelectionInterface<T extends Comparable<T>> {
	public List<Chromosome<T>> select(List<Chromosome<T>> population);
}
