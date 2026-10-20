package cli;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public record Arguments(Path file, Operation operation) {

    public enum Operation { PRINT, COUNT }

    public static Arguments parse(String[] args) {
        Map<String, String> opts = new HashMap<>();
        for (int i = 0; i < args.length; i++) {
            String a = args[i];
            String key;
            String value;
            if ("-f".equals(a) || "--file".equals(a)) {
                key = "file";
            } else if ("-o".equals(a) || "--oper".equals(a)) {
                key = "oper";
            } else {
                throw new IllegalArgumentException("Unknown argument: " + a);
            }
            if (i + 1 >= args.length) {
                throw new IllegalArgumentException("Missing value for " + a);
            }
            value = args[++i];
            if (opts.put(key, value) != null) {
                throw new IllegalArgumentException("Duplicate argument: " + a);
            }
        }

        if (!opts.containsKey("file")) {
            throw new IllegalArgumentException("Missing required argument: -f/--file");
        }
        if (!opts.containsKey("oper")) {
            throw new IllegalArgumentException("Missing required argument: -o/--oper");
        }

        Operation op = switch (opts.get("oper").toLowerCase()) {
            case "print" -> Operation.PRINT;
            case "count" -> Operation.COUNT;
            default -> throw new IllegalArgumentException(
                    "Unknown operation: " + opts.get("oper") + " (expected print or count)");
        };

        return new Arguments(Path.of(opts.get("file")), op);
    }
}