import java.io.Serializable;
import java.util.ArrayList;
import java.util.TreeMap;

public class PlacerHelper implements Serializable {
	
	private int[][] mainGrid = new int[10][10];
	private int shipCount = 0;
	
	//�����,������� ��������� ������� ������� ������� �� ������� ����
	public TreeMap<String,Integer> placeShip(int shipSize) {
		
		TreeMap<String,Integer> alphaCells = new TreeMap<String,Integer>();
		int[][] curCoords = new int[shipSize][2];//������ ��������� �������� �������
		int attempts = 0;
		boolean success = false;
		int i = 0;
		int j = 0;
		String temp = null;
		
		shipCount++;
		//�������� ��� ��������� �������, ����������� ��� �������������
		int incr = 1;
		String direct = "hor";
		if ((shipCount % 2) == 1) {
			incr = 1;
			direct = "ver";
		}
		
		//������� ��������� ����
		while(!success & attempts < 200){
			//��������� ������� �������� ��������� �����
			i = (int) (Math.random()*10);
			j = (int) (Math.random()*10);
			
			int x = 0;
			success = true;
			
			//
			while(success && x < shipSize) {
				if(mainGrid[i][j] == 0) {
					curCoords[x][0] = i;
					curCoords[x][1] = j;
					if (direct.equals( "hor")){
					    j += incr;
					}
					if (direct.equals("ver")){
						i += incr;
					}
					
					//���� ����� �� ������� �������� ����, �� ������� �� �����
					if (j > 9 || i > 9){
						success = false;
					}
                 x++;
				}else {
					success = false;
				}
			} 
			
		}
		
		//���������� ��������� ������� � �������� �������� 
		int x = 0;
		while (x < shipSize){
			int n = curCoords[x][0];
            int m = curCoords[x][1];
            mainGrid[curCoords[x][0]][curCoords[x][1]] = 2;
            try{
            	//checkNeighborCells(n,m,n-1,m-1);
            	checkNeighborCell(n,m);
            } catch (Exception ex) {
            	System.out.println("������: " + ex);
            }
			temp = Integer.toString(n);
			alphaCells.put(temp.concat(Integer.toString(m)),0);
			x++;
		}
		
		return alphaCells;
	}

	//�����, ������� ��������� �������� ������ ������������ ��� ������������ ������� ��������� 
    private ArrayList<String> checkNeighborCell(int n, int m){
    	
    	ArrayList<String> neighborsCells = new ArrayList<String>(); 
        
        if(n-1 > 0 && mainGrid[n-1][m] != 2 && mainGrid[n-1][m] != 1) {
        	neighborsCells.add(Integer.toString(n-1)+Integer.toBinaryString(m));
        	mainGrid[n-1][m] = 1;
        }
        if(n-1 > 0 && m-1 > 0 && mainGrid[n-1][m-1] != 2 && mainGrid[n-1][m-1] != 1){
        	neighborsCells.add(Integer.toString(n-1)+Integer.toBinaryString(m-1));
        	mainGrid[n-1][m-1] = 1;
        }
        if(m-1 > 0 && mainGrid[n][m-1] != 2 && mainGrid[n][m-1] != 1){
        	neighborsCells.add(Integer.toString(n)+Integer.toBinaryString(m-1));
        	mainGrid[n][m-1] = 1;
        }
        if(n+1 < 10 && m-1 > 0 && mainGrid[n+1][m-1] != 2 && mainGrid[n+1][m-1] != 1){
        	neighborsCells.add(Integer.toString(n+1)+Integer.toBinaryString(m-1));
        	mainGrid[n+1][m-1] = 1;
        }
        if(n+1 < 10 && mainGrid[n+1][m] != 2 && mainGrid[n+1][m] != 1){
        	neighborsCells.add(Integer.toString(n+1)+Integer.toBinaryString(m));
        	mainGrid[n+1][m] = 1;
        }
        if(n+1 < 10 && m+1 < 10 && mainGrid[n+1][m+1] != 2 && mainGrid[n+1][m+1] != 1){
        	neighborsCells.add(Integer.toString(n+1)+Integer.toBinaryString(m+1));
        	mainGrid[n+1][m+1] = 1;
        }
        if(m+1 < 10 && mainGrid[n][m+1] != 2 && mainGrid[n][m+1] != 1){
        	neighborsCells.add(Integer.toString(n)+Integer.toBinaryString(m+1));
        	mainGrid[n][m+1] = 1;
        }
        if(n-1 > 0 && m+1 < 10 && mainGrid[n-1][m+1] != 2 && mainGrid[n-1][m+1] != 1){
        	neighborsCells.add(Integer.toString(n-1)+Integer.toBinaryString(m+1));
        	mainGrid[n-1][m+1] = 1;
        }
        	
        	
        return neighborsCells;
        
    }
	
    private void checkNeighborCells(int i, int j, int cur_i, int cur_j){ 	 
    	 //������� ������ ������ �� ������� �������
    	if (cur_i < 0 && cur_j < 0) {
    		 cur_i = i;
    		 cur_j = j + 1;
    	}
    	if (i == 9 && cur_j > 9) {
   		     cur_i = i;
   		     cur_j = j - 1;	
    	}
    	if (cur_i > 9 && cur_j >= 0 && cur_j <= 9) {
    		cur_i = i;
    		cur_j = j - 1;
    	}
    	if (cur_i < 0 && cur_j >= 0 && cur_j <= 9 ) {
    			cur_i = i;
    			cur_j = j + 1;   		
    	}
    	if (cur_j > 9 && cur_i >= 0 && cur_i <= 9){
    		cur_j = j;
    		cur_i = i+1;
    	}
    	if (cur_j < 0 && cur_i >= 0 && cur_i <= 9){
    		if (cur_i > i){
    			return;
    		}else {
    			cur_i = i - 1;
    			cur_j = j;
    		}
    	}
    	//������� ����
        if (cur_i<i){
        	if (cur_j<=j){
        		checkNeighborCells(i, j, cur_i, cur_j + 1);
            }else {
            	checkNeighborCells(i, j, cur_i+1, cur_j);
             }
        }else {
        	if(cur_i == i) {
        		if(cur_j > j){
        			checkNeighborCells(i, j, cur_i+1, cur_j);        			   
                }
            }else {
            	if(cur_j >= j) {
            		checkNeighborCells(i, j, cur_i, cur_j - 1);
                }else {
                	checkNeighborCells(i, j, cur_i-1, cur_j);
                 }
             }
        }
        if (mainGrid[cur_i][cur_j] != 2 && mainGrid[cur_i][cur_j] != 1 ){
        	mainGrid[cur_i][cur_j] = 1;
        }
            
    }
    
    //����� ������ �� ������� �������� ����
    public void printGrid(){
    	for ( int i = 0; i < mainGrid.length; i++){
    		for ( int j = 0; j< mainGrid.length; j++){
    			System.out.print(mainGrid[i][j] + " ");
            }
            System.out.println();
        }
 
    }
}
