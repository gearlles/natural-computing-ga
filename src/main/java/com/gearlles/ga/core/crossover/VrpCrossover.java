package com.gearlles.ga.core.crossover;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import br.poli.ecomp.nestor.cn.graph.NodeVrp;
import br.poli.ecomp.nestor.cn.graph.distance.DistanceFunction;

import com.gearlles.ga.core.Chromosome;
import com.gearlles.ga.core.Route;

public class VrpCrossover implements CrossoverInterface
{

	private static Random rand = new Random();

	public Chromosome[] mate(Chromosome dad, Chromosome mom)
	{
		List<Route> dadGene = dad.getGene();
		List<Route> momGene = mom.getGene();
		List<Route> momGeneCopy = new ArrayList<Route>(momGene);

		DistanceFunction distanceFunction = dad.getMap().getFunction();
		int randomRoute = rand.nextInt(dadGene.size());

		// 0, 1, 2, 3, 4, 5 size = 6
		int numberNodes = dadGene.get(randomRoute).getNodes().size();
		int randomSubRoutStart = rand.nextInt(numberNodes - 1); // 0
																								// até
																								// 4
		int randomSubRoutEnd = randInt(randomSubRoutStart + 1, numberNodes - 1); // do
		// número
		// escolhido
		// acima,
		// até
		// o
		// fim
		// (inicio,
		// fim[
		
		ArrayList<NodeVrp> subRoute = numberNodes == 1? new ArrayList(dadGene.get(randomRoute).getNodes()) : new ArrayList(dadGene.get(randomRoute).getNodes()
				.subList(randomSubRoutStart, randomSubRoutEnd + 1));
		NodeVrp a1 = subRoute.get(0);

		// achar o nó mais próximo de a1
		int momCloserRouteIndex = 0;
		int momCloserRouteNodeIndex = 0;
		double closerDistance = Double.MAX_VALUE;
		for (int i = 0; i < momGeneCopy.size(); i++)
		{
			Route momRoute = momGeneCopy.get(i);
			List<NodeVrp> nodes = momRoute.getNodes();
			for (int j = 0; j < nodes.size(); j++)
			{
				NodeVrp node = nodes.get(j);
				if (!(node.getX() == a1.getX() && node.getY() == a1.getY()))
				{ // verificando se estão na mesma posição
					double dist = distanceFunction.calculate(node.getX(), node.getY(), a1.getX(), a1.getY());
					if (dist < closerDistance)
					{
						closerDistance = dist;
						momCloserRouteIndex = i;
						momCloserRouteNodeIndex = j;
					}
				}
			}
		}

		// achamos a posição do nó mais próximo, vamos concatenar
		Route momSelectedRoute = momGeneCopy.get(momCloserRouteIndex);
		momSelectedRoute.getNodes().addAll(momCloserRouteNodeIndex + 1, subRoute);

		// remover nós duplicados
		List<Route> routesToRemove = new ArrayList<Route>();
		for (int i = 0; i < momGeneCopy.size(); i++)
		{
			Route route = momGeneCopy.get(i);
			List<NodeVrp> nodes = route.getNodes();
			List<NodeVrp> nodesToRemove = new ArrayList<NodeVrp>();
			for (int j = 0; j < nodes.size(); j++)
			{
				NodeVrp nodeVrp = nodes.get(j);
				// nessa verificação não haverá o problema de remover o que
				// acabamos de colocar
				// porque são objetos diferentes, então contains funcionará
				if (i != momCloserRouteIndex
						|| (i == momCloserRouteIndex && (j <= momCloserRouteNodeIndex || j > momCloserRouteNodeIndex + subRoute.size())))
				{
					if (subRoute.contains(nodeVrp))
					{
						nodesToRemove.add(nodeVrp);
					}
				}
			}

			nodes.removeAll(nodesToRemove);

			// se não sobrou nós da rota, remove a rota
			if (route.getNodes().isEmpty())
			{
				routesToRemove.add(route);
			}
		}
		momGeneCopy.removeAll(routesToRemove);

		List<Route> processedRoute = process(momGeneCopy);
		return new Chromosome[] { new Chromosome(processedRoute, dad.getMap()) };
	}

	private List<Route> process(List<Route> momGeneCopy)
	{
		Iterator<Route> routeIterator = momGeneCopy.iterator();
		while (routeIterator.hasNext())
		{
			Route route = (Route) routeIterator.next();

			if (route.getLoad() <= route.getMaxCapacity())
			{
				continue;
			}

			routeIterator.remove();

			List<Route> res = splitRoute(route);
			for (Route rout : res)
			{
				momGeneCopy.add(rout);
			}
		}
		return momGeneCopy;
	}

	private List<Route> splitRoute(Route route)
	{
		double capacity = route.getMaxCapacity();

		List<Route> res = new ArrayList<Route>();
		List<NodeVrp> toSplit = route.getNodes();

		Route r = new Route(new ArrayList<NodeVrp>(), capacity, route.getDistanceFunction());

		for (NodeVrp node : toSplit)
		{
			if (r.getLoad() + node.getDemand() <= capacity)
			{
				r.addNode(node);
			}
			else
			{
				res.add(r);
				r = new Route(new ArrayList<NodeVrp>(), capacity, route.getDistanceFunction());
			}
		}

		if (!r.getNodes().isEmpty())
		{
			res.add(r);
		}

		return res;
	}

	private double getLoad(List<NodeVrp> nodes)
	{
		double load = 0;
		for (NodeVrp nodeVrp : nodes)
		{
			load += nodeVrp.getDemand();
		}
		return load;
	}

	public static int randInt(int min, int max)
	{
		int randomNum = rand.nextInt((max - min) + 1) + min;
		return randomNum;
	}

}
