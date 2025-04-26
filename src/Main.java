import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        String path = args[0];
        System.out.println(path);

        try{
            ReadAndPrint(path, 100);
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public static void ReadAndPrint(String path, int maxPrintCount) throws IOException {
        TcDataReader dataReader = new TcDataReader();

        List<TcToolUsageRecord> tcToolUsageRecords = dataReader.readData(path);

        for(int i = 0; i < maxPrintCount; i++){
            System.out.println(tcToolUsageRecords.get(i).toString());
        }
    }
}