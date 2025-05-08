package hexlet.code.formatters;

import java.util.*;
import java.util.stream.Collectors;

public class Stylish {

    private static final Map<String, String> PREFIXES = Map.of(
            "added", "+ ",
            "removed", "- ",
            "nested", "  ",
            "unchanged", "  "
    );

    public static String stylishFormat(List<Map<String, Object>> ast) {
        return iter(ast, 1);
    }

    private static String iter(List<Map<String, Object>> nodes, int depth) {
        String indent = "  ".repeat(depth);
        String bracketEnd = "  ".repeat(depth - 1);

        List<String> result = nodes.stream()
                .flatMap(node -> {
                    String key = (String) node.get("key");
                    Object value = node.get("value");
                    String status = (String) node.get("status");

                    String keyValue = makeStylishString(key, value, depth + 2);

                    switch (status) {
                        case "updated":
                            Object oldValue = node.get("oldValue");
                            String oldKeyValue = makeStylishString(key, oldValue, depth + 2);
                            return Arrays.asList(
                                    PREFIXES.get("removed") + oldKeyValue,
                                    PREFIXES.get("added") + keyValue
                            ).stream();
                        case "nested":
                            List<Map<String, Object>> children = (List<Map<String, Object>>) node.get("children");
                            return Collections.singletonList(
                                    PREFIXES.get("nested") + key + ": " + iter(children, depth + 2)
                            ).stream();
                        default:
                            if (PREFIXES.containsKey(status)) {
                                return Collections.singletonList(
                                        PREFIXES.get(status) + keyValue
                                ).stream();
                            } else {
                                throw new IllegalArgumentException("Wrong status name: " + status);
                            }
                    }
                })
                .collect(Collectors.toList());

        return "{\n" + indent + String.join("\n" + indent, result) + "\n" + bracketEnd + "}";
    }

    private static String makeStylishString(String key, Object value, int depth) {
        if (!(value instanceof Map)) {
            return key + ": " + value;
        }

        String indent = "  ".repeat(depth);
        String end = "  ".repeat(depth - 1);
        String start = "\n" + indent;

        List<String> entries = ((Map<String, Object>) value).entrySet().stream()
                .map(entry -> PREFIXES.get("unchanged") + makeStylishString(entry.getKey(), entry.getValue(), depth + 2))
                .collect(Collectors.toList());

        String result = String.join(start, entries);

        return key + ": {" + start + result + "\n" + end + "}";
    }
}
