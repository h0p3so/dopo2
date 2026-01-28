package presentation.render;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.Timer;

import domain.GameMode;
import domain.MotionDirection;
import domain.MotionEndsUp;
import domain.map.chars.Character;
import domain.map.chars.cream.IceCream;
import presentation.BaDopoCreamGUI;
import presentation.Intermediary;
import presentation.constants.GameEndsUp;
import presentation.constants.Styles;
import presentation.constants.Titles;
import presentation.recycle.Generics;

public class LevelView extends JLayeredPane implements ActionListener, KeyListener, RenderComunication {
    private static LevelView INSTANCE;
    private static Intermediary INTER;
    
    private StaticTilesView tilesLayer;
    private InteractiveView charsLayer;
    private HUDView hudLayer;
    
    private Timer timer;
    private final int fps = 1000 / 60;
    
    public static final int ENEMY_UPDATE_FPS = 8;
    public static final int ICECREAM_UPDATE_FPS = 8;

    public static final int FRUIT_UPDATE_FPS = 8;
    public static final int HDU_UPDATE = 35;
	private static final int TILES_UPDATE_FPS = 35;

    private int elapsed = 0;
    private JPanel fruitroundPanel;

    private WinLostView victoryView;
    private JPanel victoryPanel;

    private PauseView pauseView;
    private JPanel pausePanel;
    
    private boolean paused = false;
    
    private LevelView () {
        this.setPreferredSize(new Dimension(BaDopoCreamGUI.WINDOW_WIDTH, BaDopoCreamGUI.WINDOW_HEIGHT));
        this.timer = new Timer(this.fps, this);

        this.tilesLayer = new StaticTilesView(INTER);
        this.charsLayer = new InteractiveView(INTER);
        this.hudLayer = new HUDView(INTER);

        this.tilesLayer.setBounds(0, 0, BaDopoCreamGUI.WINDOW_WIDTH, BaDopoCreamGUI.WINDOW_HEIGHT);
        this.charsLayer.setBounds(0, 0, BaDopoCreamGUI.WINDOW_WIDTH, BaDopoCreamGUI.WINDOW_HEIGHT);

        this.hudLayer.setBounds(0, 0, BaDopoCreamGUI.WINDOW_WIDTH, BaDopoCreamGUI.WINDOW_HEIGHT); 
        this.add(this.tilesLayer, JLayeredPane.DEFAULT_LAYER);
        this.add(this.charsLayer, JLayeredPane.PALETTE_LAYER);
        this.add(this.hudLayer, JLayeredPane.MODAL_LAYER);
        
        this.setUpFruitsPanel();
        this.setUpVictoryPanel();
        this.setUpPausePanel();

        this.setFocusable(true);
        this.addKeyListener(this);
    }
    
    private void setUpVictoryPanel () {
        this.victoryView = new WinLostView(INTER);
        this.victoryPanel = this.victoryView.getPanel();
        
        this.victoryPanel.setBounds(
        	(BaDopoCreamGUI.WINDOW_WIDTH - (BaDopoCreamGUI.WINDOW_WIDTH / 2)) / 2,
        	(BaDopoCreamGUI.WINDOW_HEIGHT - (BaDopoCreamGUI.WINDOW_HEIGHT / 4)) / 2,
        	BaDopoCreamGUI.WINDOW_WIDTH / 2,
        	BaDopoCreamGUI.WINDOW_HEIGHT / 4
        );
        this.add(this.victoryPanel, JLayeredPane.DRAG_LAYER);
        this.victoryPanel.setVisible(false);
    }
    
    private void setUpPausePanel () {
    	this.pauseView = new PauseView(INTER, this);
    	this.pausePanel = this.pauseView.getPanel();

        this.pausePanel.setBounds(
        	(BaDopoCreamGUI.WINDOW_WIDTH - (BaDopoCreamGUI.WINDOW_WIDTH / 2)) / 2,
        	(BaDopoCreamGUI.WINDOW_HEIGHT - (BaDopoCreamGUI.WINDOW_HEIGHT / 4)) / 2,
        	BaDopoCreamGUI.WINDOW_WIDTH / 2,
        	BaDopoCreamGUI.WINDOW_HEIGHT / 4
        );

        this.add(this.pausePanel, JLayeredPane.DRAG_LAYER);
        this.pausePanel.setVisible(false);
    }
    
    private void setUpFruitsPanel () {
        this.fruitroundPanel = Generics.createGoldenPanel(Styles.BORDER_THICKNESS_MEDIUM);
        this.fruitroundPanel.setLayout(new java.awt.BorderLayout());
        this.fruitroundPanel.setBounds(0, BaDopoCreamGUI.WINDOW_HEIGHT - 70, BaDopoCreamGUI.WINDOW_WIDTH, 35); 
        
        final int FRUIT_HEIGHT = 40; 

        JPanel imgcontainer = new JPanel();
        imgcontainer.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 0));
        imgcontainer.setOpaque(false);
        
        for (Image originalSprite: INTER.getController().loadFruitPreviewImages()) {
            
            Image scaledSprite = originalSprite.getScaledInstance(
                -1, 
                FRUIT_HEIGHT, 
                Image.SCALE_SMOOTH
            );
            
            ImageIcon icon = new ImageIcon(scaledSprite);
            
            JLabel label = new JLabel(icon);
            imgcontainer.add(label);
        }
        
        this.fruitroundPanel.add(imgcontainer, BorderLayout.CENTER);
        this.add(this.fruitroundPanel, JLayeredPane.PALETTE_LAYER);	
    }
     
    @Override public void addNotify () {
        super.addNotify();
        this.requestFocusInWindow();
    }
    
    @Override public void actionPerformed (final ActionEvent e) {
        if ((this.elapsed % ENEMY_UPDATE_FPS) == 0) {
            INTER.getController().moveEnemies();
        }
        if ((this.elapsed % TILES_UPDATE_FPS) == 0) {
        	this.charsLayer.drawTiles(this.charsLayer.getGraphics());
        }
        if (((this.elapsed % ICECREAM_UPDATE_FPS) == 0) && GameMode.isSimulation(INTER.getController().getMode())) {
        	INTER.getController().generateAutonomousMoveForIceCream();
        }
        if ((this.elapsed % HDU_UPDATE) == 0) {
            this.hudLayer.repaint();
            INTER.getController().oneSecondPassedBy();
        }
        
        this.elapsed++;
        this.charsLayer.updateMap();
        this.hudLayer.repaint();
    }
    
    @Override public void keyPressed (final KeyEvent e) {
    	if (this.paused || GameMode.isSimulation(INTER.getController().getMode()))
    	{
    		return;
    	}
        MotionEndsUp follows = MotionEndsUp.JUST_MOVING;
        
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP: {
                follows = INTER.getController().characterTryingToMove(
                    MotionDirection.UP,
                    INTER.getController().getCharacter1()
                );
                this.handleMotionLogicallyViaController(INTER.getController().getCharacter1(), follows);
                break;
            }

            case KeyEvent.VK_DOWN: {
                follows = INTER.getController().characterTryingToMove(
                    MotionDirection.DOWN,
                    INTER.getController().getCharacter1()
                );
                this.handleMotionLogicallyViaController(INTER.getController().getCharacter1(), follows);
                break;
            }

            case KeyEvent.VK_LEFT: {
                follows = INTER.getController().characterTryingToMove(
                    MotionDirection.LEFT,
                    INTER.getController().getCharacter1()
                );
                this.handleMotionLogicallyViaController(INTER.getController().getCharacter1(), follows);
                break;
            }

            case KeyEvent.VK_RIGHT: {
                follows = INTER.getController().characterTryingToMove(
                    MotionDirection.RIGHT,
                    INTER.getController().getCharacter1()
                );
                this.handleMotionLogicallyViaController(INTER.getController().getCharacter1(), follows);
                break;
            }
            
            case KeyEvent.VK_SPACE: {
            	INTER.getController().createDestroyWall(INTER.getController().getCharacter1());
            	break;
            }    
            
            case KeyEvent.VK_ESCAPE:
            case KeyEvent.VK_P: {
            	this.paused = true;
            	this.timer.stop();
            	this.pausePanel.setVisible(true);
            	break;
            }
        } 

        this.maybeATwoPlayerMotion(e.getKeyCode());
        this.charsLayer.updateMap();
    }
    
    private void maybeATwoPlayerMotion (final int type) {
    	if (!GameMode.isTwoPlayerMode(INTER.getController().getMode())) {
    		return;
    	}
    	
        MotionEndsUp follows = MotionEndsUp.JUST_MOVING;
    	switch (type) {
    		case KeyEvent.VK_W: {
                follows = INTER.getController().characterTryingToMove(
                    MotionDirection.UP,
                    INTER.getController().getCharacter2()
                );
                this.handleMotionLogicallyViaController(INTER.getController().getCharacter2(), follows);
    			break;
    		}
            case KeyEvent.VK_S: {
                follows = INTER.getController().characterTryingToMove(
                    MotionDirection.DOWN,
                    INTER.getController().getCharacter2()
                );
                this.handleMotionLogicallyViaController(INTER.getController().getCharacter2(), follows);
            	break;
            }

            case KeyEvent.VK_A: {
                follows = INTER.getController().characterTryingToMove(
                    MotionDirection.LEFT,
                    INTER.getController().getCharacter2()
                );
                this.handleMotionLogicallyViaController(INTER.getController().getCharacter2(), follows);
            	break;
            }

            case KeyEvent.VK_D: {
                follows = INTER.getController().characterTryingToMove(
                    MotionDirection.RIGHT,
                    INTER.getController().getCharacter2()
                );
                this.handleMotionLogicallyViaController(INTER.getController().getCharacter2(), follows);
            	break;
            }

            case KeyEvent.VK_F: {
            	INTER.getController().createDestroyWall(INTER.getController().getCharacter2());
            	break;
            }
    	}
    }
    
    private void handleMotionLogicallyViaController (final Character ch, final MotionEndsUp ends)
    {
        if (ends == MotionEndsUp.PLAYER_KILLING_PLAYER) {
        	if (GameMode.isTwoPlayerMode(INTER.getController().getMode())) {
        		INTER.getController().playerKillsPlayer();
        	}
        	else {
        		final IceCream ic = (IceCream) ch;
        		INTER.getController().deadCharacter(ch.getPosition(), ic);
        	}
        	
        }
        if (ends == MotionEndsUp.COLLECTING_FRUIT) {
        	INTER.getController().playerGotFood(ch);
        }
        if (ends == MotionEndsUp.PLAYER_DIYING) {
        	final IceCream ic = (IceCream) ch;
        	INTER.getController().deadCharacter(ch.getPosition(), ic);
        }
    }
    
    public void resetView() {
        if (this.timer.isRunning()) {
            this.timer.stop();
        }
        this.elapsed = 0;
        this.victoryPanel.setVisible(false);
        this.requestFocusInWindow();
        this.timer.start();
        
        this.requestFocusInWindow(); 
        
        this.charsLayer.initCharOne();
        this.charsLayer.initCharTwo();
    }

    public void showLostScreen () {
    	this.timer.stop();
    	this.victoryView.renderFinalInfo(GameEndsUp.LOSING);
    	this.victoryPanel.setVisible(true);
    }

    public void justWon () {
    	this.timer.stop();
    	this.victoryView.renderFinalInfo(GameEndsUp.WINNING);
    	this.victoryPanel.setVisible(true);
    }
    
    @Override public void keyTyped (final KeyEvent e) {}
    @Override public void keyReleased (final KeyEvent e) {}
    
    public static LevelView getInstace (final Intermediary inter) {
        if (INSTANCE == null) {
            INTER = inter;
            INSTANCE = new LevelView();
        }
        INSTANCE.resetView();
        INTER.setViewTitle(Titles.playingLevel(INTER.getController().getLevelNumber()));
        return INSTANCE;
    }    
    
    @Override public void resumeGame () {
    	this.timer.start();
    	this.paused = false;
    	this.pausePanel.setVisible(false);
    }
}