import java.util.ArrayList;

public class Bank {
    String bankName;
    ArrayList<Account> accountArrayList=new ArrayList<Account>();
    void creatAccount(String userNamee,String passwordd,double leftMoneyy){
        Account temp=new Account(userNamee,passwordd,leftMoneyy);
        accountArrayList.add(temp);
    }
    Bank(String name){
        bankName=name;
        System.out.println("A bank called "+name+" is created.");
    }
}
