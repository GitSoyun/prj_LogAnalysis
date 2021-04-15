package project_1;

import java.awt.Color;
import java.awt.Font;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 * 로그파일 관련 버튼 있는 창 로그파일 불러오기 버튼 JButton ->누르면 아래 JTF에 파일주소 미리보기 출력 범위 입력
 * JTextField x2 View 버튼 JButton -> 누르면 LogAnalysisViewWindow 다이얼로그 실행 Report 버튼
 * JButton -> 누르면 LAViewWindow에 전송될 값이 파일로 저장됨. (눌렀을 때 파일 저장 다이얼로그 실행)
 * @author group1
 */
@SuppressWarnings("serial")
public class LogAnalysis extends JFrame {
	
	public static final Font FontSetup = new Font("나눔고딕", Font.PLAIN, 15);

	private JButton jbtOpen, jbtView, jbtReport;
	private JTextField jtfAddress, jtfStart, jtfEnd;
	private JLabel jlbStart, jlbEnd, jlbTitle, jlbInfoOpen, jlbInfoLine;

	public LogAnalysis(Login login) throws IOException {
		super("로그분석App");

		// 생성
		jlbTitle = new JLabel("LOG ANALYSIS");
		
		jlbInfoOpen = new JLabel("◎ 분석할 LOG 파일을 선택하세요.");
		jbtOpen = new JButton("OPEN");
		jtfAddress = new JTextField(200);
		
		jlbInfoLine = new JLabel("◎ LINE 입력 시 해당 LINE만 분석합니다.");
		jlbStart = new JLabel("LINE");
		jtfStart = new JTextField(10);
		jlbEnd = new JLabel("~");
		jtfEnd = new JTextField(10);
		
		jbtView = new JButton("V I E W");
		jbtReport = new JButton("R E P O R T");
		
		
		// 디자인
		jlbTitle.setFont(new Font("나눔고딕", Font.BOLD, 50));
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

		
		// 이벤트 처리 객체 생성
		LAEvt laEvt = new LAEvt(this); //LAevt(이벤트처리class)와 연결
		jbtOpen.addActionListener(laEvt);
		jbtView.addActionListener(laEvt);
		jbtReport.addActionListener(laEvt);
		
		
		// 배치관리자 설정
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

		
		// 컴포넌트 배치
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
		
		// 윈도우 사이즈
		getContentPane().setBackground(new Color(0x99CCFF)); //배경색
		setBounds(300, 300, 450, 500);
		setLocationRelativeTo(null); //가운데 정렬
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
