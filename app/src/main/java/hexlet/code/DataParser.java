package hexlet.code;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.Map;
import java.nio.file.Paths;

public class DataParser {
    static ObjectMapper mapper = new ObjectMapper();

    public static Map parseData (String rawPath) throws Exception {
        var path = Paths.get(rawPath).toAbsolutePath().normalize();
        var fileData = mapper.readValue(new File(path.toUri()), Map.class);
        return fileData;
    }
}
