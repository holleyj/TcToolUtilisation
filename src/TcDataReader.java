import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*

 */
public class TcDataReader {

    public Map<String, List<TcToolUsageRecord>> readDataIndexedByPartFamily(String filePath) throws IOException, AssertionError {
        Map<String, List<TcToolUsageRecord>> index = new HashMap<>();

        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(filePath);
        if (inputStream == null) throw new AssertionError("Datei nicht gefunden: " + filePath);

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            int lineNumber = 0;
            while ((line = reader.readLine()) != null) {
                lineNumber++;

                try {
                    TcToolUsageRecord record = extractTcToolUsageRecordFromLine(line);
                    //Index refresh
                    index.computeIfAbsent(record.partFamily(), k -> new ArrayList<>()).add(record);

                } catch (Exception e) {
                    System.out.println("Fehler in Zeile " + lineNumber + ": " + line);
                    e.printStackTrace();
                }
            }
        }

        return index;
    }

    public List<TcToolUsageRecord> readData(String filePath) throws IOException, AssertionError {
        List<TcToolUsageRecord> tcToolUsageRecords = new ArrayList<>();

        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(filePath);
        if (inputStream == null) throw new AssertionError("Datei nicht gefunden: " + filePath);

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            int lineNumber = 0;
            while ((line = reader.readLine()) != null) {
                lineNumber++;

                try {
                    TcToolUsageRecord record = extractTcToolUsageRecordFromLine(line);
                    tcToolUsageRecords.add(record);

                } catch (Exception e) {
                    System.out.println("Fehler in Zeile " + lineNumber + ": " + line);
                    e.printStackTrace();
                }
            }
        }

        return tcToolUsageRecords;
    }

    private int extractNumberFromTcString(String tcString) throws IllegalStateException, NoSuchFieldException {
        Pattern pattern = Pattern.compile("^\\D*(\\d{1,2})\\D*$");
        Matcher matcher = pattern.matcher(tcString);
        if (matcher.find()) {
            return Integer.parseInt(matcher.group(1));
        }
        throw new NoSuchFieldException("Keine gültige Zahl in String: " + tcString);
    }

    private TcToolUsageRecord extractTcToolUsageRecordFromLine(String line) throws NoSuchFieldException {

        String[] fields = line.split(",");

        String tcItemId = fields[0];
        String sapMaterialNumber = fields[1];
        int set = extractNumberFromTcString(fields[2]);
        int afo = extractNumberFromTcString(fields[3]);
        String sapWorkplace = fields[4];
        int clamping = extractNumberFromTcString(fields[5]);
        String tcProgramNumber = fields[7];
        int revision = Integer.parseInt(fields[8]);
        String partFamily = fields[9];
        double tcTime = Double.parseDouble(fields[10]);
        int pieceCount = Integer.parseInt(fields[11]);
        String toolNumber = fields[12];
        double tcToolTime = Double.parseDouble(fields[13]);
        int toolSequence = Integer.parseInt(fields[14]);

        return new TcToolUsageRecord(
                tcItemId, sapMaterialNumber, set, afo, clamping,
                sapWorkplace, tcProgramNumber, revision, partFamily,
                tcTime, pieceCount, toolNumber, tcToolTime, toolSequence
        );
    }
}
