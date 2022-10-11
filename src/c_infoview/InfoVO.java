package c_infoview;

//DB의 값을 저장하는 클래스 주로 VO라고 쓴다.

public class InfoVO {

		private String name;
		private String id;
		private String tel;
		private String gender;
		private int age;
		private String home;
		//함부러 지정값을 바꾸지 못하게 private으로 설정한다.
		
		//생성자
		public InfoVO() {	}	//기본 생성자
		
		//인자가 있는 생성자(마우스 오른쪽 클릭> source> generate constructor using field 클릭)
		public InfoVO(String name, String id, String tel, String gender, int age, String home) {
			super();
			this.name = name;
			this.id = id;
			this.tel = tel;
			this.gender = gender;
			this.age = age;
			this.home = home;
		}

		//출력 문구 (마우스 오른쪽 클릭> source> generate to String() 클릭)
		public String toString() {
			return "InfoVO [name=" + name + ", id=" + id + ", tel=" + tel + ", gender=" + gender + ", age=" + age
					+ ", home=" + home + "] \n";
		}
		
		//게터, 세터 (마우스 오른쪽 클릭> source> generate getter, setter 클릭)
		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getTel() {
			return tel;
		}
		public void setTel(String tel) {
			this.tel = tel;
		}
		public String getGender() {
			return gender;
		}
		public void setGender(String gender) {
			this.gender = gender;
		}
		public int getAge() {
			return age;
		}
		public void setAge(int age) {
			this.age = age;
		}
		public String getHome() {
			return home;
		}
		public void setHome(String home) {
			this.home = home;
		}

}
