import javax.swing.*;

public interface IFrame {
    public JFrame getJFrame(String title);

    public JPanel getJPanel();

    public JScrollPane getJScrollPane();

    public JLabel getJLabel(String title);

    public JButton getButton(String title);
    public JButton getButton1(String title);

    public JTextField  getJTextField(String title);

}
