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

        JPanel jPanel2 = new JPanel();
        jPanel2.setBorder(new EmptyBorder(10,0,0,0));

        JLabel jPort = serverFrame.getJLabel("Your Port number is 1212");
        jPanel2.add(jPort);

        JPanel jPanel1 = new JPanel();
        JButton jshowFiles = serverFrame.getButton("All Available Server Files");
        jPanel1.add(jshowFiles);

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

        if(serverSocket==null)
        serverSocket = new ServerSocket(serverPortNumber);

        Socket socket = serverSocket.accept();
        waitClient.setText("Client is Connected");

        allFileOutputStream = socket.getOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(allFileOutputStream);
        objectOutputStream.writeObject(allFiles);


        while (true) {

            try {

                DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
                int fileNameLength = dataInputStream.readInt();

                if (fileNameLength > 0) {

                    byte[] fileNameBytes = new byte[fileNameLength];
                    dataInputStream.readFully(fileNameBytes, 0, fileNameBytes.length);

                    String fileName = new String(fileNameBytes);

                    int fileContentLength = dataInputStream.readInt();

                    if (fileContentLength > 0) {
                        byte[] fileContentBytes = new byte[fileContentLength];
                        dataInputStream.readFully(fileContentBytes, 0, fileContentBytes.length);

                        JPanel jpFileRow = new JPanel();
                        jpFileRow.setLayout(new BoxLayout(jpFileRow, BoxLayout.X_AXIS));

                        JLabel jlFileName = new JLabel(fileName);
                        jlFileName.setFont(new Font("Arial", Font.BOLD, 20));
                        jlFileName.setBorder(new EmptyBorder(10, 0, 10, 0));

                        if (getFileExtension(fileName).equalsIgnoreCase("txt")) {
                            jpFileRow.setName((String.valueOf(fileId)));

                            jpFileRow.add(jlFileName);
                            jPanel.add(jpFileRow);

                            jFrame.validate();
                        } else {
                            jpFileRow.setName((String.valueOf(fileId)));

                            jpFileRow.add(jlFileName);
                            jPanel.add(jpFileRow);

                            jFrame.validate();
                        }

                        myFiles.add(new MyFile(fileId, fileName, fileContentBytes, getFileExtension(fileName)));
                        fileId++;
                        ReadFiles.readAllFile();

                        File fileToDownload = new File("Server File/"+fileName);

                        FileOutputStream fileOutputStream = new FileOutputStream(fileToDownload);
                        fileOutputStream.write(fileContentBytes);
                        fileOutputStream.close();

                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static String getFileExtension(String fileName) {
        return FileExtension.getExtension(fileName);
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