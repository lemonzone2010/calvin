package collection;

import java.util.ArrayList;
import java.util.List;

public class Rank {
	final static int MAX = 100;
	final static int MIN = 0;

	public static void main(String[] args) {

		int[] number = { 12, 0, 2, 1, 2, 3, 5, 6, 8, 9, 10, 11 };
		List<Integer> score = new ArrayList<Integer>();
		for (int i : number) {
			score.add(i);
		}

		int[] juni = new int[Rank.MAX + 2];
		for (int i : score)
			juni[i]++;
		juni[Rank.MAX + 1] = 1;

		for (int i = Rank.MAX; i >= Rank.MIN; i--)
			juni[i] += juni[i + 1];

		System.out.println("得分\t排行");
		for (int i : score) {
			System.out.println(i + "\t" + juni[i + 1]);
		}
	}
}
