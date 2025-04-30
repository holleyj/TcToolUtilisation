import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*

 */
public class TcDataReader {

    public List<TcToolUsageRecord> readData(String filePath) throws IOException,AssertionError {
        List<TcToolUsageRecord> tcToolUsageRecords = new ArrayList<>();

        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(filePath);
        if (inputStream == null) throw new AssertionError();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {

            String line;
            int lineNumber = 0;
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                String[] fields = line.split(",");

                try {

                    String tcItemId = fields[0];
                    String sapMaterialNumber = fields[1];
                    int set = extractNumberFromTcString(fields[2]);
                    int afo = extractNumberFromTcString(fields[3]);
                    String sapWorkplace = fields[4];
                    int clamping = extractNumberFromTcString(fields[5]);
                    //Index 6: Releasestatus
                    String tcProgramNumber = fields[7];
                    int revision = Integer.parseInt(fields[8]);
                    String partFamily = fields[9];
                    double tcTime = Double.parseDouble(fields[10]);
                    int pieceCount = Integer.parseInt(fields[11]);
                    String toolNumber = fields[12];
                    double tcToolTime = Double.parseDouble(fields[13]);
                    int toolSequence = Integer.parseInt(fields[14]);

                    tcToolUsageRecords.add(new TcToolUsageRecord(tcItemId, sapMaterialNumber, set, afo, clamping, sapWorkplace, tcProgramNumber, revision, partFamily, tcTime, pieceCount, toolNumber, tcToolTime, toolSequence));
                } catch (NullPointerException | IllegalStateException | NoSuchFieldException e) {
                    System.out.println("Error reading line " + lineNumber);
                    System.out.println(line);
                    e.printStackTrace();
                }
            }
        }

        return tcToolUsageRecords;
    }

    private int extractNumberFromTcString(String tcString) throws IllegalStateException, NoSuchFieldException {
        Pattern pattern = Pattern.compile("^\\D*(\\d{1,2})\\D*$");
        Matcher matcher = pattern.matcher(tcString);
        if(matcher.find()) {
            String match = matcher.group(1);
            return Integer.parseInt(match);
        }

        throw new NoSuchFieldException("No match found");
    }
}
