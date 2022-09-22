import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

public class GlobalFrameZhou extends JFrame {
    private JPanel panel1;
    private JPanel LeftPanel;
    private JPanel UpPanel;
    private JButton monthButton;
    private JButton weekButton;
    private JButton createToDoButton;
    private JPanel DownPanel;

    private JLabel WeatherLabel;
    private JButton 其他日历Button;
    private JButton 课程日历Button;
    private JPanel MiddlePanel;
    private JFormattedTextField SearchFormattedTextField;
    private JButton NewsButton;
    private JButton SearchButton;
    private JPanel SearchPanel;
    private JButton 初始化日历Button;
    private JButton 导出ics文件Button;

    static DBAccess db ;

    CardLayout CardLayoutGlobal =new CardLayout();
    String tmpString="";
    public GlobalFrameZhou() {
        NewsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Desktop desktop = Desktop.getDesktop();
                URI uri = null;
                try {
//                    System.out.println("uri:" + tmpString);
                    uri = new URI(tmpString);
                    desktop.browse(uri);
                } catch (URISyntaxException | IOException ex) {
                    ex.printStackTrace();
                }
                Crawler crawler = new Crawler();
                String[] newsStrings = new String[0];
                try {
                    newsStrings = crawler.getNews();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                NewsButton.setText(newsStrings[0]);
                tmpString="";
                tmpString += newsStrings[4];
            }
        });
        db = new DBAccess();
        ini();
//        init_db();
    }

    private  void init_db(){
        db.resetTable();
        db.insert("title_aaa", "type1", "2022-06-02 04:30:40.000", "blue", "description");
        db.insert("title_bbb", "type2", "2022-06-02 08:30:40.000", "red", "description");
        db.insert("title_ccc", "type3", "2022-06-02 12:30:40.000", "yellow", "description");
        db.insert("title_ddd", "type3", "2022-06-02 23:30:40.000", "yellow", "description");
    }
    static WeekPanel wp=new WeekPanel();
    static MonthPanel mp=new MonthPanel();

    static ClassPanel cp=new ClassPanel();
    void ini(){

        setLayout(new BorderLayout());
        getContentPane().add(panel1,BorderLayout.CENTER);
        setBounds(0,0,800,500);
        MiddlePanel.setLayout(CardLayoutGlobal);
        mp.ini();//需要显示的年，月
        //wp.Set_db(db);
        wp.ini();  // 添加日程后调用此函数刷新周识图

        cp.ini();
        ResultPanel rp = new ResultPanel();

        MiddlePanel.add("mmm", mp);
        MiddlePanel.add("www", wp);
        MiddlePanel.add("rrr", rp);
        MiddlePanel.add("ccc", cp);
        monthButton.addActionListener(e -> {
            CardLayoutGlobal.show(MiddlePanel,"mmm");
        });
        weekButton.addActionListener(e -> {
            wp.ini();
            CardLayoutGlobal.show(MiddlePanel,"www");
        });
        课程日历Button.addActionListener(e -> {
            cp.ini();
            CardLayoutGlobal.show(MiddlePanel,"ccc");
        });
        其他日历Button.addActionListener(e -> {
            cp.ini();
            CardLayoutGlobal.show(MiddlePanel,"www");
        });
        createToDoButton.addActionListener(e->{
            CreateSchedule createSchedule=new CreateSchedule();
        });
        SearchButton.addActionListener(e->{
            rp.build(SearchFormattedTextField.getText());
            CardLayoutGlobal.show(MiddlePanel, "rrr");
        });
        初始化日历Button.addActionListener(e -> {
            init_db();
            wp.ini();
        });
        try {
            Crawler crawler = new Crawler();
            String[] weatherStrings = crawler.getWeather();
            String[] newsStrings = crawler.getNews();
            WeatherLabel.setText(weatherStrings[0] + " " + weatherStrings[1]);
            NewsButton.setText(newsStrings[0]);
            tmpString += newsStrings[4];
        } catch (Exception e) {
            e.printStackTrace();
        }

        导出ics文件Button.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            String fileName = "";
            int status = chooser.showSaveDialog(this);
            if (status == JFileChooser.APPROVE_OPTION) {
                fileName = chooser.getSelectedFile().getAbsolutePath();
            }
            // Check if the extension is .ics
            if (!fileName.endsWith(".ics")) {
                fileName += ".ics";
            }
            // If the file name is not null
            if (fileName != null) {
                List<Scheme> allSchemes = db.selectAll();
                ICSUtil.exportSchemesToIcs(allSchemes, fileName);
            }
        });
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        GlobalFrameZhou gz=new GlobalFrameZhou();
    }


}
