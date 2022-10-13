package model.dao;
//화면 클래스와 SQL을 연결할 클래스

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import model.CustomerDao;
import model.vo.CustomerVO;

public class CustomerDaoImpl implements CustomerDao{					//implements로 상속, 부모는 선언만 하며, 반드시 자식이 정의를 오버라이딩해서 사용한다.

	final static String DRIVER	="oracle.jdbc.driver.OracleDriver";		//final static이 붙으면 상수 명을 대문자로 쓴다.
	final static String URL		="jdbc:oracle:thin:@127.0.0.1:1521:xe";	//driver와 url 경로 정확하게 입력하기.
	final static String USER	="scott";								//sql user id	
	final static String PASS	="tiger";			


	public CustomerDaoImpl() throws Exception{		//기본 생성자 안에서 드라이버 로딩 해준다. 예외 던지기.
		// 1. 드라이버로딩
		Class.forName(DRIVER);		//기본 생성자 안에 드라이버 로딩 해주고 성공 시 안내멘트 출력
		System.out.println("드라이버 로딩 성공");

	}

	/*
	 * 메소드 명  : insertCustomer
	 * 인자		: CustomerVO 
	 * 리턴값		: 없음
	 * 역할		: 사용자가 입력한 정보를 CustomerVO를 통해 db에 저장
	 */
	public void insertCustomer(CustomerVO vo) throws Exception{			//implements에서 오버라이딩 한 메소드 받기
		Connection con=null;											//전송 객체 선언 해주기
		PreparedStatement ps=null;
		//CustomerVO v=new CustomerVO();

		// 2. Connection 연결객체 얻어오기
		try {
			con=DriverManager.getConnection(URL,USER,PASS);					//연결객체 얻어오기(TRY, CATCH)로 받기 

			// 3. sql 문장 만들기***
			String sql = "INSERT INTO CUSTOMER(name, pno, s_pno, addr, email) VALUES (?,?,?,?,?)";

			// 4. sql 전송객체 (PreparedStatement)	
			ps=con.prepareStatement(sql);

			// ? 설정해주기
			ps.setString(1,vo.getName());
			ps.setString(2,vo.getpNo());
			ps.setString(3,vo.getS_Pno());
			ps.setString(4,vo.getAddr());
			ps.setString(5,vo.getEmail());

			// 5. sql 전송
			ps.executeUpdate();

		}finally {
			// 6. 닫기 
			ps.close();
			con.close();
		}
	}

	/*
	 * 메소드 명  : searchByTel
	 * 인자		: 검색할 전화번호
	 * 리턴값		: 전화번호 검색에 따른 고객정보
	 * 역할		: 사용자가 입력한 전화번호를 받아서 해당하는 고객정보를 리턴
	 */
	public CustomerVO selectByTel(String tel) throws Exception{
		CustomerVO dao = new CustomerVO();
		Connection con=null;
		PreparedStatement ps=null;

		//2. 연결객체 얻어오기
		try {
			con=DriverManager.getConnection(URL,USER,PASS);
			//3. SQL 문장 만들기
			String sql="SELECT * FROM CUSTOMER WHERE PNO=?";

			//4. 전송객체 얻어오기
			ps=con.prepareStatement(sql);
			ps.setString(1, tel);

			//5. 전송
			ResultSet rs=ps.executeQuery();
			if(rs.next()) {
				dao.setName(rs.getString("NAME"));
				dao.setpNo(rs.getString("pno"));
				dao.setS_Pno(rs.getString("s_pno"));
				dao.setAddr(rs.getString("addr"));
				dao.setEmail(rs.getString("email"));
			}

		}finally {
			//6. 닫기	
			//rs.close();
			ps.close();
			con.close();
		}
		return dao;
	}

	public int updateCustomer(CustomerVO vo) throws Exception{
		//2.연결객체 얻어오기
		Connection con=null;
		PreparedStatement ps=null;

		try {
			con=DriverManager.getConnection(URL,USER,PASS);
			//3. SQL문장 만들기
			String sql="UPDATE CUSTOMER SET NAME= ? , "
					+ " S_PNO = ? , ADDR = ? , EMAIL = ?"
					+ " WHERE PNO = ?";

			//4. 전송객체 얻어오기
			ps=con.prepareStatement(sql);
			// ? 세팅 **
			ps.setString(1,vo.getName());
			ps.setString(2,vo.getS_Pno());
			ps.setString(3,vo.getAddr());
			ps.setString(4,vo.getEmail());
			ps.setString(5,vo.getpNo());

			//5. 전송
			ps.executeUpdate();				//sql이 위에 들어가있으므로 다시 괄호 안에 넣으면 안됨


		}finally{
			//6.닫기
			ps.close();
			con.close();
		}//End Finally

		int result = 0;


		return result;
	}

	@Override
	public int update(CustomerVO vo) throws Exception {
		//2. 연결객체 얻어오기
		Connection con=null;
		PreparedStatement ps=null;
		

		try {
			con=DriverManager.getConnection(DRIVER, USER, PASS);

			//3. sql
			String sql="UPDATE CUSTOMER SET NAME = ?, S_PNO = ?, "
					+ " ADDR = ?, EMAIL = ?  WHERE PNO = ? ";

			//4. 전송객체 얻어오기
			ps=con.prepareStatement(sql);
			ps.setString(1, vo.getName());
			ps.setString(2, vo.getS_Pno());
			ps.setString(3, vo.getAddr());
			ps.setString(4, vo.getEmail());
			ps.setString(5, vo.getpNo());

			//5. 전송
			ps.executeUpdate();
			

		} finally {
			//6. 닫기 
			ps.close();
			con.close();
		}
		return 0;

	}//End delete
}
