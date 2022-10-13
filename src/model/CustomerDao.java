package model;

import java.sql.SQLException;

import model.vo.CustomerVO;

/** 고객관리 JDBC 연결 **/
//가장 먼저 인터페이스로 메인 함수들 설정하고 클래스 짜기
public interface CustomerDao {
	public void insertCustomer(CustomerVO vo) throws Exception;		// 회원가입
	public CustomerVO selectByTel(String tel) throws Exception;		// 전화번호로 검색
	public int updateCustomer(CustomerVO vo) throws Exception;		// 고객정보 수정
	int update(CustomerVO vo) throws  Exception;						// 삭제
}
