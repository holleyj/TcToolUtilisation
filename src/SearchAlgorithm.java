import java.io.IOException;
import java.util.List;

public interface SearchAlgorithm {
    List<TcToolUsageRecord> search(String targetPartFamily);

    //read data and/or do preprocessing here
    void setup(String filePath) throws IOException;
}
