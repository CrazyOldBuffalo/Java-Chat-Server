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

i.e - This example showcases the extent of the persistance.
{   
    open test
    postto test hi
    "server dies"

    open test
    read test
    hi
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

-----------------------------------------------------------------------------------------------------------------------------------------

List of Commands and their Usages

name    - Use name to set the name of your client when posting messages, follow the command with the name you would like to set
          e.g: name Harry
          name will only allow you to enter a name that is more than 2 characters long and is not the same as any other.
          You can change your name as many times as you like whenever you like, changing you name will break subscriptions

post    - Use post to send a message to the global message board that all clients have access to, follow the command with your message.
          e.g: post Hello There

postto  - Use postto to send a message to a specific room, this command must be followed with a room name and then your message.
          e.g: postto friends How is everyone today?
          This command must have an open room to post to, and the client must be subscribed to the room to be able to post to it.

read    - read has two usages, the first is to read all unread messages in the global room to the client
          e.g: read
          The second usage is to read all unread messages in a particular room, for this command you must add the name of the room to the
          argument. This will only work if the client is subscribed.
          e.g: read friends

open    - Opens a new Channel 

