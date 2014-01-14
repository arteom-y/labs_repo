package mux.pkg;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MatrixMulti {
	public static final int numberOfThreads=10;
    public static void main(String[] args) {
        int[][] matrixA;
        int[][] matrixB;
        int colA = 0;
        int rowA = 0;
        int colB = 0;
        int rowB = 0;
        Scanner userInput = new Scanner(System.in);
        System.out.println("Ввод размерности матрицы A");

        do {
            System.out.print("число столбцов матрицы A: ");
            colA = userInput.nextInt();
            System.out.println();
        } while (!validDimension(colA));

        rowB = colA;

        do {
            System.out.print("число рядов матрицы A: ");
            rowA = userInput.nextInt();
            System.out.println();
        } while (!validDimension(rowA));

        matrixA = new int[rowA][colA];

        System.out.println("Ввод размерности матрицы B:");
        do {
            System.out.print("число столбцов матрицы B: ");
            colB = userInput.nextInt();
            System.out.println();
        } while (!validDimension(colB));

        matrixB = new int[rowB][colB];


        fillMatrix(matrixA);
        fillMatrix(matrixB);

        System.out.println("Напечатать матрицы? (y/n)");
        String userResponse = userInput.next();
        if (userResponse.equalsIgnoreCase("y")) {
            System.out.println("Матрица A:");
            printBackMatrix(matrixA);
            System.out.println();
            System.out.println("Матрица B:");
            printBackMatrix(matrixB);
            System.out.println();
        }

        long startTime = System.currentTimeMillis();
        int[][] matrixProduct3 = multMatrixWithThreadsSync(matrixA, matrixB);
        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;
	    System.out.println("Время выполнения, мс: "+elapsedTime);
	    
        String fileName = "test.txt";
        System.out.println("Результат записан в " + fileName);
        try {
            printMatrixToFile(matrixProduct3, fileName);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private static int[][] multMatrixWithThreadsSync(int[][] matrixA, int[][] matrixB) {

        int[][] matrixProduct = new int[matrixA.length][matrixB[0].length];
        int[] matrixProductColumn = new int[matrixA.length];
        //
        ConcurrentMatrixMultiplyingTask.ConcurrencyContext context = new ConcurrentMatrixMultiplyingTask.ConcurrencyContext(matrixProduct.length);
        //
        Runnable task = new ConcurrentMatrixMultiplyingTask(context, matrixA, matrixB, matrixProduct);
        Thread[] workers = new Thread[numberOfThreads];
        for (int i = 0; i < workers.length; i++) {
            workers[i] = new Thread(task, "Thread-"+i);
        }
        for (int i = 0; i < workers.length; i++) {
            Thread worker = workers[i];
            worker.start();
        }
        for (int i = 0; i < workers.length; i++) {
            Thread worker = workers[i];
            try {
                worker.join();
            } catch (InterruptedException ex) {
                Logger.getLogger(MatrixMulti.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return matrixProduct;
    }

    private static void printMatrixToFile(int[][] matrix, String fileName) throws IOException {
        PrintWriter userOutput = new PrintWriter(new FileWriter(fileName));
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                userOutput.print(matrix[i][j] + " ");
            }
            userOutput.println();
        }
        userOutput.close();

    }

    private static void printBackMatrix(int[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }

    private static void fillMatrix(int[][] matrix) {
        Random rand = new Random();

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                matrix[i][j] = rand.nextInt(100) + 1;
            }
        }

    }

    public static boolean validDimension(int dim) {
        if (dim <= 0 || dim > 10000) {
            System.err.println("Неправильная размерность");
            return false;
        }
        return true;

    }
}