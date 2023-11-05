import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;

public class MonthPanel extends JPanel {
    private JButton []button ;
    private JButton []button_change; //左右翻页按钮
    private  LocalDate beginday;
    private  LocalDate today;
    private final String []xingqi = {"星期日","星期一","星期二","星期三","星期四","星期五","星期六","星期日"};
    private JPanel field_backlayer; //总体panel
    private JPanel filed_secondlayer_head; //显示点击日期所在Panel
    private JPanel field_secondlayer;//日期所在所在Panel
    private JPanel xingqitext;//显示星期一到五所在所在Panel
    private JPanel days;

    public  MonthPanel(){
        setLayout(new BorderLayout());

    }
    private void ini(int year,int month){
        removeAll();
        repaint();
        updateUI();
        panel_ini();
        beginday = Month_beginday(LocalDate.of(year, month, 1));
        //System.out.println("1111"+month);
        create_line_withtodaytext(year, month);

        add(BorderLayout.NORTH,filed_secondlayer_head);
        add(BorderLayout.CENTER,field_secondlayer);


        field_secondlayer.add(BorderLayout.NORTH,xingqitext);
        field_secondlayer.add(BorderLayout.CENTER,days);
        for(int i=1 ; i<=7 ;i++) {  /// 加入9个按钮 按窗口大小平均分配
            xingqitext.add(new JLabel(xingqi[i-1],JLabel.CENTER));
        }

        //setLayout(new GridLayout(5,7));
        for(int i=1 ; i<=42 ;i++) {  /// 加入9个按钮 按窗口大小平均分配
            button[i-1] = new JButton(""+beginday.plusDays(i-1).getDayOfMonth());
            if(beginday.plusDays(i-1).equals(today)){
                button[i-1].setForeground(Color.RED);
            }
            //button.setForeground(Color.GREEN);
            //button.setOpaque(false);
            //button.setBorderPainted(true);
            //JPanel temp = new JPanel(new FlowLayout());
            Font myfont;
            if (beginday.plusDays(i-1).getMonth().getValue()==month){
                myfont = new Font("Courier New",Font.BOLD,22);
            }
            else {
                myfont = new Font("Courier New",Font.PLAIN,22);
            }

            button[i-1].setFont(myfont);
            days.add(button[i-1]);
        }

//        setVisible(true);
    }

    void ini(){

        removeAll();
        repaint();
        updateUI();
        panel_ini();
        int year = today.getYear();
        int month = today.getMonthValue();
        beginday = Month_beginday(LocalDate.of(year, month, 1));
        //System.out.println("1111"+month);
        create_line_withtodaytext(year, month);

        add(BorderLayout.NORTH,filed_secondlayer_head);
        add(BorderLayout.CENTER,field_secondlayer);


        field_secondlayer.add(BorderLayout.NORTH,xingqitext);
        field_secondlayer.add(BorderLayout.CENTER,days);
        for(int i=1 ; i<=7 ;i++) {  /// 加入9个按钮 按窗口大小平均分配
            xingqitext.add(new JLabel(xingqi[i-1],JLabel.CENTER));
        }

        //setLayout(new GridLayout(5,7));
        for(int i=1 ; i<=42 ;i++) {  /// 加入9个按钮 按窗口大小平均分配
            button[i-1] = new JButton(""+beginday.plusDays(i-1).getDayOfMonth());
            if(beginday.plusDays(i-1).equals(today)){
                button[i-1].setForeground(Color.RED);
            }
            //button.setForeground(Color.GREEN);
            //button.setOpaque(false);
            //button.setBorderPainted(true);
            //JPanel temp = new JPanel(new FlowLayout());
            Font myfont;
            if (beginday.plusDays(i-1).getMonth().getValue()==month){
                myfont = new Font("Courier New",Font.BOLD,22);
            }
            else {
                myfont = new Font("Courier New",Font.PLAIN,22);
            }

            button[i-1].setFont(myfont);
            days.add(button[i-1]);
        }

//        setVisible(true);
    }

    private  void panel_ini(){
        button = new  JButton[42] ;
        button_change = new  JButton[2] ; //左右翻页按钮
        today = LocalDate.now();
        //field_backlayer = new JPanel(new BorderLayout());
        field_secondlayer = new JPanel(new BorderLayout());
        filed_secondlayer_head = new JPanel(new BorderLayout());
        days = new JPanel(new GridLayout(6,7));
        xingqitext= new JPanel(new GridLayout(1,7));
    }
    private void create_line_withtodaytext(int year , int month){
        LocalDate firstday = LocalDate.of(year, month, 1);
        int month_delta = (int)(today.until(firstday, ChronoUnit.MONTHS) );
        //int month_delta = Period.between(today, firstday).getMonths();
        JLabel today_info_text;
        if (month_delta==0){
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
            }
        } else if (month_delta<0) {
            today_info_text = new JLabel(firstday.getYear()+""+'年'+
                    firstday.getMonth().getValue()+""+"月,"+(-month_delta)+""+"个月前",JLabel.CENTER);
        }
        else {
            today_info_text = new JLabel(firstday.getYear()+""+'年'+
                    firstday.getMonth().getValue()+""+"月,"+(month_delta)+""+"个月后",JLabel.CENTER);
        }

        filed_secondlayer_head.add(BorderLayout.CENTER,today_info_text);
        button_change[0]=new JButton("前一月");
        button_change[1]=new JButton("后一月");
        filed_secondlayer_head.add(BorderLayout.WEST,button_change[0]);
        filed_secondlayer_head.add(BorderLayout.EAST,button_change[1]);
        button_change[0].addActionListener(e -> {ini(firstday.minusMonths(1).getYear(),firstday.minusMonths(1).getMonthValue());});
        button_change[1].addActionListener(e -> {ini(firstday.plusMonths(1).getYear(),firstday.plusMonths(1).getMonthValue());});
    }
    private LocalDate Month_beginday(LocalDate begin){
        int day_before = begin.getDayOfWeek().getValue();
        if(day_before == 7){
            return begin;
        }
        else{
            return begin.minusDays(day_before) ;
        }

    }

}
