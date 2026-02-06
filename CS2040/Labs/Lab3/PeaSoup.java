import java.util.*;

public class PeaSoup {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int restaurantCount = sc.nextInt();
        boolean found = false;

        for (int i=0; i<restaurantCount; i++){
            int peaSoupCount = 0;
            int pancakesCount = 0;
            int menuSize = sc.nextInt();
            String restaurant = sc.nextLine();
            restaurant = sc.nextLine();
            for (int j=0; j<menuSize; j++){
                String menuItem = sc.nextLine();
                if(menuItem.equals("pea soup")){
                    peaSoupCount ++;
                }
                else if (menuItem.equals("pancakes")){
                    pancakesCount ++;
                }
                if(peaSoupCount==1 && pancakesCount ==1){
                    System.out.println(restaurant);
                    found = true;
                    break;
                }
            }
            if (found) break;
        }
        
        if(!found){
            System.out.println("Anywhere is fine I guess");
        }
    }
}
