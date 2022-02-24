package SERVER;

import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GraphicsDevice;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.*;


public class Initconnect
{
	ServerSocket socket = null;
	DataInputStream password = null;
	DataOutputStream verify = null;

	public Initconnect(int port, String value1)
	{
		Robot robot = null;
		Rectangle rectangle = null;
		try
		{
			System.out.println("Awaiting Connection from Client");
			socket = new ServerSocket(port);
			
			while (true)
			{
				Socket sc = socket.accept();
				password = new DataInputStream(sc.getInputStream());
				verify = new DataOutputStream(sc.getOutputStream());

				String pssword = password.readUTF();

				if (pssword.equals(value1)){
					verify.writeUTF("valid");
					new SendScreen().start();
					socket.close();
				}
				else
				{
					verify.writeUTF("Invalid");
				}
			}
		} catch (Exception ex) {
			
		}
	}

}