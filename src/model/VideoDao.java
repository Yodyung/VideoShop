package model;

import java.util.ArrayList;

import model.vo.VideoVO;

public interface VideoDao {
	public void insertVideo(VideoVO vo, int count) throws Exception;	//비디오 입고
	public int updateVideo(VideoVO vo) throws Exception;				//비디오 정보 수정
	public ArrayList selectVideo(String box,String text) throws Exception;	//제목으로 검색
	public VideoVO selectAll(int vNum) throws Exception;				// 선택한 칸에 해당되는 number의 비디오 정보 출력
	int delete(int vno) throws  Exception;								// 삭제
}
