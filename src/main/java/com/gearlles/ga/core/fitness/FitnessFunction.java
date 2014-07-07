package com.gearlles.ga.core.fitness;

import java.util.List;

import com.gearlles.ga.core.Route;

public abstract class FitnessFunction {
	public double LOWER_LIMIT[];
	public double UPPER_LIMIT[];
	public int dimensions;

	public abstract double evaluate(List<Route> gene);

	public int getDimensions() {
		return dimensions;
	}
}
