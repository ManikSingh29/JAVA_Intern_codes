import java.util.Arrays;

public class TOAEstimation {
   public static void main(String[] args) {
        int N = 200;
        double[] rxSensorX = new double[N];
        double[] rxSensorY = new double[N];
        double sourceX = 0.5;
        double sourceY = -0.2;
        double[] noise = new double[N];

        // Generate random values for rxSensorX and rxSensorY
        for (int i = 0; i < N; i++) {
            rxSensorX[i] = Math.random();
            rxSensorY[i] = Math.random();
            noise[i] = 0.0;
        }

        // Assign specific values for rxSensorX, rxSensorY, sourceX, and sourceY
        rxSensorX = new double[]{1, 1, 1, 1};
        rxSensorY = new double[]{2, 4, 6, 8};
        sourceX = -0.486;
        sourceY = 1.5;

        double[] relSourcePos = getSourceWithTDOA(rxSensorX, rxSensorY, sourceX, sourceY, noise);
        double[] rangeVector = new double[]{0.6003, 2.48668, 4.4590, 6.5170};
        double[] sourcePos = getSourceWithTDOAWithRange(rxSensorX, rxSensorY, rangeVector, 3);

        System.out.println("Relative Source Position:");
        System.out.println("X: " + relSourcePos[0]);
        System.out.println("Y: " + relSourcePos[1]);

        System.out.println("Source Position with Range:");
        System.out.println("X: " + sourcePos[0]);
        System.out.println("Y: " + sourcePos[1]);
    }

    public static double[] getSourceWithTDOA(double[] rxSensorX, double[] rxSensorY, double sourceX, double sourceY, double[] noiseVec) {
        int N = rxSensorX.length;
        double[] relRxSensorX = new double[N - 1];
        double[] relRxSensorY = new double[N - 1];
        double relSourceX = sourceX - rxSensorX[0];
        double relSourceY = sourceY - rxSensorY[0];
        double relSourceRange = Math.sqrt(relSourceX * relSourceX + relSourceY * relSourceY);
        double[] RTrue = new double[N - 1];

        for (int i = 1; i < N; i++) {
            relRxSensorX[i - 1] = rxSensorX[i] - rxSensorX[0];
            relRxSensorY[i - 1] = rxSensorY[i] - rxSensorY[0];
            RTrue[i - 1] = Math.sqrt((rxSensorX[i] - sourceX) * (rxSensorX[i] - sourceX)
                    + (rxSensorY[i] - sourceY) * (rxSensorY[i] - sourceY));
        }

        double[] R = new double[N - 1];
        for (int i = 0; i < N - 1; i++) {
            R[i] = RTrue[i] - relSourceRange + noiseVec[i];
        }

        double[][] A = new double[N - 1][3];
        double[] b = new double[N - 1];
        for (int i = 0; i < N - 1; i++) {
            b[i] = 0.5 * (relRxSensorX[i] * relRxSensorX[i] + relRxSensorY[i] * relRxSensorY[i] - R[i] * R[i]);
            A[i][0] = relRxSensorX[i];
            A[i][1] = relRxSensorY[i];
            A[i][2] = R[i];
        }

        double[] xhat = matrixVectorMultiply(matrixInverse(A), b);
        double error = Math.sqrt((xhat[0] - relSourceX) * (xhat[0] - relSourceX)
                + (xhat[1] - relSourceY) * (xhat[1] - relSourceY));
        xhat[0] += rxSensorX[0];
        xhat[1] += rxSensorY[0];

        return new double[]{xhat[0], xhat[1], error};
    }

    public static double[] getSourceWithTDOAWithRange(double[] rxSensorX, double[] rxSensorY, double[] rangeVector, int refIndex) {
        swapElements(rxSensorX, refIndex, rxSensorX.length - 1);
        swapElements(rxSensorY, refIndex, rxSensorY.length - 1);
        swapElements(rangeVector, refIndex, rangeVector.length - 1);

        int N = rxSensorX.length;
        double[] relRxSensorX = new double[N - 1];
        double[] relRxSensorY = new double[N - 1];
        double[] RRel = new double[N - 1];

        for (int i = 0; i < N - 1; i++) {
            relRxSensorX[i] = rxSensorX[i] - rxSensorX[N - 1];
            relRxSensorY[i] = rxSensorY[i] - rxSensorY[N - 1];
            RRel[i] = rangeVector[i] - rangeVector[N - 1];
        }

        double[][] A = new double[N - 1][3];
        double[] b = new double[N - 1];
        for (int i = 0; i < N - 1; i++) {
            b[i] = 0.5 * (relRxSensorX[i] * relRxSensorX[i] + relRxSensorY[i] * relRxSensorY[i] - RRel[i] * RRel[i]);
            A[i][0] = relRxSensorX[i];
            A[i][1] = relRxSensorY[i];
            A[i][2] = RRel[i];
        }

        double[] xhat = matrixVectorMultiply(matrixInverse(A), b);
        xhat[0] += rxSensorX[N - 1];
        xhat[1] += rxSensorY[N - 1];

        return xhat;
    }

    public static void swapElements(double[] array, int i, int j) {
        double temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    public static double[][] matrixInverse(double[][] matrix) {
        int n = matrix.length;
        double[][] augmentedMatrix = new double[n][2 * n];
        double[][] inverseMatrix = new double[n][n];

        for (int i = 0; i < n; i++) {
            System.arraycopy(matrix[i], 0, augmentedMatrix[i], 0, n);
            augmentedMatrix[i][i + n] = 1;
        }

        for (int i = 0; i < n; i++) {
            double pivot = augmentedMatrix[i][i];
            for (int j = 0; j < 2 * n; j++) {
                augmentedMatrix[i][j] /= pivot;
            }
            for (int j = 0; j < n; j++) {
                if (i != j) {
                    double factor = augmentedMatrix[j][i];
                    for (int k = 0; k < 2 * n; k++) {
                        augmentedMatrix[j][k] -= factor * augmentedMatrix[i][k];
                    }
                }
            }
        }

        for (int i = 0; i < n; i++) {
            System.arraycopy(augmentedMatrix[i], n, inverseMatrix[i], 0, n);
        }

        return inverseMatrix;
    }

    public static double[] matrixVectorMultiply(double[][] matrix, double[] vector) {
        int rows = matrix.length;
        int cols = matrix[0].length;
        double[] result = new double[rows];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result[i] += matrix[i][j] * vector[j];
            }
        }
        return result;
    }
}