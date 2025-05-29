import java.io.IOException;
import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws IOException {
        String filePath = args[0];
        System.out.println(filePath);

        String targetPartFamily = "Leiste"; //hier die gesuchte Teilefamilie eintragen
        int benchmarkRuns = 10;

        SearchAlgorithm[] algorithms = new SearchAlgorithm[] {
                new HashSearch(filePath),
                new JumpSearch(filePath)
        };

        for (SearchAlgorithm algorithm : algorithms) {
            List<Long> setupTimes = BenchmarkRunner.runBenchmark(() -> {
                try {
                    algorithm.setup(filePath);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }, benchmarkRuns);
            List<Long> searchTimes = BenchmarkRunner.runBenchmark(() -> algorithm.search(targetPartFamily), benchmarkRuns);

            List<TcToolUsageRecord> results = algorithm.search(targetPartFamily);

            System.out.println(algorithm.getClass().getSimpleName());
            System.out.println("\nSetup Benchmarks:");
            printBenchmarkResults(setupTimes);
            System.out.println("\nSearch Benchmarks:");
            printBenchmarkResults(searchTimes);
            System.out.println("\nSample Data:");
            printData(results, 5);
            System.out.println();
        }
    }

    public static void printData(List<TcToolUsageRecord> tcToolUsageRecords, int maxPrintCount) {
        System.out.println("Count of records: " + tcToolUsageRecords.size());
        for(int i = 0; i < maxPrintCount; i++){
            System.out.println(tcToolUsageRecords.get(i).toString());
        }
    }

    public static void printBenchmarkResults(List<Long> times) {
        double avg = times.stream().mapToLong(Long::longValue).average().orElse(0.0);
        double stdDev = Math.sqrt(times.stream()
                .mapToDouble(t -> Math.pow(t - avg, 2))
                .sum() / times.size());

        System.out.printf("Durchschnitt: %.2f ms %.2f ns \n", avg / 1_000_000, avg);
        System.out.printf("Standardabweichung: %.2f ms %.2f ns \n", stdDev / 1_000_000, stdDev);
    }
}