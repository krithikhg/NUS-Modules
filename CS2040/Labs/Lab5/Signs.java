//Krithikh Gopalakrishnan, A0243980Y
//Collaborators: Keith Loh, Jaye Ong

import java.util.*;


public class Signs {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int count = sc.nextInt();
        String garbage = sc.nextLine();
        String[] answer = new String[count];
        for (int i=0; i<count; i++){
            answer[i] = sc.nextLine();
        }
          
        Arrays.sort(answer, new SignComparator());
        for (int i=0; i<count; i++){
            System.out.println(answer[i]);
        }
    }


    public static class SignComparator implements Comparator<String> {
        public int compare(String s1, String s2){
            String m1;
            String m2;
            int l1 = s1.length();
            if(l1 %2 != 0){
                m1 = Character.toString(s1.charAt(l1/2));
            }
            else{
                m1 = Character.toString(s1.charAt(l1/2-1)) + Character.toString(s1.charAt(l1/2));
            }
            int l2 = s2.length();
            if(l2 %2 != 0){
                m2 = Character.toString(s2.charAt(l2/2));
            }
            else{
                m2 = Character.toString(s2.charAt(l2/2-1)) + Character.toString(s2.charAt(l2/2));
            }
            return m1.compareTo(m2);
        }
    }
}
