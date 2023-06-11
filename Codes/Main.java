package Codes;

import Codes.Receiver.TDOA;

class Receiver {
    private int id;
    private double x;
    private double y;
    
    public Receiver(int id, double x, double y) {
        this.id = id;
        this.x = x;
        this.y = y;
    }
    
    // Getters for position and ID
    public int getId() {
        return id;
    }
    
    public double getX() {
        return x;
    }
    
    public double getY() {
        return y;
    }
    
    public class TDOA {
        public static double calculateTDOA(double receptionTime1, double receptionTime2) {
            // Assuming the speed of signal propagation is known and constant
            double speedOfPropagation = 300000; // meters per second
            
            // Calculate the time difference of arrival
            return (receptionTime2 - receptionTime1) * speedOfPropagation;
        }
    }
}
    
    public class Main {
        public static void main(String[] args) {
            // Create receiver objects with known positions
            Receiver receiver1 = new Receiver(1, 0.0, 0.0);
            Receiver receiver2 = new Receiver(2, 3.0, 0.0);
            
            // Get reception times from your system
            double receptionTime1 = 10.5; // Example reception time at receiver 1
            double receptionTime2 = 12.3; // Example reception time at receiver 2
            
            // Calculate TDOA
            double tdoa = TDOA.calculateTDOA(receptionTime1, receptionTime2);
            
            // Estimate source position based on TDOA and receiver positions
            double xSource = receiver1.getX() + (tdoa * (receiver2.getX() - receiver1.getX())) / (receiver2.getId() - receiver1.getId());
            double ySource = receiver1.getY() + (tdoa * (receiver2.getY() - receiver1.getY())) / (receiver2.getId() - receiver1.getId());
            
            // Output the estimated source position
            System.out.println("Estimated source position: (" + xSource + ", " + ySource + ")");
        }
    }
    
