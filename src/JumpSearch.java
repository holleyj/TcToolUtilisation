import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class JumpSearch implements SearchAlgorithm {
    private List<TcToolUsageRecord> baseData;

    public JumpSearch(String filePath) throws IOException {
        setup(filePath);
    }

    @Override
    public List<TcToolUsageRecord> search(String targetPartFamily) {
        List<TcToolUsageRecord> result = new ArrayList<>();

        if (baseData == null || baseData.isEmpty()) {
            return result;
        }

        int n = baseData.size();
        int jumpSize = (int) Math.floor(Math.sqrt(n));
        int prev = 0;
        int curr = 0;

        //jump steps
        while (curr < n && baseData.get(curr).partFamily().compareTo(targetPartFamily) < 0) {
            prev = curr;
            curr += jumpSize;
        }

        //setup for forward / backward extension
        int start = prev;
        int end = Math.min(n, curr);

        //linear search in block
        for (int i = start; i < end; i++) {
            if (baseData.get(i).partFamily().equals(targetPartFamily)) {
                result.add(baseData.get(i));
            }
        }

        //backwards extension (linear search)
        int i = start - 1;
        while (i >= 0 && baseData.get(i).partFamily().equals(targetPartFamily)) {
            result.add(0, baseData.get(i)); //maintain order
            i--;
        }

        //forward extension (linear search)
        i = end;
        while (i < n && baseData.get(i).partFamily().equals(targetPartFamily)) {
            result.add(baseData.get(i));
            i++;
        }

        return result;
    }

    @Override
    public void setup(String filePath) throws IOException {
        TcDataReader tcDataReader = new TcDataReader();
        baseData = tcDataReader.readData(filePath);
        baseData.sort(Comparator.comparing(TcToolUsageRecord::partFamily));
    }
}
