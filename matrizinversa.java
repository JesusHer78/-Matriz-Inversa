import java.io.*;
import java.util.*;

public class matrizinversa {

    private static final String BASE_PATH = "C:\\archivos\\";

    public static void main(String[] args) {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        try {
            System.out.println("Archivo de entrada:");
            String input = br.readLine();

            System.out.println("Archivo de salida:");
            String output = br.readLine();

            String inputPath = BASE_PATH + input;
            String outputPath = BASE_PATH + output;

            double[][] matriz = cargarMatriz(inputPath);
            double[][] inversa = calcularInversa(matriz);

            guardarResultado(outputPath, matriz, inversa);
            mostrarEnConsola(matriz, inversa);

            System.out.println("\nArchivo generado correctamente en: " + outputPath);

        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    // ------------------ LECTURA ---------------------

    public static double[][] cargarMatriz(String ruta) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(ruta));
        List<double[]> filas = new ArrayList<>();

        String linea;
        while ((linea = br.readLine()) != null) {
            String[] parts = linea.trim().split("\\s+");
            double[] row = new double[parts.length];

            for (int i = 0; i < parts.length; i++) {
                row[i] = Double.parseDouble(parts[i]);
            }
            filas.add(row);
        }
        br.close();

        return filas.toArray(new double[0][]);
    }

    // ------------------ PROCESO ---------------------

    public static double[][] calcularInversa(double[][] A) {

        int n = A.length;

        for (double[] r : A)
            if (r.length != n)
                throw new RuntimeException("Matriz NO cuadrada.");

        double[][] aug = new double[n][2 * n];

        for (int i = 0; i < n; i++) {
            System.arraycopy(A[i], 0, aug[i], 0, n);
            aug[i][n + i] = 1;
        }

        for (int i = 0; i < n; i++) {

            double pivote = aug[i][i];
            if (pivote == 0) throw new RuntimeException("La matriz NO es invertible.");

            for (int j = 0; j < 2 * n; j++)
                aug[i][j] /= pivote;

            for (int f = 0; f < n; f++) {
                if (f != i) {
                    double factor = aug[f][i];
                    for (int j = 0; j < 2 * n; j++)
                        aug[f][j] -= factor * aug[i][j];
                }
            }
        }

        double[][] inv = new double[n][n];

        for (int i = 0; i < n; i++) {
            System.arraycopy(aug[i], n, inv[i], 0, n);
        }

        return inv;
    }

    // ------------------ SALIDA ---------------------

    public static void guardarResultado(String ruta, double[][] original, double[][] inversa) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(ruta));

        bw.write("Matriz original:\n");
        for (double[] f : original) {
            for (double v : f) bw.write(v + " ");
            bw.write("\n");
        }

        bw.write("\nMatriz inversa:\n");
        for (double[] f : inversa) {
            for (double v : f) bw.write(v + " ");
            bw.write("\n");
        }

        bw.close();
    }

    public static void mostrarEnConsola(double[][] original, double[][] inversa) {
        System.out.println("Matriz original:");
        for (double[] f : original) {
            for (double v : f) System.out.print(v + " ");
            System.out.println();
        }

        System.out.println("\nMatriz inversa:");
        for (double[] f : inversa) {
            for (double v : f) System.out.print(v + " ");
            System.out.println();
        }
    }
}
