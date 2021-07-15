
public class Tuple<T, K> {
	private T first;
	private K second;

	public Tuple(T first, K second) {
		this.first = first;
		this.second = second;

	}

	public T getFirst() {
		return first;
	}

	public void setFirst(T first) {
		this.first = first;
	}

	public K getSecond() {
		return second;
	}

	public void setSecond(K second) {
		this.second = second;
	}

	@Override
	public String toString() {
		return "(" + first + "," + second + ")";
	}

}
