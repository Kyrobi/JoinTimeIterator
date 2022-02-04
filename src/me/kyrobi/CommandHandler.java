package me.kyrobi;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.Calendar;
import java.util.UUID;

public class CommandHandler implements CommandExecutor {

    private Main plugin;

    public CommandHandler(final Main plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {

        if (args[0].equalsIgnoreCase("iterate")) {
            File dir = new File("");
            String filename;
            DatabaseHandler db = new DatabaseHandler();
            File folderDirectory = new File(dir.getAbsolutePath() + File.separator + "world" + File.separator + "playerdata");

            File[] directoryListing = folderDirectory.listFiles();
            if (directoryListing != null) {
                for (File child : directoryListing) {
                    //trim = (child.getName().lastIndexOf('.'));
                    filename = (child.getName().substring(0, 36));

                    //If the user does not exist in the database, we add it
                    if (!db.ifExist(filename)) {
                        System.out.println(filename + " does not exist. Adding now!");
                        db.insert(filename, Bukkit.getOfflinePlayer(UUID.fromString(filename)).getFirstPlayed());
                    }
                }
            }

        }

        if (args[0].equalsIgnoreCase("convert")) {
            File dbFile = new File("");
            File folderDirectory = new File(dbFile.getAbsolutePath() + File.separator + "plugins" + File.separator + "JoinTimeIterator");
            String url = "jdbc:sqlite:" + folderDirectory + File.separator + "storage.db";
            String uuid;
            long time;
            String selectDescOrder = "SELECT * FROM `users` ORDER BY `time` ASC LIMIT 1000";

            try {
                Connection conn = DriverManager.getConnection(url); // Make connection
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(selectDescOrder); // Execute the command

                FileWriter myWriter = new FileWriter("first1000.txt");

                //We loop through the database. If the userID matches, we break out of the loop
                while (rs.next()) {
                    time = rs.getLong("time");
                    uuid = rs.getString("uuid");

                    System.out.println(Bukkit.getOfflinePlayer(UUID.fromString(uuid)).getName() + "|" + millisecondsToDate(time));
                    myWriter.write(Bukkit.getOfflinePlayer(UUID.fromString(uuid)).getName() + "|" + millisecondsToDate(time) + "\n");
                    //myWriter.write(Bukkit.getOfflinePlayer(UUID.fromString(uuid)).getName() + "\n");
                    //myWriter.write(millisecondsToDate(time) + "\n");
                }
                rs.close();
                conn.close();
                myWriter.close();

            } catch (SQLException | IOException ev) {
                ev.printStackTrace();
                System.out.println("Error code: " + ev.getMessage());
            }
            return true;
        }
        return true;
    }

    private String millisecondsToDate(long timeInMillis) {

        final Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeInMillis);
        final int mYear = calendar.get(1);
        int mMonth = calendar.get(2);
        ++mMonth;
        final int mDay = calendar.get(5);
        String date = "";
        String monthToName = null;
        switch (mMonth) {
            case 1: {
                monthToName = "January";
                break;
            }
            case 2: {
                monthToName = "February";
                break;
            }
            case 3: {
                monthToName = "March";
                break;
            }
            case 4: {
                monthToName = "April";
                break;
            }
            case 5: {
                monthToName = "May";
                break;
            }
            case 6: {
                monthToName = "June";
                break;
            }
            case 7: {
                monthToName = "July";
                break;
            }
            case 8: {
                monthToName = "August";
                break;
            }
            case 9: {
                monthToName = "September";
                break;
            }
            case 10: {
                monthToName = "October";
                break;
            }
            case 11: {
                monthToName = "November";
                break;
            }
            case 12: {
                monthToName = "December";
                break;
            }
            default: {
                monthToName = "Error";
                break;
            }
        }
        if (mYear == 1969) {
            date = " has never joined or it's an invalid username.";
            return date;
        }
        date = String.valueOf(String.valueOf(monthToName)) + " " + mDay + ", " + mYear;
        return date;
    }
}

