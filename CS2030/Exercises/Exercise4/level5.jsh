Dist<String> toss(Dist<String> dist, int n) {
    return dist.roll(n, 
        (x,y) -> y.equals("H") ? y + x : x + y);
}

double atLeastTwoSixes(Dist<Integer> dist, int n) {
    return dist
        .roll(n, (x,y) -> y == 6 ? x + y : x)
        .query(x -> x >= 12);
}

