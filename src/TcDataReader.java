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
                try {
                    String[] fields = line.split(",");

                    String tcItemRevId = fields[0];
                    String sapMaterialNumber = fields[1];
                    int set = extractNumberFromTcString(fields[2]);
                    int afo = extractNumberFromTcString(fields[3]);
                    int clamping = extractNumberFromTcString(fields[4]);
                    String sapWorkplace = fields[5];
                    String tcProgramNumber = fields[6];
                    int revision = Integer.parseInt(fields[7]);
                    String partFamily = fields[8];
                    double tcTime = Double.parseDouble(fields[9]);
                    int pieceCount = Integer.parseInt(fields[10]);
                    String toolNumber = fields[11];
                    double tcToolTime = Double.parseDouble(fields[12]);
                    int toolSequence = Integer.parseInt(fields[13]);

                    tcToolUsageRecords.add(new TcToolUsageRecord(tcItemRevId, sapMaterialNumber, set, afo, clamping, sapWorkplace, tcProgramNumber, revision, partFamily, tcTime, pieceCount, toolNumber, tcToolTime, toolSequence));
                } catch (NullPointerException | IllegalStateException e) {
                    System.out.println("Error reading line " + lineNumber);
                    System.out.println(line);
                    e.printStackTrace();
                }
            }
        }

        return tcToolUsageRecords;
    }

    private int extractNumberFromTcString(String tcString) throws IllegalStateException {
        Pattern pattern = Pattern.compile("^\\D*(\\d{1,2})\\D*$");
        Matcher matcher = pattern.matcher(tcString);
        int number;

        number = Integer.parseInt(matcher.group());
        return number;
    }
}
