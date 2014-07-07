package com.gearlles.ga.core.fitness;

import java.util.List;

public abstract class FitnessFunction<T> {
	public T LOWER_LIMIT[];
	public T UPPER_LIMIT[];
	public int dimensions;

	public abstract double evaluate(List<T> gene);
	public abstract List<T> getRandom();
	public abstract List<T> getRandom(T lower, T upper);

	public int getDimensions() {
		return dimensions;
	}
	
}
