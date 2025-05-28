import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class HashSearch implements SearchAlgorithm {
    private Map<String, List<TcToolUsageRecord>> baseData;

    //automatically calls setup() method, to do the necessary preprocessing
    public HashSearch(String filePath) throws IOException {
        setup(filePath);
    }

    @Override
    public List<TcToolUsageRecord> search(String targetPartFamily){
        return baseData.getOrDefault(targetPartFamily, Collections.emptyList());
    }

    public void setup(String filePath) throws IOException {
        TcDataReader dataReader = new TcDataReader();

        baseData = dataReader.readDataIndexedByPartFamily(filePath);
    }
}
