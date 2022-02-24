package CLIENT;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.IOException;
import static java.lang.Thread.sleep;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.plaf.DimensionUIResource;

public class ReceiveScreen extends Thread
{
//Height and Width sync class
	class Control
	{
		private JFrame frame = new JFrame("Remote Desktop");

		private JPanel cPanel = new JPanel();
	}

	final Control c = new Control();

	private static int portNo = 8087;
	static String ip = "14.180.73.45";
	DataInputStream verification = null;
	String width = "", height = "";

//Receive Screen Thread
	class T1 implements Runnable {
		@Override
		public void run() {
			while (true) {
				try {
					Socket serverSocket = new Socket(ip, portNo);
					BufferedImage img = ImageIO.read(serverSocket.getInputStream());
					c.cPanel.getGraphics().drawImage(img, 0, 0, c.cPanel.getWidth(), c.cPanel.getHeight(), null);
					serverSocket.close();
				}
				catch (IOException ex) {

				}
			}

		}
	}

	// Send Events Thread
	class T2 implements Runnable
	{
		@Override
		public void run()
		{
			try
			{
				Socket eve = new Socket(ip, 7749);
				verification = new DataInputStream(eve.getInputStream());
				width = verification.readUTF();
				height = verification.readUTF();

				new SendEvents(eve, c.cPanel, width, height);

			}
			catch (IOException ex)
			{
				Logger.getLogger(ReceiveScreen.class.getName()).log(Level.SEVERE, null, ex);
			}

		}
	}
	public ReceiveScreen(String ip)
	{
		this.ip = ip;
		c.frame = new JFrame();
		c.cPanel = new JPanel();

		c.frame.setSize(1930, 1050);
		c.cPanel.setSize(1930, 1050);
		c.frame.setExtendedState(Frame.MAXIMIZED_BOTH);
		c.frame.add(c.cPanel);
		//c.frame.setSize(new Dimension(500, 500));
		//c.frame.setMinimumSize(new Dimension(500, 500));
		c.frame.setVisible(true);

//Creating two Threads
		T1 t1 = new T1();
		T2 t2 = new T2();

//Starting Threads
		new Thread(t1).start();
		new Thread(t2).start();

	}

}