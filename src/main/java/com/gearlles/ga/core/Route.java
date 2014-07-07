package com.gearlles.ga.core;

import java.util.List;

import br.poli.ecomp.nestor.cn.graph.NodeVrp;
import br.poli.ecomp.nestor.cn.graph.distance.DistanceFunction;

public class Route
{
	private List<NodeVrp> nodes;
	private double maxCapacity;
	private DistanceFunction distanceFunction;

	public Route(List<NodeVrp> nodes, double maxCapacity, DistanceFunction distanceFunction)
	{
		this.nodes = nodes;
		this.maxCapacity = maxCapacity;
		this.distanceFunction = distanceFunction;
	}
	
	public void addNode(NodeVrp node)
	{
		nodes.add(node);
	}
	
	public NodeVrp getNode(int i)
	{
		return nodes.get(i);
	}

	public double calculateTimeSpentWithOrigin(NodeVrp depot)
	{
		double routeTime = 0;
		for (int i = 0; i < nodes.size() - 1; i++)
		{
			NodeVrp origin = nodes.get(i);
			NodeVrp dest = nodes.get(i + 1);
			routeTime += this.distanceFunction.calculate(origin.getX(), origin.getY(), dest.getX(), dest.getY());
		}
		
		// Add time from depot to first node
		routeTime += this.distanceFunction.calculate(depot.getX(), depot.getY(), nodes.get(0).getX(), nodes.get(0).getY());
		
		// Add time from last node to depot
		routeTime += this.distanceFunction.calculate(nodes.get(nodes.size() - 1).getX(), nodes.get(nodes.size() - 1).getY(), depot.getX(), depot.getY());

		return routeTime;
	}
	
	public double getLoad()
	{
		double load = 0;
		for(NodeVrp node : nodes)
		{
			load += node.getDemand();
		}
		
		return load;
	}

	public double getMaxCapacity()
	{
		return maxCapacity;
	}

	public void setMaxCapacity(double maxCapacity)
	{
		this.maxCapacity = maxCapacity;
	}

	public List<NodeVrp> getNodes()
	{
		return nodes;
	}

	public void setNodes(List<NodeVrp> nodes)
	{
		this.nodes = nodes;
	}

	public DistanceFunction getDistanceFunction() {
		return distanceFunction;
	}
}
