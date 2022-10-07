package a_statement;

import java.sql.Connection;
import java.sql.*;


public class InsertEmp {

	public static void main(String[] args) {
		
		try {
			//1. 드라이버 로딩+try catch 문으로 예외 잡아놓기
			Class.forName("oracle.jdbc.driver.OracleDriver");
			System.out.println("드라이버 로딩 성공2");
			
			//2. 연결객체 얻어오기
			String url ="jdbc:oracle:thin:@192.168.0.9:1521:xe";
			String user ="scott";
			String pass ="tiger";
			
			Connection con = DriverManager.getConnection(url, user, pass);
			System.out.println("DB 연결 성공2");
			
			//3. SQL 문장
			String sql= "INSERT INTO EMP(EMPNO, ENAME, SAL) "
					+ " VALUES(1120,'쯔와압',10000)";		//한 줄 띄면 앞에 공백 하나 만들어주기
			//String sql2= "UPDATE EMP SET SAL=SAL*1.1 WHERE DEPTNO=10";
			//String sql2= "DELETE FROM EMP WHERE SAL>=10000";
			
			//4. SQL 전송객체
			Statement stmt =con.createStatement();
			
			//5. SQL 전송
			/*		-return값 resultSet executeQuery()    : SELECT
			 * 		-return값 int 		executeUpdate()   : INSERT/DELETE/UPDATE
			 */
			int result=stmt.executeUpdate(sql);
			System.out.println(result+"행을 실행");
			
			//6. 닫기
			stmt.close();
			con.close();
			
		} catch (Exception e) {
			System.out.println("DB 실패 :"+e);
		}

	}

}
