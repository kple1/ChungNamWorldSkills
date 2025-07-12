package Other;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Form.Login;

public class DB {
	static Connection c;
	static Statement st;
	
	static {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			c = DriverManager.getConnection("jdbc:mysql://localhost:3306/clothingstore?serverTimeZone=UTC", "root", "1234");
			st = c.createStatement();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static Object getObject(String q, Object... o) {
		try {
			var pstm = c.prepareStatement(q);
			for (int i = 0; i < o.length; i++) pstm.setObject(i + 1, o[i]);
			var rs = pstm.executeQuery();
			return rs.next() ? rs.getObject(1) : null; 
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
				for (int i = 0; i < c; i++) {
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
		var list = new Object[] {};
		try {
			var pstm = c.prepareStatement(q);
			for (int i = 0; i < o.length; i++) {
				pstm.setObject(i + 1, o[i]);
			}
			var rs = pstm.executeQuery();
			var c = rs.getMetaData().getColumnCount();
			while (rs.next()) {
				var obj = new Object[c];
				for (int i = 0; i < c; i++) {
					obj[i] = rs.getObject(i + 1);
				}
				list = obj;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public static List<Object[]> getSaleList() {
		return array("select sa_sale, p_no from salelist");
	}
	
	public static byte[] getBytes(String q, Object... o) {
		return (byte[])getObject(q, o);
	}
	
	public static List<Object[]> getProductList(String option) {
		return array("select p_img, p_name, p_price, p_no,\r\n"
				+ "floor((select avg(r_star) from review r\r\n"
				+ "join purchase pu on pu.pu_no = r.pu_no\r\n"
				+ "join productlist p on p.p_no = pu.p_no\r\n"
				+ "where p.p_no = pro.p_no)) as star, c.c_no, s.sb_no from productlist pro\r\n"
				+ "join subcategory s on s.sb_no = pro.sb_no\r\n"
				+ "join category c on c.c_no = s.c_no " + option);
	}
	
	public static Object[] getCategory() {
		return array("select c_name from category").stream().map(x -> x[0]).toArray();
	}
	
	public static Object[] getSubCategory() {
		return array("select sb_name from subcategory").stream().map(x -> x[0]).toArray();
	}
	
	public static List<Object[]> getProductInfo(Object sbno, Object pno) {
		return array("SELECT p_img, p_name, p_no FROM productlist where sb_no = ? and p_no != ?", sbno, pno);
	}
	
	public static Object[] sendProductData(Object sbno, Object pno) {
		return list("select p_no, p_img, p_name, u.u_name, floor((select avg(r_star) from review r\r\n"
				+ "join purchase pu on pu.pu_no = r.pu_no\r\n"
				+ "join productlist p on p.p_no = pu.p_no\r\n"
				+ "where p.p_no = pro.p_no)) as star, concat_ws('/', c.c_name, s.sb_name) as category, p_price\r\n"
				+ "from productlist pro\r\n"
				+ "join user u on u.u_no = pro.u_no\r\n"
				+ "join subcategory s on s.sb_no = pro.sb_no\r\n"
				+ "join category c on c.c_no = s.c_no\r\n"
				+ "where pro.sb_no = ? and pro.p_no = ?", sbno, pno);
	}
	
	public static Object[] getUserInfo() {
		return list("SELECT u_name, u_id, u_pw, u_price, u_birth FROM user where u_no = ?;", Login.no);
	}
	
	public static List<Object[]> getPurchaseList() {
		return array("select pu_no, p.p_img, p.p_name, concat_ws(',', pu.p_s, pu.p_m, pu.p_l, pu.p_xl) as sum, pu_date, p.p_price from purchase pu\r\n"
				+ "join productlist p on p.p_no = pu.p_no\r\n"
				+ "where pu.u_no = ?;", Login.no);
	}
	
	public static List<Object[]> getShoppingBasket() {
		return array("select s_no, p.p_img, p.p_name, concat_ws(',', s.p_s, s.p_m, s.p_l, s.p_xl) as c, u.u_name, p.p_price from shoppingbasket s\r\n"
				+ "join productlist p on p.p_no = s.p_no\r\n"
				+ "join user u on u.u_no = s.u_no\r\n"
				+ "where s.u_no = ?", Login.no);
	}
	
	public static List<Object[]> getPayment() {
		return array("SELECT p.p_img, p.p_name, cast(p.p_price * (s.p_s + s.p_m + s.p_l + s.p_xl) as signed) as money, p.p_no, s.s_no FROM shoppingbasket s\r\n"
				+ "join productlist p on p.p_no = s.p_no\r\n"
				+ "where s.u_no = ?", Login.no);
	}
	
	public static void update(String q, Object... o) {
		try {
			var pstm = c.prepareStatement(q);
			for (int i = 0; i < o.length; i++) pstm.setObject(i + 1, o[i]);
			pstm.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}