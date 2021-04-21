import java.util.ArrayList;
import java.util.List;

/**
 * Class that collects timing information about AList construction.
 */
public class TimeAList {
    private static void printTimingTable(List<Integer> Ns, List<Double> times, List<Integer> opCounts) {
        System.out.printf("%12s %12s %12s %12s\n", "N", "time (s)", "# ops", "microsec/op");
        System.out.printf("------------------------------------------------------------\n");
        for (int i = 0; i < Ns.size(); i += 1) {
            int N = Ns.get(i);
            double time = times.get(i);
            int opCount = opCounts.get(i);
            double timePerOp = time / opCount * 1e6;
            System.out.printf("%12d %12.2f %12d %12.2f\n", N, time, opCount, timePerOp);
        }
    }

    public static void main(String[] args) {
        timeAListConstruction();
    }

    public static void timeAListConstruction() {
        // TODO: YOUR CODE HERE

        for (int i=0; i<1000; i++) {
            
        }
        //ArrayList<Integer> aList = new ArrayList<Integer>();
        //for(int i=0; i < 1000; i++) {
         //   aList.add(i);
        //}
       // System.out.println(time);

        //int Ns;
        //double times;
        //int opCounts;

        //ArrayList<Integer> aList = new ArrayList<Integer>();

        //for (int i=0; i < 1000; i++) {
            //aList.add(i);
        }
        //System.out.println(timeAListConstruction.Ns);

        //printTimingTable(Ns, times, opCounts);



    }



