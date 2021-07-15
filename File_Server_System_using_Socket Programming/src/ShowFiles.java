import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
public class ShowFiles extends Client{
    public static FrameElement showFrame =new FrameElement();
    public static void showAvailableFiles(){

        int[] size = new int[1];
        size[0] = 0;
        JFrame jFrame1 = showFrame.getJFrame("Server Files");
        jFrame1.setSize(600, 600);
        JPanel jPanel = showFrame.getJPanel();
        JScrollPane jScrollPane = new JScrollPane(jPanel);
        jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        JLabel jlTitle = showFrame.getJLabel("File Lists");
        jFrame1.add(jlTitle);
        jFrame1.add(jScrollPane);
        jFrame1.setVisible(true);

        downloadedFile.clear();

        int fileId = 0;
        File dic = new File("Server File/");
        File[] dicList = dic.listFiles();
        int i=0;
        for (File file : dicList) {
            i++;
            try {
                FileInputStream fileInputStream = new FileInputStream(file.getAbsolutePath());
                String FileName = file.getName();
                byte[] fileContentBytes = new byte[(int) file.length()];
                if ((int) file.length() > 0) {
                    fileInputStream.read(fileContentBytes);
                }
                MyFile newFile = new MyFile(fileId, FileName, fileContentBytes, FileExtension.getExtension(FileName));
                newFile.setData(fileContentBytes);
                downloadedFile.add(newFile);
                fileId++;
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
            JLabel jlFileName = showFrame.getJLabel(file.name);
            jpFileRow.setName((String.valueOf(file.id)));
            jpFileRow.addMouseListener(getMyMouseListener());
            jpFileRow.add(jlFileName);
            jPanel.add(jpFileRow);
            jFrame1.validate();
        }
    }
}
