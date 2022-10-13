package  view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.table.AbstractTableModel;

import model.RentDao;
import model.dao.RentDaoImpl;
import model.vo.CustomerVO;



public class RentView extends JPanel 
{
	JTextField tfRentTel, tfRentCustName, tfRentVideoNum;
	JButton bRent;

	JTextField tfReturnVideoNum;
	JButton bReturn;

	JTable tableRecentList;

	RentTableModel rentTM;

	//비즈니스 로직
	RentDao model;



	//==============================================
	//	 생성자 함수
	public RentView(){
		addLayout();	//화면구성
		eventProc();  	
		connectDB();  //DB연결
		selectList(); //대여목록
	}

	// DB 연결 
	void connectDB(){
		try {
			model=new RentDaoImpl();
		} catch (Exception e) {
			System.out.println("rent DB 로딩 실패 :"+e.getMessage());
			e.printStackTrace();
		}


	}


	/*	화면 구성   */
	void addLayout(){
		// 멤버변수 객체 생성
		tfRentTel = new JTextField(20);
		tfRentCustName = new JTextField(20);
		tfRentVideoNum = new JTextField(20);
		tfReturnVideoNum = new JTextField(10);

		bRent = new JButton("대여");
		bReturn = new JButton("반납");

		tableRecentList = new JTable();

		rentTM=new RentTableModel();
		tableRecentList = new JTable(rentTM);

		// ************* 화면구성 *****************
		// 화면의 윗쪽
		JPanel p_north = new JPanel();
		p_north.setLayout(new GridLayout(1,2));
		// 화면 윗쪽의 왼쪽
		JPanel p_north_1 = new JPanel();
		p_north_1.setBorder(new TitledBorder("대		여"));
		p_north_1.setLayout(new GridLayout(4,2));
		p_north_1.add(new JLabel("전 화 번 호"));
		p_north_1.add(tfRentTel);
		p_north_1.add(new JLabel("고 객 명"));
		p_north_1.add(tfRentCustName);
		p_north_1.add(new JLabel("비디오 번호"));
		p_north_1.add(tfRentVideoNum);
		p_north_1.add(bRent);



		// 화면 윗쪽의 오른쪽
		JPanel p_north_2 = new JPanel();	
		p_north_2.setBorder(new TitledBorder("반		납"));
		p_north_2.add(new JLabel("비디오 번호"));
		p_north_2.add(tfReturnVideoNum);
		p_north_2.add(bReturn);

		//
		setLayout(new BorderLayout());
		add(p_north, BorderLayout.NORTH);
		add(new JScrollPane(tableRecentList),BorderLayout.CENTER);


		p_north.add(p_north_1);
		p_north.add(p_north_2);
	}

	//미납 목록 검색
	void selectList() {
		try {
			rentTM.data=model.overdue();
			rentTM.fireTableDataChanged();
		} catch (Exception e) {
			System.out.println("미납 목록 검색 실패 :"+e.getMessage());
			e.printStackTrace();
		}
	}//End

	class RentTableModel extends AbstractTableModel { 

		ArrayList data = new ArrayList();
		String [] columnNames = {"비디오번호","제목","고객명","전화번호","반납예정일","반납여부"};

		public int getColumnCount() { 
			return columnNames.length; 
		} 

		public int getRowCount() { 
			return data.size(); 
		} 

		public Object getValueAt(int row, int col) { 
			ArrayList temp = (ArrayList)data.get( row );
			return temp.get( col ); 
		}

		public String getColumnName(int col){
			return columnNames[col];
		}
	}

	// 이벤트 등록
	public void eventProc(){
		ButtonEventHandler btnHandler = new ButtonEventHandler();

		tfRentTel.addActionListener(btnHandler);
		bRent.addActionListener(btnHandler);
		bReturn.addActionListener(btnHandler);


		tableRecentList.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent ev){

				try{
					int row = tableRecentList.getSelectedRow();
					int col = 0;	// 검색한 열을 클릭했을 때 클릭한 열의 비디오번호

					Integer vNum = (Integer)(tableRecentList.getValueAt(row, col));
					// 그 열의 비디오번호를 tfReturnVideoNum 에 띄우기
					tfReturnVideoNum.setText(vNum.toString());

				}catch(Exception ex){
					System.out.println("실패 : "+ ex.getMessage());
				}

			}
		});

	}

	// 이벤트 핸들러
	class ButtonEventHandler implements ActionListener{
		public void actionPerformed(ActionEvent ev){
			Object o = ev.getSource();

			if(o==tfRentTel){  			// 전화번호 엔터
				rentSelectTel();				
			}
			else if(o==bRent){  		// 대여 클릭
				rentClick();
				selectList(); 			//대여목록
			}
			else if(o==bReturn){  		// 반납 클릭
				returnClick();
				selectList(); 			//대여목록
			}

		}


	}

	// 반납버튼 눌렀을 때 
	public void returnClick(){
		int vnum=Integer.parseInt(tfReturnVideoNum.getText());		//tf 창 값은 string이라 int로 변환 후 받아준다.
		CustomerVO vo=new CustomerVO();								//VO 클래스 열어주기

		try {
			model.returnVideo(vnum);								//model에서 returnVideo() 메소드 불러온 후 위의 int 값으로 설정
			tfRentCustName.setText(vo.getName());					//tf 이름 창에 vo에 저장되어 있는 이름을 불러와 set한다.
			JOptionPane.showMessageDialog(null, "반납 완료");
		} catch (Exception e) {										//오류 시 잡아줄 구문 
			System.out.println("비디오 반납 오류 :"+e.getMessage());
			e.printStackTrace();
		}

	}

	// 대여 버튼 눌렀을 때 
	public void rentClick(){
		String tel=tfRentTel.getText();								//tf tel의 전화번호를 가져옴
		int vnum=Integer.parseInt(tfRentVideoNum.getText());		//tf num의 대여번호를 가져옴(int로 항변환)

		try {
			model.rentvideo(tel, vnum);								//model impl에서 rentvideo 메소드를 불러와 위에 선언한 변수들로 인자 넣기 
			JOptionPane.showMessageDialog(null, "대여 완료");			//대여 성공시 출력할 메세지 설정
		} catch (Exception e) {
			System.out.println("대여 처리 오류 :"+e.getMessage());
			e.printStackTrace();
		}

	}

	// 전화번호입력후 엔터
	public void rentSelectTel(){
		String tel=tfRentTel.getText();								//tf tel의 전화번호를 가져옴

		try {														//model impl에서 searchName 메소드를 불러와 위에 선언한 변수로 인자 넣기
			String name=model.searchName(tel);						//인자를 저장해 줄 변수 설정 (실제로 창에 출력해야하기 때문에 변수 설정해주어야 함)
			tfRentCustName.setText(name);							//설정해준 변수를 tf 이름창에 set하기
			JOptionPane.showMessageDialog(null, "확인 중 입니다.");
		} catch (Exception e) {
			System.out.println("전화번호 탐색 오류 :"+e.getMessage());
			e.printStackTrace();
		}
	}//End 



}
