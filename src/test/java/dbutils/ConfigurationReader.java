package dbutils;

import java.io.FileInputStream;
import java.util.Properties;

public class ConfigurationReader {
    public static Properties properties;

    static {
        try{
            FileInputStream input=new FileInputStream("configuration.properties");
            properties=new Properties();
            properties.load(input);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static String get(String key){
        return properties.getProperty(key);
    }
}
