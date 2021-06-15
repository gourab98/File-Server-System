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

    static Socket socket;
    static ArrayList<MyFile> downloadedFile = new ArrayList<>();
    static ArrayList<MyFile> allFiles = new ArrayList<>();

    public static void main(String[] args) throws IOException, ClassNotFoundException {

        final File[] fileToSend = new File[1];
        final String[] string3 = new String[1];
        final int[] check = new int[1];

        int[] size = new int[1];
        size[0] = 0;

        JFrame jFrame = new JFrame("Client GUI");
        jFrame.setSize(900,600);
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


        JTextField jPo = new JTextField("1212");
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
                int  port = Integer.parseInt(string2);
                string3[0] = string1;
                 check[0] = port;
                if(check[0] == 1212){
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
<<<<<<< HEAD

            socket = new Socket("localhost", 1212);
            InputStream inputStream = socket.getInputStream();
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            allFiles = (ArrayList<MyFile>) objectInputStream.readObject();
            System.out.println(allFiles.size());
=======
        socket = new Socket("localhost", 1234);
        InputStream inputStream = socket.getInputStream();
        ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
        allFiles = (ArrayList<MyFile>) objectInputStream.readObject();
        System.out.println(allFiles.size());
>>>>>>> fd083e34f493d342cac5f3d686692d6c05b55ed2

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
                JFrame jFrame1 = new JFrame("Server Files");
                jFrame1.setSize(400, 400);
                jFrame1.setLayout(new BoxLayout(jFrame1.getContentPane(), BoxLayout.Y_AXIS));

                JPanel jPanel = new JPanel();
                jPanel.setLayout(new BoxLayout(jPanel, BoxLayout.Y_AXIS));

                JScrollPane jScrollPane = new JScrollPane(jPanel);
                jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

                JLabel jlTitle = new JLabel("File Lists");
                jlTitle.setFont(new Font("Arial", Font.BOLD, 25));
                jlTitle.setBorder(new EmptyBorder(20, 0, 10, 0));
                jlTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

                jFrame1.add(jlTitle);
                jFrame1.add(jScrollPane);

                jFrame1.setVisible(true);



                int fileid = 0;
                File dic = new File("Server File/");
                File[] diclist = dic.listFiles();
                int i=0;
                downloadedFile.clear();

                for (File file : diclist) {
                    i++;
                    try {
                        FileInputStream fileInputStream = new FileInputStream(file.getAbsolutePath());
                        String FileName = file.getName();
                        byte[] fileContentBytes = new byte[(int) file.length()];
                        if ((int) file.length() > 0) {
                            fileInputStream.read(fileContentBytes);
                        }

                        MyFile newFile = new MyFile(fileid, FileName, fileContentBytes, getFileExtension(FileName));
                        newFile.setData(fileContentBytes);


                        downloadedFile.add(newFile);
                        fileid++;

                        System.out.println(newFile.getId() + " " + newFile.getName() + " " + newFile.getData().length + " " + newFile.getFileExtension());
                    } catch (FileNotFoundException er) {
                        er.printStackTrace();
                    } catch (IOException er) {
                        er.printStackTrace();
                    }
                }

                size[0] = i;

                for (MyFile file : downloadedFile) {

                    JPanel jpFileRow = new JPanel();
                    jpFileRow.setLayout(new BoxLayout(jpFileRow, BoxLayout.X_AXIS));

                    JLabel jlFileName = new JLabel(file.name);
                    jlFileName.setFont(new Font("Arial", Font.BOLD, 20));
                    jlFileName.setBorder(new EmptyBorder(10, 0, 10, 0));
                    if (getFileExtension(file.name).equalsIgnoreCase("txt")) {

                        jpFileRow.setName((String.valueOf(file.id)));
                        jpFileRow.addMouseListener(getMyMouseListener());

                        jpFileRow.add(jlFileName);
                        jPanel.add(jpFileRow);
                        jFrame1.validate();
                    } else {

                        jpFileRow.setName((String.valueOf(file.id)));

                        jpFileRow.addMouseListener(getMyMouseListener());

                        jpFileRow.add(jlFileName);
                        jPanel.add(jpFileRow);

                        jFrame1.validate();
                    }
                }

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

        int i = fileName.lastIndexOf('.');
        if (i > 0) {
            return fileName.substring(i + 1);
        } else {
            return "No extension found.";
        }
    }

    public static MouseListener getMyMouseListener() {
        return new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JPanel jPanel = (JPanel) e.getSource();
                int fileId = Integer.parseInt(jPanel.getName());

                for (MyFile myFile : downloadedFile) {

                    if (myFile.getId() == fileId) {
                        JFrame jfPreview = createFrame(myFile.getName(), myFile.getData(), myFile.getFileExtension());
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

    public static JFrame createFrame(String fileName, byte[] fileData, String fileExtension) {

        JFrame jFrame = new JFrame("File Downloader");
        jFrame.setSize(400, 400);

        JPanel jPanel = new JPanel();
        jPanel.setLayout(new BoxLayout(jPanel, BoxLayout.Y_AXIS));

        JLabel jlTitle = new JLabel("Downloader");
        jlTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        jlTitle.setFont(new Font("Arial", Font.BOLD, 25));
        jlTitle.setBorder(new EmptyBorder(20, 0, 10, 0));


        JLabel jlPrompt = new JLabel("Are you sure you want to download " + fileName + "?");
        jlPrompt.setFont(new Font("Arial", Font.BOLD, 20));
        jlPrompt.setBorder(new EmptyBorder(20, 0, 10, 0));
        jlPrompt.setAlignmentX(Component.CENTER_ALIGNMENT);


        JButton jbYes = new JButton("Yes");
        jbYes.setPreferredSize(new Dimension(150, 75));
        jbYes.setFont(new Font("Arial", Font.BOLD, 20));

        JButton jbNo = new JButton("No");
        jbNo.setPreferredSize(new Dimension(150, 75));
        jbNo.setFont(new Font("Arial", Font.BOLD, 20));

        JLabel jlFileContent = new JLabel();
        jlFileContent.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel jpButtons = new JPanel();
        jpButtons.setBorder(new EmptyBorder(20, 0, 10, 0));

        jpButtons.add(jbYes);
        jpButtons.add(jbNo);


        if (fileExtension.equalsIgnoreCase("txt")) {
            jlFileContent.setText("<html>" + new String(fileData) + "</html>");
        } else {
            jlFileContent.setIcon(new ImageIcon(fileData));
        }

        jbYes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                File fileToDownload = new File("Client File/"+fileName);
                try {

                    FileOutputStream fileOutputStream = new FileOutputStream(fileToDownload);
                    fileOutputStream.write(fileData);
                    fileOutputStream.close();
                    jFrame.dispose();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

            }
        });


        jbNo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jFrame.dispose();
            }
        });

        jPanel.add(jlTitle);
        jPanel.add(jlPrompt);
        jPanel.add(jlFileContent);
        jPanel.add(jpButtons);
        jFrame.add(jPanel);

        return jFrame;

    }



}
