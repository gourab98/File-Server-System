import javax.swing.*;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class ServerController {
    static FrameElement frameElement=new FrameElement();
    public static void getClientData(Socket socket, JFrame jFrame,JPanel jPanel, ArrayList<MyFile> myFiles,int fileId){

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

                    JLabel jlFileName = frameElement.getJLabel(fileName);

                    if (FileExtension.getExtension(fileName).equalsIgnoreCase("txt")) {
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

                    myFiles.add(new MyFile(fileId, fileName, fileContentBytes, FileExtension.getExtension(fileName)));
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
