/**
 * @author 赖雨亲
 */
import java.util.Date;
import java.util.Calendar;

/**
 * 事件类
 */
public class Scheme implements Comparable<Scheme>{
    // 以下字段保存在数据库中
    private int id; // 事件在数据库中的id，主键
    private String title; // 事件标题
    private String type; // 事件类型
    private String datetime; // 日期时间 YYYY-MM-DD HH:MM:SS.SSS
    private String color; // 事件颜色（用于显示）
    private String description; // 事件描述
    
    // 以下字段不保存在数据库中，也不作为构造函数的变量
    private int year; // 年份
    private int month; // 月份 1-12
    private int day; // 日期 1-31
    private int hour; // 小时
    private int minute; // 分钟
    private int second; // 秒

    private boolean isLeapYear; // 是否是闰年

    private Date date; // 日期（使用java.util.Date）
    private Calendar calendarInstance; // 日历实例（使用java.util.Calendar）

    private int dayOfYear; // 当前日期是年份的第几天
    private int weekOfYear; // 当前日期是年份的第几周
    private int dayOfWeek; // 星期几，0-6 对应周日到周一
    private boolean isWeekend; // 是否是周末

    /**
     * 构造函数
     * @param id 事件在数据库中的id，主键
     * @param title 事件标题
     * @param type 事件类型
     * @param datetime 日期时间，格式：YYYY-MM-DD HH:MM:SS.SSS
     * @param color 事件颜色（用于显示）
     * @param description 事件描述
     */
    public Scheme(int id, String title, String type, String datetime, String color, String description) {
        this.id = id;
        this.title = title;
        this.type = type;
        this.datetime = datetime;
        this.color = color;
        this.description = description;
        
        // 初始化年月日时分秒
        year = Integer.parseInt(datetime.substring(0, 4));
        month = Integer.parseInt(datetime.substring(5, 7));
        day = Integer.parseInt(datetime.substring(8, 10));
        hour = Integer.parseInt(datetime.substring(11, 13));
        minute = Integer.parseInt(datetime.substring(14, 16));
        second = Integer.parseInt(datetime.substring(17, 19));

        // 初始化是否是闰年
        isLeapYear = (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);

        // 初始化日期
        calendarInstance = Calendar.getInstance();
        calendarInstance.set(year, month - 1, day, hour, minute, second);
        date = calendarInstance.getTime();
        
        // 初始化日期是年份的第几天
        dayOfYear = calendarInstance.get(Calendar.DAY_OF_YEAR);
        // 初始化日期是年份的第几周
        weekOfYear = calendarInstance.get(Calendar.WEEK_OF_YEAR);
        // 初始化星期几，0-6 对应周日到周一
        dayOfWeek = calendarInstance.get(Calendar.DAY_OF_WEEK) - 1;
        // 初始化是否是周末
        isWeekend = (dayOfWeek == 0 || dayOfWeek == 6);
    }
    
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 若更新了datetime，则需要同时更新其它字段
     * @param datetime 更新后的datetime，格式为yyyy-MM-dd HH:mm:ss.SSS
     */
    public void updateDatetime(String datetime) {
        year = Integer.parseInt(datetime.substring(0, 4));
        month = Integer.parseInt(datetime.substring(5, 7));
        day = Integer.parseInt(datetime.substring(8, 10));
        hour = Integer.parseInt(datetime.substring(11, 13));
        minute = Integer.parseInt(datetime.substring(14, 16));
        second = Integer.parseInt(datetime.substring(17, 19));

        isLeapYear = (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
        calendarInstance.set(year, month - 1, day, hour, minute, second);
        date = calendarInstance.getTime();
        dayOfYear = calendarInstance.get(Calendar.DAY_OF_YEAR);
        weekOfYear = calendarInstance.get(Calendar.WEEK_OF_YEAR);
        dayOfWeek = calendarInstance.get(Calendar.DAY_OF_WEEK) - 1;
        isWeekend = (dayOfWeek == 0 || dayOfWeek == 6);
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
        updateDatetime(datetime);
    }

    /**
     * 若更新了year, month, day, hour, minute, second，则需要同时更新其它字段
     */
    public void updateDatetime() {
        datetime = year + "-" + month + "-" + day + " " + hour + ":" + minute + ":" + second+".000";
        isLeapYear = (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
        calendarInstance.set(year, month - 1, day, hour, minute, second);
        date = calendarInstance.getTime();
        dayOfYear = calendarInstance.get(Calendar.DAY_OF_YEAR);
        weekOfYear = calendarInstance.get(Calendar.WEEK_OF_YEAR);
        dayOfWeek = calendarInstance.get(Calendar.DAY_OF_WEEK) - 1;
        isWeekend = (dayOfWeek == 0 || dayOfWeek == 6);
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
        updateDatetime();
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
        updateDatetime();
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
        updateDatetime();
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
        updateDatetime();
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
        updateDatetime();
    }

    public int getSecond() {
        return second;
    }

    public void setSecond(int second) {
        this.second = second;
        updateDatetime();
    }

    // 以下字段只能Get不能Set
    public int getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public Calendar getCalendarInstance() {
        return calendarInstance;
    }

    public int getDayOfYear() {
        return dayOfYear;
    }

    public int getWeekOfYear() {
        return weekOfYear;
    }

    public int getDayOfWeek() {
        return dayOfWeek;
    }

    public boolean isWeekend() {
        return isWeekend;
    }

    public boolean isLeapYear() {
        return isLeapYear;
    }

    @Override
    public String toString() {
        return "Scheme{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", type='" + type + '\'' +
                ", datetime='" + datetime + '\'' +
                ", color='" + color + '\'' +
                ", description='" + description + '\'' +
                ", dayOfYear=" + dayOfYear +
                ", weekOfYear=" + weekOfYear +
                ", dayOfWeek=" + dayOfWeek +
                ", isWeekend=" + isWeekend +
                ", isLeapYear=" + isLeapYear +
                '}';
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        result = prime * result + ((title == null) ? 0 : title.hashCode());
        result = prime * result + ((type == null) ? 0 : type.hashCode());
        result = prime * result + ((datetime == null) ? 0 : datetime.hashCode());
        result = prime * result + ((color == null) ? 0 : color.hashCode());
        result = prime * result + ((description == null) ? 0 : description.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Scheme other = (Scheme) obj;
        if (id != other.id)
            return false;
        if (title == null) {
            if (other.title != null)
                return false;
        } else if (!title.equals(other.title))
            return false;
        if (type == null) {
            if (other.type != null)
                return false;
        } else if (!type.equals(other.type))
            return false;
        if (datetime == null) {
            if (other.datetime != null)
                return false;
        } else if (!datetime.equals(other.datetime))
            return false;
        if (color == null) {
            if (other.color != null)
                return false;
        } else if (!color.equals(other.color))
            return false;
        if (description == null) {
            if (other.description != null)
                return false;
        } else if (!description.equals(other.description))
            return false;
        return true;
    }

    @Override
    /**
     * 按照year,month,day,hour,minute,second,id,title,type,color,description排序
     * @param o 另一个Scheme对象
     */
    public int compareTo(Scheme o) {
        if (this.year != o.year) {
            return this.year - o.year;
        } else if (this.month != o.month) {
            return this.month - o.month;
        } else if (this.day != o.day) {
            return this.day - o.day;
        } else if (this.hour != o.hour) {
            return this.hour - o.hour;
        } else if (this.minute != o.minute) {
            return this.minute - o.minute;
        } else if (this.second != o.second) {
            return this.second - o.second;
        } else if (this.id != o.id) {
            return this.id - o.id;
        } else if (this.title != null && o.title != null) {
            return this.title.compareTo(o.title);
        } else if (this.type != null && o.type != null) {
            return this.type.compareTo(o.type);
        } else if (this.color != null && o.color != null) {
            return this.color.compareTo(o.color);
        } else if (this.description != null && o.description != null) {
            return this.description.compareTo(o.description);
        } else {
            return 0;
        }
    }
}
