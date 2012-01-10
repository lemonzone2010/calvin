package collection;

public class SortTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	static void insertSort(int[] array) {
		int in, out;
		for (out = 0; out < array.length; out++) {
			int tmp = array[out];
			in = out;
			while (in > 0 && array[in - 1] >= tmp) {
				array[in] = array[in - 1];
				--in;
			}
			array[in] = tmp;
		}
	}

}
