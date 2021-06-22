import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class Client {

    private static String serverName;
    private static int portNumber;
    public static Socket socket;
    public static ArrayList<MyFile> downloadedfile = new ArrayList<>();
    public static ArrayList<MyFile> allFiles = new ArrayList<>();

    public static void main(String[] args) throws IOException, ClassNotFoundException {

        final File[] fileToSend = new File[1];
        final String[] string3 = new String[1];
        final int[] b =new int[1];
        int[] size = new int[1];
        size[0] = 0;

        JFrame jFrame = new JFrame("Client GUI");
        jFrame.setSize(700,700);
        jFrame.setLayout(new BoxLayout(jFrame.getContentPane(),BoxLayout.Y_AXIS));
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel jlTitle = new JLabel("Client GUI");
        jlTitle.setFont(new Font("Arial", Font.BOLD, 25));
        jlTitle.setBorder(new EmptyBorder(20,0,10,0));
        jlTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel jPanel = new JPanel();
        jPanel.setBorder(new EmptyBorder(10,0,10,0));

        JLabel jIplable = new JLabel("Enter IP : ");
        jIplable.setFont(new Font("Arial",Font.BOLD,20));
        jIplable.setAlignmentX(Component.LEFT_ALIGNMENT);


        JTextField jIp = new JTextField("localhost");
        jIp.setFont(new Font("Arial",Font.ITALIC,20));
        jIp.setPreferredSize(new Dimension(100,50));
        jIp.setAlignmentX(Component.LEFT_ALIGNMENT);

        jPanel.add(jIplable);
        jPanel.add(jIp);

        JPanel jPanel2 = new JPanel();
        jPanel2.setBorder(new EmptyBorder(3,0,0,0));

        JLabel jPort = new JLabel("Enter Port : ");
        jPort.setFont(new Font("Arial",Font.BOLD,20));
        jPort.setAlignmentX(Component.LEFT_ALIGNMENT);


        JTextField jPo = new JTextField("0000");
        jPo.setFont(new Font("Arial",Font.ITALIC,20));
        jPo.setPreferredSize(new Dimension(100,50));

        JPanel jPanel3 = new JPanel();
        jPanel3.setBorder(new EmptyBorder(3,0,0,0));

        JButton jConnect = new JButton("Connect");
        jConnect.setPreferredSize(new Dimension(150,50));
        jConnect.setFont(new Font("Arial",Font.BOLD,18));
        jConnect.setAlignmentX(Component.CENTER_ALIGNMENT);

        jPanel2.add(jPort);
        jPanel2.add(jPo);
        jPanel3.add(jConnect);


        JLabel jlFileName = new JLabel("Choose a file to send to the server");
        jlFileName.setFont(new Font("Arial", Font.BOLD, 20));
        jlFileName.setBorder(new EmptyBorder(50, 0, 0, 0));
        jlFileName.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel jpButton = new JPanel();
        jpButton.setBorder(new EmptyBorder(30, 0, 10, 0));

        JButton jbSendFile = new JButton("Upload File");
        jbSendFile.setPreferredSize(new Dimension(150, 60));
        jbSendFile.setFont(new Font("Arial", Font.BOLD, 20));

        JButton jbChooseFile = new JButton("Choose File");
        jbChooseFile.setPreferredSize(new Dimension(150, 60));
        jbChooseFile.setFont(new Font("Arial", Font.BOLD, 20));

        JPanel jPanel4 = new JPanel();
        jPanel4.setBorder(new EmptyBorder(10,0,0,0));

        JButton jDownload = new JButton("Download from server");
        jDownload.setPreferredSize(new Dimension(300,75));
        jDownload.setFont(new Font("Arial",Font.BOLD,20));

        jpButton.add(jbSendFile);
        jpButton.add(jbChooseFile);

        jPanel4.add(jDownload);

        jConnect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String string1 = jIp.getText();
                String string2 = jPo.getText();
                int  a = Integer.parseInt(string2);
                string3[0] = string1;
                b[0] = a;

                serverName = jIp.getText();
                portNumber= Integer.parseInt(jPo.getText());

                try {
                    socket = new Socket(serverName, portNumber);
                    InputStream inputStream = socket.getInputStream();
                    ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
                    allFiles = (ArrayList<MyFile>) objectInputStream.readObject();
                    System.out.println(allFiles.size());

                } catch (IOException | ClassNotFoundException ex) {
                    ex.printStackTrace();
                }
                System.out.println(serverName);
                System.out.println(portNumber);

                if(b[0] == 1212){
                    JOptionPane.showMessageDialog(jFrame,"Connected with server");
                }else{
                    JOptionPane.showMessageDialog(jFrame,"Server is not connected");
                }
            }
        });

        jbChooseFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser jFileChooser = new JFileChooser();
                jFileChooser.setDialogTitle("Choose a file to send");

                if(jFileChooser.showOpenDialog(null)== JFileChooser.APPROVE_OPTION){
                    fileToSend[0] = jFileChooser.getSelectedFile();
                    jlFileName.setText("The file you want to send is "+ fileToSend[0].getName());
                }
            }
        });
//        socket = new Socket("localhost", 1212);
//        InputStream inputStream = socket.getInputStream();
//        ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
//        allFiles = (ArrayList<MyFile>) objectInputStream.readObject();
//        System.out.println(allFiles.size());

        jbSendFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(fileToSend[0]== null){
                    jlFileName.setText("Please choose a file first.");
                }
                else {
                    try {
                        FileInputStream fileInputStream = new FileInputStream(fileToSend[0].getAbsolutePath());


                        DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());

                        String fileName = fileToSend[0].getName();
                        byte[] fileNameBytes = fileName.getBytes();
                        byte[] fileContentBytes = new byte[(int) fileToSend[0].length()];

                        fileInputStream.read(fileContentBytes);

                        dataOutputStream.writeInt(fileNameBytes.length);
                        dataOutputStream.write(fileNameBytes);

                        dataOutputStream.writeInt(fileContentBytes.length);
                        dataOutputStream.write(fileContentBytes);

                    } catch (IOException error){
                        error.printStackTrace();
                    }
                }
            }
        });

        jDownload.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ShowFiles.showAvailableFiles();

            }
        });

        jFrame.add(jlTitle);
        jFrame.add(jPanel);
        jFrame.add(jPanel2);
        jFrame.add(jPanel3);
        jFrame.add(jlFileName);
        jFrame.add(jpButton);
        jFrame.add(jPanel4);
        jFrame.setVisible(true);

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

                for (MyFile myFile : downloadedfile) {

                    if (myFile.getId() == fileId) {
                        JFrame jfPreview = FileDownloadByClient.createFrame(myFile.getName(), myFile.getData(), myFile.getFileExtension());
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