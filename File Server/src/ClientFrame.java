import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ClientFrame extends Client {
    public static void showFrame(){
        JFrame jFrame= new JFrame("New Client Gui");
        jFrame.setSize(700,700);
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


        JTextField jPo = new JTextField("0000");
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

    }

}

