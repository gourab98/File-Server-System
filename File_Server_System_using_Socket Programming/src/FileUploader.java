import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;

public class FileUploader {
    public static void uploadFile(File fileToSend, Socket socket){
        try {
            FileInputStream fileInputStream = new FileInputStream(fileToSend.getAbsolutePath());

            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());

            String fileName = fileToSend.getName();
            byte[] fileNameBytes = fileName.getBytes();
            byte[] fileContentBytes = new byte[(int) fileToSend.length()];

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
