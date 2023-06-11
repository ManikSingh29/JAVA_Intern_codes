package Codes;
import java.util.Scanner;
public class TDOA_Apli {
    public static double calculateTDOA(double receptionTime1, double receptionTime2) {
        // Assuming the speed of signal propagation is known and constant
        double speedOfPropagation = 300000;
        return (receptionTime2 - receptionTime1) * speedOfPropagation;
    } 
   public static void main(String[]args)
   {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the Relative position for Reciver 1");
        System.out.print("X: ");
        int x1 = sc.nextInt();
        System.out.print("\nY: ");
        int y1 = sc.nextInt();
        System.out.println("Enter the Relative position for Reciver 2");
        System.out.print("X: ");
        int x2 = sc.nextInt();
        System.out.print("\nY: ");
        int y2 = sc.nextInt();

        System.out.println("Enter the Relative Time");
        System.out.print("At reciver 1 : ");
        double t1 = sc.nextDouble();
        System.out.print("\nAt reciver 2 : ");
        double t2 = sc.nextDouble();

        double tdoa = calculateTDOA(t1, t2);
        double RelativeX= x1 + (tdoa *(x2 - x1));
        double RelativeY= y2 + (tdoa *(y2 - y1));
        System.out.println("Relative position of object is");
        System.out.print("X: "+RelativeX);
        System.out.print("\nY: "+RelativeY);

   }
}
