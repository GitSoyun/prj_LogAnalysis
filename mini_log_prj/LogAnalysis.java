package project_1;

import java.awt.Color;
import java.awt.Font;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 * �α����� ���� ��ư �ִ� â �α����� �ҷ����� ��ư JButton ->������ �Ʒ� JTF�� �����ּ� �̸����� ��� ���� �Է�
 * JTextField x2 View ��ư JButton -> ������ LogAnalysisViewWindow ���̾�α� ���� Report ��ư
 * JButton -> ������ LAViewWindow�� ���۵� ���� ���Ϸ� �����. (������ �� ���� ���� ���̾�α� ����)
 * @author group1
 */
@SuppressWarnings("serial")
public class LogAnalysis extends JFrame {
	
	public static final Font FontSetup = new Font("�������", Font.PLAIN, 15);

	private JButton jbtOpen, jbtView, jbtReport;
	private JTextField jtfAddress, jtfStart, jtfEnd;
	private JLabel jlbStart, jlbEnd, jlbTitle, jlbInfoOpen, jlbInfoLine;

	public LogAnalysis(Login login) throws IOException {
		super("�α׺м�App");

		// ����
		jlbTitle = new JLabel("LOG ANALYSIS");
		
		jlbInfoOpen = new JLabel("�� �м��� LOG ������ �����ϼ���.");
		jbtOpen = new JButton("OPEN");
		jtfAddress = new JTextField(200);
		
		jlbInfoLine = new JLabel("�� LINE �Է� �� �ش� LINE�� �м��մϴ�.");
		jlbStart = new JLabel("LINE");
		jtfStart = new JTextField(10);
		jlbEnd = new JLabel("~");
		jtfEnd = new JTextField(10);
		
		jbtView = new JButton("V I E W");
		jbtReport = new JButton("R E P O R T");
		
		
		// ������
		jlbTitle.setFont(new Font("�������", Font.BOLD, 50));
		jlbTitle.setForeground(new Color(0xFFFFFF));
		
		jlbInfoOpen.setFont(FontSetup);
		jbtOpen.setFont(FontSetup);
		jbtOpen.setBackground(new Color(0xFFFFFF));
		jbtOpen.setFocusPainted(false);

		jlbInfoLine.setFont(FontSetup);
		jlbStart.setFont(FontSetup);
		jlbEnd.setFont(FontSetup);
		
		jbtView.setBackground(new Color(0xFFFFFF));
		jbtView.setFont(FontSetup);
		jbtView.setFocusPainted(false);
		jbtReport.setBackground(new Color(0xFFFFFF));
		jbtReport.setFont(FontSetup);
		jbtReport.setFocusPainted(false);

		
		// �̺�Ʈ ó�� ��ü ����
		LAEvt laEvt = new LAEvt(this); //LAevt(�̺�Ʈó��class)�� ����
		jbtOpen.addActionListener(laEvt);
		jbtView.addActionListener(laEvt);
		jbtReport.addActionListener(laEvt);
		
		
		// ��ġ������ ����
		setLayout(null);
		jlbTitle.setBounds(45, 25, 400, 100);
		
		jlbInfoOpen.setBounds(60, 143, 300, 30);
		jbtOpen.setBounds(290, 143, 80, 30);
		jtfAddress.setBounds(67, 180, 300, 30);
		
		jlbInfoLine.setBounds(80, 245, 300, 25);
		jlbStart.setBounds(90, 280, 80, 30);
		jtfStart.setBounds(130, 280, 80, 30);
		jlbEnd.setBounds(223, 280, 80, 30);
		jtfEnd.setBounds(245, 280, 80, 30);
		
		jbtView.setBounds(50, 350, 150, 50);
		jbtReport.setBounds(233, 350, 150, 50);

		
		// ������Ʈ ��ġ
		add(jlbTitle);
		add(jlbInfoOpen);
		add(jbtOpen);
		add(jtfAddress);
		add(jlbInfoLine);
		add(jlbStart);
		add(jtfStart);
		add(jlbEnd);
		add(jtfEnd);
		add(jbtView);
		add(jbtReport);
		//add(lineView);
		
		// ������ ������
		getContentPane().setBackground(new Color(0x99CCFF)); //����
		setBounds(300, 300, 450, 500);
		setLocationRelativeTo(null); //��� ����
		setVisible(true);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}// LogAnalysis

	public JButton getJbtOpen() {
		return jbtOpen;
	}

	public JButton getJbtView() {
		return jbtView;
	}

	public JButton getJbtReport() {
		return jbtReport;
	}

	public JTextField getJtfAddress() {
		return jtfAddress;
	}

	public JTextField getJtfStart() {
		return jtfStart;
	}

	public JTextField getJtfEnd() {
		return jtfEnd;
	}

	
}// LogAnalysis
