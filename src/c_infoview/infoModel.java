package c_infoview;

import java.sql.SQLException;
import java.util.ArrayList;

public interface infoModel {
	//원래 인터페이스를 먼저 만들고 이 인터페이스를 구현하는 클래스를 만들어야 함.
	//순서로 따지면 가장 먼저 만들어야 하는 페이지.

	/*
	 * 사용자 입력값을 받아서 DB에 저장하는 역할
	 */
	void insertInfo(InfoVO vo) throws SQLException;		//혹시나 오류가 났을 때를 대비해 오류를 던짐

	/*
	 * 전체 info_tab의 레코드 검색
	 */
	ArrayList<InfoVO> selectAll() throws SQLException;	//리턴 값을 정확히 지정해주고 클래스 만들어야함.
	
	/*
	 * 전화번호를 넘겨받아서 해당하는 사람의 정보를 검색
	 */
	InfoVO selectByTel(String tel) throws SQLException;

	/*
	 * 전화번호를 넘겨받아서 해당하는 사람의 정보를 삭제
	 */
	int delete(String tel) throws SQLException;
	
	/*
	 * 전화번호를 넘겨받아서 해당하는 사람의 정보를 수정
	 */
	void modify(InfoVO vo) throws SQLException;

}