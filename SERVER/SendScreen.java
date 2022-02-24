package SERVER;
 
import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.net.*;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
 
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
 
public class SendScreen extends Thread
{
    public Thread Server_Thread_1 = null;
    public Thread Server_Thread_2 = null;
    static  DataOutputStream verify = null;
   

    public void run()
    {
        Server_Thread_1.start();
        Server_Thread_2.start();
    }
    public SendScreen() throws IOException, SQLException, ClassNotFoundException, Exception
    {
        this.Server_Thread_1 = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    Robot rob = new Robot();
                    Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
                  
                    while (true)
                    {
	                    ServerSocket soc = new ServerSocket(8087);
	                    Socket so = soc.accept();
                        
                        BufferedImage img = rob.createScreenCapture(new Rectangle(0, 0, (int) d.getWidth(), (int) d.getHeight()));
 
                        ByteArrayOutputStream ous = new ByteArrayOutputStream();
                        ImageIO.write(img, "png", ous);
                        so.getOutputStream().write(ous.toByteArray());
                        soc.close();
                        try
                        {
                            Thread.sleep(66);
                        }
                        catch (Exception e)
                        {
                        	
                        }
                    }
                }
                catch (Exception e)
                {
             
                }
            }
        });
        		
        		
        this.Server_Thread_2 = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    Robot rob = new Robot();
                    Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
                    String width=""+d.getWidth();
        			String height=""+d.getHeight();
        			ServerSocket  soc = new ServerSocket(7749);
                    while (true)
                    { 
                       Socket  so = soc.accept();
                       verify=new DataOutputStream(so.getOutputStream());
                       verify.writeUTF(width);
   					   verify.writeUTF(height);
                       new ReceiveEvents(so, rob); 
                    }
                }
                catch (Exception e)
                {
                	e.printStackTrace();
                }
            }
        });
    }
}