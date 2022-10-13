package model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import model.VideoDao;
import model.vo.VideoVO;

public class VideoDaoImpl implements VideoDao{

	final static String DRIVER	="oracle.jdbc.driver.OracleDriver";		//final static이 붙으면 상수 명을 대문자로 쓴다.
	final static String URL		="jdbc:oracle:thin:@127.0.0.1:1521:xe";	//driver와 url 경로 정확하게 입력하기.
	final static String USER	="scott";								//sql user id	
	final static String PASS	="tiger";			

	public void insertVideo(VideoVO vo, int count) throws Exception{
		// 1. 드라이버로딩
		Class.forName(DRIVER);
		System.out.println("드라이버 로딩 성공");

		// 2. Connection 연결객체 얻어오기
		Connection con=null;
		PreparedStatement ps=null;
		try {
			con=DriverManager.getConnection(URL,USER,PASS);	//DriverManager.getConnection 먼저 선언 후 url,user,password 순으로 설정하고 try catch 설정

			// 3. sql 문장 만들기
			String sql = "INSERT INTO VIDEO (VIDEONO, GENERE, TITLE, DIRECTOR, ACTOR, EXPL) "
					+ "VALUES (seq_video.NEXTVAL,?,?,?,?,?)";

			// 4. sql 전송객체 (PreparedStatement)
			ps=con.prepareStatement(sql);
			// ?세팅 **
			//ps.setString(1,vo.getVideoNo());
			ps.setString(1,vo.getGenre());
			ps.setString(2,vo.getTitle());
			ps.setString(3,vo.getDirector());
			ps.setString(4,vo.getActor());
			ps.setString(5,vo.getExpl());

			// 5. sql 전송
			for(int i=0; i<count; i++) {		//여러개의 입고가 들어오면 count로 수를 받고 그 수만큼 똑같은 정보를 업데이트 해주기 위한 for문
				ps.executeUpdate();				//select 일때만 query 쓰고 나머지는 update 쓰기
			}

		}finally{
			//6.닫기
			con.close();
			ps.close();
		}	
	}//insertVideo

	@Override
	public int updateVideo(VideoVO vo) throws Exception {
		//2.연결객체 얻어오기
		Connection con=null;
		PreparedStatement ps=null;

		try {
			con=DriverManager.getConnection(URL,USER,PASS);
			//3. SQL문장 만들기
			String sql="UPDATE VIDEO SET TITLE = ? , EXPL = ? , "
					+ " GENERE = ? , DIRECTOR = ? , ACTOR = ? "
					+ " WHERE VIDEONO = ?";

			//4. 전송객체 얻어오기
			ps=con.prepareStatement(sql);
			// ? 세팅 **
			ps.setString(1,vo.getTitle());
			ps.setString(2,vo.getExpl());
			ps.setString(3,vo.getGenre());
			ps.setString(4,vo.getDirector());
			ps.setString(5,vo.getActor());
			ps.setString(6,vo.getVideoNo());
			

			//5. 전송
			ps.executeUpdate();				//sql이 위에 들어가있으므로 다시 괄호 안에 넣으면 안됨


		}finally{
			//6.닫기
			ps.close();
			con.close();
		}//End Finally

		int result = 0;


		return result;
	}//updateVideo


	//selectVideo 비디오 검색 
	@Override
	public ArrayList selectVideo(String box,String text) throws Exception {
		ArrayList data=new ArrayList();

		//2. 연결객체 
		Connection con=null;
		PreparedStatement ps=null;
		try {
			con=DriverManager.getConnection(URL,USER,PASS);

			if(box.equals("제목")) {
				//3. sql
				//String []colNames= {"제목 컬럼명","감동 컬럼명"};
				//String sql="SELECT VIDEONO, TITLE, DIRECTOR, ACTOR FROM VIDEO "
				//" WHERE "+TITLE like colNames[idx]+" like '%"+word+"%'";
				//여기서 ? 대신 word 넣는 이유 : ?만 넣으면 '?' 이렇게 ''까지 포함되어서 setString이 먹히지 않음.

				String sql="SELECT VIDEONO, TITLE, DIRECTOR, ACTOR FROM VIDEO WHERE TITLE like '%' || ? || '%'";

				//4. 전송객체
				ps=con.prepareStatement(sql);
				ps.setString(1, text);

			}else if(box.equals("감독")) {
				//3. sql
				String sql="SELECT VIDEONO, TITLE, DIRECTOR, ACTOR FROM VIDEO WHERE DIRECTOR like '%' || ? || '%'";

				//4. 전송객체
				ps=con.prepareStatement(sql);
				ps.setString(1, text);

			}else {
				//3. sql
				String sql="SELECT VIDEONO, TITLE, DIRECTOR, ACTOR FROM VIDEO ";

				//4. 전송객체
				ps=con.prepareStatement(sql);

			}

			//5. 전송
			ResultSet rs=ps.executeQuery();
			while(rs.next()) {
				ArrayList temp = new ArrayList<>();
				temp.add(rs.getInt("videono"));
				temp.add(rs.getString("title"));
				temp.add(rs.getString("director"));
				temp.add(rs.getString("actor"));
				data.add(temp);
			}
		}finally{
			//6.닫기
			con.close();
			ps.close();
		}	
		return data;
	}//selectVideo

	@Override
	public VideoVO selectAll(int vNum) throws Exception {

		VideoVO vo = new VideoVO();

		// 2. 연결객체 얻어오기
		Connection con = null; 
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DriverManager.getConnection(URL,USER,PASS);

			// 3. sql 문장
			String sql = "SELECT * FROM video WHERE videono=?";

			// 4. 전송객체
			ps = con.prepareStatement(sql);
			ps.setInt(1, vNum);
			// 5. 전송
			rs = ps.executeQuery();
			while(rs.next()) {
				vo.setVideoNo(rs.getString("videono"));
				vo.setTitle(rs.getString("title"));
				vo.setGenre(rs.getString("genere"));
				vo.setDirector(rs.getString("director"));
				vo.setActor(rs.getString("actor"));
				vo.setExpl(rs.getString("expl"));
			}

			return vo;
		}finally {
			//6. 닫기
			rs.close();
			con.close();
		}
	}


	@Override
	public int delete(int vno) throws Exception {
		//INT로 받는 이유는 delete를 하게 되면 0,1값으로 값이 나오기 때문이다.

		//2.연결객체 얻어오기
		Connection con=null;
		PreparedStatement ps=null;

		try {
			con=DriverManager.getConnection(URL,USER,PASS);
			//3. SQL문장 만들기
			String sql="delete from Video where videono = ?";

			//4. 전송객체 얻어오기
			ps=con.prepareStatement(sql);
			ps.setInt(1, vno);

			//5. 전송
			ps.executeUpdate();				//sql이 위에 들어가있으므로 다시 괄호 안에 넣으면 안됨

		}finally{
			//6.닫기
			ps.close();
			con.close();
		}//End Finally
		return 0;							//0이나 1로 리턴한다.
	}



}




