package Codes;

class Antenna {
        private int id;
        private double x;
        private double y;
        
        public Antenna(int id, double x, double y) {
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
    }
    class DOA {
        public static double calculateDOA(double angleMeasurement1, double angleMeasurement2, double antennaSpacing) {
            // Calculate the direction of arrival
            return Math.atan2(angleMeasurement2 - angleMeasurement1, antennaSpacing);
        }
    }
    public class kk {
        public static void main(String[] args) {
            // Create antenna objects with known positions
            Antenna antenna1 = new Antenna(1, 0.0, 0.0);
            Antenna antenna2 = new Antenna(2, 3.0, 0.0);
            
            // Get angle measurements from your system (in degrees)
            double angleMeasurement1 = 30.0; // Example angle measurement at antenna 1
            double angleMeasurement2 = 45.0; // Example angle measurement at antenna 2
            
            // Assuming a known antenna spacing
            double antennaSpacing = 3.0; // meters
            
            // Convert angle measurements to radians
            double angle1Rad = Math.toRadians(angleMeasurement1);
            double angle2Rad = Math.toRadians(angleMeasurement2);
            
            // Calculate DOA
            double doa = DOA.calculateDOA(angle1Rad, angle2Rad, antennaSpacing);
            
            // Output the estimated source direction
            System.out.println("Estimated source direction: " + Math.toDegrees(doa) + " degrees");
        }
    }