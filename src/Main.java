import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws IOException {
        String path = args[0];
        System.out.println(path);

        TcDataReader dataReader = new TcDataReader();

        List<TcToolUsageRecord> tcToolUsageRecords = dataReader.readData(path);

        System.out.println("Total records: " + tcToolUsageRecords.size());
        PrintData(tcToolUsageRecords, 100);
    }

    public static void PrintData(List<TcToolUsageRecord> tcToolUsageRecords, int maxPrintCount) {
        for(int i = 0; i < maxPrintCount; i++){
            System.out.println(tcToolUsageRecords.get(i).toString());
        }
    }
}