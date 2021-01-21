import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.Scanner;

public class ClientMenu {
    public static void Menu(Socket clientSocket) {
        String line = "*******************";
        System.out.println(line);
        System.out.println("Welcome to The Server");
        System.out.println("See Below for Commands");
        System.out.println(line);
        System.out.println("To Open A Chat Channel Press:   O");
        System.out.println("To Disconnect Press:    D");
        Scanner clientMenuInput = ClientMenuScanner();
        try{
            String clientMenuChoice = clientMenuInput.nextLine();
            if (clientMenuChoice.toUpperCase() == "D")
            {
                clientSocket.close();
            }
            if (clientMenuChoice.toUpperCase() == "O")
            {
                System.out.println("Please Enter a Name for the channel");
            }
        }
        catch (IOException ClientMenuIOE)
        {
            System.err.println("Incorrect Input Entered");
            System.exit(1);
        }
        
        
    }

    private static Scanner ClientMenuScanner()
    {
        return new Scanner(System.in);
    }
    
}
