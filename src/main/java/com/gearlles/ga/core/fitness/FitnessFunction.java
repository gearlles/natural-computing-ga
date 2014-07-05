package com.gearlles.ga.core.fitness;

public abstract class FitnessFunction {
	public double LOWER_LIMIT[];
	public double UPPER_LIMIT[];
	public int dimensions;

	public abstract double evaluate(double[] gene);

	public int getDimensions() {
		return dimensions;
	}
}
