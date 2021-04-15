package project_1;

import java.awt.Color;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 * RunProgram 실행시 실행되는 로그인창 ID입력 JTextField (엔터 누르면 PW칸으로 이동) PW입력
 * JPasswordField (엔터 누르면 로그인 버튼 실행) 로그인버튼 JButton (둘 다 엔터 안눌러도 버튼 클릭시 실행)
 * @author group1
 */
@SuppressWarnings("serial")
public class Login extends JFrame {
	
	public static final Font FontSetup = new Font("나눔고딕", Font.PLAIN, 15); //폰트설정 constant

	private JLabel jlbId, jlbPw, jlbMain;
	private JTextField jtfId;
	private JPasswordField jpfPw;
	private JButton jbLogin;

	public Login() {
		super("LOGIN");
//		System.out.println("로그인");

		// 생성
		ImageIcon Img = new ImageIcon("C:\\dev\\workspace\\GroupProject_1\\img\\loginImg.png"); //경로 확인 필수
		jlbMain = new JLabel(Img);
		jlbId = new JLabel("I D");
		jlbPw = new JLabel("PW");
		jtfId = new JTextField(20);
		jpfPw = new JPasswordField(20);
		jbLogin = new JButton("L O G I N");
		
		//디자인
		jbLogin.setFocusPainted(false); //버튼 속 글자 테두리 제거
		jbLogin.setBackground(new Color(0xFFFFFF));
		jlbId.setFont(FontSetup);
		jlbPw.setFont(FontSetup);
		jbLogin.setFont(FontSetup);
		
		//이벤트 처리 객체 생성
		LoginEvt levt=new LoginEvt(this);
		jbLogin.addActionListener(levt);
		jtfId.addKeyListener(levt);
		jpfPw.addKeyListener(levt);
		
		// 배치관리자 설정
		setLayout(null);
		jlbMain.setBounds(0, 0, 330, 190);
		jlbId.setBounds(95, 202, 30, 30);
		jtfId.setBounds(93, 225, 150, 30);
		jlbPw.setBounds(95, 262, 30, 30);
		jpfPw.setBounds(93, 285, 150, 30);
		jbLogin.setBounds(73, 350, 190, 50);

		// 컴포넌트 배치
		add(jlbMain);
		add(jlbId);
		add(jlbPw);
		add(jtfId);
		add(jpfPw);
		add(jbLogin);
		
		getContentPane().setBackground(new Color(0x99CCFF)); //배경색
		setBounds(300, 300, 350, 500);
		setLocationRelativeTo(null); //창 해상도에 관계없이 가운데 고정
		setResizable(false); //창 사이즈 조절 불가
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}// Login

	public JTextField getJtfId() {
		return jtfId;
	}//getJtfId
	public JPasswordField getJpfPw() {
		return jpfPw;
	}//getJpfPw
	public JButton getJbLogin() {
		return jbLogin;
	}//getJbLogin
	
	
}// class_Login
