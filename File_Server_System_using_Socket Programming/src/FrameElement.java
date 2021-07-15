import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class FrameElement implements IFrame {

    JPanel jPanel = new JPanel();
    @Override
    public JFrame getJFrame(String title) {
        JFrame jFrame = new JFrame(title);
        jFrame.setSize(700, 700);
        jFrame.setLayout(new BoxLayout(jFrame.getContentPane(), BoxLayout.Y_AXIS));
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        return jFrame;
    }

    @Override
    public JPanel getJPanel() {
        jPanel.setLayout(new BoxLayout(jPanel, BoxLayout.Y_AXIS));
        return jPanel;
    }

    @Override
    public JScrollPane getJScrollPane() {
        JScrollPane jScrollPane = new JScrollPane(jPanel);
        jScrollPane.setPreferredSize(new Dimension(250,400));
        jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        return  jScrollPane;
    }

    @Override
    public JLabel getJLabel(String title) {
        JLabel jlTitle = new JLabel(title);
        jlTitle.setFont(new Font("Arial", Font.BOLD, 25));
        jlTitle.setBorder(new EmptyBorder(20, 0, 10, 0));
        jlTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        return jlTitle;
    }

    @Override
    public JButton getButton(String title) {
        JButton jButton = new JButton(title);
        jButton .setPreferredSize(new Dimension(250,50));
        jButton .setFont(new Font("Arial", Font.BOLD, 15));
        jButton .setAlignmentX(Component.CENTER_ALIGNMENT);
        return  jButton ;
    }
    @Override
    public JButton getButton1(String title) {
        JButton jButton = new JButton(title);
        jButton.setPreferredSize(new Dimension(150, 75));
        jButton.setFont(new Font("Arial", Font.BOLD, 20));
        return  jButton ;
    }

    @Override
    public JTextField getJTextField(String title) {
        JTextField jTextField = new JTextField(title);
        jTextField.setFont(new Font("Arial",Font.ITALIC,20));
        jTextField.setPreferredSize(new Dimension(100,50));
        return jTextField;
    }

    public JPanel getJPanelType1(int top,int left,int bottom ,int right) {
        JPanel jPanel = new JPanel();
        jPanel.setBorder(new EmptyBorder(top, left, bottom, right));
        return jPanel;
    }
}
