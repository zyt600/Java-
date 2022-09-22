import java.util.List;

public class TestSearch {
    public static void main(String[] args) {
//        System.out.println(GlobalFrameZhou.db);
//        List<Scheme> result = db.selectAll();
//
//        for (Scheme scheme : result) {
//            System.out.println(scheme);
//        }
        GlobalFrameZhou zzz=new GlobalFrameZhou();
        List<Scheme> result = GlobalFrameZhou.db.selectAll();
        for (Scheme scheme : result) {
            System.out.println(scheme);
        }
    }
}
