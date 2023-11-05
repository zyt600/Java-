//RedPacketWithPicture是绘图的红包模拟

import javax.swing.*;
import java.awt.*;

//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.util.Scanner;
public class RedPacketWithPicture{
    static int peopleNum=20,trailTimes=10000;//默认设初始红包为200元，20个人来抢
    static double leftMoney=200,totalAverage=leftMoney/peopleNum;
    public static void main(String[] args) {
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


        //开始绘图
        MyPicture mp=new MyPicture(peopleNum,bestList);

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

class MyPicture extends JFrame {
    int peopleNum;
    int[] bestList;
    public MyPicture(int pn,int[] bl){
        peopleNum=pn;
        setTitle("代表性的选取了手气最佳次数以绘制柱状图");
        bestList=bl;
        setBounds(0,0,1200,800);
        setVisible(true);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
//        paint();
    }
    public void paint(Graphics g){
        g.setColor(Color.ORANGE);
        for(int ren=1;ren<=peopleNum;ren++){
            g.fillRect(0,(ren-1)*30,bestList[ren],25);
        }
        g.setColor(Color.BLACK);
        g.drawString("由于其他数据的对比不够明显,在此选用了手气最佳次数绘图",0,700);
    }
}