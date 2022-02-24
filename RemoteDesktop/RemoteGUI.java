package RemoteDesktop;

import java.awt.EventQueue;
import CLIENT.ReceiveScreen;
import SERVER.Initconnect;
import SERVER.SendScreen;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.Label;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.SystemColor;
import java.awt.TextField;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Random;

import javax.swing.UIManager;

import java.awt.Font;
import java.awt.Color;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.awt.event.ActionEvent;

public class RemoteGUI {
	String verify = "";
	private Socket cSocket = null;
	String width = "", height = "";
	DataOutputStream psswrchk = null;
	DataInputStream verification = null;
	static int port =5001;
	private JFrame frame;
	static private JTextField txtMyIP;
	static private JTextField txtMyPassword;
	static private JTextField txtYourIP;
	static private JTextField txtYourPassword;

	private static char[] generatePassword(int length) {
		String capitalCaseLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String lowerCaseLetters = "abcdefghijklmnopqrstuvwxyz";
		String numbers = "1234567890";

		String combinedChars = capitalCaseLetters + lowerCaseLetters + numbers;
		Random random = new Random();
		char[] password = new char[length];

		for (int i = 0; i < length; i++) {
			password[i] = combinedChars.charAt(random.nextInt(combinedChars.length()));
		}

		return password;
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try
				{
					RemoteGUI window = new RemoteGUI();
					window.frame.setVisible(true);
					InetAddress IP = InetAddress.getLocalHost();
					String pString = String.copyValueOf(generatePassword(6));
					String x = IP.getHostAddress();
					txtMyIP.setText(x);
					txtMyPassword.setText(pString);
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public RemoteGUI() {
		initialize();
	}

	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 793, 450);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JPanel panel = new JPanel();
		panel.setBackground(new Color(175, 238, 238));
		panel.setBounds(0, 0, 301, 401);
		frame.getContentPane().add(panel);
		panel.setLayout(null);

		Label label = new Label("Allow Remote Control");
		label.setFont(new Font("Segoe UI", Font.BOLD, 12));
		label.setForeground(SystemColor.desktop);
		label.setBounds(61, 10, 181, 33);
		panel.add(label);

		Label label_2 = new Label("Your IP");
		label_2.setFont(new Font("Trebuchet MS", Font.ITALIC, 11));
		label_2.setBounds(37, 49, 62, 22);
		panel.add(label_2);

		Label label_4 = new Label("PassWord");
		label_4.setFont(new Font("Trebuchet MS", Font.ITALIC, 11));
		label_4.setBounds(37, 148, 62, 22);
		panel.add(label_4);

		txtMyIP = new JTextField();
		txtMyIP.setBounds(75, 102, 181, 40);
		panel.add(txtMyIP);
		txtMyIP.setColumns(10);
		txtMyIP.setEditable(false);

		txtMyPassword = new JTextField();
		txtMyPassword.setColumns(10);
		txtMyPassword.setBounds(75, 203, 181, 39);
		txtMyPassword.setEditable(false);
		panel.add(txtMyPassword);

		JButton btnAccept = new JButton("Accept");
		btnAccept.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				String value1 = txtMyPassword.getText();
				try
				{
					new Initconnect(port, value1);
				}
				catch (Exception e1)
				{
					e1.printStackTrace();
				}

			}
		});
		btnAccept.setFont(new Font("Segoe UI", Font.BOLD, 11));
		btnAccept.setBackground(new Color(127, 255, 212));
		btnAccept.setBounds(92, 275, 102, 40);
		panel.add(btnAccept);

		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(176, 224, 230));
		panel_1.setBounds(301, 0, 480, 401);
		frame.getContentPane().add(panel_1);
		panel_1.setLayout(null);

		Label label_1 = new Label("Control Remote Computer");
		label_1.setFont(new Font("Segoe UI", Font.BOLD, 12));
		label_1.setBounds(121, 25, 229, 39);
		panel_1.add(label_1);

		Label label_3 = new Label("Partner IP");
		label_3.setFont(new Font("Trebuchet MS", Font.ITALIC, 11));
		label_3.setBounds(109, 70, 86, 29);
		panel_1.add(label_3);

		Label label_5 = new Label("PassWord");
		label_5.setFont(new Font("Trebuchet MS", Font.ITALIC, 11));
		label_5.setBounds(109, 164, 86, 29);
		panel_1.add(label_5);

		JButton btnConnect = new JButton("Connect");
		btnConnect.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				String ip = "";
				try
				{
					ip = txtYourIP.getText();
					cSocket = new Socket(ip, port);
					String value2 = txtYourPassword.getText();
					psswrchk = new DataOutputStream(cSocket.getOutputStream());
					verification = new DataInputStream(cSocket.getInputStream());
					psswrchk.writeUTF(value2);
					verify = verification.readUTF();
				} 
				catch (IOException ee)
				{
					ee.printStackTrace();
				}

				if (verify.equals("valid"))
				{
					ReceiveScreen aScreen = new ReceiveScreen(ip);
				}
				else
				{
					JOptionPane.showMessageDialog(frame, "Incorrect password", "Error Title",JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnConnect.setBackground(new Color(127, 255, 212));
		btnConnect.setFont(new Font("Segoe UI", Font.BOLD, 11));
		btnConnect.setBounds(198, 274, 112, 39);
		panel_1.add(btnConnect);

		txtYourIP = new JTextField();
		txtYourIP.setColumns(10);
		txtYourIP.setBounds(161, 119, 200, 39);
		panel_1.add(txtYourIP);

		txtYourPassword = new JTextField();
		txtYourPassword.setColumns(10);
		txtYourPassword.setBounds(161, 203, 200, 39);
		panel_1.add(txtYourPassword);
	}

}