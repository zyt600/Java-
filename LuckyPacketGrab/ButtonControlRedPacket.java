// ButtonControlRedPacket是用按钮控制、设置参数的红包模拟器
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;
public class ButtonControlRedPacket{
    static int peopleNum=20,trailTimes=10000;//默认设初始红包为200元，20个人来抢
    static double leftMoney=200,totalAverage=leftMoney/peopleNum;
    public static void main(String[] args) {
        MyFrame f=new MyFrame("模拟红包程序");
        Label lb=new Label("请在输入抢红包人数");
        JTextField tf=new JTextField(10);
        MyFrame f2=new MyFrame("输入参数");
        Button b=new Button("点击使用默认参数运行一组模拟");
        Button b2=new Button("点击自行设置参数");
        Button b3=new Button("确认人数");
        Button b4=new Button();
        ActionListener al=new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String sss=e.getActionCommand();
                switch (sss){
                    case "使用设置好的参数运行一组模拟":
                        stimulate();
                        break;
                    case "点击使用默认参数运行一组模拟":
                        stimulate();
                        break;
                    case "点击自行设置参数":
                        f.setVisible(false);
                        f2.setVisible(true);
                        break;
                    case "确认人数":
                        String in=tf.getText();
                        lb.setText("请输入红包金额");
                        tf.setText("");
                        b3.setLabel("确认金额");
                        peopleNum=Integer.parseInt(in);
                        break;
                    case "确认金额":
                        String in2=tf.getText();
                        b3.setLabel("确认次数");
                        tf.setText("");
                        lb.setText("请输入模拟次数");
                        leftMoney=Double.parseDouble(in2);
                        break;
                    case "确认次数":
                        String in3=tf.getText();
                        tf.setText("");
                        trailTimes=Integer.parseInt(in3);
                        b3.setLabel("请输入红包金额");
                        f2.setVisible(false);
                        b4.setLabel("使用设置好的参数运行一组模拟");
                        f.add(b4);
                        f.setVisible(true);
                        break;
                    case"绘图":
                        stimulate();
                        break;
                }
            }
        };
        f.add(b);
        f.add(b2);
        f2.add(lb);
        f2.add(tf);
        f2.add(b3);
        b.addActionListener(al);
        b2.addActionListener(al);
        b3.addActionListener(al);
        b4.addActionListener(al);
        f.setVisible(true);
    }





    public static void stimulate(){

        System.out.printf("设初始红包为%.2f元，%d个人来抢，实验进行%d次\n",leftMoney,peopleNum,trailTimes);
        double[][] moneyList=new double[trailTimes+5][peopleNum+5];
        int[] bestList=new int[trailTimes+5];
        for(int i=1;i<=trailTimes;i++)
            oneTrail(peopleNum,leftMoney,moneyList[i],bestList,i);

        //开始算均值
        double[] eachAverage=new double[peopleNum+5];
        for(int ren=1;ren<=peopleNum;ren++){
            for(int trail=1;trail<=trailTimes;trail++){
                eachAverage[ren]+=moneyList[trail][ren];
            }
            eachAverage[ren]/=trailTimes;
            System.out.printf("第%d个人的平均值为%.2f\n",ren,eachAverage[ren]);
        }
        System.out.printf("先抢后抢的均值相同\n\n");
        //结束算均值



        //开始算方差
        double[] fangcha=new double[peopleNum+5];
        for(int ren=1;ren<=peopleNum;ren++){
            for(int trail=1;trail<=trailTimes;trail++){
                fangcha[ren]+=(moneyList[trail][ren]-eachAverage[ren])*(moneyList[trail][ren]-eachAverage[ren]);
            }
            fangcha[ren]/=trailTimes;
        }
        for(int ren=1;ren<=peopleNum;ren++){
            System.out.printf("第%d个人的方差为%.2f\n",ren,eachAverage[ren]);
        }
        System.out.printf("先抢后抢的方差相同\n\n");
        //结束算方差


        //开始统计超级大小红包
        System.out.printf("这里的超大定义为大于均值的3倍,超小定义为小于等于均值的十分之一\n");
        int[] extreaMax=new int[peopleNum+5],extreaMin=new int[peopleNum+5];
        for(int ren=1;ren<=peopleNum;ren++){
            for(int trail=1;trail<=trailTimes;trail++){
                if(moneyList[trail][ren]<=0.1*totalAverage)
                    extreaMin[ren]++;
                else if(moneyList[trail][ren]>=totalAverage*3)
                    extreaMax[ren]++;
            }
            System.out.printf("第%d个人的极大极小红包数分别为%d,%d\n", ren,extreaMax[ren],extreaMin[ren]);
        }
        System.out.printf("极大值更容易出现在靠后抢红包的人身上;极小值相对平均,但靠后的人更容易出现\n\n");
        //结束统计超级大小红包

        //开始手气最佳
        for(int ren=1;ren<=peopleNum;ren++){
            System.out.printf("第%d个人获得过%d次手气最佳\n", ren,bestList[ren]);
        }
        System.out.printf("最靠后的人们最容易手气最佳;最靠前的人们比靠中间的人容易手气最佳,这个统计结果与红包个数无关\n\n");
        //结束手气最佳
    }

    public static void oneTrail(int peopleNum,double leftlMoney,double[] moneyList,int[] bestList,int whichTrail){
        for(int peopleSequence=1;peopleSequence<=peopleNum-1;peopleSequence++){
            double average=leftlMoney/(peopleNum+1-peopleSequence);
            double moneyAcquire=0;
            while(moneyAcquire==0){
                moneyAcquire=Math.random()*average*2;
            }
            int temp=(int)(moneyAcquire*100);
            moneyAcquire=temp/100.0;
            leftlMoney-=moneyAcquire;
            moneyList[peopleSequence]=moneyAcquire;
        }
        moneyList[peopleNum]=leftlMoney;
        double maxMoney=0;
        int pn=0;
        for(int i=1;i<=peopleNum;i++){
            if(moneyList[i]>maxMoney){
                maxMoney=moneyList[i];
                pn=i;
            }
        }
        bestList[pn]++;
    }
}


class MyFrame extends JFrame{
    public MyFrame(){
        super();
        defaultInitialize();
    }
    public MyFrame(String s){
        super(s);
        defaultInitialize();
    }
    public void defaultInitialize(){
        setLayout(new FlowLayout());
        setBounds(300,300,500,100);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }

}