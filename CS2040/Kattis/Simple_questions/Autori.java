import java.util.*;

public class Autori {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        String namelist = sc.next();
        String auto = "";
        auto += namelist.charAt(0);
        for (int i=1; i<namelist.length(); i++){
            char c = namelist.charAt(i);
            if(c == '-'){
                i++;
                auto += namelist.charAt(i);
            }
        }
        System.out.println(auto);
    }
}
