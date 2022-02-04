package me.kyrobi;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import java.io.File;
import java.util.UUID;

public class Main extends JavaPlugin {

    public ConsoleCommandSender console;

    @Override
    public void onEnable(){
        console = Bukkit.getServer().getConsoleSender();
        this.getCommand("jointime").setExecutor((CommandExecutor)new CommandHandler(this)); //Registers the command
        this.saveDefaultConfig();

        DatabaseHandler sqlite = new DatabaseHandler();

        //Check if the database exists. If not, create a new one
        File dbFile = new File("");
        File folderDirectory = new File(dbFile.getAbsolutePath() + File.separator + "plugins" + File.separator + "JoinTime" + File.separator + "storage.db");

        boolean exists = folderDirectory.exists();
        if(!exists){
            System.out.println("DATABASE FILE NOT EXIST!");
            System.out.println(folderDirectory);
            sqlite.createNewDatabase();
        }

        console.sendMessage("[Jointime] Loaded!");
        //BukkitTask task = (BukkitTask) new iteratorLoop(this);

//        Bukkit.getScheduler().runTaskAsynchronously(this, new Runnable() {
//            @Override
//            public void run() {
//                System.out.println("ASYNC RUNNING TIME");
//                UUID uuid;
//                File dir = new File("");
//                String filename;
//                DatabaseHandler db = new DatabaseHandler();
//                File folderDirectory = new File(dir.getAbsolutePath() + File.separator + "world" + File.separator + "playerdata");
//
//                File[] directoryListing = folderDirectory.listFiles();
//                if (directoryListing != null) {
//                    for (File child : directoryListing) {
//                        //filename = (child.getName().substring(0, trim));
//                        filename = (child.getName().substring(0,36));
//
//                        //If the user does not exist in the database, we add it
//                        if(!db.ifExist(filename)){
//                            System.out.println(filename + " does not exist. Adding now!");
//                            db.insert(filename, Bukkit.getOfflinePlayer(UUID.fromString(filename)).getFirstPlayed());
//                        }
//                    }
//                }
//            }
//        });
    }

    public void onDisable(){
        console.sendMessage("[Jointime] Disabled!");
    }
}

