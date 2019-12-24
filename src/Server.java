import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import javax.swing.*;

class ServerFrame implements ActionListener {
	JButton input_but; // �Է� ��ư
	JTextArea chat_record; // ä�� ȭ�� ���
	JTextField chat_input; // ���� �Է�
	
	ServerSocket serverSocket = null;
	Socket clientSocket = null;
	BufferedReader in = null;
	PrintWriter out = null;
	String inputMessage;
	String outputMessage;
	
	public ServerFrame() {
		JFrame frame = new JFrame("Server Chat");
		frame.setLocation(700, 100);
		frame.setPreferredSize(new Dimension(450, 800));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Container contentpane = frame.getContentPane();
		
		JPanel panel1 = new JPanel(); // ���� ���
		JLabel label = new JLabel("���� ä�� ȭ��");
		label.setFont(new Font("�߸���", Font.BOLD, 25));
		panel1.add(label);
		
		JPanel panel2 = new JPanel(); // ���� ȭ��
		chat_record = new JTextArea(35, 35);
		chat_input = new JTextField(30);
		input_but = new JButton("�Է�");
		panel2.add(new JScrollPane(chat_record)); // ��ũ�ѹ�
		panel2.add(chat_input);
		panel2.add(input_but);
		chat_record.setEditable(false); // ä�� ��� �κ� ä�� �Ұ�
		input_but.addActionListener(this);
		
		contentpane.add(panel1, BorderLayout.NORTH);
		contentpane.add(panel2, BorderLayout.CENTER);
		
		frame.pack();
		frame.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String s;
		s = "����: " + chat_input.getText();
		
		if(e.getSource() == input_but) {
			chat_record.append(s + "\n");
			out.println(s);
			chat_input.setText("");
		}
	}
	
	public void sStart() throws IOException {
		try {
			serverSocket = new ServerSocket(1357);
			clientSocket = serverSocket.accept();
		}
		catch(IOException e) {
			System.out.println(e.getMessage());
		}
		
		in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		out = new PrintWriter(clientSocket.getOutputStream(), true);
		
		outputMessage = "����: ���ӿ� �����߽��ϴ�.";
		out.println(outputMessage);
		chat_record.append("Ŭ���̾�Ʈ: ���ӿ� �����߽��ϴ�.\n");
		
		while(true) {
			inputMessage = in.readLine();
			if(inputMessage.equalsIgnoreCase("quit")) {
				chat_record.append("Ŭ���̾�Ʈ: ������ �����߽��ϴ�.\n");
				break;
			}
			
			inputMessage += "\n";
			chat_record.append(inputMessage);
		}
	}
}

public class Server {
	public static void main(String args[]) throws IOException {
		ServerFrame sFrame = new ServerFrame();
		sFrame.sStart();
	}
}
