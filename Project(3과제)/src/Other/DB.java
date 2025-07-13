package Other;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;

import Form.Login;

public class DB {
	static Connection c;
	
	static {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			c = DriverManager.getConnection("jdbc:mysql://localhost:3306/question?serverTimeZone=UTC", "root", "1234");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static Object getObject(String q, Object... o) {
		try {
			var pstm = c.prepareStatement(q);
			for (int i = 0; i < o.length; i++) {
				pstm.setObject(i + 1, o[i]);
			}
			var rs = pstm.executeQuery();
			if (rs.next()) {
				return rs.getObject(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static int getInt(String q, Object... o) {
		return Integer.parseInt(getObject(q, o).toString());
	}
	
	public static String getString(String q, Object... o) {
		return getObject(q, o).toString();
	}
	
	public static List<Object[]> array(String q, Object... o) {
		var list = new ArrayList<Object[]>();
		try {
			var pstm = c.prepareStatement(q);
			for (int i = 0; i < o.length; i++) {
				pstm.setObject(i + 1, o[i]);
			}
			var rs = pstm.executeQuery();
			var c = rs.getMetaData().getColumnCount();
			while (rs.next()) {
				var obj = new Object[c];
				for (int i = 0; i < obj.length; i++) {
					obj[i] = rs.getObject(i + 1);
				}
				list.add(obj);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public static Object[] list(String q, Object... o) {
		var list = new Object[] {0};
		try {
			var pstm = c.prepareStatement(q);
			for (int i = 0; i < o.length; i++) {
				pstm.setObject(i + 1, o[i]);
			}
			var rs = pstm.executeQuery();
			var c = rs.getMetaData().getColumnCount();
			while (rs.next()) {
				var obj = new Object[c];
				for (int i = 0; i < obj.length; i++) {
					obj[i] = rs.getObject(i + 1);
				}
				list = obj;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public static Object[] requestUserInfo(String col, Object value) {
		return list("select * from\r\n"
				+ "(select uno, name, id, pw, 'user' from user\r\n"
				+ "union all\r\n"
				+ "select tno, name, id, pw, 'teacher' from teacher) as c\r\n"
				+ "where " + col + " = ?", value);
	}
	
	public static List<Object[]> option1() {
		return array("select name, count(c.tno), t.uni, t.grade from catalog c\r\n"
				+ "join teacher t on t.tno = c.tno\r\n"
				+ "group by c.tno\r\n"
				+ "order by count(c.tno) desc, name");
	}
	
	public static List<Object[]> option2() {
		return array("select type, count(type) from catalog c\r\n"
				+ "group by type\r\n"
				+ "order by count(type) desc, type");
	}
	public static List<Object[]> getTeacherList(String option) {
		return array("select tno, name, uni,\r\n"
				+ "(select sum(explan is not null) from catalog c where c.tno = t.tno) as a,\r\n"
				+ "(select floor(sum(explan is not null) / count(*) * 100) from catalog c where c.tno = t.tno) as b\r\n"
				+ "from teacher t " + option);
	}
	
	public static void update(String q, Object... o) {
		try {
			var pstm = c.prepareStatement(q);
			for (int i = 0; i < o.length; i++) {
				pstm.setObject(i + 1, o[i]);
			}
			pstm.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static List<Object[]> getWrong() {
		return array("select * from wrong where uno = ?", Login.no);
	}
}
