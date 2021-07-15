import java.util.Comparator;

public class TupleComparator implements Comparator<Tuple<String, Integer>> {

	@Override
	public int compare(Tuple<String, Integer> o1, Tuple<String, Integer> o2) {

		return o1.getSecond().compareTo(o2.getSecond());
	}
}
