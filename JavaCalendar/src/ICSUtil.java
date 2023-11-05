/**
 * @author 赖雨亲
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Calendar;
import java.util.List;

/**
 * A class that contains utility methods for ICS files.
 */
public class ICSUtil {
    /**
     * The default constructor.
     */
    public ICSUtil() {
    }
    
    /**
     * Export the Schemes to ics format file.
     * @param schemes The list of Schemes.
     * @param fileName The name of the file.
     */
    public static void exportSchemesToIcs(List<Scheme> schemes, String fileName) {
        if (schemes != null && fileName != null) {
            StringBuilder sb = new StringBuilder();
            sb.append("BEGIN:VCALENDAR\n");
            sb.append("VERSION:2.0\n");
            sb.append("CALSCALE:GREGORIAN\n");
            sb.append("METHOD:PUBLISH\n");
            sb.append("X-WR-CALNAME:Schemes\n");
            sb.append("X-WR-TIMEZONE:Asia/Shanghai\n");
            sb.append("X-WR-CALDESC:Schemes\n");
            for (Scheme scheme : schemes) {
                /**
                 * a tz timestamp such as 20010911T124640Z with the format
                 * <year (4 digits)><month (2)><day (2)>T<hour (2)><minute (2)><second (2)>
                 * Z for a total fixed length of 16 characters.
                 * Z indicates the use of UTC (referring to its "Zulu" time zone).
                 */
                Calendar calendar = scheme.getCalendarInstance();
                String tzTimestamp = String.format("%04d%02d%02dT%02d%02d%02dZ",
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH) + 1,
                        calendar.get(Calendar.DAY_OF_MONTH),
                        calendar.get(Calendar.HOUR_OF_DAY),
                        calendar.get(Calendar.MINUTE),
                        calendar.get(Calendar.SECOND));
                sb.append("BEGIN:VEVENT\n");
                sb.append("CATEGORIES:" + scheme.getType() + "\n");
                sb.append("UID:" + scheme.getId() + "\n");
                sb.append("DTSTAMP:" + tzTimestamp + "\n");
                sb.append("DTSTART:" + tzTimestamp + "\n");
                sb.append("SUMMARY:" + scheme.getTitle() + "\n");
                sb.append("DESCRIPTION:" + scheme.getDescription() + "\n");
                sb.append("END:VEVENT\n");
            }
            sb.append("END:VCALENDAR\n");
            /**
             * Write the content to the file.
             */
            FileUtil.writeFile(fileName, sb.toString());
        }
    }
}

/**
 * A class that contains utility methods for files.
 */
class FileUtil {
    /**
     * Write the content to the file.
     * @param fileName The name of the file.
     * @param content The content to be written.
     */
    public static void writeFile(String fileName, String content) {
        if (fileName != null && content != null) {
            File file = new File(fileName);
            try {
                file.createNewFile();
                FileWriter writer = new FileWriter(file);
                writer.write(content);
                writer.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    /**
     * Read the content from the file.
     * @param fileName The name of the file.
     * @return The content of the file.
     */
    public static String readFile(String fileName) {
        String content = null;
        if (fileName != null) {
            File file = new File(fileName);
            try {
                FileReader reader = new FileReader(file);
                BufferedReader bufferedReader = new BufferedReader(reader);
                StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                }
                content = sb.toString();
                reader.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return content;
    }
}