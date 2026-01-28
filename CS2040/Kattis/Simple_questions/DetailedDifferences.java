import java.util.*;

public class DetailedDifferences {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int count = sc.nextInt();
        List<String> inputs = new ArrayList<>();
        for (int i=0; i<count*2; i++){
            inputs.add(sc.next());
        }
        
        List<String> outputs = new ArrayList<>();
        for (int i=0; i<count; i++){
            String first = inputs.get(i*2);
            String second = inputs.get(i*2+1);
            outputs.add(first);
            outputs.add(second);
            outputs.add(comparison(first, second));
        }

        for (int i=0; i<count; i++){
            System.out.println(outputs.get(i*3));
            System.out.println(outputs.get(i*3 + 1));
            System.out.println(outputs.get(i*3 + 2));
            System.out.println("");
        }
    }

    static String comparison(String first, String second){
        String output = "";
        int size = first.length();

        for (int i=0; i<size; i++){
            if(first.charAt(i) == second.charAt(i)){
                output += '.';
            }
            else{
                output += '*';
            }
        }
        return output;
    }
}
