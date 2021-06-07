import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class Client {

    public static void main(String[] args) {
        final File[] fileToSend = new File[1];

        JFrame jFrame = new JFrame("Client");
        jFrame.setSize(450,450);
        jFrame.setLayout(new BoxLayout(jFrame.getContentPane(),BoxLayout.Y_AXIS));
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //when we exit from the GUI, the program will be close as well.

        JLabel jlTitle = new JLabel("Field Sender");
        jlTitle.setFont(new Font("Arial", Font.BOLD, 25));
        jlTitle.setBorder(new EmptyBorder(20,0,10,0));
        jlTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel jlFileName = new JLabel("Choose a file to send");
        jlFileName.setFont(new Font("Arial", Font.BOLD, 20));
        jlFileName.setBorder(new EmptyBorder(50, 0, 0, 0));
        jlFileName.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel jpButton = new JPanel();
        jpButton.setBorder(new EmptyBorder(75, 0, 10, 0));

        JButton jbSendFile = new JButton("Send File");
        jbSendFile.setPreferredSize(new Dimension(150, 75));
        jbSendFile.setFont(new Font("Arial", Font.BOLD, 20));

        JButton jbChooseFile = new JButton("Choose File");
        jbChooseFile.setPreferredSize(new Dimension(150, 75));
        jbChooseFile.setFont(new Font("Arial", Font.BOLD, 20));

        jpButton.add(jlFileName);
        jpButton.add(jbChooseFile);

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

        jbSendFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(fileToSend[0]== null){
                    jlFileName.setText("Please choose a file first.");
                }
            }
        });




    }
}
