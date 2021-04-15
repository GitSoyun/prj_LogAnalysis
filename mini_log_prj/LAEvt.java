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

	String lResultString = ""; // 1~6번 문제 분석 결과를 넣어줄 문자열

	// log파일의 경로를 매개변수로 받는다.
	// Line도 매개변수로 받는다.
	// [0] String path : 로그파일의 경로
	// [1] String pLineStart : 라인 범위의 시작, 빈칸으로 들어오면 전체범위로 판단함.
	// [2] String pLineEnd : 라인 범위의 끝, 위와 마찬가지.

	public LAEvt(LogAnalysis pLa) {
		this.la = pLa;

	}// GroupWork1

	/**
	 * 사용자가 선택한 log파일을 List 배열에 넣고
	 * 1~6번 문제대로 분석해 lResultString(문자열)에 다 넣어줌.
	 * 문자열에 넣는 이유는 최종 문자열 그대로 View하거나 Report하기 위함.
	 * 
	 * @param path       : log파일의 경로
	 * @param pLineStart : line 범위를 정할 경우 시작 line
	 * @param pLineEnd   : line 범위를 정할 경우 끝 line
	 * @throws IOException
	 */
	public void setLogMethod(String path, String pLineStart, String pLineEnd) throws IOException {

		int lLineStart;
		int lLineEnd;

		lResultString = ""; // 분석할 때마다 초기화해줌. 분석 결과 누적 방지.

		// line에 값이 하나라도 입력되지 않으면 전체line 분석하게끔
		if (pLineStart == null || pLineEnd == null || pLineStart.equals("") || pLineEnd.equals("")) {
			lLineStart = 0;
			lLineEnd = 0;

		} else { // line 범위가 둘 다 있으면 지정한 범위만큼 매개변수에 넣어 분석
			try {
				lLineStart = Integer.parseInt(pLineStart);
				lLineEnd = Integer.parseInt(pLineEnd);

			} catch (NumberFormatException e) { // 범위에 숫자가 아닌 다른 값이 입력된 경우
				System.out.println("line 범위는 숫자를 입력해 주세요.");
				e.printStackTrace();
				return;
			} // catch
		} // else

		// 범위의 시작line이 끝line 숫자보다 더 작은 경우 
		if (lLineStart > lLineEnd) {
			return;
		}//if
		
		
		// txt파일 읽기
		File file = new File(path); // 다른 log파일을 넣을 경우 경로 변경 가능
		// 파일에 스트림 연결
		FileInputStream fis = new FileInputStream(file);
		// Charset을 Encoding하는 기능스트림 사용
		InputStreamReader isr = new InputStreamReader(fis);
		// 줄단위로 읽어들이는 기능 스트림 연결
		BufferedReader br = new BufferedReader(isr);
		
		//파일이 생성된 날짜
		BasicFileAttributes attrs =  Files.readAttributes(file.toPath(), BasicFileAttributes.class);
	    FileTime creationTime = attrs.creationTime();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String dateForm = sdf.format(new Date(creationTime.toMillis()));
		
		//View, Report할 내용 상단부분
		lResultString += "----------------------------------------------------------------------------\n";
		lResultString += "파일명(" + file.getName() + ")\n";
		lResultString += "(생성된 날짜 " + dateForm + " )\n"; 
		lResultString += "----------------------------------------------------------------------------\n\n";

		// log data를 [response][url][browser][date] 로 나눠서 담아줄 배열
		ArrayList<String> responseArr = new ArrayList<String>();
		ArrayList<String> urlArr = new ArrayList<String>();
		ArrayList<String> browserArr = new ArrayList<String>();
		ArrayList<String> dateArr = new ArrayList<String>();

		// Set<ValueType> : 중복 불가 => 어떤 종류가 있는지 구할 때 사용
		Set<String> responseSet = new HashSet<String>();
		Set<String> urlSet = new HashSet<String>();
		Set<String> browserSet = new HashSet<String>();
		Set<String> dateSet = new HashSet<String>();

		// Map<KeyType, ValueType> : 키만 중복 불가 => 종류를 키로 두고 각 키의 횟수를 구할 때 사용
		Map<String, Integer> responseMap = new HashMap<String, Integer>();
		Map<String, Integer> urlMap = new HashMap<String, Integer>();
		Map<String, Integer> browserMap = new HashMap<String, Integer>();
		Map<String, Integer> dateMap = new HashMap<String, Integer>();

		
		int lLineCount = 0; // 몇번째 line을 읽고있는지 카운트 하기 위한 변수
		String temp = ""; // 문자열을 넣기 위한 임시변수
		String str = ""; // 스트림 데이터를 읽기 위한 String

		// 한 line씩 읽기 : 읽은 라인을 아래 배열에 넣어주기 위함
		while ((str = br.readLine()) != null) {
			
			// 사용자가 Line 범위에 값을 입력했을 경우,
			if (!(pLineStart == null || pLineEnd == null || pLineStart.equals("") || pLineEnd.equals(""))) {
				// log파일을 한 line씩 읽으면서 지정한 범위가 아닌 줄은 무시하고 넘어가도록 처리(continue)
				if (!(lLineStart <= lLineCount && lLineEnd > lLineCount)) {
					lLineCount++; // 범위가 아닌 줄을 계속 카운트해야 몇 번째 줄인지 알 수 있음.
					continue; // 해당 반복부분은 넘어가고 다음번 반복을 이어서 하는 제어문
				}// if
			}// if

			// 사용자가 Line 범위에 값을 입력하지 않았을 경우(전체라인) 혹은 지정한 line 범위인 경우 
			// line의 데이터를 쪼개서 log배열에 넣기
			String[] log = str.split("]"); //split을 사용하면 해당 문자열은 자동으로 잘림.
			// "["도 잘라내기 위해 substring 사용
			// log[0] response
			temp = log[0].substring(1, log[0].length());
			// 가공완료된 데이터를 ArrayList와 Set에 넣어줌
			responseArr.add(temp);
			responseSet.add(temp);

			// log[1] url
			temp = log[1].substring(1, log[1].length());
			// url 중 key 값 잘라내기
			// key 값이 있다면
			if (temp.indexOf("key=") != -1) {
				// key=xxx& 에서 xxx만 뽑아내기
				temp = temp.substring(temp.indexOf("key=") + 4, temp.indexOf("&"));
				// 가공완료된 데이터를 ArrayList와 Set에 넣어줌
				urlArr.add(temp);
				urlSet.add(temp);

				// "key=" 혹은 key값이 아예 없는 경우
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
			// date 중 시간대만 잘라내기
//			temp = temp.substring(temp.indexOf(" "), temp.indexOf(":"));
			temp = temp.substring(11, temp.indexOf(":"));
			dateArr.add(temp);
			dateSet.add(temp);

			lLineCount++;
		} // while

		// Set은 검색이 불가능, 중복없이 검색하기 위해 Iterator에 제어권 할당
		Iterator<String> responseIta = responseSet.iterator();
		Iterator<String> urlIta = urlSet.iterator();
		Iterator<String> browserIta = browserSet.iterator();
		Iterator<String> dateIta = dateSet.iterator();

		
		
		// 1. 최다사용 키의 이름과 횟수 | java xx회

		// log 파일에서 종류, 횟수 뽑아내 map에 넣기
		int count = 0;

		while (urlIta.hasNext()) {
			count = 0; // 종류가 바뀔 때마다 초기화
			temp = urlIta.next(); // 종류를 하나하나씩 꺼내서 비교

			for (int j = 0; j < urlArr.size(); j++) {
				if (urlArr.get(j).equals(temp)) {
					count++;
				} // if
			} // for

			urlMap.put(temp, count); // key:종류, value:횟수

//			System.out.println("키:" + temp + " / 값:" + urlMap.get(temp));

		} // while

		// 횟수가 가장 많은 종류 구하기
		int num = 0; // 최고 값(횟수)를 구하기 위한 변수
		String maxKey = ""; // 최다사용 키(종류)

		// 위에서 이미 포인터가 맨끝까지 이동했기 때문에 검색을 위한 Iterator 재설정
		urlIta = urlSet.iterator();

		while (urlIta.hasNext()) {
			// 종류를 하나하나씩 꺼내서 비교
			temp = urlIta.next();

			// map.get(key) => key에 맞는 값 => 횟수
			// 최댓값이 나올때까지 key값(횟수)을 num에 넣어줌
			if (num < urlMap.get(temp)) {
				num = urlMap.get(temp);
				// 최댓값의 key(종류)를 maxKey 문자열에 넣어줌
				maxKey = temp;
			} // if
		} // while

//		System.out.println("1. 최다사용 키의 이름과 횟수  |  " + maxKey + " " + urlMap.get(maxKey) + "회");
		lResultString += "1. 최다 사용키 : " + maxKey + " " + urlMap.get(maxKey) + "회";

		
		
		// 2. 브라우저별 접속횟수, 비율\n IE - XX (XX%) ...

		int countBrowser = 0;
		String browserKey = "";

		lResultString += "\n\n2. 브라우저별 접속횟수, 비율";

		while (browserIta.hasNext()) {
			countBrowser = 0; // 종류가 바뀔 때마다 초기화
			temp = browserIta.next(); // 종류를 하나하나씩 꺼내서 비교

			for (int j = 0; j < browserArr.size(); j++) {
				if (browserArr.get(j).equals(temp)) {
					countBrowser++;
					browserKey = temp;
				} // if
			} // for

			browserMap.put(browserKey, countBrowser); // key:브라우저 종류, value:접속횟수

			// 비율 : 비율을 구할 값 / 전체 값 * 100
			// Arr.size()는 배열의 개수, 즉, 분석한 범위의 전체line수(전체 값)를 나타냄.
			String ratePattern = "0.##"; // 나타낼 소수점 자리 형식 지정
			DecimalFormat form = new DecimalFormat(ratePattern); 
			
			lResultString += "\n   " + browserKey + " - " + browserMap.get(browserKey) + "회 ("
			+ form.format((double)((double)browserMap.get(browserKey) / (double)browserArr.size()) * 100) + "%)";
		} // while
		
		
		
		// 3. 서비스를 성공적으로 수행한 횟수, 실패(404) 횟수
		// 200-성공, 404-실패(페이지없음)
		int countresponse = 0;
		String responseKey = "";

		lResultString += "\n\n3. 서비스를 성공적으로 수행한 횟수, 실패(404) 횟수";

		while (responseIta.hasNext()) {
			countresponse = 0; // 종류가 바뀔 때마다 초기화
			temp = responseIta.next(); // 종류를 하나하나씩 꺼내서 비교

			for (int j = 0; j < responseArr.size(); j++) {
				if (responseArr.get(j).equals(temp)) {
					countresponse++;
					responseKey = temp;
				} // if
			} // for

			responseMap.put(responseKey, countresponse); // key:응답결과 종류, value:응답횟수

		} // while
		
		// 위에서 이미 포인터가 맨끝까지 이동했기 때문에 검색을 위한 Iterator 재설정
		responseIta = responseSet.iterator();
		while (responseIta.hasNext()) {
			temp = responseIta.next();

			// 응답결과가 403인 경우 String에 넣지 않음, 결과 출력 되지 않게 => 403은 5번 문제
			if (temp.equals("403")) {
				continue;
			} // if

			lResultString += "\n   " + temp + " 횟수 - " + responseMap.get(temp) + "회";
		} // while

		
		
		// 4. 요청이 가장 많은 시간 [ XX시 ]
		// log 파일을 가공한 데이터List에서 시간대, 횟수 뽑아내 map에 넣기
		int countTime = 0;

		while (dateIta.hasNext()) {
			countTime = 0; // 종류가 바뀔 때마다 초기화
			temp = dateIta.next(); // 종류를 하나하나씩 꺼내서 비교

			for (int j = 0; j < dateArr.size(); j++) {
				if (dateArr.get(j).equals(temp)) {
					countTime++;
				} // if
			} // for

			dateMap.put(temp, countTime); // key:시간대, value:횟수

//			System.out.println("키:" + temp + " / 값:" + dateMap.get(temp));
		} // while

		// 횟수가 가장 많은 시간대 구하기
		int time = 0; // 최고 값(횟수)를 구하기 위한 변수
		String manyKey = ""; // 최다사용 키(시간대)

		// 위에서 이미 포인터가 맨끝까지 이동했기 때문에 검색을 위한 Iterator 재설정
		dateIta = dateSet.iterator();

		while (dateIta.hasNext()) {
			// 시간대(Key)를 하나하나씩 꺼내서 비교
			temp = dateIta.next();

			// map.get(key) => key에 맞는 값 => 횟수
			// 최댓값이 나올때까지 key값(횟수)을 time에 넣어줌
			if (time < dateMap.get(temp)) {
				time = dateMap.get(temp);
				// 최댓값의 key(시간대)를 manyKey 문자열에 넣어줌
				manyKey = temp;
			} // if
		} // while
		
		lResultString += "\n\n4. 요청이 가장 많은 시간 [ " + manyKey + "시 ]";

		
		
		// 5. 비정상적인 요청(403)이 발생한 횟수, 비율 구하기 => 3번 문제와 연결
		lResultString += "\n\n5. 비정상적인 요청(403)이 발생한 횟수, 비율";

		// Line 범위를 지정한 경우, 범위 내에 "403"key가 없을 경우 오류가 발생하므로 조건문 처리
		if (responseMap.get("403") == null) { 
			lResultString += "\n   비정상적인 요청(403) - 없음 (0.00%)";
			
		} else {
			// 비율 : 비율을 구할 값 / 전체 값 * 100
			// Arr.size()는 배열의 개수, 즉, 분석한 범위의 전체line수(전체 값)를 나타냄.
			String ratePattern = "0.##"; // 나타낼 소수점 자리 형식 지정
			DecimalFormat form = new DecimalFormat(ratePattern); 
			
			lResultString += "\n   비정상적인 요청(403) - " + responseMap.get("403") + "회 ("
			+ form.format((double)((double)responseMap.get("403") / (double)responseArr.size()) * 100) + "%)";
		} // else

		
		
		// 6. 입력되는 라인에 해당하는 정보 출력
		
		// 입력된 라인이 없을 경우 => 전체 출력
		if (pLineStart == null || pLineEnd == null || pLineStart.equals("") || pLineEnd.equals("")) {
			lResultString += "\n\n6. 이 분석 정보는 log의 전체 범위에 해당하는 정보입니다.";
			
			// 라인이 있을 경우 몇 번째 라인인지
		} else {
			lResultString += "\n\n6. 이 분석 정보는 " + pLineStart + "~" + pLineEnd + "번째 라인에 해당하는 정보입니다.\n";
		} // else

		// System.out.println(lResultString);

		// 스트림 끊기
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

		if (e.getSource() == la.getJbtOpen()) { // 파일오픈 버튼을 눌렀을 때

			FileDialog fdOpen = new FileDialog(la, "log 파일 선택", FileDialog.LOAD);
			fdOpen.setFile("*.log"); // 불러올 때 log 확장자 파일만 보이게 함
			fdOpen.setVisible(true);

			String dir = fdOpen.getDirectory(); // 폴더명
			String fileName = fdOpen.getFile(); // 파일명

			// 열지않고 취소했을 경우 "nullnull" 출력 방지
			if (dir != null) { // 폴더명이 얻어졌다면
				StringBuilder sb = new StringBuilder();
				sb.append(dir).append(fileName).append(fileName);
				la.getJtfAddress().setText(dir + fileName);
				// 불러온 log파일의 파일경로+이름으로 받아야 String에 받아 ArrayList에 넣을 수 있음.
			} // if
		} // end if

		if (e.getSource() == la.getJbtView()) {// View 버튼을 눌렀을 때

			// 파일경로 JTextField가 비어있다면(log 파일 경로가 없다면)
			if (la.getJtfAddress().getText().equals("") || la.getJtfAddress().getText() == "nullnull") {
				JOptionPane.showMessageDialog(la, "log 파일을 선택해 주세요.");
				return;
			} // if

			try {
				// 매개변수 3개(log파일경로, 시작라인, 끝라인) : 라인이 공백일 경우 전체범위 분석하게 처리해둠
				this.setLogMethod(la.getJtfAddress().getText(), la.getJtfStart().getText(), la.getJtfEnd().getText());

				// View Dialog를 띄움
				new LAViewWindow(this.getResultString());

			} catch (IOException ie) {
				ie.printStackTrace();
			} // catch
		} // end if

		if (e.getSource() == la.getJbtReport()) {// Report 버튼이 눌리면

			if (la.getJtfAddress().getText() == null || la.getJtfAddress().getText().equals("")
					|| la.getJtfAddress().getText() == "nullnull") {
				JOptionPane.showMessageDialog(la, "파일 경로를 선택해 주세요.");
				return;
			} // if

			// dat파일 생성
			try {
				this.setLogMethod(la.getJtfAddress().getText(), la.getJtfStart().getText(), la.getJtfEnd().getText());
				makeFileDat(this.getResultString());
				JOptionPane.showMessageDialog(la, "dat 파일이 c:/dev/report에 생성되었습니다.");

			} catch (IOException e1) {
				e1.printStackTrace();
			}

		} // end if
	}// actionPerformed

}// class
