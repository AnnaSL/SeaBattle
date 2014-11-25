import java.util.*;
import java.awt.*;

import javax.swing.*;

import java.awt.event.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Game implements Serializable {	

	private PlacerHelper helper;
 	private ArrayList<Ship> ShipList = new ArrayList<Ship>();//ship's list
 	private ArrayList<Ship> WreckedShipList = new ArrayList<Ship>();
 	private ArrayList<String> ShotPlace = new ArrayList<String>();
 	//private int numOfGuesses = 0;
 	JPanel mainPanel;
	ArrayList<JButton> buttonList;
	JFrame theFrame;
	JButton buttons[][] = new JButton[10][10] ;
	ButtonListener bl;
	//массивы алфавита и цифр для отображения на фрейме
	private static final String[] ALPHA = {"a","b","c","d","e","f","g","h","i","j"};
	private static final String[] NUM_OF_COL= {"0","1","2","3","4","5","6","7","8","9"};
	private Game game;
	
	//метод, который создаёт GUI рограммы
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
		grid.setVgap(0);
		grid.setHgap(0);		
		mainPanel = new JPanel(grid);
		panel.add(BorderLayout.CENTER,mainPanel);
		
		bl = new ButtonListener(); 
		
		for(int i = 0; i < 10; i++){
			for(int j = 0; j < 10; j++){
				buttons[i][j] = new JButton("");
				buttons[i][j].putClientProperty("column",i);
				buttons[i][j].putClientProperty("row",j);
				buttons[i][j].addActionListener(bl);
				//buttons[i][j].setBorder(null);
				//buttons[i][j].setBorderPainted(false);
				//buttons[i][j].setContentAreaFilled(false);				
				//buttons[i][j].setOpaque(false);
				buttons[i][j].setBackground(null);
				mainPanel.add(buttons[i][j]);
			}
		}
		//mainPanel.revalidate();
		//mainPanel.repaint();
		//добавление меню
		JMenuBar mainMenu = new JMenuBar();
		JMenu mGame = new JMenu("Игра");
		JMenu help = new JMenu("Справка"); 
		mainMenu.add(mGame);
		mainMenu.add(help);
		//добавляю подменю
		JMenuItem newGame = new JMenuItem("Новая игра");
		JMenuItem gameStatistics = new JMenuItem("Статистика");
		JMenuItem settings = new JMenuItem("Настройки");
		JMenuItem saveGame = new JMenuItem("Сохранить игру");
		JMenuItem loadGame = new JMenuItem("Загрузить игру");
		newGame.addActionListener(new NewGameListener());
		gameStatistics.addActionListener(new GameStatisticsListener());
		settings.addActionListener(new SettingsListener());
		saveGame.addActionListener(new SaveGameListener());
		loadGame.addActionListener(new LoadGameListener());
		// обработчик события
		/*
		newGame.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					///
				}
			
		});
		*/
		mGame.add(newGame);
		mGame.add(saveGame);
		mGame.add(loadGame);
		mGame.add(gameStatistics);
		mGame.add(settings);			
		
		theFrame.setJMenuBar(mainMenu);
		theFrame.setBounds(50,50,300,300);
		theFrame.pack();
		theFrame.setVisible(true);
	}
	
	//отображает сохранённое состояние игрового поля
	private void drawShipsGrid (){	
		for (Ship shipToTest:WreckedShipList){
			String[] coordinates =  shipToTest.getCoordinate();
			int m = shipToTest.getCountOfBlocks();
			for (int k = 0; k < m; k++){
				int l = Character.getNumericValue(coordinates[k].charAt(0));
				int n = Character.getNumericValue(coordinates[k].charAt(1));
				buttons[l][n].setBackground(Color.BLACK);						
			}			
		}
		
		for (Ship shipToTest:ShipList){
			String[] coordinates =  shipToTest.getCoordinate();
			Integer[] values = shipToTest.getValues();
			int m = shipToTest.getCountOfBlocks();
			for (int k = 0; k < m; k++){
				
				if (values[k] == 1){
					int l = Character.getNumericValue(coordinates[k].charAt(0));
					int n = Character.getNumericValue(coordinates[k].charAt(1));
					buttons[l][n].setBackground(Color.GRAY);
				}										
			}			
		}
		for(String shotCoords: ShotPlace){
			int m = Character.getNumericValue(shotCoords.charAt(0));
			int d = Character.getNumericValue(shotCoords.charAt(1));
			buttons[m][d].setBackground(Color.LIGHT_GRAY);					
		}
	}
	
	//внутренние классы - слушатели событий
	public class NewGameListener implements ActionListener,Serializable{
		public void actionPerformed(ActionEvent a){
			// код обработки события
			newGame();
		}
	}
	
	public class GameStatisticsListener implements ActionListener,Serializable {
		public void actionPerformed(ActionEvent a){
			// код обработки события
		}
	} 
	
	public class SettingsListener implements ActionListener,Serializable{
		public void actionPerformed(ActionEvent a){
			//код обработки события
		}
	} 
	
	public class SaveGameListener implements ActionListener, Serializable{
		public void actionPerformed(ActionEvent a){
			//код обработки события
			saveGame();
		}
	}
	
	public class LoadGameListener implements ActionListener,Serializable{
		public void actionPerformed(ActionEvent a){
			loadGame();
			drawShipsGrid();
		}
	}
	
	//сериализует текущее состояние игрового поля
	public void saveGame(){
		try{
			SavedState state = new SavedState(ShipList, WreckedShipList, ShotPlace);
			FileOutputStream fs = new FileOutputStream("game.ser");
			ObjectOutputStream os = new ObjectOutputStream(fs);
			os.writeObject(state);
			os.close();
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	
	//десериализует сохранённое состояние игрового поля
	public void loadGame(){
		SavedState state;
		try{
			FileInputStream fis = new FileInputStream("game.ser");
			ObjectInputStream is = new ObjectInputStream(fis);
			state = (SavedState)is.readObject();
			this.ShipList = state.sl;
			this.WreckedShipList = state.ml;
			this.ShotPlace = state.shotPlace;
			is.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	//класс, который хранит списки всех кораблей екущего игрового процесса
	class SavedState implements Serializable {
		ArrayList<Ship> s;
		ArrayList<Ship> ml;
		ArrayList<String> shotPlace;
		
		public SavedState(ArrayList<Ship> s, ArrayList<Ship> m, ArrayList<String> sh){
			this.sl = s;
			this.ml = m;
			this.shotPlace = sh;
		}
	}
	
	//	обработчик события нажатия кнопки
	public class ButtonListener implements ActionListener,Serializable {
		public void actionPerformed(ActionEvent a){
			JButton btn = (JButton) a.getSource();
			int i = (int)btn.getClientProperty("column");
			int j = (int)btn.getClientProperty("row");
			String i_str = Integer.toString(i);
			String userGuess = i_str.concat(Integer.toString(j));
			
			String result = "Мимо";
			 
			for (Ship shipToTest: ShipList) {
				
				int m = shipToTest.getCountOfBlocks();
				String[] coordinates =  shipToTest.getCoordinate();
				result = shipToTest.checkYourself(userGuess);
				
				if (result.equals("Попал")){
					buttons[i][j].setBackground(Color.GRAY);
					buttons[i][j].removeActionListener(this);
					break;
				}
				if (result.equals("Потопил")){
					for (int k = 0; k < m; k++){
						int l = Character.getNumericValue(coordinates[k].charAt(0));
						int n = Character.getNumericValue(coordinates[k].charAt(1));
						buttons[l][n].setBackground(Color.BLACK);						
					}
					buttons[i][j].removeActionListener(this);
					WreckedShipList.add(shipToTest);
					ShipList.remove(shipToTest);
					break;
				}				
			}
			
			if (result.equals("Мимо")){
				buttons[i][j].setBackground(Color.LIGHT_GRAY);
				String currentCoordinates = btn.getClientProperty("column").toString()+btn.getClientProperty("row").toString(); 
				buttons[i][j].removeActionListener(this);
				ShotPlace.add(currentCoordinates);
			}
			
			if (ShipList.isEmpty()){
				finishGame();
			}
		}
	}
 
 
 //Метод для начала игры и расстановки кораблей на игровом поле
 private void setUpGame() {
	 
	 helper = new PlacerHelper();
	 int count4Blocks = 0;
	 int count3Blocks = 0;
	 int count2Blocks = 0;
	 int count1Blocks = 0;	 
	 int countOfBlocks = 0;
	 
     //размещаем 10 кораблей
	 for (int i = 0; i < 10; i++) {
		 if(count4Blocks < 1){
			 countOfBlocks = 4;
			 count4Blocks++;
		 }else{
			 if(count3Blocks < 2){
				 countOfBlocks = 3;
				 count3Blocks++;
			 }else{
				 if(count2Blocks < 3){
					 countOfBlocks = 2;
					 count2Blocks++;
				 }else{
					 if(count1Blocks < 4){
						 countOfBlocks = 1;
						 count1Blocks++;
					 }
				 }
			 }
		 }
		 
		 Ship ship_temp = new Ship();
		 ship_temp.setCountOfBlocks(countOfBlocks);
		 ship_temp.setLocationCells(helper.placeShip(countOfBlocks));
		 //ship_temp.getCoordinate();
		 ShipList.add(ship_temp);		 
	 }
	 
	//helper.printGrid();
 }
 
 //метод заверешния игры и  вывода статистики по игре
 private void finishGame(){
	 System.out.println("Все корабли потоплены! Поздравляем :)");
 }
 
 //мтод очистки игрового поля
 public void newGame(){
	 ShipList.clear();
	 WreckedShipList.clear();
	 ShotPlace.clear();
	 setUpGame(); 
	 for (int i = 0; i < buttons.length; i++){
		 for (int j = 0; j < buttons.length; j++){
			 buttons[i][j].setBackground(null);
			 buttons[i][j].addActionListener(bl);
		 }
	 }
 }

 
 public static void main (String[] args) {
	 Game game = new Game();
	 game.setUpGame();
	 game.buildGUI();
 }
 
}