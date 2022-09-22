public class Account {
    String userName,password;
    private double leftMoney=0;
    Account(String userNamee,String passwordd,double leftMoneyy){
        userName=userNamee;
        password=passwordd;
        leftMoney=leftMoneyy;
        System.out.println("A new account is created with username "+userNamee+" and password "+passwordd+".");
    }
    void saveMoney(double money,String passwordd){
        if(!passwordd.equals(password)){
            System.out.println("Password is wrong.");
            return;
        }
        leftMoney+=money;
        System.out.println("Now there is "+leftMoney+"$ left in the account of "+userName+".");
    }
    void charge(double money,String passwordd){
        if(!passwordd.equals(password)){
            System.out.println("Password is wrong.");
            return;
        }
        leftMoney-=money;
        System.out.println("Now there is "+leftMoney+"$ left in the account of "+userName+".");

    }
    void moneyInquiry(String passwordd){
        if(!passwordd.equals(password)){
            System.out.println("Password is wrong.");
            return;
        }
        System.out.println("Now there is "+leftMoney+"$ left in the account of "+userName+".");

    }
}
