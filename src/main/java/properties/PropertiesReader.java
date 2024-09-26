package properties;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class PropertiesReader {
    protected Properties properties;
    private final String propertyFilePath = "config.properties";

    public PropertiesReader() {
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(propertyFilePath));
            properties = new Properties();
            try {
                properties.load(reader);
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("config.properties not found at " + propertyFilePath);
        }
    }

    public String getBaseUri() {
        String uri = properties.getProperty("baseUri");
        if (uri != null) return uri;
        else throw new RuntimeException("uri not specified in the Config.properties file.");
    }
}
