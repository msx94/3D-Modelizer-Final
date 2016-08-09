package inter;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

public class MultiActions extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6396610014201471706L;
	
	private String actionType;
	private Window window;
	
	public MultiActions() {
		
	}
	
	public MultiActions(String text) {
		super(text);
	}
	
	public MultiActions(Window window, String actionType, String text) {
		super(text);
		
		this.actionType = actionType;
		this.setWindow(window);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		switch (actionType) {
			case "load file" :

				break;

			case "load folder" :
				
				break;
				
			case "exit" :
				System.exit(0);
				break;
				
		}
		
	}

	public String getActionType() {
		return this.actionType;
	}
	
	public void setActionType(String actionType) {
		this.actionType = actionType;
	}
	
	public Window getWindow() {
		return window;
	}

	public void setWindow(Window window) {
		this.window = window;
	}

	
	
}
