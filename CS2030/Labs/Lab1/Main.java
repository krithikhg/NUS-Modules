import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.List;

void main() {}

IntStream twinPrimes(int n){
	return IntStream.rangeClosed(3, n).
		filter(x -> isPrime(x)).
		filter(x -> (isPrime(x-2) || isPrime(x+2)));
}



private boolean isPrime(int n){
	return IntStream.range(2, n).
		noneMatch(x -> n % x == 0);
}


String reverse(String str){
	return IntStream.range(0, str.length()).
		boxed().
		map(x -> str.substring(x, x+1)).
		reduce((x, y) -> y + x).
		orElse("");
}

int countRepeats(List<Integer> list){
	return IntStream.range(0, list.size() - 1).
		filter(x -> list.get(x+1) == list.get(x)).
		filter(x -> (x-1 >= 0) ? list.get(x-1) != list.get(x) : true).
		reduce(0, (x,y) -> x+1);
}
