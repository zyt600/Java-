public class TestBank {
    public static void main(String[] args) {
        Bank nationalBank=new Bank("NationalBank");//开银行
        Bank greatBank=new Bank("GreatBank");
        nationalBank.creatAccount("Alice","123456",666);//开账户
        greatBank.creatAccount("Bob","123123",2333.33);
        nationalBank.accountArrayList.get(0).saveMoney(100,"888888");//存钱
        nationalBank.accountArrayList.get(0).saveMoney(100,"123456");//存钱
        greatBank.accountArrayList.get(0).charge(123,"123123");//取钱
        nationalBank.creatAccount("Carol","654321",9999);
        nationalBank.accountArrayList.get(1).moneyInquiry("654321");//查账户显示余额
        nationalBank.creatAccount("Dave","666555",10.2);
    }
}
