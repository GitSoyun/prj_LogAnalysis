package project_1;

import java.awt.Color;

import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

@SuppressWarnings("serial")
public class LAViewWindow extends JDialog {

	public LAViewWindow(String pStr) {
		// ����
		JTextArea jtaLogDisplay = new JTextArea();
		jtaLogDisplay.setEditable(false); //JTextArea �ȿ� �Է� �� �ǰ� ����
		
		jtaLogDisplay.append(pStr); //1~6 ��³���
		
		JScrollPane jsp = new JScrollPane(jtaLogDisplay); // ��ũ�ѹ�
		
		// ��ġ
		add(jsp);

		// ������ ����
		getContentPane().setBackground(new Color(0x99CCFF)); //����
		setBounds(400, 300, 450, 530);
		setLocationRelativeTo(null); //â �ػ󵵿� ������� ��� ����
		setResizable(false); //â ������ ���� �Ұ�
		setVisible(true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE); //JDialog�� DISPOSE_ON_CLOSE
	}// LAViewWindow

}// class LAViewWindow
