/**
 * @author 赖雨亲
 */
import java.util.List;

public class TestDB {
    public static void main(String[] args) {
        DBAccess db = new DBAccess();

        db.resetTable();
        db.insert("title_aaa", "type1", "2022-06-01 12:30:40.000", "blue", "description");
        db.insert("title_bbb", "type2", "2022-06-02 12:30:40.000", "red", "description");
        db.insert("title_ccc", "type3", "2022-07-03 23:00:00.000", "yellow", "description");
        db.insert("title_ddd", "type3", "2022-12-03 12:30:40.000", "yellow", "description");
        List<Scheme> result = db.selectAll();

        for (Scheme scheme : result) {
            System.out.println(scheme);
        }
        System.out.println("===========================");
        result.get(0).setDatetime("2022-06-07 12:30:40.000");
        db.update(result.get(0));
        result = db.selectAll();
        for (Scheme scheme : result) {
            System.out.println(scheme);
        }
        System.out.println("===========================");
        db.delete(result.get(0));
        result = db.selectAll();
        for (Scheme scheme : result) {
            System.out.println(scheme);
        }
        System.out.println("===========================");
        result = db.selectByType("type3");
        for (Scheme scheme : result) {
            System.out.println(scheme);
        }
        System.out.println("===========================");
        result = db.selectByColor("yellow");
        for (Scheme scheme : result) {
            System.out.println(scheme);
        }
        System.out.println("===========================");
        result = db.searchByTitleAndDescription("bb");
        for (Scheme scheme : result) {
            System.out.println(scheme);
        }
        System.out.println("===========================");
        result = db.selectByDatetimeRange("2022-07-01 00:00:00.000", "2022-07-31 00:00:00.000");
        for (Scheme scheme : result) {
            System.out.println(scheme);
        }
        System.out.println("===========================");
        result = db.selectByDatetimeRange("2022-06-02 00:00:00.000","2022-07-04 00:00:00.000");
        for (Scheme scheme : result) {
            System.out.println(scheme);
        }
        System.out.println("===========================");
    }
}