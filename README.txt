Basic Chat Server/Twitter Application
-----------------------------------------------------------------------------------------------------------------------------------------

Created for Assignment 2 for Java Network Applications, This application allows two users to communicate via a Server using two seperate Clients. Server is currently Set to Only run on LocalHost and Port 12345, as per the assignment specifications, as such when running the server it is not required to enter any arguments.

----------------------------------------------------------------------------------------------------------------------------------------

Contents of Application

.vscode - Ignore this folder (Created by VScode for debugging files, etc).
Board.java - Class for creating and storing information on rooms; such as the name, clients and messages.
Client.java - Client used to connect to the server and pass through information to the server through commands and arguments.
ClientHandler - Creates a new Thread for each client when they connect to the server and handles the commands and messages for the board.
json-simple-1.1.1.jar - Used to compile the application to be used with JSON for the messages.
Message.java - JSON file that parses the sent messages and other information into json to be stored in the room boards and return the values when needed.
MessageBoard