package com.gearlles.ga.core.fitness;

import java.util.List;

import br.poli.ecomp.nestor.cn.graph.NodeVrp;

import com.gearlles.ga.core.Route;

public abstract class FitnessFunction {
	public double LOWER_LIMIT[];
	public double UPPER_LIMIT[];
	public int dimensions;

	public abstract double evaluate(List<Route> gene, NodeVrp depot);

	public int getDimensions() {
		return dimensions;
	}
}
