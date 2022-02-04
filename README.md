# JoinTimeIterator
A tool to grab all the jointime of users on your server and put them in a database.
 
You will first need to run the JoinTime plugin once here: https://github.com/Kyrobi/JoinTime. This will generate the folder path and the database file.
 
After you run JoinTime on your server, you will need to unload that plugin or else commands or probably collide.
 
Usage: /jointime iterate. This will loop through all the player data in the world/playerdata/ folder.
Note: This will halt the server until the operation is done. DO NOT USE in a production server. I recommend copying the playerdata folder to a local server and run this plugin.


Usage: /jointime convert
This will get the first 1000 users from the database in ascending order and puts it in a 'first1000.txt' in your server root folder.
