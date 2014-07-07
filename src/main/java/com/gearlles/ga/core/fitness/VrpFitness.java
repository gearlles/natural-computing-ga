package com.gearlles.ga.core.fitness;

import java.util.List;

import br.poli.ecomp.nestor.cn.graph.NodeVrp;

import com.gearlles.ga.core.Route;

public class VrpFitness extends FitnessFunction {

	@Override
	public double evaluate(List<Route> gene, NodeVrp depot) {
		double fitness = Double.MIN_VALUE;
		
		for(Route r : gene)
		{
			double tempo = r.calculateTimeSpentWithOrigin(depot);
			if(tempo > fitness)
			{
				fitness = tempo;
			}
		}
		
		return fitness;
	}

}
