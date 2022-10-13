package model.vo;
//Customer 정보를 담아둘 VO클래스를 만들기 

public class CustomerVO {		//아무나 값을 건드릴 수 없게 프라이빗으로 변수를 선언한다.
	private String pNo;			//전화번호
	private String name;		//이름
	private String s_Pno;		//서브 전화번호
	private String addr;		//주소
	private String email;		//이메일


	public CustomerVO() {		//기본 생성자 함수

	}
	//변수가 있는 생성자를 만들어 나중에 변수에 값을 넣어 불러쓸 수 있게 한다.
	public CustomerVO(String pNo, String name, String s_Pno, String addr, String email) {
		super();
		this.pNo = pNo;
		this.name = name;
		this.s_Pno = s_Pno;
		this.addr = addr;
		this.email = email;
	}

	//TO STRING으로 출력 구문 만들어 놓기
	@Override
	public String toString() {
		return "InfoVo [pNo=" + pNo + ", name=" + name + ", s_Pno=" + s_Pno + ", addr=" + addr + ", email=" + email
				+ "]\n";
	}

	//GETTER SETTER 설정해두기 
	public String getpNo() {
		return pNo;
	}
	public void setpNo(String pNo) {
		this.pNo = pNo;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getS_Pno() {
		return s_Pno;
	}
	public void setS_Pno(String s_Pno) {
		this.s_Pno = s_Pno;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
}
