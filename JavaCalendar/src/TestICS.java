/**
 * @author 赖雨亲
 */
import java.util.List;

public class TestICS {
    public static void main(String[] args) {
        DBAccess db = new DBAccess();

        db.resetTable();
        db.insert("title_aaa", "type1", "2022-06-01 12:30:40.000", "blue", "description");
        db.insert("title_bbb", "type2", "2022-06-02 12:30:40.000", "red", "description");
        db.insert("title_ccc", "type3", "2022-07-03 23:00:00.000", "yellow", "description");
        db.insert("title_ddd", "type3", "2022-12-03 12:30:40.000", "yellow", "description");
        List<Scheme> result = db.selectAll();
        for (Scheme scheme : result) {
            System.out.println(scheme.getYear() + "-" + scheme.getMonth() + "-" + scheme.getDay() + " " + scheme.getHour() + ":" + scheme.getMinute() + ":" + scheme.getSecond());
        }
        ICSUtil.exportSchemesToIcs(result, "calendar.ics");
    }
}
