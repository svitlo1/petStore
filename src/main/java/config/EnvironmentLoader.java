package config;

import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.LoaderOptions;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EnvironmentLoader {

    public static EnvironmentProperties loadActiveEnvironment() {
        // 1. load application.yaml
        EnvConfigurationProperties envConfig = loadSingle("application.yml", EnvConfigurationProperties.class);

        // 2. load env.yml (list of environments)
        List<EnvironmentProperties> envs = loadEnvironments();

        // 3. match by name
        return envs.stream()
                .filter(e -> e.getName().equalsIgnoreCase(envConfig.getName()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No environment found: " + envConfig.getName()));
    }

    private static <T> T loadSingle(String file, Class<T> clazz) {
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        try (InputStream in = cl.getResourceAsStream(file)) {
            if (in == null) {
                throw new RuntimeException("Resource not found: " + file);
            }
            Yaml yaml = new Yaml(new Constructor(clazz, new LoaderOptions()));
            return yaml.load(in);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load YAML file: " + file, e);
        }
    }

    public static List<EnvironmentProperties> loadEnvironments() {
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        try (InputStream in = cl.getResourceAsStream("env.yml")) {
            if (in == null) {
                throw new RuntimeException("Resource not found: env.yml");
            }

            Yaml yaml = new Yaml(new Constructor(new LoaderOptions()));
            Iterable<Object> docs = yaml.loadAll(in);

            List<EnvironmentProperties> result = new ArrayList<>();
            for (Object doc : docs) {
                if (doc instanceof List) {
                    List<Map<String, Object>> list = (List<Map<String, Object>>) doc;
                    for (Map<String, Object> map : list) {
                        EnvironmentProperties env = new EnvironmentProperties();
                        env.setName((String) map.get("name"));
                        env.setBaseUrl((String) map.get("baseUrl"));
                        result.add(env);
                    }
                }
            }
            return result;
        } catch (Exception e) {
            throw new RuntimeException("Failed to load YAML file: env.yml", e);
        }
    }

}
