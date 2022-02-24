package RemoteDesktop;

public enum Instruction
{
	MOUSE_PRESS(-1),
	MOUSE_RELEASE(-2),
	MOUSE_MOVE(-3),
	KEY_PRESS(-4),
	KEY_RELEASE(-5);
	
	private int currentEvent;
	
	Instruction(int c)
	{
		currentEvent = c;
	}
	
	public int getEvent()
	{
		return currentEvent;
	}
}
