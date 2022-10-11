package c_infoview;

//DB와 프레임이 있는 자바 클래스와 SQL을 연결하는 클래스

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class InfoModelImpl implements infoModel {
//implements (interface 구현)
//부모 객체는 선언만 하며 정의(내용)은 자식에서 오버라이딩 (재정의) 해서 사용해야함
	
	//String 으로 드라이버(오라클류), 연결할 URL, 드라이버의 아이디, 비밀번호를 선언해 준다. 
	final static String DRIVER	="oracle.jdbc.driver.OracleDriver";		//final static이 붙으면 상수 명을 대문자로 쓴다.
	final static String URL		="jdbc:oracle:thin:@127.0.0.1:1521:xe";	//driver와 url 경로 정확하게 입력하기.
	final static String USER	="scott";								//sql user id	
	final static String PASS	="tiger";								//sql user password


	public InfoModelImpl() throws ClassNotFoundException {
		//1.드라이버 로딩
		Class.forName(DRIVER);		//기본 생성자 안에 드라이버 로딩 해주고 성공 시 안내멘트 출력
		System.out.println("드라이버 로딩 성공");
	}//End InfoModelImpl throws

	/*
	 * 사용자 입력값을 받아서 DB에 저장하는 역할
	 */
	@Override
	public void insertInfo(InfoVO vo) throws SQLException {
		//2.연결객체 얻어오기
		Connection con=null;
		PreparedStatement ps=null;
		try {
			con=DriverManager.getConnection(URL,USER,PASS);

			//3. SQL 문장 **
			String sql="INSERT INTO info_tab(name, jumin, tel, gender, age, home) "
					+ " VALUES(?,?,?,?,?,?)";

			//4.전송객체
			ps=con.prepareStatement(sql);
			// ?세팅 **
			ps.setString(1,vo.getName());
			ps.setString(2,vo.getId());
			ps.setString(3,vo.getTel());
			ps.setString(4,vo.getGender());
			ps.setInt(5,vo.getAge());
			ps.setString(6,vo.getHome());

			//5.전송
			ps.executeUpdate();		//select 구문이 아닌 거의 대부분의 구문은 executeUpdate를 사용함.

		}finally{
			//6.닫기
			con.close();
			ps.close();
		}

	}//End insertInfo()

	/*
	 * 전체 info_tab의 레코드 검색
	 */
	@Override
	public ArrayList<InfoVO> selectAll() throws SQLException {
		//2.연결객체 얻어오기
		Connection con=null;
		PreparedStatement ps=null;
		ResultSet rs = null;
		try {
			con=DriverManager.getConnection(URL,USER,PASS);

			//3.SQL
			String sql="SELECT * FROM info_tab";

			//4.전송객체 얻어오기
			ps=con.prepareStatement(sql);

			//5.전송
			rs = ps.executeQuery();		//select문이니까 executeQuery로 전송하기

			ArrayList<InfoVO> list = new ArrayList<InfoVO>();	//result set으로 받은 정보를 저장할 ArrayList 선언
			while(rs.next()) {									//rs가 참일 때 실행된다.
				InfoVO vo=new InfoVO();							//VO 클래스를 선언해주고 
				vo.setName(rs.getString("name"));				//VO 클래스의 변수들을 sql의 컬럼으로 set해준다.
				vo.setId(rs.getString("jumin"));
				vo.setTel(rs.getString("tel"));
				vo.setGender(rs.getString("gender"));
				vo.setAge(rs.getInt("age"));
				vo.setHome(rs.getString("home"));

				list.add(vo);									//add 함수로 vo에 데이터 누적하기
			}
			return list;										//리턴 값을 똑같이 InfoVO로 해준다.

		}finally{						//finally 구문은 예외처리가 발생여부를 떠나 무조건 실행하도록 하는 구문입니다.
			//6.닫기
			rs.close();
			ps.close();
			con.close();
		}

	}

	@Override
	public InfoVO selectByTel(String tel) throws SQLException {
		//2.연결객체 얻어오기
		Connection con=null;
		PreparedStatement ps=null;
		InfoVO vo=new InfoVO();

		try {
			con=DriverManager.getConnection(URL,USER,PASS);
			//3. SQL문장 만들기
			String sql="SELECT * FROM info_tab WHERE tel=? ";

			//4. 전송객체 얻어오기
			ps=con.prepareStatement(sql);
			ps.setString(1, tel);

			//5. 전송
			ResultSet rs=ps.executeQuery();
			if(rs.next()) {
				vo.setName(rs.getString("NAME"));
				vo.setId(rs.getString("JUMIN"));
				vo.setTel(rs.getString("TEL"));
				vo.setGender(rs.getString("GENDER"));
				vo.setAge(rs.getInt("AGE"));
				vo.setHome(rs.getString("HOME"));
			}

		}finally{
			//6.닫기
			ps.close();
			con.close();
		}//End Finally
		return vo;
	}//End selectByTel

	/*
	 * 메소드명 : delete
	 * 인자 	  : 전화번호
	 * 리턴값   : 삭제한 행 수
	 * 역할	  : 전화번호를 넘겨받아 해당 전화번호의 레코드를 삭제
	 */
	@Override
	public int delete(String Tel) throws SQLException {
		//INT로 받는 이유는 delete를 하게 되면 0,1값으로 값이 나오기 때문이다.
		
		//2.연결객체 얻어오기
		Connection con=null;
		PreparedStatement ps=null;

		try {
			con=DriverManager.getConnection(URL,USER,PASS);
			//3. SQL문장 만들기
			String sql="delete from info_tab where tel=?";

			//4. 전송객체 얻어오기
			ps=con.prepareStatement(sql);
			ps.setString(1, Tel);

			//5. 전송
			ps.executeUpdate();				//sql이 위에 들어가있으므로 다시 괄호 안에 넣으면 안됨


		}finally{
			//6.닫기
			ps.close();
			con.close();
		}//End Finally
		return 0;							//0이나 1로 리턴한다.
	}//end delete


	@Override
	public void modify(InfoVO vo) throws SQLException {
		//2.연결객체 얻어오기
		Connection con=null;
		PreparedStatement ps=null;
		

		try {
			con=DriverManager.getConnection(URL,USER,PASS);
			//3. SQL문장 만들기
			String sql="UPDATE info_tab SET NAME= ? , JUMIN = ? , "
					+ " GENDER = ? , AGE = ? , HOME = ?"
					+ " WHERE TEL = ?";

			//4. 전송객체 얻어오기
			ps=con.prepareStatement(sql);
			// ? 세팅 **
			ps.setString(1,vo.getName());
			ps.setString(2,vo.getId());
			ps.setString(3,vo.getGender());
			ps.setInt(4,vo.getAge());
			ps.setString(5,vo.getHome());
			ps.setString(6,vo.getTel());


			//5. 전송
			ps.executeUpdate();				//sql이 위에 들어가있으므로 다시 괄호 안에 넣으면 안됨


		}finally{
			//6.닫기
			ps.close();
			con.close();
		}//End Finally
	}


}





