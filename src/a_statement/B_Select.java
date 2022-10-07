package a_statement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class B_Select {

	public static void main(String[] args) {
		
		try {
			//1.
			Class.forName("oracle.jdbc.driver.OracleDriver");
			System.out.println("드라이버 로딩 성공2");
			
			//2.
			String url ="jdbc:oracle:thin:@192.168.0.246:1521:xe";
			String user ="scott";
			String pass ="tiger";
			
			Connection con = DriverManager.getConnection(url, user, pass);
			System.out.println("DB 연결 성공2");
			//3.(******)
			String sql="SELECT EMPNO, ENAME, SAL, JOB FROM EMP";

			//4.
			Statement stmt=con.createStatement();
			
			//5.
			ResultSet rs=stmt.executeQuery(sql);
			while(rs.next()) {
				int empno=	 rs.getInt("EMPNO");
				String ename=rs.getString("ENAME");
				int sal=	 rs.getInt("SAL");
				String job=  rs.getString("JOB");
				System.out.println(empno+"/"+ename+"/"+sal+"/"+job);
			}
			
			//6.닫기
			rs.close();
			stmt.close();
			con.close();
			
			
		} catch (Exception e) {
			System.out.println("실패 :"+e);
		}

	}

}
