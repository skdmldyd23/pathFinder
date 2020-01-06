package com.douzone.bit.pathfinder.service.algorithm;

import java.util.*;

import org.springframework.security.core.context.SecurityContextHolder;

public class Recursive {

	private final int N;
	private final int START_NODE;
	private final int FINISHED_STATE;

	private double[][] map;
	private double minTourCost = Double.POSITIVE_INFINITY;

	private Map<Integer, Double> tour = new LinkedHashMap<>();

	private boolean ranSolver = false;

	public Recursive(double[][] map) {
		this(0, map);
	}

	public Recursive(int startNode, double[][] map) {

		this.map = map;
		N = map.length;
		START_NODE = startNode;

		// Validate inputs.

		// The finished state is when the finished state mask has all bits are set to
		// one (meaning all the nodes have been visited).
		FINISHED_STATE = (1 << N) - 1;
	}

	// Returns the optimal tour for the traveling salesman problem.
	public Map<Integer, Double> getTour() {
		long start = System.currentTimeMillis();

		if (!ranSolver)
			solve();

		long end = System.currentTimeMillis();
		System.out.println(SecurityContextHolder.getContext().getAuthentication().getName() + " / "+ "실행 시간(초) : " + (end - start) / 1000.0);

		return tour;
	}

	// Returns the minimal tour cost.
	public double getTourCost() {
		if (!ranSolver)
			solve();
		return minTourCost;
	}

	public void solve() {

		// Run the solver
		int state = 1 << START_NODE;

		Double[][] memo = new Double[N][1 << N];
		Integer[][] prev = new Integer[N][1 << N];

		minTourCost = tsp(START_NODE, state, memo, prev);

		// Regenerate path
		int index = START_NODE;
		while (true) {
			if (prev[index][state] != null) {
        tour.put(index, map[index][prev[index][state]]);
			} else {
				tour.put(index, null);
			}

			Integer nextIndex = prev[index][state];
			if (nextIndex == null)
				break;
			int nextState = state | (1 << nextIndex);
			state = nextState;
			index = nextIndex;
		}
		ranSolver = true;
	}

	private double tsp(int i, int state, Double[][] memo, Integer[][] prev) {
		// Done this tour. Return cost of going back to start node.

		if (state == FINISHED_STATE)
			return map[0][START_NODE];

		// Return cached answer if already computed.
		if (memo[i][state] != null)
			return memo[i][state];

		double minCost = Double.POSITIVE_INFINITY;
		int index = -1;
		for (int next = 0; next < N; next++) {

			// Skip if the next node has already been visited.
			if ((state & (1 << next)) != 0)
				continue;

			int nextState = state | (1 << next);
			double newCost = map[i][next] + tsp(next, nextState, memo, prev);
			if (newCost < minCost) {
				minCost = newCost;
				index = next;
			}
		}

		prev[i][state] = index;
		return memo[i][state] = minCost;
	}
}