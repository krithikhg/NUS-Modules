//Krithikh Gopalakrishnan A0243980Y
//Collaborators: Loh Zi Quan Keith

import java.util.*;

public class T9Spelling{

    static String[] dict = new String[256];
    static String chars = "abcdefghijklmnopqrstuvwxyz ";
    static String[] values = new String[] {"2", "22", "222", "3", "33", "333", "4", "44", "444", "5", "55", "555", "6", "66", "666", "7", "77", "777" ,"7777", "8", "88", "888", "9", "99", "999", "9999", "0"};


    public static void main(String[] args) {
        for (int i=0; i<chars.length(); i++){
            dict[chars.charAt(i)] = values[i];
        }
        
        Scanner sc = new Scanner(System.in);
        int count = sc.nextInt();
        String garbage = sc.nextLine();

        for (int i=0; i<count; i++){
            char last_digit = '%';
            String input = sc.nextLine();
            String output = "";

            for (int j=0; j<input.length(); j++){
                char letter = input.charAt(j);
                String t_nine = dict[letter];
                if(t_nine.charAt(0) == last_digit){
                    output += " ";
                    output += t_nine;
                    last_digit = t_nine.charAt(0);
                }

                else{
                    output += t_nine;
                    last_digit = t_nine.charAt(0);
                }
            }

            System.out.println("Case #" + (i+1) + ": " + output);
        }


    }
}