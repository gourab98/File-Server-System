import javax.swing.*;
public class ServerFiles extends Server{
    public static FrameElement showFrame =new FrameElement();
    public static void showAvailableFiles(){
        JFrame jFrame1 = showFrame.getJFrame("Server Files");
        jFrame1.setSize(600, 600);
        JPanel jPanel = showFrame.getJPanel();
        JScrollPane jScrollPane = new JScrollPane(jPanel);
        jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        JLabel jlTitle = showFrame.getJLabel("File Lists");
        jFrame1.add(jlTitle);
        jFrame1.add(jScrollPane);
        jFrame1.setVisible(true);

        allFiles.clear();

        ReadFiles.readAllFile();

        for (MyFile file : allFiles) {
            JPanel jpFileRow = new JPanel();
            jpFileRow.setLayout(new BoxLayout(jpFileRow, BoxLayout.X_AXIS));
            JLabel jlFileName = showFrame.getJLabel(file.name);

            jpFileRow.setName((String.valueOf(file.id)));
            if(showButtonPress==0) {
                jpFileRow.addMouseListener(getMyMouseListener());
            }
            jpFileRow.add(jlFileName);
            jPanel.add(jpFileRow);
            jFrame1.validate();
        }
    }
}
