import javax.swing.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.ArrayList;

public class ClientConnection {
    public static int pTrueClient;
    public static Socket getClientConnected(String serverName, int portNumber, ArrayList<MyFile> allFiles ,int ptrue,JFrame jFrame){
        Socket socket = null;
        try {
            socket = new Socket(serverName, portNumber);
            InputStream inputStream = socket.getInputStream();
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            allFiles = (ArrayList<MyFile>) objectInputStream.readObject();
            System.out.println(allFiles.size());
            ptrue = 1;
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        System.out.println(serverName);
        System.out.println(portNumber);

        if (ptrue==0) {
            JOptionPane.showMessageDialog(jFrame,"Server is not connected","Error",JOptionPane.WARNING_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(jFrame,"Connected with server");
        }
        pTrueClient=ptrue;
        return socket;
    }
    public int getPTrue(){
        return pTrueClient;
    }
}
