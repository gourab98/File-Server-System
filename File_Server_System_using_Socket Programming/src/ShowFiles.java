import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ShowFiles extends Client{
    public static void showAvailableFiles(){
        int[] size = new int[1];
        size[0] = 0;
        JFrame jFrame1 = new JFrame("All Available Server Files");
        jFrame1.setSize(600, 600);
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
        downloadedfile.clear();

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


                downloadedfile.add(newFile);
                fileid++;

                System.out.println(newFile.getId() + " " + newFile.getName() + " " + newFile.getData().length + " " + newFile.getFileExtension());
            } catch (FileNotFoundException er) {
                er.printStackTrace();
            } catch (IOException er) {
                er.printStackTrace();
            }
        }

        size[0] = i;

        for (MyFile file : downloadedfile) {

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
}
