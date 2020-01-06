package com.douzone.bit.pathfinder.service.algorithm;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Iterative {
	private final int N, start;
	private double[][] distance;
	private Map<Integer, Double> tour = new LinkedHashMap<>();
	private double minTourCost = Integer.MAX_VALUE;
	private boolean ranSolver = false;

	public Iterative(double[][] distance) {
		this(0, distance);
	}

	public Iterative(int start, double[][] distance) {
		N = distance.length;

		this.start = start;
		this.distance = distance;
	}

	public double getTourCost() {
		if (!ranSolver)
			solve();
		return minTourCost;
	}

	public Map<Integer, Double> getTour() {
		if (!ranSolver)
			solve();
		return tour;
	}

	public void solve() {

		if (ranSolver)
			return;

		final int END_STATE = (1 << N) - 1;
		Double[][] memo = new Double[N][1 << N];

		for (int end = 0; end < N; end++) {
			if (end == start)
				continue;
			memo[end][(1 << start) | (1 << end)] = distance[start][end];
		}

		for (int r = 3; r <= N; r++) {
			for (int subset : combinations(r, N)) {
				if (notIn(start, subset))
					continue;
				for (int next = 0; next < N; next++) {
					if (next == start || notIn(next, subset))
						continue;
					int subsetWithoutNext = subset ^ (1 << next);
					double minDist = Integer.MAX_VALUE;
					for (int end = 0; end < N; end++) {
						if (end == start || end == next || notIn(end, subset))
							continue;
						double newDistance = memo[end][subsetWithoutNext] + distance[end][next];
						if (newDistance < minDist) {
							minDist = newDistance;
						}
					}
					memo[next][subset] = minDist;
				}
			}
		}

		for (int i = 0; i < N; i++) {
			if (i == start)
				continue;
			double tourCost = memo[i][END_STATE];
			if (tourCost < minTourCost) {
				minTourCost = tourCost;
			}
		}

		int lastIndex = start;
		int state = END_STATE;

		double sum = minTourCost;

		for (int i = 1; i < N; i++) {

			int index = -1;
			for (int j = 0; j < N; j++) {
				if (j == start || notIn(j, state))
					continue;
				if (index == -1)
					index = j;
				double prevDist = memo[index][state] + distance[index][lastIndex];
				double newDist = memo[j][state] + distance[j][lastIndex];

				if (newDist < prevDist) {
					index = j;
				}
			}

			if (i != 1) {
				tour.put(index, sum - memo[index][state]);
				sum = memo[index][state];
			} else {
				tour.put(index, null);
			}

			state = state ^ (1 << index);
			lastIndex = index;
		}

		tour.put(start, distance[0][lastIndex]);
		ranSolver = true;
	}

	private boolean notIn(int elem, int subset) {
		return ((1 << elem) & subset) == 0;
	}

	public List<Integer> combinations(int r, int n) {
		List<Integer> subsets = new ArrayList<>();
		combinations(0, 0, r, n, subsets);
		return subsets;
	}

	private void combinations(int set, int at, int r, int n, List<Integer> subsets) {

		int elementsLeftToPick = n - at;
		if (elementsLeftToPick < r)
			return;

		if (r == 0) {
			subsets.add(set);
		} else {
			for (int i = at; i < n; i++) {
				set ^= (1 << i);

				combinations(set, i + 1, r - 1, n, subsets);

				set ^= (1 << i);
			}
		}
	}
}