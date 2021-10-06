import org.junit.jupiter.api.Test;

import java.net.ConnectException;
import java.sql.*;

public class TestJDBC {

    @Test
    public void test1() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection= DriverManager.getConnection("jdbc:mysql://jlht.icu:3306/orange_music", "root", "root");
        Statement statement= connection.createStatement();
        long time=System.currentTimeMillis();
        boolean bl=statement.execute("select * from singer WHERE plat_id=4607");
        System.out.println("使用時間"+(System.currentTimeMillis()-time));
        System.out.println(bl);





    }
}
