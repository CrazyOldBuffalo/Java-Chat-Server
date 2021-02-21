Basic Chat Server/Twitter Application
-----------------------------------------------------------------------------------------------------------------------------------------

Created for Assignment 2 for Java Network Applications, This application allows two users to communicate via a Server using two seperate Clients. 
Server is currently Set to Only run on LocalHost and Port 12345, as per the assignment specifications, as such when running the server it is not required to enter any arguments.

----------------------------------------------------------------------------------------------------------------------------------------

Contents of Application

.vscode - Ignore this folder (Created by VScode for debugging files, etc).
Board.java - Class for creating and storing information on rooms; such as the name, clients and messages.
Client.java - Client used to connect to the server and pass through information to the server through commands and arguments.
ClientHandler - Creates a new Thread for each client when they connect to the server and handles the commands and messages for the board.
json-simple-1.1.1.jar - Used to compile the application to be used with JSON for the messages.
Message.java - JSON file that parses the sent messages and other information into json to be stored in the room boards and return the values when needed.
README.txt - This file (clearly)
Request.java - Unused and unimplemented request file.
Server.java - Used to connect the two Clients together using the socket and LocalHost, and pass through any commands/messages for processing.

--------------------------------------------------------------------------------------------------------------------------------------------------------

Persistance Task

To a degree this application does use persistance to ensure that previous messages have been saved to a txt file, this file is only created when one
message has been posted to the board. This is done in Board.java, but it will only write the messages to the file, thus if the server is stopped and#
then restarted the board will no longer exist and will have to be recreated and all clients will have to resubscribe to it.

The persistance exists and will work however if a user creates a new channel using the same name and this will then allow all messages to be added from
the stored txt file and read, but any client who was in the room will have to subscribe again (unless they create it in the next iteration).

i.e - This example showcases the extent of the presistance.
{   open test
    postto test hi
    "server dies"

    open test
    read test
}
This will return all the messages that were saved before the server failed.

---------------------------------------------------------------------------------------------------------------------------------------------------------

To compile & run

To compile the application via command line do the following:

    - Navigate to the directory the file is stored using:   cd /filepathhere/
    - When in the directory compile with Java using:        javac -cp json-simple-1.1.1.jar *.java
    - This will compile all java files within the folder.
    - The Application is now ready for running. 

To run the application via command line follow these steps:

    - (if not continuing from compiling) navigate to the directory where the file is stored using:  cd /filepathhere/
    - First Run the Server using this command to run the server first:                              java Server
    - Then you can run a Client using the same command as above:                                    java Client
    - Issue all commands through the client and run as many clients as needed.
