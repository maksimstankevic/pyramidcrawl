package local.pyramid;

import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) {
        Stream<Integer> s = Stream.of(1);
        IntStream is = s.mapToInt(x -> x);
        DoubleStream ds = s.mapToDouble(x -> x);
        Stream<Double> s2 = ds.mapToObj(x -> x);
        s2.forEach(System.out::print);
	}
}
