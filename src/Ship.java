import java.io.Serializable;
import java.util.*;

public class Ship implements Serializable {
 private Map<String,Integer> locationCells;
 private int countOfBlocks;

 public void setLocationCells(TreeMap<String,Integer> loc) {
	 locationCells = loc;
 }
 
 public void setCountOfBlocks (int count) {
	 countOfBlocks = count;
 }
 
 public int getCountOfBlocks () {
	 return countOfBlocks;
 }
 
 //метод возвращает массив координат корабля 
 public String[] getCoordinate(){
	 String[] shipCoordinate = new String[countOfBlocks];
	 Iterator<String> iter = locationCells.keySet().iterator();
	 int i = 0;
	 while (iter.hasNext()){
		 String key = (String) iter.next();
		 shipCoordinate[i] = key;
		 i++;
		//System.out.println("i = " + key + "");
	 }
	return shipCoordinate;
 }
 
 //возвращает статус каждой координаты 0 - не поражена, 1 - поражена противником
 public Integer[] getValues(){
	 Integer[] values = new Integer[countOfBlocks];
	 Iterator<String> iter = locationCells.keySet().iterator();
	 int i = 0;
	 while (iter.hasNext()){
		 String key = (String) iter.next();
		 values[i] = locationCells.get(key); 
		 i++;
		//System.out.println("i = " + value + "");
	 }
	 return values;
 }
 
 //метод,который проверяет ход пользователя на попадание в одну из ячеек размещённых кораблей
 public String checkYourself (String userInput) {
  String result = "Мимо";
  boolean isContains = locationCells.containsKey(userInput);
  if(isContains){
	  result = "Попал";
	  locationCells.replace(userInput, 1);
	  if(!locationCells.values().contains(0)){
		  result = "Потопил";
	  }
  }  
   return result;
 }
}