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
 
 //����� ���������� ������ ��������� ������� 
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
 
 //���������� ������ ������ ���������� 0 - �� ��������, 1 - �������� �����������
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
 
 //�����,������� ��������� ��� ������������ �� ��������� � ���� �� ����� ����������� ��������
 public String checkYourself (String userInput) {
  String result = "����";
  boolean isContains = locationCells.containsKey(userInput);
  if(isContains){
	  result = "�����";
	  locationCells.replace(userInput, 1);
	  if(!locationCells.values().contains(0)){
		  result = "�������";
	  }
  }  
   return result;
 }
}