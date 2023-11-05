import javax.swing.*;
import java.awt.*;
import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Field;
import javax.swing.BorderFactory;
import javax.swing.border.Border;
import java.time.LocalDate;
import java.time.Period;
import java.awt.FontMetrics;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.ArrayList;
import java.time.format.DateTimeFormatter;
public class WeekPanel extends JPanel {
    //JButton []button ;
    private  LocalDate beginday;//显示的一周中的周日
    private  LocalDate showday;//today减去对应的周数
    private  LocalDate today;
    private final String []xingqi = {"星期日","星期一","星期二","星期三","星期四","星期五","星期六","星期日"};
    private JPanel headline;//显示上一页，下一页，所在周
    private JPanel bodyline;//显示主体
    private JPanel [] days; //显示星期以及日期，共7个
    private JButton []button_change;

    private  DBAccess db;
    public  WeekPanel(){
        setLayout(new BorderLayout());

    }

    static int Weed_delta = 0 ; //储存Weed_delta用于db更新后的显示
    public  void Set_db(){
        db = GlobalFrameZhou.db;
    }
    private void ini(int weekdelta ){
        Weed_delta = weekdelta;
        ini_core();
    }
    void ini(){
        Weed_delta = ClassPanel.Weed_delta;
        ini_core();
    }
    void ini_core(){
        Set_db();
        removeAll();
        repaint();
        updateUI();

        panel_ini(Weed_delta);


        create_line_withtodaytext();
        add(headline,BorderLayout.NORTH);
        add(bodyline,BorderLayout.CENTER);

        days[0].add(new JLabel("星期：",JLabel.CENTER));
        days[0].add(new JLabel("日期：",JLabel.CENTER));
        bodyline.add(days[0]);
        for(int i=1;i<days.length;i++){
            days[i].add(new JLabel(xingqi[i-1],JLabel.CENTER));
            days[i].add(new JLabel(""+beginday.plusDays(i-1).getDayOfMonth(),JLabel.CENTER));
            bodyline.add(days[i]);
        }
        String []text = {"上午","下午","晚上"};
        Border blackline = BorderFactory.createLineBorder(Color.black);
        Border redline = BorderFactory.createLineBorder(Color.red);
        int []time_period = {0,12,18,24};



        for(int i=0;i<3;i++){
            JLabel text_index = new JLabel(text[i],JLabel.CENTER);
            text_index.setBorder(blackline);
            bodyline.add(text_index);
            for(int j=0;j<7;j++){
                String string_day = beginday.plusDays(j).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                JPanel text_detail ;
                List<Scheme> result_raw = db.selectByDatetimeRange(string_day+" "+time_period[i],string_day+" "+time_period[i+1]);
                List<Scheme> result =new ArrayList<>();
                for(Scheme scheme : result_raw){
                    if (!scheme.getType().equals("其他日历")){continue;}
                    result.add(scheme);
                }
                if(result.size()==0){
                    text_detail = new JPanel(new GridLayout(1,1));

                    JLabel t2 = new JLabel("");

                    t2.setBorder(blackline);
                    text_detail.add(t2);
                }
                else{

                    text_detail = new JPanel(new GridLayout(0,1));
                    for (Scheme scheme : result){

                        //JLabel t2 = new JLabel(scheme.getDescription());
                        JLabel t2 = new JLabel();
                        t2.setSize(200, 0);
                        try {
                            jlabelsettext(t2,scheme.getDescription());
                        }
                            catch(InterruptedException e) {
                            System.out.println("Something wrong with jlabelsettext");
                            t2.setText(scheme.getDescription());
                        }

                        t2.setBorder(blackline);
                        t2.setForeground(get_color( scheme.getColor()));
                        text_detail.add(t2);
                    }

                }

                bodyline.add(text_detail);
            }
        }
        //beginday = Month_beginday(LocalDate.of(year, month, 1));

//        setVisible(true);
    }
    private void jlabelsettext(JLabel jlabel, String longstring)
            throws InterruptedException {
        StringBuilder builder = new StringBuilder("<html>");
        char[] chars = longstring.toCharArray();
        FontMetrics fontmetrics = jlabel.getFontMetrics(jlabel.getFont());
        int start = 0;
        int len = 0;
        while (start + len < longstring.length()) {
            while (true) {
                len++;
                if (start + len > longstring.length())break;
                if (fontmetrics.charsWidth(chars, start, len)
                        > jlabel.getWidth()) {
                    break;
                }
            }
            builder.append(chars, start, len-1).append("<br/>");
            start = start + len - 1;
            len = 0;
        }
        builder.append(chars, start, longstring.length()-start);
        builder.append("</html>");
        jlabel.setText(builder.toString());
    }
    private  void panel_ini(int week_delta){ //用于初始化部件，在ini中进行具体赋值

        today = LocalDate.now();
        showday = today.plusWeeks(week_delta);
        beginday = showday.minusDays(seven_to_zero(showday.getDayOfWeek().getValue()));
        days = new JPanel[8] ;
        for(int i=0;i<days.length;i++){
            days[i]=new JPanel(new GridLayout(2,1));
        }
        button_change = new  JButton[2] ; //左右翻页按钮
        headline = new JPanel(new BorderLayout());
        bodyline = new JPanel(new GridLayout(4,8));

    }

    private  Color get_color(String c){
        Color color;
        try {
            Field field = Color.class.getField(c);
            color = (Color)field.get(null);
        } catch (Exception e) {
            color = null; // Not define
        }
        return  color;
    }
    private void create_line_withtodaytext(){
        LocalDate firstday = showday;
        Border blackline = BorderFactory.createLineBorder(Color.black);
        //int week_delta = Period.between(today, showday).getDays()/7;
        int week_delta = (int)(today.until(showday, ChronoUnit.DAYS) /7);
        JLabel today_info_text;
        if (week_delta==0){
            /*
            if(today.getMonthValue()>firstday.getMonthValue()){
                today_info_text = new JLabel(firstday.getYear()+""+'年'+
                        firstday.getMonth().getValue()+""+"月,"+(1)+""+"个月前",JLabel.CENTER);
            }
            else if(today.getMonthValue()<firstday.getMonthValue()){
                today_info_text = new JLabel(firstday.getYear()+""+'年'+
                        firstday.getMonth().getValue()+""+"月,"+(1)+""+"个月后",JLabel.CENTER);
            }
            else {
                today_info_text = new JLabel(today.getYear()+""+'年'+
                        today.getMonth().getValue()+""+'月'+today.getDayOfMonth()+""+"日，"+xingqi[today.getDayOfWeek().getValue()],JLabel.CENTER);
            }*/
            today_info_text = new JLabel(today.getYear()+""+'年'+
                    today.getMonth().getValue()+""+'月'+today.getDayOfMonth()+""+"日，"+xingqi[today.getDayOfWeek().getValue()],JLabel.CENTER);
        } else if (week_delta<0) {
            today_info_text = new JLabel(firstday.getYear()+""+'年'+
                    firstday.getMonth().getValue()+""+"月,"+(-week_delta)+""+"周前",JLabel.CENTER);
        }
        else {
            today_info_text = new JLabel(firstday.getYear()+""+'年'+
                    firstday.getMonth().getValue()+""+"月,"+(week_delta)+""+"周后",JLabel.CENTER);
        }
        today_info_text.setBorder( blackline );
        headline.add(BorderLayout.CENTER,today_info_text);
        button_change[0]=new JButton("前一周");
        button_change[1]=new JButton("后一周");
        headline.add(BorderLayout.WEST,button_change[0]);
        headline.add(BorderLayout.EAST,button_change[1]);
        button_change[0].addActionListener(e -> {ini(week_delta-1);});
        button_change[1].addActionListener(e -> {ini(week_delta+1);});
    }
    int seven_to_zero(int a){
        if (a==7){
            return 0;
        }
        else {
            return a ;
        }
    }
}

