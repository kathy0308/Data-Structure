package bearmaps.proj2c;

import java.util.*;
import bearmaps.proj2ab.ArrayHeapMinPQ;
import bearmaps.proj2ab.ExtrinsicMinPQ;
import edu.princeton.cs.algs4.Stopwatch;

public class AStarSolver<Vertex> implements ShortestPathsSolver<Vertex> {
    private SolverOutcome outcome;
    private List<Vertex> solution;
    private double solutionWeight;
    private int numStatesExplored;
    private double explorationTime;

    private ArrayHeapMinPQ<Vertex> fringe;
    ExtrinsicMinPQ<Vertex> PQ;
    HashMap<Vertex, Double> distTo;
    HashMap<Vertex, Vertex> edgeTo;
    HashMap<Vertex, Double> aStarMap;

    public AStarSolver(AStarGraph<Vertex> input, Vertex start, Vertex end, double timeout) {
        Stopwatch sw = new Stopwatch();
        fringe = new ArrayHeapMinPQ<>();
        distTo = new HashMap<>();
        edgeTo = new HashMap<>();
        aStarMap = new HashMap<>();
        solution = new LinkedList<>();

        fringe.add(start, 0.0);
        distTo.put(start, 0.0);
        aStarMap.put(start, (Double) input.estimatedDistanceToGoal(start, end));

        while (fringe.size() != 0 && sw.elapsedTime() < timeout) {
            if (!fringe.getSmallest().equals(end)) {
                Vertex p = fringe.removeSmallest();
                numStatesExplored++;
                for (WeightedEdge<Vertex> neighbor : input.neighbors(p)) {
                    relax(neighbor, input, end);
                }
            } else {
                break;
            }
        }
        if (fringe.size() == 0) {
            outcome = SolverOutcome.UNSOLVABLE;
        } else if (fringe.getSmallest().equals(end)) {
            outcome = SolverOutcome.SOLVED;
            Vertex p = fringe.getSmallest();
            solutionWeight = distTo.get(p);
            while (p != null) {

                solution.add(0, p);
                p = edgeTo.get(p);
            }
        } else {
            outcome = SolverOutcome.TIMEOUT;
        }
        explorationTime = sw.elapsedTime();
    }

    private void relax(WeightedEdge<Vertex> e, AStarGraph<Vertex> input, Vertex end) {
        Vertex p = e.from();
        Vertex q = e.to();
        double w = e.weight();

        if (!aStarMap.containsKey(q)) {
            aStarMap.put(q, input.estimatedDistanceToGoal(q, end));
        }

        if (!distTo.containsKey(q)) {
            distTo.put(q, Double.POSITIVE_INFINITY);
        }

        if (distTo.get(p) + w < distTo.get(q)) {
            distTo.replace(q, distTo.get(p) + w);
            edgeTo.put(q, p);
            if (fringe.contains(q)) {
                fringe.changePriority(q, distTo.get(q) + aStarMap.get(q));
            } else {
                fringe.add(q, distTo.get(q) + aStarMap.get(q));
            }
        }
    }



    @Override
    public SolverOutcome outcome() {
        // Returns one of SolverOutcome.SOLVED, SolverOutcome.TIMEOUT, or SolverOutcome.UNSOLVABLE.
        // Should be SOLVED if the AStarSolver was able to complete all work in the time given.
        // UNSOLVABLE if the priority queue became empty. TIMEOUT if the solver ran out of time.
        // You should check to see if you have run out of time every time you dequeue.
        return outcome;
    }

    @Override
    public List<Vertex> solution() {
        // A list of vertices corresponding to a solution.
        // Should be empty if result was TIMEOUT or UNSOLVABLE.
        return solution;
    }

    @Override
    public double solutionWeight() {
        // The total weight of the given solution taking into account edge weights.
        // Should be 0 if result was TIMEOUT or UNSOLVABLE.
        return solutionWeight;
    }

    @Override
    public int numStatesExplored() {
        // The total number of priority queue dequeue operations.
        return numStatesExplored;
    }

    @Override
    public double explorationTime() {
        // The total time spent in seconds by the constructor.
        return explorationTime;
    }
}
