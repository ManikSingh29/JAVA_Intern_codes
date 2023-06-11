package Codes;

import java.util.Scanner;

public class TOA_App
{
    public static void main(String[]args)
    {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the Absolute time in seconds= ");
        int time = sc.nextInt();
        double speed = 3.6 * 3 * Math.pow(10, 3);
        float ab_time= (float) (time*0.278);
        double distance = ab_time * speed ;
        System.out.println("Distance required = "+distance+"Km " );
    }
}