package project_1;

import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.swing.JOptionPane;

public class LAEvt extends WindowAdapter implements ActionListener {

	LogAnalysis la;

	String lResultString = ""; // 1~6�� ���� �м� ����� �־��� ���ڿ�

	// log������ ��θ� �Ű������� �޴´�.
	// Line�� �Ű������� �޴´�.
	// [0] String path : �α������� ���
	// [1] String pLineStart : ���� ������ ����, ��ĭ���� ������ ��ü������ �Ǵ���.
	// [2] String pLineEnd : ���� ������ ��, ���� ��������.

	public LAEvt(LogAnalysis pLa) {
		this.la = pLa;

	}// GroupWork1

	/**
	 * ����ڰ� ������ log������ List �迭�� �ְ�
	 * 1~6�� ������� �м��� lResultString(���ڿ�)�� �� �־���.
	 * ���ڿ��� �ִ� ������ ���� ���ڿ� �״�� View�ϰų� Report�ϱ� ����.
	 * 
	 * @param path       : log������ ���
	 * @param pLineStart : line ������ ���� ��� ���� line
	 * @param pLineEnd   : line ������ ���� ��� �� line
	 * @throws IOException
	 */
	public void setLogMethod(String path, String pLineStart, String pLineEnd) throws IOException {

		int lLineStart;
		int lLineEnd;

		lResultString = ""; // �м��� ������ �ʱ�ȭ����. �м� ��� ���� ����.

		// line�� ���� �ϳ��� �Էµ��� ������ ��üline �м��ϰԲ�
		if (pLineStart == null || pLineEnd == null || pLineStart.equals("") || pLineEnd.equals("")) {
			lLineStart = 0;
			lLineEnd = 0;

		} else { // line ������ �� �� ������ ������ ������ŭ �Ű������� �־� �м�
			try {
				lLineStart = Integer.parseInt(pLineStart);
				lLineEnd = Integer.parseInt(pLineEnd);

			} catch (NumberFormatException e) { // ������ ���ڰ� �ƴ� �ٸ� ���� �Էµ� ���
				System.out.println("line ������ ���ڸ� �Է��� �ּ���.");
				e.printStackTrace();
				return;
			} // catch
		} // else

		// ������ ����line�� ��line ���ں��� �� ���� ��� 
		if (lLineStart > lLineEnd) {
			return;
		}//if
		
		
		// txt���� �б�
		File file = new File(path); // �ٸ� log������ ���� ��� ��� ���� ����
		// ���Ͽ� ��Ʈ�� ����
		FileInputStream fis = new FileInputStream(file);
		// Charset�� Encoding�ϴ� ��ɽ�Ʈ�� ���
		InputStreamReader isr = new InputStreamReader(fis);
		// �ٴ����� �о���̴� ��� ��Ʈ�� ����
		BufferedReader br = new BufferedReader(isr);
		
		//������ ������ ��¥
		BasicFileAttributes attrs =  Files.readAttributes(file.toPath(), BasicFileAttributes.class);
	    FileTime creationTime = attrs.creationTime();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String dateForm = sdf.format(new Date(creationTime.toMillis()));
		
		//View, Report�� ���� ��ܺκ�
		lResultString += "----------------------------------------------------------------------------\n";
		lResultString += "���ϸ�(" + file.getName() + ")\n";
		lResultString += "(������ ��¥ " + dateForm + " )\n"; 
		lResultString += "----------------------------------------------------------------------------\n\n";

		// log data�� [response][url][browser][date] �� ������ ����� �迭
		ArrayList<String> responseArr = new ArrayList<String>();
		ArrayList<String> urlArr = new ArrayList<String>();
		ArrayList<String> browserArr = new ArrayList<String>();
		ArrayList<String> dateArr = new ArrayList<String>();

		// Set<ValueType> : �ߺ� �Ұ� => � ������ �ִ��� ���� �� ���
		Set<String> responseSet = new HashSet<String>();
		Set<String> urlSet = new HashSet<String>();
		Set<String> browserSet = new HashSet<String>();
		Set<String> dateSet = new HashSet<String>();

		// Map<KeyType, ValueType> : Ű�� �ߺ� �Ұ� => ������ Ű�� �ΰ� �� Ű�� Ƚ���� ���� �� ���
		Map<String, Integer> responseMap = new HashMap<String, Integer>();
		Map<String, Integer> urlMap = new HashMap<String, Integer>();
		Map<String, Integer> browserMap = new HashMap<String, Integer>();
		Map<String, Integer> dateMap = new HashMap<String, Integer>();

		
		int lLineCount = 0; // ���° line�� �а��ִ��� ī��Ʈ �ϱ� ���� ����
		String temp = ""; // ���ڿ��� �ֱ� ���� �ӽú���
		String str = ""; // ��Ʈ�� �����͸� �б� ���� String

		// �� line�� �б� : ���� ������ �Ʒ� �迭�� �־��ֱ� ����
		while ((str = br.readLine()) != null) {
			
			// ����ڰ� Line ������ ���� �Է����� ���,
			if (!(pLineStart == null || pLineEnd == null || pLineStart.equals("") || pLineEnd.equals(""))) {
				// log������ �� line�� �����鼭 ������ ������ �ƴ� ���� �����ϰ� �Ѿ���� ó��(continue)
				if (!(lLineStart <= lLineCount && lLineEnd > lLineCount)) {
					lLineCount++; // ������ �ƴ� ���� ��� ī��Ʈ�ؾ� �� ��° ������ �� �� ����.
					continue; // �ش� �ݺ��κ��� �Ѿ�� ������ �ݺ��� �̾ �ϴ� ���
				}// if
			}// if

			// ����ڰ� Line ������ ���� �Է����� �ʾ��� ���(��ü����) Ȥ�� ������ line ������ ��� 
			// line�� �����͸� �ɰ��� log�迭�� �ֱ�
			String[] log = str.split("]"); //split�� ����ϸ� �ش� ���ڿ��� �ڵ����� �߸�.
			// "["�� �߶󳻱� ���� substring ���
			// log[0] response
			temp = log[0].substring(1, log[0].length());
			// �����Ϸ�� �����͸� ArrayList�� Set�� �־���
			responseArr.add(temp);
			responseSet.add(temp);

			// log[1] url
			temp = log[1].substring(1, log[1].length());
			// url �� key �� �߶󳻱�
			// key ���� �ִٸ�
			if (temp.indexOf("key=") != -1) {
				// key=xxx& ���� xxx�� �̾Ƴ���
				temp = temp.substring(temp.indexOf("key=") + 4, temp.indexOf("&"));
				// �����Ϸ�� �����͸� ArrayList�� Set�� �־���
				urlArr.add(temp);
				urlSet.add(temp);

				// "key=" Ȥ�� key���� �ƿ� ���� ���
			} else {
				urlArr.add(""); // null
				urlSet.add("");
			} // else

			// log[2] browser
			temp = log[2].substring(1, log[2].length());
			browserArr.add(temp);
			browserSet.add(temp);

			// log[3] date
			temp = log[3].substring(1, log[3].length());
			// date �� �ð��븸 �߶󳻱�
//			temp = temp.substring(temp.indexOf(" "), temp.indexOf(":"));
			temp = temp.substring(11, temp.indexOf(":"));
			dateArr.add(temp);
			dateSet.add(temp);

			lLineCount++;
		} // while

		// Set�� �˻��� �Ұ���, �ߺ����� �˻��ϱ� ���� Iterator�� ����� �Ҵ�
		Iterator<String> responseIta = responseSet.iterator();
		Iterator<String> urlIta = urlSet.iterator();
		Iterator<String> browserIta = browserSet.iterator();
		Iterator<String> dateIta = dateSet.iterator();

		
		
		// 1. �ִٻ�� Ű�� �̸��� Ƚ�� | java xxȸ

		// log ���Ͽ��� ����, Ƚ�� �̾Ƴ� map�� �ֱ�
		int count = 0;

		while (urlIta.hasNext()) {
			count = 0; // ������ �ٲ� ������ �ʱ�ȭ
			temp = urlIta.next(); // ������ �ϳ��ϳ��� ������ ��

			for (int j = 0; j < urlArr.size(); j++) {
				if (urlArr.get(j).equals(temp)) {
					count++;
				} // if
			} // for

			urlMap.put(temp, count); // key:����, value:Ƚ��

//			System.out.println("Ű:" + temp + " / ��:" + urlMap.get(temp));

		} // while

		// Ƚ���� ���� ���� ���� ���ϱ�
		int num = 0; // �ְ� ��(Ƚ��)�� ���ϱ� ���� ����
		String maxKey = ""; // �ִٻ�� Ű(����)

		// ������ �̹� �����Ͱ� �ǳ����� �̵��߱� ������ �˻��� ���� Iterator �缳��
		urlIta = urlSet.iterator();

		while (urlIta.hasNext()) {
			// ������ �ϳ��ϳ��� ������ ��
			temp = urlIta.next();

			// map.get(key) => key�� �´� �� => Ƚ��
			// �ִ��� ���ö����� key��(Ƚ��)�� num�� �־���
			if (num < urlMap.get(temp)) {
				num = urlMap.get(temp);
				// �ִ��� key(����)�� maxKey ���ڿ��� �־���
				maxKey = temp;
			} // if
		} // while

//		System.out.println("1. �ִٻ�� Ű�� �̸��� Ƚ��  |  " + maxKey + " " + urlMap.get(maxKey) + "ȸ");
		lResultString += "1. �ִ� ���Ű : " + maxKey + " " + urlMap.get(maxKey) + "ȸ";

		
		
		// 2. �������� ����Ƚ��, ����\n IE - XX (XX%) ...

		int countBrowser = 0;
		String browserKey = "";

		lResultString += "\n\n2. �������� ����Ƚ��, ����";

		while (browserIta.hasNext()) {
			countBrowser = 0; // ������ �ٲ� ������ �ʱ�ȭ
			temp = browserIta.next(); // ������ �ϳ��ϳ��� ������ ��

			for (int j = 0; j < browserArr.size(); j++) {
				if (browserArr.get(j).equals(temp)) {
					countBrowser++;
					browserKey = temp;
				} // if
			} // for

			browserMap.put(browserKey, countBrowser); // key:������ ����, value:����Ƚ��

			// ���� : ������ ���� �� / ��ü �� * 100
			// Arr.size()�� �迭�� ����, ��, �м��� ������ ��üline��(��ü ��)�� ��Ÿ��.
			String ratePattern = "0.##"; // ��Ÿ�� �Ҽ��� �ڸ� ���� ����
			DecimalFormat form = new DecimalFormat(ratePattern); 
			
			lResultString += "\n   " + browserKey + " - " + browserMap.get(browserKey) + "ȸ ("
			+ form.format((double)((double)browserMap.get(browserKey) / (double)browserArr.size()) * 100) + "%)";
		} // while
		
		
		
		// 3. ���񽺸� ���������� ������ Ƚ��, ����(404) Ƚ��
		// 200-����, 404-����(����������)
		int countresponse = 0;
		String responseKey = "";

		lResultString += "\n\n3. ���񽺸� ���������� ������ Ƚ��, ����(404) Ƚ��";

		while (responseIta.hasNext()) {
			countresponse = 0; // ������ �ٲ� ������ �ʱ�ȭ
			temp = responseIta.next(); // ������ �ϳ��ϳ��� ������ ��

			for (int j = 0; j < responseArr.size(); j++) {
				if (responseArr.get(j).equals(temp)) {
					countresponse++;
					responseKey = temp;
				} // if
			} // for

			responseMap.put(responseKey, countresponse); // key:������ ����, value:����Ƚ��

		} // while
		
		// ������ �̹� �����Ͱ� �ǳ����� �̵��߱� ������ �˻��� ���� Iterator �缳��
		responseIta = responseSet.iterator();
		while (responseIta.hasNext()) {
			temp = responseIta.next();

			// �������� 403�� ��� String�� ���� ����, ��� ��� ���� �ʰ� => 403�� 5�� ����
			if (temp.equals("403")) {
				continue;
			} // if

			lResultString += "\n   " + temp + " Ƚ�� - " + responseMap.get(temp) + "ȸ";
		} // while

		
		
		// 4. ��û�� ���� ���� �ð� [ XX�� ]
		// log ������ ������ ������List���� �ð���, Ƚ�� �̾Ƴ� map�� �ֱ�
		int countTime = 0;

		while (dateIta.hasNext()) {
			countTime = 0; // ������ �ٲ� ������ �ʱ�ȭ
			temp = dateIta.next(); // ������ �ϳ��ϳ��� ������ ��

			for (int j = 0; j < dateArr.size(); j++) {
				if (dateArr.get(j).equals(temp)) {
					countTime++;
				} // if
			} // for

			dateMap.put(temp, countTime); // key:�ð���, value:Ƚ��

//			System.out.println("Ű:" + temp + " / ��:" + dateMap.get(temp));
		} // while

		// Ƚ���� ���� ���� �ð��� ���ϱ�
		int time = 0; // �ְ� ��(Ƚ��)�� ���ϱ� ���� ����
		String manyKey = ""; // �ִٻ�� Ű(�ð���)

		// ������ �̹� �����Ͱ� �ǳ����� �̵��߱� ������ �˻��� ���� Iterator �缳��
		dateIta = dateSet.iterator();

		while (dateIta.hasNext()) {
			// �ð���(Key)�� �ϳ��ϳ��� ������ ��
			temp = dateIta.next();

			// map.get(key) => key�� �´� �� => Ƚ��
			// �ִ��� ���ö����� key��(Ƚ��)�� time�� �־���
			if (time < dateMap.get(temp)) {
				time = dateMap.get(temp);
				// �ִ��� key(�ð���)�� manyKey ���ڿ��� �־���
				manyKey = temp;
			} // if
		} // while
		
		lResultString += "\n\n4. ��û�� ���� ���� �ð� [ " + manyKey + "�� ]";

		
		
		// 5. ���������� ��û(403)�� �߻��� Ƚ��, ���� ���ϱ� => 3�� ������ ����
		lResultString += "\n\n5. ���������� ��û(403)�� �߻��� Ƚ��, ����";

		// Line ������ ������ ���, ���� ���� "403"key�� ���� ��� ������ �߻��ϹǷ� ���ǹ� ó��
		if (responseMap.get("403") == null) { 
			lResultString += "\n   ���������� ��û(403) - ���� (0.00%)";
			
		} else {
			// ���� : ������ ���� �� / ��ü �� * 100
			// Arr.size()�� �迭�� ����, ��, �м��� ������ ��üline��(��ü ��)�� ��Ÿ��.
			String ratePattern = "0.##"; // ��Ÿ�� �Ҽ��� �ڸ� ���� ����
			DecimalFormat form = new DecimalFormat(ratePattern); 
			
			lResultString += "\n   ���������� ��û(403) - " + responseMap.get("403") + "ȸ ("
			+ form.format((double)((double)responseMap.get("403") / (double)responseArr.size()) * 100) + "%)";
		} // else

		
		
		// 6. �ԷµǴ� ���ο� �ش��ϴ� ���� ���
		
		// �Էµ� ������ ���� ��� => ��ü ���
		if (pLineStart == null || pLineEnd == null || pLineStart.equals("") || pLineEnd.equals("")) {
			lResultString += "\n\n6. �� �м� ������ log�� ��ü ������ �ش��ϴ� �����Դϴ�.";
			
			// ������ ���� ��� �� ��° ��������
		} else {
			lResultString += "\n\n6. �� �м� ������ " + pLineStart + "~" + pLineEnd + "��° ���ο� �ش��ϴ� �����Դϴ�.\n";
		} // else

		// System.out.println(lResultString);

		// ��Ʈ�� ����
		br.close();
	}// setLogMethod

	
	public String getResultString() {
		return this.lResultString;
	}// getResultString

	public void makeFileDat(String fileReport) throws IOException {
		StringBuilder sb = new StringBuilder();
		SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
		Date date = new Date();
		String dateForm = sdf.format(date);

		sb.append("report_").append(dateForm).append(".dat");
		File file = new File("C:/dev/report/");
		file.mkdirs();

		BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new FileWriter("C:/dev/report/" + sb.toString()));
			bw.write(fileReport);
			bw.flush();
		} finally {
			if (bw != null) {
				bw.close();
			} // end if
		} // end finally
	}// makeFileDat

	
	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == la.getJbtOpen()) { // ���Ͽ��� ��ư�� ������ ��

			FileDialog fdOpen = new FileDialog(la, "log ���� ����", FileDialog.LOAD);
			fdOpen.setFile("*.log"); // �ҷ��� �� log Ȯ���� ���ϸ� ���̰� ��
			fdOpen.setVisible(true);

			String dir = fdOpen.getDirectory(); // ������
			String fileName = fdOpen.getFile(); // ���ϸ�

			// �����ʰ� ������� ��� "nullnull" ��� ����
			if (dir != null) { // �������� ������ٸ�
				StringBuilder sb = new StringBuilder();
				sb.append(dir).append(fileName).append(fileName);
				la.getJtfAddress().setText(dir + fileName);
				// �ҷ��� log������ ���ϰ��+�̸����� �޾ƾ� String�� �޾� ArrayList�� ���� �� ����.
			} // if
		} // end if

		if (e.getSource() == la.getJbtView()) {// View ��ư�� ������ ��

			// ���ϰ�� JTextField�� ����ִٸ�(log ���� ��ΰ� ���ٸ�)
			if (la.getJtfAddress().getText().equals("") || la.getJtfAddress().getText() == "nullnull") {
				JOptionPane.showMessageDialog(la, "log ������ ������ �ּ���.");
				return;
			} // if

			try {
				// �Ű����� 3��(log���ϰ��, ���۶���, ������) : ������ ������ ��� ��ü���� �м��ϰ� ó���ص�
				this.setLogMethod(la.getJtfAddress().getText(), la.getJtfStart().getText(), la.getJtfEnd().getText());

				// View Dialog�� ���
				new LAViewWindow(this.getResultString());

			} catch (IOException ie) {
				ie.printStackTrace();
			} // catch
		} // end if

		if (e.getSource() == la.getJbtReport()) {// Report ��ư�� ������

			if (la.getJtfAddress().getText() == null || la.getJtfAddress().getText().equals("")
					|| la.getJtfAddress().getText() == "nullnull") {
				JOptionPane.showMessageDialog(la, "���� ��θ� ������ �ּ���.");
				return;
			} // if

			// dat���� ����
			try {
				this.setLogMethod(la.getJtfAddress().getText(), la.getJtfStart().getText(), la.getJtfEnd().getText());
				makeFileDat(this.getResultString());
				JOptionPane.showMessageDialog(la, "dat ������ c:/dev/report�� �����Ǿ����ϴ�.");

			} catch (IOException e1) {
				e1.printStackTrace();
			}

		} // end if
	}// actionPerformed

}// class
