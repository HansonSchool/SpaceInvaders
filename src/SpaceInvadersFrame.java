import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.plaf.basic.BasicMenuBarUI;



public class SpaceInvadersFrame extends JFrame implements ActionListener {

	SpaceInvadersPanel sip;
	
	public SpaceInvadersFrame() {
		super("Space Invaders!! ");
		setLayout(new BorderLayout());
		createMenus();
		this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
		sip = new SpaceInvadersPanel();
		this.add(sip);
		pack();
		this.validate();
	}

	
	private void createMenus() {
		// TODO Auto-generated method stub
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setVisible(true);
		JMenu fileMenu = new JMenu("File");
		JMenuItem newItem = new JMenuItem("New");
		
		newItem.addActionListener(this);
		
		fileMenu.add(newItem);
		menuBar.add(fileMenu);
		
		//menuBar.setUI(new BasicMenuBarUI());

		
		setJMenuBar(menuBar);
		//add(menuBar, BorderLayout.NORTH);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("you just clicked new...");
		//if(e.)
		startGame();
	}


	private void startGame() {
		// create a new Panel
		sip.start();
		sip.requestFocusInWindow();
	}
}
