package CLIENT;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;

import RemoteDesktop.Instruction;

public class SendEvents implements MouseInputListener, KeyListener
{
	private Socket soc = null;
	private double width = 0, height = 0;
	private PrintWriter writer = null;
	private JPanel panel = null;
	
	public SendEvents(Socket s, JPanel p, String w, String h)
	{
		try
		{
			soc = s;
			panel = p;
			width = Double.valueOf(w.trim());
			height = Double.valueOf(h.trim());
			
			writer = new PrintWriter(soc.getOutputStream());
			p.addKeyListener(this);
			p.addMouseMotionListener(this);
			p.addMouseListener(this);
			p.setFocusable(true);
			p.requestFocus(); 
		}
		catch(NumberFormatException numf)
		{
			numf.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	@Override
	public void mouseClicked(MouseEvent e)
	{
		
	}

	@Override
	public void mousePressed(MouseEvent e)
	{
		writer.println(Instruction.MOUSE_PRESS.getEvent());
		int button = e.getButton();
		writer.println(button);
		writer.flush();
	}

	@Override
	public void mouseReleased(MouseEvent e)
	{
		writer.println(Instruction.MOUSE_RELEASE.getEvent());
		int button = e.getButton();
		writer.println(button);
		writer.flush();
	}

	@Override
	public void mouseEntered(MouseEvent e)
	{
		
	}

	@Override
	public void mouseExited(MouseEvent e)
	{
		
	}

	@Override
	public void mouseDragged(MouseEvent e)
	{
		
	}

	@Override
	public void mouseMoved(MouseEvent e)
	{
		double wScale = (double)width/panel.getWidth();
		double hScale = (double)height/panel.getHeight();
		
		writer.println(Instruction.MOUSE_MOVE.getEvent());
		writer.println((int)e.getX()*wScale);
		writer.println((int)e.getY()*hScale);
		writer.flush();
	}
	@Override
	public void keyTyped(KeyEvent e)
	{
		
	}

	@Override
	public void keyPressed(KeyEvent e)
	{
		writer.println(Instruction.KEY_PRESS.getEvent());
		System.out.println(e.getKeyChar() + " " + e.getKeyCode());
		writer.println(e.getKeyCode());
		writer.flush();
	}

	@Override
	public void keyReleased(KeyEvent e)
	{
		writer.println(Instruction.KEY_RELEASE.getEvent());
		System.out.println(e.getKeyChar() + " " + e.getKeyCode());
		writer.println(e.getKeyCode());
		writer.flush();
	}

}
