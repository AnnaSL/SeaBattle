import java.awt.*;
import javax.swing.*;
import java.util.*;
import java.awt.event.*;

public class BuildGUI {

	JPanel mainPanel;
	ArrayList<JButton> buttonList;
	JFrame theFrame;
	JButton buttons[][] = new JButton[10][10] ;
	ButtonListener bl;
	//������� �������� � ���� ��� ����������� �� ������
	private static final String[] ALPHA = {"a","b","c","d","e","f","g","h","i","j"};
	private static final String[] NUM_OF_COL= {"0","1","2","3","4","5","6","7","8","9"};
	
	//public static void main(String[] args){
	//	new BuildGUI().buildGUI();
	//}
	
	public void buildGUI(){
		theFrame = new JFrame("SeaWar");
		theFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		theFrame.setResizable(false);
		
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		
		Box charBox = new Box(BoxLayout.X_AXIS);
		for (int i = 0; i < 10;i++){
			charBox.add(new Label(ALPHA[i].toUpperCase()));
		}
		panel.add(BorderLayout.NORTH,charBox);
		
		Box intBox = new Box(BoxLayout.Y_AXIS);
		for (int i = 0; i < 10;i++){
			intBox.add(new Label(NUM_OF_COL[i].toUpperCase()));
		}
		panel.add(BorderLayout.WEST,intBox);
		
		theFrame.getContentPane().add(panel);
		
		GridLayout grid = new GridLayout(10,10);
		grid.setVgap(1);
		grid.setHgap(1);
		mainPanel = new JPanel(grid);
		panel.add(BorderLayout.CENTER,mainPanel);
		
		 bl = new ButtonListener(); 
		
		for(int i = 0; i < 10; i++){
			for(int j = 0; j < 10; j++){
				buttons[i][j] = new JButton("");
				buttons[i][j].putClientProperty("column",i);
				buttons[i][j].putClientProperty("row",j);
				buttons[i][j].addActionListener(bl);
				mainPanel.add(buttons[i][j]);
			}
		}
		//mainPanel.revalidate();
		//mainPanel.repaint();
		//�������� ����
		JMenuBar mainMenu = new JMenuBar();
		JMenu game = new JMenu("����");
		JMenu help = new JMenu("�������"); 
		mainMenu.add(game);
		mainMenu.add(help);
		//�������� �������
		JMenuItem newGame = new JMenuItem("����� ����");
		JMenuItem gameStatistics = new JMenuItem("����������");
		JMenuItem settings = new JMenuItem("���������");
		newGame.addActionListener(new NewGameListener());
		gameStatistics.addActionListener(new GameStatisticsListener());
		settings.addActionListener(new SettingsListener());
		//��� ����� ������ ���������� �������
		/*
		newGame.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					///
				}
			
		});
		*/
		game.add(newGame);
		game.add(gameStatistics);
		game.add(settings);				
		
		theFrame.setJMenuBar(mainMenu);
		theFrame.setBounds(50,50,300,300);
		theFrame.setVisible(true);
		
	}
	
	//���������� ������ - ��������� �������
	public class NewGameListener implements ActionListener{
		public void actionPerformed(ActionEvent a){
			//����� ��� ��������� �������
		}
	}
	
	public class GameStatisticsListener implements ActionListener {
		public void actionPerformed(ActionEvent a){
			//����� ��� ��������� �������
		}
	} 
	
	public class SettingsListener implements ActionListener{
		public void actionPerformed(ActionEvent a){
			//����� ��� ��������� �������
		}
	} 
	
	public class ButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent a){
			JButton btn = (JButton) a.getSource();
			System.out.println("i = " + btn.getClientProperty("column") + "  j = " + btn.getClientProperty("row"));
		}
	}
	
	
}
