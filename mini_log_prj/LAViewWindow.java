package project_1;

import java.awt.Color;

import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

@SuppressWarnings("serial")
public class LAViewWindow extends JDialog {

	public LAViewWindow(String pStr) {
		// 생성
		JTextArea jtaLogDisplay = new JTextArea();
		jtaLogDisplay.setEditable(false); //JTextArea 안에 입력 안 되게 막음
		
		jtaLogDisplay.append(pStr); //1~6 출력내용
		
		JScrollPane jsp = new JScrollPane(jtaLogDisplay); // 스크롤바
		
		// 배치
		add(jsp);

		// 윈도우 설정
		getContentPane().setBackground(new Color(0x99CCFF)); //배경색
		setBounds(400, 300, 450, 530);
		setLocationRelativeTo(null); //창 해상도에 관계없이 가운데 고정
		setResizable(false); //창 사이즈 조절 불가
		setVisible(true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE); //JDialog는 DISPOSE_ON_CLOSE
	}// LAViewWindow

}// class LAViewWindow
