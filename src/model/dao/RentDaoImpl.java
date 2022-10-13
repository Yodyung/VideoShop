package model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import model.RentDao;
import model.vo.CustomerVO;


public class RentDaoImpl implements RentDao {

	final static String DRIVER	="oracle.jdbc.driver.OracleDriver";		//final static이 붙으면 상수 명을 대문자로 쓴다.
	final static String URL		="jdbc:oracle:thin:@127.0.0.1:1521:xe";	//driver와 url 경로 정확하게 입력하기.
	final static String USER	="scott";								//sql user id	
	final static String PASS	="tiger";								//sql user password


	//Connection con;

	public RentDaoImpl() throws Exception{
		// 1. 드라이버로딩
		Class.forName(DRIVER);
		System.out.println("드라이버 로딩 성공");

	}

	/*
	 * 대여
	 */
	@Override
	public void rentvideo(String tel, int vnum) throws Exception {
		//2. 연결객체 받아오기
		Connection con = null;
		PreparedStatement ps = null;

		try {
			con=DriverManager.getConnection(URL, USER, PASS);

			//3. SQL 문장
			//checkdouble();

			String sql="INSERT INTO RENT(PNO, VIDEONO, RENTNO, RETURNYN, RENTDATE) "
					+ " VALUES(?,?,SEQ_Rent1.NEXTVAL,'N',SYSDATE) ";


			//4. 전송객체 받아오기
			ps=con.prepareStatement(sql);
			//? 세팅
			ps.setString(1, tel);
			ps.setInt(2, vnum);

			//5. 전송
			ps.executeUpdate();

		}finally {
			//6. 닫기
			ps.close();
			con.close();
		}
	}//END

	/*
	 * 반납
	 */
	@Override
	public void returnVideo(int vnum) throws Exception {
		//2. 연결객체 받아오기
		Connection con = null;
		PreparedStatement ps = null;

		try {
			con=DriverManager.getConnection(URL, USER, PASS);

			//3. SQL 문장

			String sql="UPDATE RENT SET RETURNYN = 'Y' WHERE VIDEONO = ? AND RETURNYN ='N'";

			//4. 전송객체 받아오기
			ps=con.prepareStatement(sql);
			//? 세팅
			ps.setInt(1, vnum);

			//5. 전송
			ps.executeUpdate();

		}finally {
			//6. 닫기
			ps.close();
			con.close();
		}
	}//END

	/*
	 * 미납목록 검색
	 */
	@Override
	public ArrayList overdue() throws Exception {
		ArrayList data=new ArrayList();

		//2. 연결객체 
		Connection con=null;
		PreparedStatement ps=null;
		try {
			con=DriverManager.getConnection(URL,USER,PASS);

			//3. sql
			String sql="select 	v.videono videono, v.title title, c.name name, "
					+ "	c.pno tel, r.rentdate+7 returndate, 'N' return "
					+ " from  customer c inner join rent r on c.pno=r.pno "
					+ " inner join video v on v.videono=r.videono "
					+ " where r.returnyn = 'N' ";

			//System.out.println(sql);

			//4. 전송객체
			ps=con.prepareStatement(sql);

			//5. 전송
			ResultSet rs=ps.executeQuery();
			while(rs.next()) {
				ArrayList temp = new ArrayList<>();
				temp.add(rs.getString("videono"));
				temp.add(rs.getString("title"));
				temp.add(rs.getString("name"));
				temp.add(rs.getString("tel"));
				temp.add(rs.getString("returndate"));
				temp.add(rs.getString("return"));
				data.add(temp);
			}
		}finally{
			//6.닫기
			con.close();
			ps.close();
		}	
		return data;
	}//End

	/*
	 * 전화번호입력후 엔터치면 이름 뜨게
	 */
	@Override
	public String searchName(String tel) throws Exception {
		// 2. 연결객체 얻어오기
		Connection con = null; 
		PreparedStatement ps = null;
		ResultSet rs = null;
		String name=null;

		try {
			con = DriverManager.getConnection(URL,USER,PASS);

			// 3. sql 문장
			String sql = "SELECT NAME FROM CUSTOMER WHERE PNO=?";

			// 4. 전송객체
			ps = con.prepareStatement(sql);
			ps.setString(1, tel);
			// 5. 전송
			rs = ps.executeQuery();
			if(rs.next()) {
				name=rs.getString("name");
			}
			return name;

		}finally {
			//6. 닫기
			rs.close();
			con.close();
		}
	}


}
