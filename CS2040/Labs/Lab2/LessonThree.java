import java.util.*;

public class LessonThree {
    public static void main(String[] args){
        List<Integer> arr = new ArrayList<>();
        Scanner sc = new Scanner(System.in);
        while(true){
            int input = sc.nextInt();
            if (input == -1){
                break;
            }
            arr.add(input);
        }

        List<Integer> odd_arr = new ArrayList<>();
        List<Integer> even_arr = new ArrayList<>();
        
        for (Integer i:arr){
            if(i%2 == 1){
                odd_arr.add(i);
            }
            else{
                even_arr.add(i);
            }
        }

        odd_arr = sort(odd_arr);
        even_arr = sort(even_arr);

        for (int i=0; i<odd_arr.size();i++){
            System.out.println(odd_arr.get(i));
        }

        for (int i = even_arr.size()-1; i>=0; i--){
            System.out.println(even_arr.get(i));
        }
        sc.close();
    }

    public static List<Integer> sort(List<Integer> list){
        int size = list.size();
        if (size == 1){
            return list;
        }

        List<Integer> firstHalf = new ArrayList<>(list.subList(0, size/2));
        List<Integer> lastHalf = new ArrayList<>(list.subList(size/2, size));

        List<Integer> sortedFirst = new ArrayList<>(sort(firstHalf));
        List<Integer> sortedLast = new ArrayList<>(sort(lastHalf));

        int firstSize = sortedFirst.size();
        int lastSize = sortedLast.size();
        List<Integer> sorted = new ArrayList<>();

        int firstCounter = 0;
        int lastCounter = 0;

        while(firstCounter < firstSize && lastCounter < lastSize){
            if (sortedFirst.get(firstCounter) < sortedLast.get(lastCounter)){
                sorted.add(sortedFirst.get(firstCounter));
                firstCounter ++;
            }
            else{
                sorted.add(sortedLast.get(lastCounter));
                lastCounter ++;
            }
        }

        if(firstCounter < firstSize){
            for(int i=firstCounter; i<firstSize; i++){
                sorted.add(sortedFirst.get(i));
            }
        }
        if(lastCounter < lastSize){
            for(int i=lastCounter; i<lastSize; i++){
                sorted.add(sortedLast.get(i));
            }
        }

        return sorted;
    }
}
