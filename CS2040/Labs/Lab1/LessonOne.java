import java.lang.Math;
import java.util.Scanner;
class LessOne{
    public static void main(String[] args){
        System.out.println("Hello World");

        int x = 10;
        int y = 20;
        double z = Math.hypot(x,y);
        // System.out.println("Hypotenuse" + x + y + z);
        Scanner sc = new Scanner(System.in);
        int l = sc.nextInt();
        System.out.println(l);
        sc.close();
    }
}