package hexlet.code;

import java.util.*;
import java.util.stream.Collectors;

public class Differ {
    public static List<Map<String, Object>> generateDiffObject(Map file1Data, Map file2Data) {
        Set<String> keys = new HashSet<>(file1Data.keySet());
        keys.addAll(file2Data.keySet());

        return keys.stream().map((String key) -> {
            Map<String, Object> resultEntry = new HashMap<>();

            if (file1Data.containsKey(key) && file2Data.containsKey(key)){
                var value1 = file1Data.get(key);
                var value2 = file2Data.get(key);

                if (value1.equals(value2)){
                    resultEntry.put("key", key);
                    resultEntry.put("value", value1);
                    resultEntry.put("status", "unchanged");
                } else {
                    resultEntry.put("key", key);
                    resultEntry.put("oldValue", value1);
                    resultEntry.put("value", value2);
                    resultEntry.put("status", "updated");
                }
            } else if (!file1Data.containsKey(key) && file2Data.containsKey(key)) {
                resultEntry.put("key", key);
                resultEntry.put("value", file2Data.get(key));
                resultEntry.put("status", "added");
            } else if (file1Data.containsKey(key) && !file2Data.containsKey(key)) {
                resultEntry.put("key", key);
                resultEntry.put("value", file1Data.get(key));
                resultEntry.put("status", "removed");
            }

                    return resultEntry;
                })
                .sorted(Comparator.comparing(entry -> (String) entry.get("key")))
                .collect(Collectors.toList());
    }

}
