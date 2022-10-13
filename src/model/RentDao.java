package model;

import java.util.ArrayList;

public interface RentDao {
	
	//대여
	public void rentvideo(String tel, int vnum) throws Exception;

	//반납
	public void returnVideo(int vnum) throws Exception;
	
	//미납목록 검색
	public ArrayList overdue() throws Exception;
	
	//전화번호입력후 엔터치면 이름 뜨게
	public String searchName(String tel) throws Exception;
}
