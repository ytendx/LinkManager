package de.ytendx.linkmanager.file;

import de.ytendx.linkmanager.Main;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LinkFile {

    private final File file;
    private final String link;
    private String name;

    public File getFile() {
        return file;
    }

    public String getLink() {
        return link;
    }

    public LinkFile(String name, String link) {
        this.link = link;
        this.name = name;
        this.file = new File("/var/www/html/"+name+"/index.html");
    }

    public LinkFile(String name) {
        this.link = null;
        this.name = name;
        this.file = new File("/var/www/html/"+name+"/index.html");
    }

    public void create() throws IOException {
        if(!new File("/var/www/html/").exists()){
            Main.logError("Error while trying to create File. Is Apache installed?");
            System.exit(0);
            return;
        }
        Path filePath = Paths.get("/var/www/html/"+this.name+"/");
        if(!new File("/var/www/html/"+this.name+"/").exists()){
            Files.createDirectory(filePath);
        }
        if(!this.file.exists()){
            this.file.createNewFile();
            final FileWriter fileWriter = new FileWriter(this.file);
            fileWriter.write("<!DOCTYPE html>\n" +
                    "<html lang=\"de\" dir=\"ltr\">\n" +
                    "  <head>\n" +
                    "    <meta charset=\"utf-8\">\n" +
                    "    <meta http-equiv=\"refresh\" content=\"2; URL="+this.link+"\">\n" +
                    "    <title>Redirecting...</title>\n" +
                    "  </head>\n" +
                    "  <body>\n" +
                    "    <p>Wait a bit. You will be redirected...</p>\n" +
                    "    <p>Default redirecting Page by ytendx. | 10.10.2020</p>\n" +
                    "  </body>\n" +
                    "</html>\n");
            fileWriter.close();
        }
    }

    public void delete(){
        if(this.file.exists()){
            this.file.delete();
            this.file.getParentFile().delete();
        }
    }
}
