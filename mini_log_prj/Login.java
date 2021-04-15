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
 * RunProgram ����� ����Ǵ� �α���â ID�Է� JTextField (���� ������ PWĭ���� �̵�) PW�Է�
 * JPasswordField (���� ������ �α��� ��ư ����) �α��ι�ư JButton (�� �� ���� �ȴ����� ��ư Ŭ���� ����)
 * @author group1
 */
@SuppressWarnings("serial")
public class Login extends JFrame {
	
	public static final Font FontSetup = new Font("�������", Font.PLAIN, 15); //��Ʈ���� constant

	private JLabel jlbId, jlbPw, jlbMain;
	private JTextField jtfId;
	private JPasswordField jpfPw;
	private JButton jbLogin;

	public Login() {
		super("LOGIN");
//		System.out.println("�α���");

		// ����
		ImageIcon Img = new ImageIcon("C:\\dev\\workspace\\GroupProject_1\\img\\loginImg.png"); //��� Ȯ�� �ʼ�
		jlbMain = new JLabel(Img);
		jlbId = new JLabel("I D");
		jlbPw = new JLabel("PW");
		jtfId = new JTextField(20);
		jpfPw = new JPasswordField(20);
		jbLogin = new JButton("L O G I N");
		
		//������
		jbLogin.setFocusPainted(false); //��ư �� ���� �׵θ� ����
		jbLogin.setBackground(new Color(0xFFFFFF));
		jlbId.setFont(FontSetup);
		jlbPw.setFont(FontSetup);
		jbLogin.setFont(FontSetup);
		
		//�̺�Ʈ ó�� ��ü ����
		LoginEvt levt=new LoginEvt(this);
		jbLogin.addActionListener(levt);
		jtfId.addKeyListener(levt);
		jpfPw.addKeyListener(levt);
		
		// ��ġ������ ����
		setLayout(null);
		jlbMain.setBounds(0, 0, 330, 190);
		jlbId.setBounds(95, 202, 30, 30);
		jtfId.setBounds(93, 225, 150, 30);
		jlbPw.setBounds(95, 262, 30, 30);
		jpfPw.setBounds(93, 285, 150, 30);
		jbLogin.setBounds(73, 350, 190, 50);

		// ������Ʈ ��ġ
		add(jlbMain);
		add(jlbId);
		add(jlbPw);
		add(jtfId);
		add(jpfPw);
		add(jbLogin);
		
		getContentPane().setBackground(new Color(0x99CCFF)); //����
		setBounds(300, 300, 350, 500);
		setLocationRelativeTo(null); //â �ػ󵵿� ������� ��� ����
		setResizable(false); //â ������ ���� �Ұ�
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
