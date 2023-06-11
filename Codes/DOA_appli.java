package Codes;
import java.util.Scanner;
public class DOA_appli {
    public static double calculateDOA(double angleMeasurement1, double angleMeasurement2, double antennaSpacing) {
        // Calculate the direction of arrival
        return Math.atan2(angleMeasurement2 - angleMeasurement1, antennaSpacing);
    }
    
    public static void main(String[]args)
   {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the Relative angular measurement in degrees:");
        System.out.print("For Reciver1 : ");
        double x1 =Math.toRadians( sc.nextDouble());
        System.out.print("\nFor Reciver2: ");
        double y1 =Math.toRadians( sc.nextDouble());
        System.out.println("Enter the Distance between the antennas :");
        double dis = sc.nextDouble();
        double DOA = calculateDOA(x1, y1, dis);
        System.out.println("Estimated direction of object w.r.t Reciver1");
        System.out.print("Pos: "+Math.toDegrees(DOA)+" deg");

   }
}
