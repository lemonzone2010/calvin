package collection;

public class Search {
	public static int fibonacci(int[] number, int des) {
		int[] fib = createFibonacci(number.length);
		int max = number.length - 1;
		int y = findY(fib, max + 1);
		int m = max - fib[y];
		int x = y - 1;
		// System.out.printf("\nx=%d, m=%d, fib[x]=%d", x, m, fib[x]);
		int i = x;

		if (number[i] < des)
			i += m;

		while (fib[x] > 0) {
			if (number[i] < des)
				i += fib[--x];
			else if (number[i] > des)
				i -= fib[--x];
			else
				return i;
		}

		return -1;

	}

	private static int[] createFibonacci(int max) {
		int[] fib = new int[max];
		for (int i = 0; i < fib.length; i++) {
			fib[i] = Integer.MIN_VALUE;
		}

		fib[0] = 0;
		fib[1] = 1;

		for (int i = 2; i < max; i++)
			fib[i] = fib[i - 1] + fib[i - 2];

		return fib;
	}

	private static int findY(int[] fib, int n) {
		int i = 0;
		while (fib[i] <= n)
			i++;
		i--;
		return i;
	}

	public static void main(String[] args) {
		int[] number = { 1, 2, 3, 5, 6, 8, 9, 10, 11 };
		int find = Search.fibonacci(number, 1);
		System.out.println(find >= 0 ? "找到數值於索引" + find : "找不到數值");
	}
}