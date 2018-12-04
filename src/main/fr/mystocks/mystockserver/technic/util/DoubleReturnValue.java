package fr.mystocks.mystockserver.technic.util;

/**
 * Permet de retourner plusieurs valeurs d'une méthode
 * @author auzanne
 *
 * @param <T>
 * @param <V>
 */
public class DoubleReturnValue<T, V> {

	private T value1;
	private V value2;

	public DoubleReturnValue() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DoubleReturnValue(T value1, V value2) {
		super();
		this.value1 = value1;
		this.value2 = value2;
	}

	public T getValue1() {
		return value1;
	}

	public void setValue1(T value1) {
		this.value1 = value1;
	}

	public V getValue2() {
		return value2;
	}

	public void setValue2(V value2) {
		this.value2 = value2;
	}


}
