/**
 * @author 周雨童
 */


import javax.swing.*;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class CreateSchedule extends JDialog {
    String schemeColor="black";
    String schemeDescription="无描述";
    String schemeTime="2022-06-07 18:40:00.000";
    String schemeType="其他日历";
    String schemeTitle="无主题";
    JButton chooseGreenColor =new JButton("绿色");
    JButton chooseBlackColor =new JButton("黑色");
    JButton chooseYellowColor =new JButton("黄色");
    JButton chooseRedColor =new JButton("红色");
    JButton finishCreate=new JButton("完成创建");
    CreateSchedule(){
        super();
        chooseBlackColor.setForeground(Color.BLACK);
        chooseGreenColor.setForeground(Color.GREEN);
        chooseRedColor.setForeground(Color.RED);
        chooseYellowColor.setForeground(Color.YELLOW);
        setLayout(new BorderLayout());
        JPanel northPanel=new JPanel();
        JLabel dateLabel=new JLabel("日期");
        JTextField dateText=new JTextField("2022-06-07");
        northPanel.add(dateLabel);
        northPanel.add(dateText);
        JLabel timeLabel=new JLabel("时间");
        JTextField timeText=new JTextField("18:40:00.000");
        northPanel.add(timeLabel);
        northPanel.add(timeText);
        JLabel titleLabel=new JLabel("请输入主题");
        JTextField titleText=new JTextField("例如:展示  ");
        northPanel.add(titleLabel);
        northPanel.add(titleText);
        add(northPanel,BorderLayout.NORTH);


        JPanel centerPanel=new JPanel();
        JLabel whichCalendar=new JLabel("请选择日历");
        JButton collegeCal=new JButton("课程日历");
        JButton otherCal=new JButton("其他日历");
        add(centerPanel);
        centerPanel.add(whichCalendar);
        centerPanel.add(collegeCal);
        centerPanel.add(otherCal);
        JLabel contentLabel=new JLabel("描述");
        JTextField contentText=new JTextField("例如:展示日历的功能");
        centerPanel.add(contentLabel);
        centerPanel.add(contentText);
        otherCal.setForeground(Color.GRAY);
        collegeCal.addActionListener(e -> {
            schemeType="课程日历";
            collegeCal.setForeground(Color.GRAY);
            otherCal.setForeground(Color.BLACK);
            repaint();
        });
        otherCal.addActionListener(e -> {
            schemeType="其他日历";
            collegeCal.setForeground(Color.BLACK);
            otherCal.setForeground(Color.GRAY);
            repaint();

        });



        JPanel southPanel=new JPanel();
        JLabel chooseColorLabel=new JLabel("请选择事项颜色");
        add(southPanel,BorderLayout.SOUTH);
        southPanel.add(chooseColorLabel);
        southPanel.add(chooseBlackColor);
        southPanel.add(chooseGreenColor);
        southPanel.add(chooseYellowColor);
        southPanel.add(chooseRedColor);
        southPanel.add(finishCreate);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);


        chooseBlackColor.setForeground(Color.GRAY);
        chooseBlackColor.addActionListener(e->{
            schemeColor="black";
            chooseBlackColor.setForeground(Color.BLACK);
            chooseGreenColor.setForeground(Color.GREEN);
            chooseYellowColor.setForeground(Color.YELLOW);
            chooseRedColor.setForeground(Color.RED);
            chooseBlackColor.setForeground(Color.GRAY);
            repaint();
        });
        chooseGreenColor.addActionListener(e->{
            schemeColor="green";
            chooseBlackColor.setForeground(Color.BLACK);
            chooseGreenColor.setForeground(Color.GREEN);
            chooseYellowColor.setForeground(Color.YELLOW);
            chooseRedColor.setForeground(Color.RED);
            chooseGreenColor.setForeground(Color.GRAY);
            repaint();
        });
        chooseYellowColor.addActionListener(e->{
            schemeColor="yellow";
            chooseBlackColor.setForeground(Color.BLACK);
            chooseGreenColor.setForeground(Color.GREEN);
            chooseYellowColor.setForeground(Color.YELLOW);
            chooseRedColor.setForeground(Color.RED);
            chooseYellowColor.setForeground(Color.GRAY);
            repaint();
        });
        chooseRedColor.addActionListener(e->{
            schemeColor="red";
            chooseBlackColor.setForeground(Color.BLACK);
            chooseGreenColor.setForeground(Color.GREEN);
            chooseYellowColor.setForeground(Color.YELLOW);
            chooseRedColor.setForeground(Color.RED);
            chooseRedColor.setForeground(Color.GRAY);
            repaint();
        });


        finishCreate.addActionListener(e->{
            schemeTitle=titleText.getText();
            schemeDescription=contentText.getText();
            String schemeDate=dateText.getText();
            String schemeClock=timeText.getText();
//            GlobalFrameZhou.db.insert("title_aaa", "type1", "2022-06-07 04:30:40.000", "blue", "description");
            try {
                SimpleDateFormat sdfDate=new SimpleDateFormat("yyyy-MM-dd");
                schemeDate=sdfDate.format(sdfDate.parse(schemeDate));
                try {
                    SimpleDateFormat sdfTime=new SimpleDateFormat("HH:mm:ss.SSS");
                    schemeClock=sdfTime.format(sdfTime.parse(schemeClock));
                    schemeTime=schemeDate+" "+schemeClock;
                    GlobalFrameZhou.db.insert(schemeTitle, schemeType,schemeTime, schemeColor, schemeDescription);
                    GlobalFrameZhou.wp.ini();
                    GlobalFrameZhou.cp.ini();
                    dispose();
                } catch (ParseException ex) {
                    IllegalFormatDialog iTime=new IllegalFormatDialog("时间输入格式有误");
                }
            } catch (ParseException ex) {
                IllegalFormatDialog iDate=new IllegalFormatDialog("日期输入格式有误");
            }
        });






        pack();
        setVisible(true);
    }

}
