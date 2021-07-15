import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.file.*;

public class FileDeleteByServer extends Server{
    static FrameElement showFrame=new FrameElement();
    public static JFrame createFrame(int id,String fileName, byte[] fileData, String fileExtension) {
        JFrame jFrame = showFrame.getJFrame("File Deleter");
        JPanel jPanel = showFrame.getJPanel();
        JLabel jlTitle = showFrame.getJLabel("Be Careful!");
        JLabel jlPrompt = showFrame.getJLabel("Are you sure you want to delete " + fileName + "?");
        JButton jbYes = showFrame.getButton1("Delete");
        jbYes.setBackground(Color.RED);
        JButton jbNo = showFrame.getButton1("No");

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

                Path filePath=Paths.get("Server File\\" + fileName);
                File fileToDelete = new File(String.valueOf(filePath));

                try
                {
                    if (Files.deleteIfExists(filePath)){
                        JOptionPane.showMessageDialog(jFrame,"Deleted Successfully");
                        System.out.println(fileName+ " Deleted Successfully");
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
                catch(FileSystemException Fe){
                    System.out.println(Fe);
                    System.out.println("Access Denied.");
                    JOptionPane.showMessageDialog(jFrame,"Throwing java File System Exception","Delete Failed",JOptionPane.WARNING_MESSAGE);
//                    System.out.println(Fe);
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