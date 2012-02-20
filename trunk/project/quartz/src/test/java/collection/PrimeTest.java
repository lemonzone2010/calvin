package collection;

public class PrimeTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int N = 15;
		for (int i = 1; i < N; i++) {
			if (isPrime(i)) {
				System.out.println(i);
			}
		}

	}

	private static boolean isPrime(final int i) {
		for (int j = 2; j < i; j++) {//非1及自己
			if (i % j == 0) {
				return false;
			}
		}
		return true;
	}

}
