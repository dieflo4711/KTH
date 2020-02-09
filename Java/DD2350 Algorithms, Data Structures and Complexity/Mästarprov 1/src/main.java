package mästarprov1;

public class main {
	public static int c = 1;
	
	public static int[][] algo(int[][] matrix, int m, int size, int x, int y, int r) {
		
		if(r == 0) {
			matrix[y+(size/2)-1][x+(size/2)-1] = c;
			matrix[y+(size/2)][x+(size/2)-1] = c;
			matrix[y+(size/2)][x+(size/2)] = c;
		} else if(r == 1) {
			matrix[y+(size/2)-1][x+(size/2)] = c;
			matrix[y+(size/2)][x+(size/2)-1] = c;
			matrix[y+(size/2)][x+(size/2)] = c;
		} else if(r == 2) {
			matrix[y+(size/2)-1][x+(size/2)-1] = c;
			matrix[y+(size/2)-1][x+(size/2)] = c;
			matrix[y+(size/2)][x+(size/2)-1] = c;
		} else if(r == 3) {
			matrix[y+(size/2)-1][x+(size/2)-1] = c;
			matrix[y+(size/2)-1][x+(size/2)] = c;
			matrix[y+(size/2)][x+(size/2)] = c;
		}
		
		c++;
		
		if(size == 2) 
			return matrix;
		
		if(size == m) {
			algo(matrix, m, size/2, x+size/2, y, 0);
			algo(matrix, m, size/2, x+size/2, y + size/2, 1);
			algo(matrix, m, size/2, x, y, 2);
		} else {
			algo(matrix, m, size/2, x+size/2, y, (r != 1 && r != 2 && r != 3 ? 0 : 3));
			algo(matrix, m, size/2, x+size/2, y + size/2, (r != 2 ? 1 : 2));
			algo(matrix, m, size/2, x, y, (r != 1 ? 2 : 1));
		}
		
		algo(matrix, m, size/2, x, y + size/2, (r != 3 ? 0 : 3));
		
		return matrix;
	}

	public static void main( String[] args) {
		int m = (int)Math.pow(2, 3);
		
		int[][] matrix = new int[m][m];
		
		matrix = algo(matrix, m, m, 0, 0, 0);
		
		StringBuilder s = new StringBuilder();
		for(int i = 0; i < m; i++) {
			for(int j = 0; j < m; j++)
				s.append(matrix[i][j] + " ");
			s.append("\n");
		}
		
		System.out.println(s.toString());
	}
}
