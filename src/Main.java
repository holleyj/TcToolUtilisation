import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.util.Collections;
import java.util.List;
import java.util.Map;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws IOException {
        String path = args[0];
        System.out.println(path);

        TcDataReader dataReader = new TcDataReader();

        Map<String, List<TcToolUsageRecord>> data = dataReader.readDataIndexedByPartFamily(path);

        String targetPartFamily = "Leiste"; // ← hier die gesuchte Teilefamilie eintragen
        List<TcToolUsageRecord> resultList = data.getOrDefault(targetPartFamily, Collections.emptyList());

        PrintData(resultList, 100);
    }

    public static void PrintData(List<TcToolUsageRecord> tcToolUsageRecords, int maxPrintCount) {
        System.out.println("Count of records: " + tcToolUsageRecords.size());
        for(int i = 0; i < maxPrintCount; i++){
            System.out.println(tcToolUsageRecords.get(i).toString());
        }
    }
}