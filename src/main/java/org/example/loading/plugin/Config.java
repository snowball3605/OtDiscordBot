package org.example.loading.plugin;

import java.util.List;

public class Config {
    public String name;
    public String author;
    public String main;
    public String description;
    public String version;
    public List<String> depend;
    public List<String> soft_depend;
    public String Interface;

    public String getName() {
        return name;
    }
    public String getAuthor() {
        return author;
    }
    public String getMain() {
        return main;
    }
    public String getDescription() {
        return description;
    }
    public String getVersion() {
        return version;
    }
    public List<String> getDepend() {
        return depend;
    }
    public List<String> getSoftDepend() {
        return soft_depend;
    }
    public String getInterface() {
        return Interface;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    public void setMain(String main) {
        this.main = main;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setVersion(String version) {
        this.version = version;
    }
    public void setDepend(List<String> depend) {
        this.depend = depend;
    }
    public void setSoftDepend(List<String> soft_depend) {
        this.soft_depend = soft_depend;
    }
    public void setInterface(String Interface) {
        this.Interface = Interface;
    }
}
