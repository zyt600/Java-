/**
 * @author 赖雨亲
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.List;

/**
 * A class that provides methods for accessing the SQLite database.
 * 
 * 主要方法：
 * 1. insert(String title, String type, String datetime, String color, String description) 往数据库中加入一个事件
 * 2. selectAll() 返回数据库中所有事件
 * 3. update(Scheme scheme) 更新数据库中的一个事件（按scheme.id更新）
 * 4. delete(Scheme scheme) 删除数据库中的一个事件（按scheme.id删除）
 * 5. resetTable() 清空数据库中的所有事件（重置id）
 * 6. deleteAll() 清空数据库中的所有事件（不重置id）
 * 7. selectByID(int id) 根据id查询数据库中的一个事件
 * 8. selectByType(String type) 根据type查询数据库中的事件（ 必须全等）
 * 9. selectByColor(String color) 根据color查询数据库中的事件（ 必须全等）
 * 10. searchByTitleAndDescription(String keyword) 根据title和description查询数据库中的事件（必须包含）
 * 11. selectByDatetimeRange(String startDatetime, String endDatetime) 根据时间范围查询数据库中的事件（使用BETWEEN）
 * 
 * See also: src/Scheme.java
 */
public class DBAccess {
    private static final String DATABASE_NAME    = "calendar.db";
    // private static final int    DATABASE_VERSION = 1;
    private static final String TABLE_NAME       = "Calendar_Item";

    // Column Names
    public static final String KEY_ID           = "_id";
    public static final String KEY_TITLE     	= "Title";
    public static final String KEY_TYPE         = "Type";
    public static final String KEY_DATETIME     = "DateTime";
    public static final String KEY_COLOR        = "Color";
    public static final String KEY_DESCRIPTION  = "Description";

    // Column indexes
    public static final int COLUMN_ID         	= 1;
    public static final int COLUMN_TITLE  	  	= 2;
    public static final int COLUMN_TYPE       	= 3;
    public static final int COLUMN_DATETIME   	= 4; // 格式：YYYY-MM-DD HH:MM:SS.SSS
    public static final int COLUMN_COLOR      	= 5;
    public static final int COLUMN_DESCRIPTION	= 6;
    
    public DBAccess() {
        Connection connection = null;
        // Create a database connection
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:db/" + DATABASE_NAME);
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            /**
             * 创建表（若表不存在）
             */
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(" +
                    KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    KEY_TITLE + " TEXT, " +
                    KEY_TYPE + " TEXT, " +
                    KEY_DATETIME + " TEXT, " +
                    KEY_COLOR + " TEXT, " +
                    KEY_DESCRIPTION + " TEXT)");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static final String INSERT =
            "INSERT INTO " + TABLE_NAME + "(" +
                    KEY_TITLE + ", " +
                    KEY_TYPE + ", " +
                    KEY_DATETIME + ", " +
                    KEY_COLOR + ", " +
                    KEY_DESCRIPTION + ") " +
                    "VALUES (?, ?, ?, ?, ?)";

    /**
     * 往数据库中加入一个事件
     * @param title 事件名称
     * @param type 事件类型
     * @param datetime 事件时间
     * @param color 事件颜色
     * @param description 事件描述
     */
    public void insert(String title, String type, String datetime, String color, String description) {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:db/" + DATABASE_NAME);
            // Insert using prepared statement
            PreparedStatement statement = connection.prepareStatement(INSERT);
            statement.setString(1, title);
            statement.setString(2, type);
            statement.setString(3, datetime);
            statement.setString(4, color);
            statement.setString(5, description);
            try {
                statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static final String SELECT_ALL =
            "SELECT * FROM " + TABLE_NAME;
    /**
     * 返回数据库中所有事件
     * @return 所有事件的列表
     */
    public List<Scheme> selectAll() {
        Connection connection = null;
        List<Scheme> result = new ArrayList<Scheme>();
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:db/" + DATABASE_NAME);
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            ResultSet resultSet = statement.executeQuery(SELECT_ALL);
            while (resultSet.next()) {
                int id = resultSet.getInt(COLUMN_ID);
                String title = resultSet.getString(COLUMN_TITLE);
                String type = resultSet.getString(COLUMN_TYPE);
                String datetime = resultSet.getString(COLUMN_DATETIME);
                String color = resultSet.getString(COLUMN_COLOR);
                String description = resultSet.getString(COLUMN_DESCRIPTION);
                result.add(new Scheme(id, title, type, datetime, color, description));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    private static final String SELECT_BY_ID =
            "SELECT * FROM " + TABLE_NAME + " WHERE " + KEY_ID + " = ?";
    /**
     * 根据事件ID查询数据库中的事件
     * @param id 事件ID
     * @return id对应的事件
     */
    public Scheme selectById(int id) {
        Connection connection = null;
        Scheme result = null;
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:db/" + DATABASE_NAME);
            PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String title = resultSet.getString(COLUMN_TITLE);
                String type = resultSet.getString(COLUMN_TYPE);
                String datetime = resultSet.getString(COLUMN_DATETIME);
                String color = resultSet.getString(COLUMN_COLOR);
                String description = resultSet.getString(COLUMN_DESCRIPTION);
                result = new Scheme(id, title, type, datetime, color, description);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    private static final String UPDATE =
            "UPDATE " + TABLE_NAME + " SET " +
                    KEY_TITLE + " = ?, " +
                    KEY_TYPE + " = ?, " +
                    KEY_DATETIME + " = ?, " +
                    KEY_COLOR + " = ?, " +
                    KEY_DESCRIPTION + " = ? " +
                    "WHERE " + KEY_ID + " = ?";
    /**
     * 更新数据库中的事件
     * @param scheme 要更新的事件，会根据scheme.id更新对应内容
     */
    public void update(Scheme scheme) {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:db/" + DATABASE_NAME);
            PreparedStatement statement = connection.prepareStatement(UPDATE);
            statement.setString(1, scheme.getTitle());
            statement.setString(2, scheme.getType());
            statement.setString(3, scheme.getDatetime());
            statement.setString(4, scheme.getColor());
            statement.setString(5, scheme.getDescription());
            statement.setInt(6, scheme.getId());
            try {
                statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static final String DELETE =
            "DELETE FROM " + TABLE_NAME + " WHERE " + KEY_ID + " = ?";
    /**
     * 根据事件ID删除数据库中的事件
     * @param id 事件ID
     */
    public void delete(int id) {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:db/" + DATABASE_NAME);
            PreparedStatement statement = connection.prepareStatement(DELETE);
            statement.setInt(1, id);
            try {
                statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    /**
     * 根据事件ID删除数据库中的事件
     * @param scheme 要删除的事件
     */
    public void delete(Scheme scheme) {
        delete(scheme.getId());
    }

    /**
     * 删除数据库中所有条目，注意id不会被重置
     */
    public void deleteAll() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:db/" + DATABASE_NAME);
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            statement.executeUpdate("DELETE FROM " + TABLE_NAME);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 删除数据库中所有条目，注意id会被重置
     * 使用先删除表格，再重新创建表格实现
     */
    public void resetTable() {
        dropTable();
        createTable();
    }

    /**
     * 创建表格
     */
    public void createTable() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:db/" + DATABASE_NAME);
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                    KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    KEY_TITLE + " TEXT, " +
                    KEY_TYPE + " TEXT, " +
                    KEY_DATETIME + " TEXT, " +
                    KEY_COLOR + " TEXT, " +
                    KEY_DESCRIPTION + " TEXT)");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 删除表格
     */
    public void dropTable() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:db/" + DATABASE_NAME);
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            statement.executeUpdate("DROP TABLE IF EXISTS " + TABLE_NAME);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 根据类别查询数据库中的事件
     * @param type 类别
     * @return 数据库中对应type的事件列表
     */
    public List<Scheme> selectByType(String type) {
        List<Scheme> result = new ArrayList<>();
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:db/" + DATABASE_NAME);
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            ResultSet resultSet = statement.executeQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + KEY_TYPE + " = '" + type + "'");
            while (resultSet.next()) {
                int id = resultSet.getInt(COLUMN_ID);
                String title = resultSet.getString(COLUMN_TITLE);
                String type1 = resultSet.getString(COLUMN_TYPE);
                String datetime = resultSet.getString(COLUMN_DATETIME);
                String color = resultSet.getString(COLUMN_COLOR);
                String description = resultSet.getString(COLUMN_DESCRIPTION);
                result.add(new Scheme(id, title, type1, datetime, color, description));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    /**
     * 根据颜色查询数据库中的事件
     * @param color 颜色
     * @return 数据库中对应color的事件列表
     */
    public List<Scheme> selectByColor(String color) {
        List<Scheme> result = new ArrayList<>();
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:db/" + DATABASE_NAME);
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            ResultSet resultSet = statement.executeQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + KEY_COLOR + " = '" + color + "'");
            while (resultSet.next()) {
                int id = resultSet.getInt(COLUMN_ID);
                String title = resultSet.getString(COLUMN_TITLE);
                String type1 = resultSet.getString(COLUMN_TYPE);
                String datetime = resultSet.getString(COLUMN_DATETIME);
                String color1 = resultSet.getString(COLUMN_COLOR);
                String description = resultSet.getString(COLUMN_DESCRIPTION);
                result.add(new Scheme(id, title, type1, datetime, color1, description));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    /**
     * 根据标题和描述查询数据库中的事件
     * @param keyword 标题或描述中需要包含的关键字
     * @return 数据库中包含keyword的事件列表
     */
    public List<Scheme> searchByTitleAndDescription(String keyword) {
        List<Scheme> result = new ArrayList<>();
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:db/" + DATABASE_NAME);
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            ResultSet resultSet = statement.executeQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + KEY_TITLE + " LIKE '%" + keyword + "%' OR " + KEY_DESCRIPTION + " LIKE '%" + keyword + "%'");
            while (resultSet.next()) {
                int id = resultSet.getInt(COLUMN_ID);
                String title = resultSet.getString(COLUMN_TITLE);
                String type1 = resultSet.getString(COLUMN_TYPE);
                String datetime = resultSet.getString(COLUMN_DATETIME);
                String color = resultSet.getString(COLUMN_COLOR);
                String description = resultSet.getString(COLUMN_DESCRIPTION);
                result.add(new Scheme(id, title, type1, datetime, color, description));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    /**
     * 根据日期查询数据库中的事件
     * @param startDatetime 开始日期，格式：YYYY-MM-DD HH:MM:SS.SSS
     * @param endDatetime 结束日期，格式：YYYY-MM-DD HH:MM:SS.SSS
     * @return 数据库中在startDatetime和endDatetime之间的事件列表
     */
    public List<Scheme> selectByDatetimeRange(String startDatetime, String endDatetime) {
        List<Scheme> result = new ArrayList<>();
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:db/" + DATABASE_NAME);
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            ResultSet resultSet = statement.executeQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + KEY_DATETIME + " BETWEEN '" + startDatetime + "' AND '" + endDatetime + "'");
            while (resultSet.next()) {
                int id = resultSet.getInt(COLUMN_ID);
                String title = resultSet.getString(COLUMN_TITLE);
                String type1 = resultSet.getString(COLUMN_TYPE);
                String datetime = resultSet.getString(COLUMN_DATETIME);
                String color = resultSet.getString(COLUMN_COLOR);
                String description = resultSet.getString(COLUMN_DESCRIPTION);
                result.add(new Scheme(id, title, type1, datetime, color, description));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }
}