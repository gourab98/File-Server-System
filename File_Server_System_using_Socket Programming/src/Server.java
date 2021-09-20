import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {

    static OutputStream allFileOutputStream;
    static ArrayList<MyFile> allFiles = new ArrayList<>();
    static ArrayList<MyFile> myFiles = new ArrayList<>();
    public static int serverPortNumber;
    public static int showButtonPress=0;
    public static ServerSocket serverSocket = null;
    public static void main(String[] args) throws IOException {

        int fileId = 1; //0
        int[] size = new int[1];
        size[0] = 0;
        FrameElement serverFrame =new FrameElement();
        JFrame jFrame =serverFrame.getJFrame ("Server GUI");
        JPanel jPanel = serverFrame.getJPanel();
        JScrollPane jScrollPane = serverFrame.getJScrollPane();
        JLabel jlTitle = serverFrame.getJLabel("Server File GUI");
        JLabel waitClient = serverFrame.getJLabel("Waiting For the Client to Connect");
        JPanel jPanel1 = new JPanel();
        JButton jshowFiles = serverFrame.getButton("All Available Server Files");
        jPanel1.add(jshowFiles);
        JPanel jPanel2 = new JPanel();
        jPanel2.setBorder(new EmptyBorder(10,0,0,0));
        JLabel jPort = serverFrame.getJLabel("Your Port number is 1212");
        jPanel2.add(jPort);
        JPanel jPanel3 = new JPanel();
        jPanel3.setBorder(new EmptyBorder(0,0,0,0));

        JButton jDeleteFiles = serverFrame.getButton("Delete Server Files");
        jDeleteFiles.setBackground(Color.red);
        jPanel3.add(jDeleteFiles);


        jFrame.add(jlTitle);
        jFrame.add(waitClient);
        jFrame.add(jPanel2);
        jFrame.add(jPanel1);
        jFrame.add(jPanel3);
        jFrame.add(jScrollPane);
        jFrame.setVisible(true);

        jshowFiles.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showButtonPress=1;
                ServerFiles.showAvailableFiles();
            }
        });
        jDeleteFiles.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showButtonPress=0;
                ServerFiles.showAvailableFiles();
            }
        });


        ReadFiles.readAllFile();

        serverPortNumber= 1212;
        /** Server Socket will instantiated only one
            Singleton Pattern
         **/
        if(serverSocket==null) {
            try {
                serverSocket = new ServerSocket(serverPortNumber);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Socket socket = null;
        try {
            socket = serverSocket.accept();
            waitClient.setText("Client is Connected");
        } catch (IOException e) {
            e.printStackTrace();
        }

        allFileOutputStream = socket.getOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(allFileOutputStream);
        objectOutputStream.writeObject(allFiles);
        ServerController serverController=new ServerController();

        while (true) {
            serverController.getClientData(socket,jFrame,jPanel,myFiles,fileId);
        }
    }

    public static MouseListener getMyMouseListener() {
        return new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JPanel jPanel = (JPanel) e.getSource();
                int fileId = Integer.parseInt(jPanel.getName());
                for (MyFile myFile : allFiles) {
                    if (myFile.getId() == fileId) {
                        System.out.println(myFile.id);
                        JFrame jfPreview = FileDeleteByServer.createFrame(myFile.getId(),myFile.getName(), myFile.getData(), myFile.getFileExtension());
                        jfPreview.setVisible(true);
                    }
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        };
    }

}