import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileDownloadByClient  extends Client{
    static FrameElement showFrame=new FrameElement();
    public static JFrame createFrame(String fileName, byte[] fileData, String fileExtension) {
        JFrame jFrame = showFrame.getJFrame("Download file");
        JPanel jPanel = showFrame.getJPanel();
        JLabel jlTitle = showFrame.getJLabel("File Downloader");
        JLabel jlPrompt = showFrame.getJLabel("Are you sure you want to download " + fileName + "?");
        JLabel jlFileContent = new JLabel();
        jlFileContent.setAlignmentX(Component.CENTER_ALIGNMENT);
        JPanel jpButtons = showFrame.getJPanelType1(20, 0, 10, 0);
        JButton jbYes = showFrame.getButton1("Yes");
        JButton jbNo = showFrame.getButton1("No");
        jpButtons.add(jbYes);
        jpButtons.add(jbNo);

        if (fileExtension.equalsIgnoreCase("txt")) {
            jlFileContent.setText("<html>" + new String(fileData) + "</html>");
        } else {
            jlFileContent.setIcon(new ImageIcon(fileData));
        }
        System.out.println("IN FD ............."+fileName + ".......data... "+fileData);
        jbYes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                File fileToDownload = new File("Client File/" + fileName);
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
