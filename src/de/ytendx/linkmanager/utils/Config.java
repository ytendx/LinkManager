package de.ytendx.linkmanager.utils;

public class Config {

    private String domain = "exampledomain.test";

    public Config(String domain) {
        this.domain = domain;
    }

    public String getDomain() {
        return domain;
    }

    public Config() {
        this.domain = "exampledomain.test";
    }

    public String toString() {
        return "domain="+domain;
    }

    public static Config fromString(String from){
        return new Config(from.split("=")[1]);
    }

}
