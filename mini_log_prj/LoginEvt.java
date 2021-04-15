package project_1;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.JOptionPane;

/**
 * 로그인처리 이벤트 클래스
 * @author group1
 */
public class LoginEvt extends WindowAdapter implements ActionListener, KeyListener {

	private Login login;

	public LoginEvt(Login login) {
		this.login=login;
	}//LoginEvt
	
	
	@Override
	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource() == login.getJbLogin()) {//로그인 버튼이 눌리면
			try {
				checkAcceptable();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} // end if
	}//actionPerformed
	
	private void openLogAnaylsis() throws IOException {
		new LogAnalysis(login);
	}//openLogAnaylsis


	@Override
	public void keyPressed(KeyEvent ke) {
		if (ke.getSource()==login.getJtfId()) {
			int key=ke.getKeyCode();
			if (key==KeyEvent.VK_ENTER) {
				login.getJtfId().transferFocus();
			}//end if
		}//end if
		if (ke.getSource()==login.getJpfPw()) {
			int key=ke.getKeyCode();
			if (key==KeyEvent.VK_ENTER) {
				login.getJbLogin().doClick();
			}//end if
		}//end if
		
	}//keyPressed
	
	private void checkAcceptable() throws IOException {
		String id=login.getJtfId().getText();
		String password=new String(login.getJpfPw().getPassword());
		if (id.equals("admin")&&password.equals("1234")) {
			JOptionPane.showMessageDialog(login, "로그인 성공!");
			openLogAnaylsis();
		}else if (id.equals("root")&&password.equals("1111")) {
			JOptionPane.showMessageDialog(login, "로그인 성공!");
			openLogAnaylsis();
		}else {
			JOptionPane.showMessageDialog(login, "아이디나 비밀번호를 확인하세요.");
		}//end else
	}//checkAcceptable


	@Override
	public void windowClosing(WindowEvent we) {
		login.dispose();
	}// windowClosing
	
	
	@Override
	public void keyTyped(KeyEvent e) {
	}
	@Override
	public void keyReleased(KeyEvent e) {
	}


}//class_LoginEvt
