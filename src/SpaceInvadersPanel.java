import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.Timer;


public class SpaceInvadersPanel extends JPanel implements ActionListener {
	int shot = 0;
	private static final int DEF_START_X = 100;// starting x-coord of ship
	private static final int DEF_START_Y = 700;// starting y-coord of ship
	private static final int START_LIVES = 5;
	private static final double PROB_ALIEN_SHOT = .02;
	DefenderShip defShip;
	private final int PREFERRED_W = 1000, PREFERRED_H = 800;
	//List<AlienShip> aliens = new ArrayList<AlienShip>();// list of aliens
	Army badGuys;
	List<Bullet> alienBullets = new ArrayList<Bullet>(),// list of alienbullets
			     defenderBullets = new ArrayList<Bullet>();
	
	List<Shield> shieldList = new ArrayList<Shield>();
	
	ClipPlayer cp = new ClipPlayer();
	Timer gameTimer;
	
	
	public SpaceInvadersPanel() {
		this.setPreferredSize(new Dimension(PREFERRED_W,PREFERRED_H));
		gameTimer = new Timer(10, this);
		reset();// this sets up all the entities in the game
		setVisible(true);
		this.setUpKeyBindings();
		setUpSoundMappings();
		this.setBackground(Color.black);
		GameObject.panel= this;
	}

	private void setUpSoundMappings() {
		cp.mapFile("game started", "annoying.mid");
		cp.mapFile("shoot", "bang.wav");
		cp.mapFile("shoot2", "bang2.wav");
		cp.mapFile("shoot3", "photon.wav");
		cp.mapFile("hit", "ouch.wav");
	//	cp.mapFile("fastship", "redalert.mp3");// prob with mp3...
		cp.mapFile("dead", "ShipDead.wav");
	}

	private void setUpAliens() {
		badGuys = new Army(this.DEF_START_X, 100, 5, 10, 30, 4);
		
	}

	private void setUpDefender() {
		resetDefenderLoc();
		this.defShip.setLives(START_LIVES);
	}
	private void resetDefenderLoc() {
		if(defShip == null)
			defShip = new DefenderShip(DEF_START_X, DEF_START_Y);
		this.defShip.setLocation(DEF_START_X, DEF_START_Y);
		this.defShip.stop();
	}

	private void setUpShields() {
		int x = DEF_START_X+defShip.getW();
		int y = DEF_START_Y - defShip.getH();
		int width = PREFERRED_W;
		int numShields = 4;
		for(int i = 0; i<numShields; i++) {
			Shield s = new Shield(x, y, 0, 0);// w and h not used...
			shieldList.add(s);
			x+=width/(numShields+1);
			//System.out.println(s);
		}
		
	}
	
	
	private void setUpKeyBindings() {
		this.getInputMap().put(KeyStroke.getKeyStroke("F2"),
				"new game");
		
		
		this.getInputMap().put(KeyStroke.getKeyStroke("SPACE"),
				"fire");
		this.getInputMap().put(KeyStroke.getKeyStroke("RIGHT"),
				"right");
		this.getInputMap().put(KeyStroke.getKeyStroke("released RIGHT"),
				"stop");
		this.getInputMap().put(KeyStroke.getKeyStroke("released LEFT"),
				"stop");
		this.getInputMap().put(KeyStroke.getKeyStroke("LEFT"),
				"left");
		
		this.getActionMap().put("new game",
				new AbstractAction() {
					@Override
					public void actionPerformed(ActionEvent e) {
						start();
						repaint();
						cp.play("game started");
					}
		});
		this.getActionMap().put("fire",
				new AbstractAction() {
					

					@Override
					public void actionPerformed(ActionEvent e) {
						launchWeapon();
						
//						if(shot%3==0) {
//							cp.play("shoot");
//							System.out.println("Just shot "+1);
//						}
//						else if(shot%3==1) {
//							cp.play("shoot2");
//							System.out.println("Just shot "+2);
//						}
//						else {
							//cp.play("shoot3");
//							System.out.println("Just shot "+3);
//						}
							
						repaint();
					//	shot++;
					}
		});
		this.getActionMap().put("right",
				new AbstractAction() {
					@Override
					public void actionPerformed(ActionEvent e) {
						setDefenderDir(1);// 1 moves right, 0 moves left
						repaint();
					}
			
		});
		this.getActionMap().put("stop",
				new AbstractAction() {
					@Override
					public void actionPerformed(ActionEvent e) {
						setDefenderDir(2);// 1 moves right, 0 moves left
						repaint();
					}
			
		});
		this.getActionMap().put("left",
				new AbstractAction() {
					@Override
					public void actionPerformed(ActionEvent e) {
						setDefenderDir(0);// 1 moves right, 0 moves left
						repaint();
					}
			
		});

	}

	protected void setDefenderDir(int i) {
		//System.out.println("left or right..."+i);
		if(i == 0) {
			//System.out.println("Ship will move left");
			this.defShip.moveLeft();
		}
		else if(i == 1) {
			//System.out.println("Ship will move right");
			this.defShip.moveRight();
		}
		else if(i == 2) {
			defShip.stop();
			
		}
			
	}

	protected void launchWeapon() {
		//System.out.println("Launching weapon now!!");
		if(defShip.maxBullets()> defenderBullets.size()) {
			defenderBullets.add(defShip.getBullet());
			cp.play("shoot3");
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// what do you want to do every 10th of a second?
		//System.out.println("timer!!!");
		
		moveEverything();
		checkForCollision();
		tryAlienShoot();
		repaint();
	}
	private void tryAlienShoot() {
		if(GameObject.rand.nextDouble()>1-PROB_ALIEN_SHOT) {
			alienBullets.add(badGuys.shoot());
		}
	}

	private void checkForCollision() {
		List<GameObject> removeThese = new ArrayList<GameObject>();
		for(Bullet b: defenderBullets) {
			//System.out.println("%%%%%%%Testing "+b);
			if(b.offScreen()) {
				//System.out.println(b + " is off screen!");
				removeThese.add(b);
			}
			else {
				AlienShip as = badGuys.kill(b);
				if(as!= null) {
					removeThese.add(as);
					removeThese.add(b);
					cp.play("hit");
				}
//				for(AlienShip a: this.aliens) {			
//					if(b.collides(a)) {
//						removeThese.add(a);
//						removeThese.add(b);
//					}
//				}
			}
			for(Shield s: shieldList) {
				if(s.collides(b)) {
					s.explode(1);
					
					removeThese.add(b);
				}
			}
			
		}
		for(Bullet b: alienBullets) {
			if(this.defShip.collides(b)) {
				endRound();
				
				removeThese.add(b);
				return;
			}
			for(Shield s: shieldList) {
				if(s.collides(b)) {
					s.explode(-1);
					removeThese.add(b);
				}
			}
		}
		alienBullets.removeAll(removeThese);
		defenderBullets.removeAll(removeThese);
		badGuys.removeAll(removeThese);
	}

	private void endRound() {
		defShip.shot();
		cp.play("dead");
		JOptionPane.showMessageDialog(null, "You just died! Lives left: "+ defShip.getLivesLeft(),null, 1);
		alienBullets.clear();
		this.resetDefenderLoc();
	}

	private void moveEverything() {
		//System.out.println("moving stuff!!!");
		defShip.move();
		moveBullets();
		badGuys.move();
	}

	private void moveBullets() {
		for(Bullet b:defenderBullets) {
			b.move();
		}
			
		for(Bullet b: this.alienBullets)
			b.move();
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		try {
			this.defShip.draw(g);
			for(Bullet b:this.defenderBullets)
				b.draw(g);
//			for(GameObject go: aliens) {
//				go.draw(g);
//			}
			badGuys.draw(g);
			for(Bullet b: this.alienBullets)
				b.draw(g);
			for(Shield s: this.shieldList) {
				s.draw(g);
			}
		}
		catch(Exception e) {
			
		}
		
	}

	public void start() {// this is called by the menu in the frame
		//System.out.println("Just started a new game...");
		this.reset();
		this.gameTimer.start();
		this.requestFocusInWindow();
	}

	private void reset() {
		this.setUpAliens();
		this.setUpDefender();
		this.setUpShields();	
	}


}
