import java.io.IOException;
import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws IOException {
        String filePath = args[0];
        System.out.println(filePath);

        String targetPartFamily = "Leiste"; //hier die gesuchte Teilefamilie eintragen

        SearchAlgorithm[] algorithms = new SearchAlgorithm[] {
                new HashSearch(filePath),
                new JumpSearch(filePath)
        };

        for (SearchAlgorithm algorithm : algorithms) {
            System.out.println(algorithm.getClass().getSimpleName());
            List<TcToolUsageRecord> results = algorithm.search(targetPartFamily);
            PrintData(results, 10);
        }
    }

    public static void PrintData(List<TcToolUsageRecord> tcToolUsageRecords, int maxPrintCount) {
        System.out.println("Count of records: " + tcToolUsageRecords.size());
        for(int i = 0; i < maxPrintCount; i++){
            System.out.println(tcToolUsageRecords.get(i).toString());
        }
    }
}