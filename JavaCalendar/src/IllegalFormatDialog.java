import javax.swing.*;

public class IllegalFormatDialog extends JDialog {
    IllegalFormatDialog(String warningContent){
        JLabel l=new JLabel(warningContent);
        add(l);
        setVisible(true);
//        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setBounds(0,0,150,150);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

    }
}
