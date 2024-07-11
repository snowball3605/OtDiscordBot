package org.example.util;

import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Map;

public class Get_Config {
    public static String Yaml(String Path, String KeyWord, boolean Separation, String... KeyWorld2) {
        Yaml yaml = new Yaml();

        InputStream inputStream = Get_Config.class.getClassLoader().getResourceAsStream(Path);
        Map<String, Object> yamlmap = yaml.load(inputStream);

        if (yamlmap != null) {
            if (Separation && KeyWorld2.length >= 1) {
                Map<String, Object> databaseConfig = (Map<String, Object>) yamlmap.get(KeyWord);
                String message = databaseConfig.get(KeyWorld2[0]).toString();
                return message;
            } else {
                return yamlmap.get(KeyWord).toString();
            }
        }
        return null;

    }

    public static String path_Yaml(String Path, String KeyWord) {
        Yaml yaml = new Yaml();
        try {
            InputStream inputStream = new FileInputStream(Path);
            Yaml yaml2 = new Yaml();
            Map<String, Object> data = yaml.load(inputStream);
            if (data != null) {
            String elua = data.get(KeyWord).toString();
            return elua;
            } else {
                return null;
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        System.out.println(Yaml("Lang/zh_TW.yml", "help_help", false));
    }
}
