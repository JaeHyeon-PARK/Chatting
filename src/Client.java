import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import javax.swing.*;

class ClientFrame implements ActionListener {
	JButton input_but; // 입력 버튼
	JTextArea chat_record; // 채팅 화면 출력
	JTextField chat_input; // 문장 입력
	
	BufferedReader in = null;
	PrintWriter out = null;
	String inputMessage;
	String outputMessage;
	
	public ClientFrame() {
		JFrame frame = new JFrame("Client Chat");
		frame.setLocation(700, 100);
		frame.setPreferredSize(new Dimension(450, 800));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Container contentpane = frame.getContentPane();
		
		JPanel panel1 = new JPanel(); // 제목 출력
		JLabel label = new JLabel("클라이언트 채팅 화면");
		label.setFont(new Font("견명조", Font.BOLD, 25));
		panel1.add(label);
		
		JPanel panel2 = new JPanel(); // 메인 화면
		chat_record = new JTextArea(35, 35);
		chat_input = new JTextField(30);
		input_but = new JButton("입력");
		panel2.add(new JScrollPane(chat_record)); // 스크롤바
		panel2.add(chat_input);
		panel2.add(input_but);
		chat_record.setEditable(false); // 채팅 기록 부분 채팅 불가
		input_but.addActionListener(this);
		
		contentpane.add(panel1, BorderLayout.NORTH);
		contentpane.add(panel2, BorderLayout.CENTER);
		
		frame.pack();
		frame.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String s;
		s = "클라이언트: " + chat_input.getText();
		
		if(e.getSource() == input_but) {
			chat_record.append(s + "\n");
			out.println(s);
			chat_input.setText("");
		}
	}
	
	public void cStart() throws IOException {
		Socket clientSocket = null;
		
		try {
			clientSocket = new Socket("222.104.225.157", 1357);

			in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			out = new PrintWriter(clientSocket.getOutputStream(), true);
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
		while(true) {
			inputMessage = in.readLine();
			if(inputMessage.equalsIgnoreCase("quit")) {
				chat_record.append("클라이언트: 접속을 종료했습니다.");
				break;
			}
			
			inputMessage += "\n";
			chat_record.append(inputMessage);
		}
	}
}

public class Client {
	public static void main(String args[]) throws IOException{
		ClientFrame cFrame = new ClientFrame();
		cFrame.cStart();
	}
}
