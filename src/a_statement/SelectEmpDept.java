package a_statement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class SelectEmpDept {

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
			//20번 부서의 사원들의 정보 사번 사원명 월급 부서명 근무지
			String sql="SELECT E.EMPNO 사번, E.ENAME 사원명, E.SAL 월급, D.DNAME 부서명, D.LOC 근무지 FROM EMP E,"
					+ " DEPT D WHERE E.DEPTNO=D.DEPTNO AND E.DEPTNO=20";

			//4.
			Statement stmt=con.createStatement();
			
			//5.
			ResultSet rs=stmt.executeQuery(sql);
			while(rs.next()) {
				String ename=rs.getString("사원명");
				String dname=rs.getString("부서명");
				String loc=rs.getString("근무지");
				int empno=rs.getInt("사번");
				int sal=rs.getInt("월급");
				System.out.println(empno+","+ename+","+sal+","+dname+","+loc);
				
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
