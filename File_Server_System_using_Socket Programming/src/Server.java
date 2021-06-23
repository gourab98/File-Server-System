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
    public static void main(String[] args) throws IOException {

        int fileId = 1; //0
        int[] size = new int[1];
        size[0] = 0;

        JFrame jFrame = new JFrame("Server GUI");
        jFrame.setSize(700, 700);
        jFrame.setLayout(new BoxLayout(jFrame.getContentPane(), BoxLayout.Y_AXIS));
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel jPanel = new JPanel();
        jPanel.setLayout(new BoxLayout(jPanel, BoxLayout.Y_AXIS));

        JScrollPane jScrollPane = new JScrollPane(jPanel);
        jScrollPane.setPreferredSize(new Dimension(250,400));
        jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        JLabel jlTitle = new JLabel("Server File GUI");
        jlTitle.setFont(new Font("Arial", Font.BOLD, 25));
        jlTitle.setBorder(new EmptyBorder(20, 0, 10, 0));
        jlTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel waitClient = new JLabel("Waiting For the Client to Connect");
        waitClient.setFont(new Font("Arial", Font.BOLD, 20));
        waitClient.setBorder(new EmptyBorder(2, 0, 0, 0));
        waitClient.setAlignmentX(Component.CENTER_ALIGNMENT);



        JPanel jPanel2 = new JPanel();
        jPanel2.setBorder(new EmptyBorder(10,0,0,0));

        JLabel jPort = new JLabel("Your Port number is 1212");
        jPort.setFont(new Font("Arial",Font.BOLD,20));
        jPort.setAlignmentX(Component.LEFT_ALIGNMENT);

//        JTextField jPo = new JTextField("1212");
//        jPo.setFont(new Font("Arial",Font.ITALIC,20));
//        jPo.setPreferredSize(new Dimension(100,50));
//
//        JPanel jPanel3 = new JPanel();
//        jPanel3.setBorder(new EmptyBorder(1,0,0,0));
//
//        JButton jConnect = new JButton("Confirm");
//        jConnect.setPreferredSize(new Dimension(150,50));
//        jConnect.setFont(new Font("Arial",Font.BOLD,18));
//        jConnect.setAlignmentX(Component.CENTER_ALIGNMENT);

        jPanel2.add(jPort);
//        jPanel2.add(jPo);
//        jPanel3.add(jConnect);



        JPanel jPanel1 = new JPanel();
        //  jPanel1.setBorder(new EmptyBorder(2,0,0,0));

        JButton jshowFiles = new JButton("All Available Server Files");
        jshowFiles.setPreferredSize(new Dimension(250,50));
        jshowFiles.setFont(new Font("Arial", Font.BOLD, 15));
        jshowFiles.setAlignmentX(Component.CENTER_ALIGNMENT);

        jPanel1.add(jshowFiles);

        JPanel jPanel3 = new JPanel();
        jPanel3.setBorder(new EmptyBorder(0,0,0,0));

        JButton jDeleteFiles = new JButton("Delete Server Files");
        jDeleteFiles.setPreferredSize(new Dimension(250,50));
        jDeleteFiles.setFont(new Font("Arial", Font.BOLD, 15));
        jDeleteFiles.setAlignmentX(Component.RIGHT_ALIGNMENT);
        jDeleteFiles.setBackground(Color.red);

        jPanel3.add(jDeleteFiles);


        jFrame.add(jlTitle);
        jFrame.add(waitClient);
        jFrame.add(jPanel2);
        //       jFrame.add(jPanel3);
        jFrame.add(jPanel1);
        // jPanel.add(jshowFiles);
        jFrame.add(jPanel3);
        jFrame.add(jScrollPane);
        jFrame.setVisible(true);


//        jConnect.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//
//                portNumber= Integer.parseInt(jPo.getText());
//                System.out.println(portNumber);
//                JOptionPane.showMessageDialog(jFrame,"Port number of this server is "+ portNumber);
//            }
//        });
//
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
        ServerSocket serverSocket = new ServerSocket(serverPortNumber);

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