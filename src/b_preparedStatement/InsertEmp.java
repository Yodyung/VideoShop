package b_preparedStatement;

import java.sql.Connection;
import java.sql.*;


public class InsertEmp {

	public static void main(String[] args) {
		
		try {
			//1. 드라이버 로딩+try catch 문으로 예외 잡아놓기
			Class.forName("oracle.jdbc.driver.OracleDriver");
			System.out.println("드라이버 로딩 성공2");
			
			//2. 연결객체 얻어오기
			String url ="jdbc:oracle:thin:@192.168.0.246:1521:xe";
			String user ="scott";
			String pass ="tiger";
			
			Connection con = DriverManager.getConnection(url, user, pass);
			System.out.println("DB 연결 성공2");
			
			//3. SQL 문장
			String bonmyeung="본명";
			int wolgup= 9999;
			String jikup="IT";
			
			String sql= "INSERT INTO EMP(EMPNO, ENAME, SAL, JOB)"
					+ "VALUES(5757,?,?,?)";
			
			//4. SQL 전송객체
			PreparedStatement stmt=con.prepareStatement(sql);
			stmt.setString(1, bonmyeung);
			stmt.setInt(2, wolgup);
			stmt.setString(3, jikup);
			
			//5. SQL 전송
			/*		-return값 resultSet executeQuery()    : SELECT
			 * 		-return값 int 		executeUpdate()   : INSERT/DELETE/UPDATE
			 */
			int result=stmt.executeUpdate();		//********* sql 위에서 썼기때문에 쓰면 안됨
			System.out.println(result+"행을 실행");
			
			//6. 닫기
			stmt.close();
			con.close();
			
		} catch (Exception e) {
			System.out.println("DB 실패 :"+e);
		}

	}

}
