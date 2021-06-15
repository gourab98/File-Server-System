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
    static ArrayList<MyFile> downloadedFile = new ArrayList<>();

    public static int serverPortNumber = 1212;
    public static void main(String[] args) throws IOException {

        int fileId = 0;
        int[] size = new int[1];
        size[0] = 0;

        JFrame jFrame = new JFrame("Server GUI");
        jFrame.setSize(700, 700);
        jFrame.setLayout(new BoxLayout(jFrame.getContentPane(), BoxLayout.Y_AXIS));
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel jPanel = new JPanel();
        jPanel.setLayout(new BoxLayout(jPanel, BoxLayout.Y_AXIS));

        JScrollPane jScrollPane = new JScrollPane(jPanel);
        jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        JLabel jlTitle = new JLabel("Server File GUI");
        jlTitle.setFont(new Font("Arial", Font.BOLD, 25));
        jlTitle.setBorder(new EmptyBorder(20, 0, 10, 0));
        jlTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel waitClient = new JLabel("Waiting For the Client to Connect");
        waitClient.setFont(new Font("Arial", Font.BOLD, 20));
        waitClient.setBorder(new EmptyBorder(20, 0, 0, 0));
        waitClient.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel jPanel1 = new JPanel();
        jPanel1.setBorder(new EmptyBorder(2,0,0,0));

        JButton jDownload = new JButton("All Available Server Files");
        jDownload.setPreferredSize(new Dimension(250,100));
        jDownload.setFont(new Font("Arial", Font.BOLD, 15));
        jDownload.setAlignmentX(Component.CENTER_ALIGNMENT);



        jFrame.add(jlTitle);
        jFrame.add(waitClient);
        jFrame.add(jPanel1);
        jPanel.add(jDownload);
        jFrame.add(jScrollPane);
        jFrame.setVisible(true);

        jDownload.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                JFrame jFrame1 = new JFrame("All Server File");
                jFrame1.setSize(500, 500);
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
                for (MyFile file : downloadedFile) {

                    JPanel jpFileRow = new JPanel();
                    jpFileRow.setLayout(new BoxLayout(jpFileRow, BoxLayout.X_AXIS));

                    JLabel jlFileName = new JLabel(file.name);
                    jlFileName.setFont(new Font("Arial", Font.BOLD, 20));
                    jlFileName.setBorder(new EmptyBorder(10, 0, 10, 0));
                    if (getFileExtension(file.name).equalsIgnoreCase("txt")) {

                        jpFileRow.setName((String.valueOf(file.id)));
                        jpFileRow.addMouseListener(Client.getMyMouseListener());

                        jpFileRow.add(jlFileName);
                        jPanel.add(jpFileRow);
                        jFrame1.validate();
                    } else {

                        jpFileRow.setName((String.valueOf(file.id)));

                        jpFileRow.addMouseListener(Client.getMyMouseListener());

                        jpFileRow.add(jlFileName);
                        jPanel.add(jpFileRow);

                        jFrame1.validate();
                    }
                }
            }
        });
        readAllFile();

        ServerSocket serverSocket = new ServerSocket(1212);

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
                        readAllFile();

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

        int i = fileName.lastIndexOf('.');
        if (i > 0) {
            return fileName.substring(i + 1);
        } else {
            return "No extension found.";
        }
    }



    public static JFrame createFrame(String fileName, byte[] fileData, String fileExtension) {


        JFrame jFrame = new JFrame("File Downloader");
        jFrame.setSize(400, 400);

        JPanel jPanel = new JPanel();
        jPanel.setLayout(new BoxLayout(jPanel, BoxLayout.Y_AXIS));

        JLabel jlTitle = new JLabel("File Downloader");
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
                File fileToDownload = new File("Server File\\" + fileName);
                fileToDownload.delete();
                jFrame.dispose();
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


    public static void readAllFile() {

        int fileId = 0;
        File dFile = new File("Server File/");
        File[] listOfALLFiles = dFile.listFiles();

        for(File file : listOfALLFiles){

            try {

                FileInputStream fileInputStream = new FileInputStream(file.getAbsolutePath());
                String fileName = file.getName();
                byte[] fileContentBytes = new byte[(int) file.length()];
                if((int) file.length() > 0){
                    fileInputStream.read(fileContentBytes);
                }

                MyFile newFile = new MyFile(fileId, fileName, fileContentBytes, getFileExtension(fileName));
                newFile.setData(fileContentBytes);
                allFiles.add(newFile);
                fileId++;

                System.out.println(newFile.getId()+" "+ newFile.getName()+" "+newFile.getName().length()+ " "+ newFile.getFileExtension());

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}