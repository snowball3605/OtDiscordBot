package org.example;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import org.example.Interface.I_Plugin;
import org.example.loading.Logger.Color;
import org.example.loading.plugin.Config;
import org.example.util.Get_Config;
import org.example.util.ExtractResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarFile;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main{
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);
    private static List<I_Plugin> plugins = new ArrayList<>();
    private static JDA jda;
    private String token;

    public Main (String token) {
        this.token = token;
    }

    public void setJDA(JDA jda) {
        this.jda = jda;
    }

    public static void main(String[] args) {
        try {
            ExtractResource.extractResource("plugins/README.md");
            ExtractResource.extractResource("config.yml");
            Main main = new Main(Get_Config.path_Yaml("config.yml", "token"));

            JDA jda = JDABuilder.createDefault(Get_Config.path_Yaml("config.yml", "token")).build();
            main.setJDA(jda);
            main.loadPlugins();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    private void loadPlugins() throws IOException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        LOGGER.info("Loading plugins");
        int done = 0;
        int fail = 0;

        if (!new File("plugins").exists()) {
            LOGGER.info(Color.COLOR_RED_TEXT + "Plugins directory does not exist! Reloading....");
            ExtractResource.extractResource("plugins/README.md");
        }

        for (File file : new File("plugins").listFiles()) {
            if (file.getName().endsWith(".jar") && file.getName().lastIndexOf('.') >= 1) {

                JarFile jarFile = new JarFile(file);
                try {InputStream inputStream = jarFile.getInputStream(jarFile.getEntry("in-fo.yml"));
                    Config config = new Yaml().loadAs(inputStream, Config.class);
                        URL[] urls = {file.toURI().toURL()};
                    try (URLClassLoader class_Loader = new URLClassLoader(urls, Thread.currentThread().getContextClassLoader())) {
                        Class<?> pluginClass = class_Loader.loadClass(config.main);
                        if (I_Plugin.class.isAssignableFrom(pluginClass)) {
                            Object instance = pluginClass.getDeclaredConstructor().newInstance();
                            Method method = pluginClass.getDeclaredMethod("onEnable", JDA.class);
                            method.invoke(instance, jda);
                            LOGGER.info(Color.COLOR_GREEN_TEXT + "Successfully loaded plugin: " + config.name);
                            done ++;
//                            java.lang.reflect.Constructor<?>[] constructors = pluginClass.getDeclaredConstructors();
//                            for (java.lang.reflect.Constructor<?> constructor : constructors) {
//                                System.out.println(constructor);
//                            }
                        } else {
                            fail ++;
                        }
                    }

                } catch (IOException | ClassNotFoundException e) {
                    fail ++;
                    throw new RuntimeException(e);
                }
            }
        }
        LOGGER.info(Color.COLOR_GREEN_TEXT + "Successfully loaded " + done + " plugin(s)." + Color.COLOR_RESET_TEXT);
        LOGGER.info(Color.COLOR_RED_TEXT + "FAILED loaded " + fail + " plugin(s)." + Color.COLOR_RESET_TEXT);
    }

}