package SERVER;
 
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;
 
import javax.swing.event.MouseInputListener;


import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.event.InputEvent;
import RemoteDesktop.Instruction;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
 
public class ReceiveEvents
{
    Socket soc = null;
    Robot robot = null;

    public ReceiveEvents(Socket soc, Robot robot)
    {
        this.soc = soc;
        this.robot = robot;
        new ProcessReceive(soc, robot).start();
    }
}
class ProcessReceive extends Thread
{
    Socket soc = null;
    Robot robot = null;

    public ProcessReceive(Socket soc, Robot robot)
    {
        this.soc = soc;
        this.robot = robot;
    }

    public void run()
    {
        Scanner sc = null;
        try
        {
            sc = new Scanner(soc.getInputStream());
            while(true)
            {
            	int typeEvent=0;
            	try
            	{
            		typeEvent = sc.nextInt();
            		System.out.println(typeEvent);
            	}
            	catch (NoSuchElementException e)
            	{
            		break;
            	}
            	
            	 
                switch (typeEvent)
                {
                    case -1: // MOUSE_PRESS
                    {
                        int button = sc.nextInt();
                        int buttonMask = InputEvent.getMaskForButton(button);
                        robot.mousePress(buttonMask);
                        break;
                    }
                    case -2: // MOUSE_RELEASE
                    {
                        int button = sc.nextInt();
                        int buttonMask = InputEvent.getMaskForButton(button);
                        robot.mouseRelease(buttonMask);
                        break;
                    }
                    case -3:
                    {

                        robot.mouseMove((int)(sc.nextDouble()),(int)(sc.nextDouble()));
                        break;
                    }
                    case -4:
                    {
                        robot.keyPress(sc.nextInt());
                        break;

                    }
                    case -5:
                    {
                        robot.keyRelease(sc.nextInt());
                        break;
                    }
                    default:
                        throw new IllegalArgumentException("Unexpected value: " + typeEvent);
                }
            }
        }
        catch (Exception e)
        {
            
        }
    }
}