package model;

import java.util.Random;

/**
 * Class Diceroll represents a dice roll: it stores the total rolled sum
 * and the amount of paschs (times when all dice show the same face).
 * The roll happens in the constructor; the values are never changed thereafter.
 */
public class Diceroll {
	private static final Random r = new Random();
	private static final int Ndice = 2;
	private static final int Nfaces = 6;

	private int sum;
	private int paschs;

	public Diceroll() {
		sum = 0;
		paschs = 0;

		roll();
	}

	public int getSum() {
		return sum;
	}

	public int getPaschs() {
		return paschs;
	}

	private void roll() {
		int rolls[] = new int[Ndice];

		for(int i = 0; i < Ndice; i++)
			rolls[i] = r.nextInt(Nfaces+1) + 1;  /* between 1 and Nfaces */

		/* check for pasches (whether all rolls are the same) */
		int r0 = rolls[0];
		for(int i = 0; i < Ndice; i++)
			if(r0 != rolls[i]) {
				sum += sum(rolls);
				return;
			}

		/* roll again if a pasch happened */
		paschs++;
		sum += sum(rolls);
		roll();
	}

	private int sum(int rolls[]) {
		int sum;

		sum = 0;
		for(int i = 0; i < Ndice; i++)
			sum += rolls[i];

		return sum;
	}
}
