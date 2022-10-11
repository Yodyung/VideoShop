package a_statement;

import java.sql.Connection;
import java.sql.*;


public class InsertEmp {

	public static void main(String[] args) {
		
		try {
			//1. 드라이버 로딩+try catch 문으로 예외 잡아놓기
			Class.forName("oracle.jdbc.driver.OracleDriver");		//Class.forname(드라이버 루트)
			System.out.println("드라이버 로딩 성공2");					//제대로 연결했을 시 나오는 안내문구 입력해두기
			
			//2. 연결객체 얻어오기
			String url ="jdbc:oracle:thin:@192.168.0.246:1521:xe";	//정보를 입력할 ip주소 입력하기
			String user ="scott";									//아이디
			String pass ="tiger";									//비밀번호
			
			Connection con = DriverManager.getConnection(url, user, pass);	//설정한 url,user,password 를 넣고 드라이버매니저로 커넥션 해준다.
			System.out.println("DB 연결 성공2");								//연결 성공시 나올 안내문구
			
			//3. SQL 문장
			String sql= "INSERT INTO EMP(EMPNO, ENAME, SAL) "				//SQL에 정보 입력할 시 쓰는 그대로 ""안에 입력해주기. 
					+ " VALUES(1120,'쯔와압',10000)";							//한 줄 띄면 앞에 공백 하나 만들어주기
			//String sql2= "UPDATE EMP SET SAL=SAL*1.1 WHERE DEPTNO=10";
			//String sql2= "DELETE FROM EMP WHERE SAL>=10000";
			
			//4. SQL 전송객체
			Statement stmt =con.createStatement();							//연결객체를 전송할 전송객체 만들어주기 
			
			//5. SQL 전송
			/*		-return값 resultSet executeQuery()    : SELECT
			 * 		-return값 int 		executeUpdate()   : INSERT/DELETE/UPDATE
			 */
			int result=stmt.executeUpdate(sql);								//sql이 제대로 들어갔는지 result 값 안에 넣고 전송
			System.out.println(result+"행을 실행");							//성공시 안내 문구 나옴
			
			//6. 닫기
			stmt.close();													//임포트로 열어준 것들 닫기
			con.close();													//꼭 순서대로 닫아줘야함!
			
		} catch (Exception e) {												//모든 예외 시 
			System.out.println("DB 실패 :"+e);								//catch문에서 실패시 나올 문구 설정
		}

	}

}
