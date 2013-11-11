
public class test_class {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int rowsColNum =  100;
		int[][] a = new int[rowsColNum][rowsColNum];
	    int[][] b = new int[rowsColNum][rowsColNum];
	    for (int i = 0; i < a.length; i++) {
	           for (int j = 0; j < a[0].length; j++) {
	               a[i][j] = 0 + (int)(Math.random() * ((10 - 0) + 1));
	           }
	    }
	    for (int i = 0; i < b.length; i++) {
	           for (int j = 0; j < b[0].length; j++) {
	               b[i][j] = 0 + (int)(Math.random() * ((10 - 0) + 1));
	           }
	    }
	    long startTime = System.currentTimeMillis();
	    int[][] c = multiply(a, b);
	    long stopTime = System.currentTimeMillis();
	    System.out.println("Product of A and B is");
	    for (int i = 0; i < a.length; i++) {
	           for (int j = 0; j < a[0].length; j++) {
	               System.out.print(c[i][j] + " ");
	           }
	           System.out.println();
	    }
	    long elapsedTime = stopTime - startTime;
	    System.out.println("Elapsed milliseconds: "+elapsedTime);
	}
	public static int[][] multiply(int[][] a, int[][] b) {
	       int rowsInA = a.length;
	       int columnsInA = a[0].length; // same as rows in B
	       int columnsInB = b.length;
	       int[][] c = new int[rowsInA][columnsInB];
	       for (int i = 0; i < rowsInA; i++) {
	           for (int j = 0; j < columnsInB; j++) {
	               for (int k = 0; k < columnsInA; k++) {
	                   c[i][j] = c[i][j] + a[i][k] * b[k][j];
	               }
	           }
	       }
	       return c;
	   }

}
