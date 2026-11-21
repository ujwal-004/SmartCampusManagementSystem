import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class AppConfig {
    private static final Map<String, String> LOCAL_VALUES = loadLocalEnv();

    private AppConfig() {
    }

    public static String get(String key, String defaultValue) {
        String value = System.getenv(key);
        if (value == null || value.isBlank()) {
            value = System.getProperty(key);
        }
        if (value == null || value.isBlank()) {
            value = LOCAL_VALUES.get(key);
        }
        return value == null || value.isBlank() ? defaultValue : value;
    }

    private static Map<String, String> loadLocalEnv() {
        Map<String, String> values = new HashMap<>();
        Path envFile = Paths.get(".env");
        if (!Files.isRegularFile(envFile)) {
            return values;
        }

        try {
            List<String> lines = Files.readAllLines(envFile, StandardCharsets.UTF_8);
            for (String line : lines) {
                String trimmed = line.trim();
                if (trimmed.isEmpty() || trimmed.startsWith("#")) {
                    continue;
                }
                int separator = trimmed.indexOf('=');
                if (separator <= 0) {
                    continue;
                }
                String key = trimmed.substring(0, separator).trim();
                String value = trimmed.substring(separator + 1).trim();
                if ((value.startsWith("\"") && value.endsWith("\""))
                        || (value.startsWith("'") && value.endsWith("'"))) {
                    value = value.substring(1, value.length() - 1);
                }
                values.put(key, value);
            }
        } catch (IOException exception) {
            System.out.println("Unable to read local .env file. Use environment variables instead.");
        }
        return values;
    }
}
