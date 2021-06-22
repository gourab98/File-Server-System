import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.file.*;

public class FileDeleteByServer extends Server{

   public static JFrame createFrame(String fileName, byte[] fileData, String fileExtension) {


        JFrame jFrame = new JFrame("File Deletor");
        jFrame.setSize(800, 800);

        JPanel jPanel = new JPanel();
        jPanel.setLayout(new BoxLayout(jPanel, BoxLayout.Y_AXIS));

        JLabel jlTitle = new JLabel("File Deletor");
        jlTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        jlTitle.setFont(new Font("Arial", Font.BOLD, 25));
        jlTitle.setBorder(new EmptyBorder(20, 0, 10, 0));

        JLabel jlPrompt = new JLabel("Are you sure you want to download " + fileName + "?");
        jlPrompt.setFont(new Font("Arial", Font.BOLD, 20));
        jlPrompt.setBorder(new EmptyBorder(20, 0, 10, 0));
        jlPrompt.setAlignmentX(Component.CENTER_ALIGNMENT);


        JButton jbYes = new JButton("Delete");
        jbYes.setPreferredSize(new Dimension(150, 75));
        jbYes.setFont(new Font("Arial", Font.BOLD, 20));
        jbYes.setBackground(Color.RED);

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

//                Path filePath=Paths.get("Server File\\" + fileName);

                Path filePath=Paths.get("Server File\\" + fileName);

//                File fileToDelete = new File(String.valueOf(filePath));

                try
                {
                    if (Files.deleteIfExists(filePath)){
                        System.out.println(fileName+ " Deleted Succesfully");
                        jFrame.dispose();
                    }
                    else
                        System.out.println(fileName+" Deletion Failed");
                }

                catch(NoSuchFileException Fe)
                {
                    System.out.println("No such file/directory exists");
                }
                catch(DirectoryNotEmptyException Fe)
                {
                    System.out.println("Directory is not empty.");
                }
                catch(IOException Fe)
                {
                    System.out.println("Invalid permissions.");
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
