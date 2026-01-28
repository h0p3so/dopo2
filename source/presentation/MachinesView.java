package presentation;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import domain.GameMode;
import domain.map.chars.CharType;
import presentation.constants.Fonts;
import presentation.constants.Paths;
import presentation.constants.Styles;
import presentation.recycle.BiPanel;
import presentation.recycle.Generics;
import presentation.selecting.SingleIceCreamView;

public class MachinesView extends BiPanel {
	private static MachinesView INSTANCE;
	private static Intermediary INTER;

	private static final String GIF = Paths.GIF_GENERAL;

	public MachinesView(){
		super(GIF);
	}

	@Override
	protected void setUpInformationPanel() {
		this.infoPanel = Generics.createGoldenPanel(Styles.BORDER_THICKNESS_MEDIUM);
		this.infoPanel.setLayout(new BoxLayout(this.infoPanel, BoxLayout.Y_AXIS));
		
		final JLabel title = new JLabel("Pick your flavour!");
		title.setFont(Fonts.BIG);
		title.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		this.infoPanel.add(Box.createVerticalStrut(Styles.VERTICAL_GAP_MEDIUM));
		this.infoPanel.add(title);
		this.infoPanel.add(Box.createVerticalStrut(Styles.VERTICAL_GAP_MEDIUM));
		
		final JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, Styles.PADDING_BIG, Styles.PADDING_BIG));
		panel.setOpaque(false);
		
		this.displayMachineTypes(panel);
		this.infoPanel.add(panel);
		this.infoPanel.add(Box.createVerticalStrut(Styles.VERTICAL_GAP_MEDIUM));
	}

	@Override
	protected void setUpGoBackButtonAction() {
		this.backButton.addActionListener(e -> {
			INTER.showThisView(ViewsId.SELECT_MODE);
		});
	}

	private void displayMachineTypes (final JPanel panel) {
		final String [] shortnames = {"H", "F", "E"};
		final ButtonInfo [] info = {
			new ButtonInfo("Hungry", ViewsId.SELECT_LEVEL, 0),
			new ButtonInfo("Fearful", ViewsId.SELECT_LEVEL, 1),
			new ButtonInfo("Expert", ViewsId.SELECT_LEVEL, 2),
		};
		
		final GameMode []modes = {
			GameMode.SIMULATION_HUNGRY,
			GameMode.SIMULATION_FEARFUL,
			GameMode.SIMULATION_EXPERT
		};
		
		for (int i = 0; i < info.length; i++) {
			final ButtonInfo context = info[i];
			final JButton button = Generics.createGoldenButton(context.getName(), Fonts.SMALL, Styles.BORDER_THICKNESS_MEDIUM);
			Generics.styleIncreaseFontSizeOnHover(button, Fonts.SMALL, Fonts.MEDIUM);
			Generics.styleChangeTextOnHover(button, context.getName(), shortnames[context.getPosition()]);
			
			final int j = i;
			button.addActionListener(e -> {
				INTER.getController().setGameMode(modes[j]);
				INTER.getController().setCharTypeOne(CharType.VANILLA);
				INTER.showThisView(ViewsId.SELECT_LEVEL);
			});
			
			/* TODO: move magic numbers into a constants file for button sizing
			 * @link SelectLevelView
			 * @link SingleIceCreamView
			 */
			button.setPreferredSize(new Dimension(120, 90));
			
			panel.add(button);
		}
	}
	
	public static MachinesView getInstance (final Intermediary inter) {
		if (INSTANCE == null) {
			INTER = inter;
			INSTANCE = new MachinesView();
		}
		INTER.setViewTitle("Select simulation type");
		return INSTANCE;
	}	
}
