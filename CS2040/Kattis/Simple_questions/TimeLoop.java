import java.util.*;

public class TimeLoop {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int count = sc.nextInt();
        for (int i=0; i<count; i++){
            System.out.println(i+1 + " Abracadabra");
        }
        sc.close();
    }
}
