package c_infoview;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class InfoView2 {

	//1. 멤버변수 선언
	JFrame f;	//새 창을 만들기 위한 선언
	JTextField tfname, tfId, tfTel, tfGender, tfAge, tfHome;
	//한 줄 입력 받을 때 사용
	JTextArea ta;
	//여러 줄 입력 받을 때 사용
	JButton bAdd, bShow, bSearch, bDelete, bCancel, bExit;
	//버튼 삽입
	JLabel la;
	//글씨만 한 줄 입력해야 할 때 사용
	infoModel model;
	//비즈니스 로직 연결

	//2. 멤버변수 객체생성
	InfoView2(){
		f=new JFrame("DBTest");		//새 창의 헤드부분에 나올 도메인
		tfname=new JTextField(10);	//한 줄 입력 받을 창 크기 선언
		tfId=new JTextField(10);
		tfTel=new JTextField(10);
		tfGender=new JTextField(10);
		tfAge=new JTextField(10);
		tfHome=new JTextField(10);
		ta=new JTextArea(40,20);			//여러 줄 입력 받을 창 
		bAdd=new JButton("Add");	//각 버튼들 
		bShow=new JButton("Show");
		bSearch=new JButton("Search");
		bDelete=new JButton("Delete");
		bCancel=new JButton("Cancel");
		bExit=new JButton("수정하기");

		try {
			model=new InfoModelImpl();			//try catch 구문으로 받아주기
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

	}

	//3. 화면 구성하고 출력
	/*
	 * 전체 프레임은 BorederLayout 지정 
	 * west : Jpannel 만들어서 Jlabel과 JTextField tfname, tfId, tfTel, tfGender, tfAge, tfHome; (GridLayout(6,2))
	 * center : JTextArea ta;
	 * south : Jpannel 만들어서 JButton bAdd, bShow, bSearch, bDelete, bCancel, bExit; (GridLayout(1,6))
	 */
	public void addLayout() {
		//Border 레이아웃으로 큰 창의 전체 레이아웃 먼저 설정
		f.setLayout(new BorderLayout());
		//붙히기 작업
		//하단 버튼 붙히기
		JPanel jpdown=new JPanel();		//버튼 올릴 패널 만들기
		jpdown.setLayout(new GridLayout(1,6));	//패널을 행,열 순서대로 나오게 배치 출력
		jpdown.add(bAdd);	//패널 위에 버튼 올리기
		jpdown.add(bShow);
		jpdown.add(bSearch);
		jpdown.add(bDelete);
		jpdown.add(bCancel);
		jpdown.add(bExit);
		f.add(jpdown,BorderLayout.SOUTH);	//보더 레이아웃의 아래쪽에 위치 선언

		//왼쪽 붙히기
		JPanel jpwest=new JPanel();		//입력 창과 입력 받을 창 올릴 패널 만들기
		jpwest.setLayout(new GridLayout(6,2));	//패널을 행,열 순서대로 나오게 배치 출력
		jpwest.add(new JLabel("Name",JLabel.CENTER));	//가운데 정렬하는 키 JLabel뒤 .center넣기
		jpwest.add(tfname);
		jpwest.add(new JLabel("ID",JLabel.CENTER));
		jpwest.add(tfId);
		jpwest.add(new JLabel("Tel",JLabel.CENTER));
		jpwest.add(tfTel);
		jpwest.add(new JLabel("Sex",JLabel.CENTER));
		jpwest.add(tfGender);
		jpwest.add(new JLabel("Age",JLabel.CENTER));
		jpwest.add(tfAge);
		jpwest.add(new JLabel("Home",JLabel.CENTER));
		jpwest.add(tfHome);
		f.add(jpwest, BorderLayout.WEST);		//보더 레이아웃의 왼쪽에 위치 선언

		//오른쪽 붙히기
		f.add(new JScrollPane(ta),BorderLayout.CENTER);		//보더 레이아웃의 가운데쪽에 위치 선언+스크롤 삽입

		f.setBounds(100,100,600,350);		//사이트의 크기 설정
		f.setVisible(true);					//true이기 때문에 프로그램이 보이게 작동함
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	//x키를 누르면 프로그램 자동 종료

	}//End addLayout

	public void eventProc() {			//이벤트 메소드 
		bAdd.addActionListener(new ActionListener(){						//이너 클래스, 이벤트는 이너클래스로 많이 작업한다.
			public void actionPerformed(ActionEvent e) {		  			//액션리스너 클래스는 추상클래스라서 메소드를 똑같이 상속 받아서 쓴다.
				insertdata();
			}	
		});//Add 버튼을 눌렀을 때 나오는 액션 설정

		bShow.addActionListener(new ActionListener(){						//이너 클래스, 이벤트는 이너클래스로 많이 작업한다.
			public void actionPerformed(ActionEvent e) {		  			//액션리스너 클래스는 추상클래스라서 메소드를 똑같이 상속 받아서 쓴다.
				selectAll(); 
			}	
		});//Show

		bSearch.addActionListener(new ActionListener(){						//이너 클래스, 이벤트는 이너클래스로 많이 작업한다.
			public void actionPerformed(ActionEvent e) {		  			//액션리스너 클래스는 추상클래스라서 메소드를 똑같이 상속 받아서 쓴다.
				selectByTel();
			}	
		});//Search

		tfTel.addActionListener(new ActionListener(){						//이너 클래스, 이벤트는 이너클래스로 많이 작업한다.
			public void actionPerformed(ActionEvent e) {		  			//액션리스너 클래스는 추상클래스라서 메소드를 똑같이 상속 받아서 쓴다.
				selectByTel();
			}	
		});//tfTel enter

		bDelete.addActionListener(new ActionListener(){						
			public void actionPerformed(ActionEvent e) {		  			
				deleteByTel();
			}	
		});	//End bDelete 

		bCancel.addActionListener(new ActionListener(){			
			public void actionPerformed(ActionEvent e) {		  			 
				clearText();		
			}	
		});	//End cancle

		bExit.addActionListener(new ActionListener(){						//이너 클래스, 이벤트는 이너클래스로 많이 작업한다.
			public void actionPerformed(ActionEvent e) {		  			//액션리스너 클래스는 추상클래스라서 메소드를 똑같이 상속 받아서 쓴다.
				JOptionPane.showMessageDialog(null, "수정이 완료되었습니다.");		//팝업 창 뜨면서 출력할 메세지 입력. 
				modify();
			}	
		});	
	}//End EventProc

	void selectAll() {
		try {
			ArrayList<InfoVO> data= model.selectAll();		//ArrayList 선언, model의 selectAll 메소드 값괴 연결
			ta.setText("---------검색 결과--------- \n \n");
			for(InfoVO vo : data) {							//향상된 for문으로 data받아서 설정하기
				ta.append(vo.toString());					//누적 데이터를 출력하기 위해 settext대신 append를 사용
			}
		} catch (SQLException e) {
			ta.setText("검색 실패 : "+e.getMessage());
		}
	}//End selectAll

	void insertdata() {
		// 1.사용자 입력값 얻어오기
		String name=tfname.getText();				//프레임 안의 창의 값 받아오기
		String id=tfId.getText();
		String gender=tfGender.getText();
		String tel=tfTel.getText();
		String home=tfHome.getText();
		int age=Integer.parseInt(tfAge.getText());

		// 2. 1번의 값들을 infoVO에 지정
		InfoVO vo=new InfoVO();
		vo.setName(name);							//get으로 받아온 값을 VO에 set으로 설정하기
		vo.setId(id);
		vo.setGender(gender);
		vo.setTel(tel);
		vo.setHome(home);
		vo.setAge(age);

		// 3. 모델의 insertInfo() 호출
		try {
			model.insertInfo(vo);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// 4. 화면의 입력값들을 지우기
		clearText();
	}

	void clearText() {
		tfname.setText(null);
		tfAge.setText(null);
		tfTel.setText(null);
		tfGender.setText(null);
		tfId.setText(null);
		tfHome.setText(null);

		//각 창의 모든 값을 지워버린다.
	}

	void selectByTel() {
		try {
			//1. 입력한 전봐번호 값을 얻어오기	
			String Tel=tfTel.getText();

			//2. 모델단에 selectByTel()호출
			InfoVO vo=model.selectByTel(Tel);

			//3. 받는 결과를 각각의 텍스트필드에 지정(출력)
			tfname.setText(vo.getName());
			tfId.setText(vo.getId());
			tfTel.setText(vo.getTel());
			tfGender.setText(vo.getGender());
			tfAge.setText(String.valueOf(vo.getAge()));
			tfHome.setText(vo.getHome());

		}catch(Exception ex) {
			ta.setText("전화번호 검색 실패 : "+ex.getMessage());
		}
	}//end selectByTel

	void deleteByTel() {
		//1. 입력한 전화번호 값을 얻어오기
		String Tel=tfTel.getText();
		//2. 모델단에 delete()호출
		try {
			model.delete(Tel);
			//3. 화면을 지우고
			clearText();
			ta.setText("삭제되었습니다.");
		} catch (SQLException e) {
			ta.setText("삭제 실패 : "+e.getMessage());
		}

	}//End deleteByTel

	void modify() {
		// 1.사용자 입력값 얻어오기
		String name=tfname.getText();
		String id=tfId.getText();
		String gender=tfGender.getText();
		String tel=tfTel.getText();
		String home=tfHome.getText();
		int age=Integer.parseInt(tfAge.getText());

		// 2. 1번의 값들을 infoVO에 지정
		InfoVO vo=new InfoVO();
		vo.setName(name);
		vo.setId(id);
		vo.setGender(gender);
		vo.setTel(tel);
		vo.setHome(home);
		vo.setAge(age);

		// 3. 모델의 modify() 호출
		try {
			model.modify(vo);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// 4. 화면의 입력값들을 지우기
		clearText();
	}
	public static void main(String[] args) {

		InfoView2 info=new InfoView2();	//위의 페이지를 부를 변수 선언
		info.addLayout();				//레이아웃이 보이게 함수 선언
		info.eventProc();

	}

}
