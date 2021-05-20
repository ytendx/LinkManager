package de.ytendx.linkmanager;

import de.ytendx.linkmanager.file.LinkFile;
import de.ytendx.linkmanager.utils.Config;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Scanner;

public class Main {

    public static void main(final String[] args){
        printLogo();
        Config config = new Config();
        try {
            config = resolveConfig();
        } catch (Exception e) {logError("An error occured while trying to read File:"); logError(e.getMessage());}
        logInfo("Initializing Arguments...");
        if(args.length == 3){
            if(args[0].equalsIgnoreCase("create")){
                logInfo("Creating new Link with Name: "+ args[1] + " and Link: "+args[2]+ "...");
                try {
                    new LinkFile(args[1], args[2]).create();
                    logInfo("Created! Now able to post. Link: http://"+config.getDomain()+"/"+args[1]+"/");
                } catch (IOException e) {logError("An exception occured while trying to create file:"); e.printStackTrace();}
            }else {
                logError("False usage! (#1: "+args.toString()+ ")");
                logError("Use: java -jar LinkManager.jar CREATE (NAME) (LINK)");
                logError("Use: java -jar LinkManager.jar DELETE (NAME)");
            }
        }else if(args.length == 2){
            if(args[0].equalsIgnoreCase("delete")){
                logInfo("Try to delete "+ args[1] + "...");
                new LinkFile(args[1]).delete();
                logInfo("Sucessfull Deleted!");
            }else{
                logError("False usage! (#2: "+args.toString()+ ")");
                logError("Use: java -jar LinkManager.jar CREATE (NAME) (LINK)");
                logError("Use: java -jar LinkManager.jar DELETE (NAME)");
            }
        }else{
            logError("False usage! (Too many or low argument length!)");
            logError("Use: java -jar LinkManager.jar CREATE (NAME) (LINK)");
            logError("Use: java -jar LinkManager.jar DELETE (NAME)");
        }
    }

    private static Config resolveConfig() throws IOException {
        logInfo("Try to resolve config file...");
        final File config = new File("linkmanager_config.xconfig");
        if(!config.exists()){
            logError("Config file doesn´t exist. Creating a new one.");
            config.createNewFile();
            FileWriter writer = new FileWriter(config);
            writer.write(new Config().toString());
            logInfo("Created a new Config-File.");
            return new Config();
        }else{
            logInfo("File founded! Reading...");
            final Scanner sc =new Scanner(config);
            while (sc.hasNextLine()){
                final String line = sc.nextLine();
                if(line.startsWith("//"))
                    continue;
                if(!line.contains("="))
                    continue;
                if(!line.toLowerCase(Locale.ROOT).startsWith("domain"))
                    continue;
                logInfo("File succesfully readed!");
                return Config.fromString(line);
            }
        }
        logError("Error while trying to read the Config-File!");
        return new Config();
    }

    public static void logInfo(final String toLog){
        final Date date = new Date(System.currentTimeMillis());
        final SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        System.out.println(""+format.format(date)+" | LOG -> "+toLog);
    }

    public static void logError(final String toLog){
        final Date date = new Date(System.currentTimeMillis());
        final SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        System.err.println(""+format.format(date)+" | ERROR -> "+toLog);
    }

    public static void printLogo(){
        System.out.println("\n\n\n    ___       ___       ___       ___       ___       ___   \n" +
                "   /\\__\\     /\\  \\     /\\  \\     /\\__\\     /\\  \\     /\\__\\  \n" +
                "  |::L__L    \\:\\  \\   /::\\  \\   /:| _|_   /::\\  \\   |::L__L \n" +
                "  |:::\\__\\   /::\\__\\ /::\\:\\__\\ /::|/\\__\\ /:/\\:\\__\\ /::::\\__\\\n" +
                "  /:;;/__/  /:/\\/__/ \\:\\:\\/  / \\/|::/  / \\:\\/:/  / \\;::;/__/\n" +
                "  \\/__/     \\/__/     \\:\\/  /    |:/  /   \\::/  /   |::|__| \n" +
                "                       \\/__/     \\/__/     \\/__/     \\/__/  ");
        System.out.println("\nLinkManager v1.0 by ytendx\n\n");
    }

}
