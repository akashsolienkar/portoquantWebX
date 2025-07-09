package com.quant.portoquant.domain.simulation.engine;

import com.quant.portoquant.domain.simulation.context.SimulationContext;
import com.quant.portoquant.domain.simulation.runner.SimulationRunner;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class MultiMonteCarloSimulation {

    public double[] simulate(int numPaths, SimulationRunner runner, SimulationContext context) {
        ForkJoinPool pool = new ForkJoinPool();
        MonteCarloTask rootTask = new MonteCarloTask(0, numPaths, runner, context);
        return pool.invoke(rootTask);
    }

    private static class MonteCarloTask extends RecursiveTask<double[]> {

        private static final int THRESHOLD = 10_000;

        private final int start;
        private final int end;
        private final SimulationRunner runner;
        private final SimulationContext context;

        public MonteCarloTask(int start, int end, SimulationRunner runner, SimulationContext context) {
            this.start = start;
            this.end = end;
            this.runner = runner;
            this.context = context;
        }

        @Override
        protected double[] compute() {
            int size = end - start;

            if (size <= THRESHOLD) {
                double[] outcomes = new double[size];
                for (int i = 0; i < size; i++) {
                    outcomes[i] = runner.generate(context);
                }
                return outcomes;
            } else {
                int mid = (start + end) / 2;
                MonteCarloTask left = new MonteCarloTask(start, mid, runner, context);
                MonteCarloTask right = new MonteCarloTask(mid, end, runner, context);
                invokeAll(left, right);

                double[] leftResult = left.join();
                double[] rightResult = right.join();

                double[] combined = new double[leftResult.length + rightResult.length];
                System.arraycopy(leftResult, 0, combined, 0, leftResult.length);
                System.arraycopy(rightResult, 0, combined, leftResult.length, rightResult.length);

                return combined;
            }
        }
    }
}
