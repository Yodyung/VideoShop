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
			
			//3.(******)		>> 나머지 번호들은 거의 한번 픽스해놓고 바꿀 일이 없다. but 3번에서 원하는 정보 입력하는거 잘 해야함. (SQL SELECT문 등)
			//20번 부서의 사원들의 정보 사번 사원명 월급 부서명 근무지
			String sql="SELECT E.EMPNO 사번, E.ENAME 사원명, E.SAL 월급, D.DNAME 부서명, D.LOC 근무지 FROM EMP E,"
					+ " DEPT D WHERE E.DEPTNO=D.DEPTNO AND E.DEPTNO=20";

			//4.
			Statement stmt=con.createStatement();
			
			//5.
			ResultSet rs=stmt.executeQuery(sql);	//입력하는 값이 int 나 string 값이 아닌 ResultSet이라는 값으로 설정되어 있기 때문에 ResultSet으로 받아야함
			while(rs.next()) {						//while문 안에 rs.next() > 값이 있으면 true, 값이 없으면 false여서 조건 설정해놓을 수 있음.
				String ename=rs.getString("사원명");
				String dname=rs.getString("부서명");
				String loc=rs.getString("근무지");
				int empno=rs.getInt("사번");
				int sal=rs.getInt("월급");
				System.out.println(empno+","+ename+","+sal+","+dname+","+loc);
				
			}
			
			//6.닫기
			rs.close();							//마지막에 연 순서대로 닫기 가장 마지막에 연거 닫고
			stmt.close();						//두번째로 연 순서 닫고
			con.close();						//첫번째로 연 순서 닫기
			
			
		} catch (Exception e) {
			System.out.println("실패 :"+e);
		}

	}

}
