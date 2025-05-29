import java.util.ArrayList;
import java.util.List;

public class BenchmarkRunner {

    public static <T> List<Long> runBenchmark(Runnable action, int runs) {
        List<Long> times = new ArrayList<>();

        for (int i = 0; i < runs; i++) {
            long start = System.nanoTime();
            action.run();
            long duration = System.nanoTime() - start;
            times.add(duration);
        }

        return times;
    }


}
