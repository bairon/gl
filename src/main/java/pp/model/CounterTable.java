package pp.model;

import pp.model.enums.BtlType;
import pp.model.enums.GladType;

import java.util.Arrays;

/**
 * @author alexander.sokolovsky.a@gmail.com
 */
public final class CounterTable {
	// use in battles
	public static final int [] counter(GladType myType, GladType oppType, int [] params, BtlType btlType) {
		int[] counter = FT[(int) myType.getId()][(int) oppType.getId()];
		return counter;
	}
	// use only if opponents 100% know what type you are using, good for duels and survivals
	public static final int [] antiCounter(GladType myType, GladType oppType, int [] params, BtlType btlType) {
		int [] antiCounter =  FT[(int)myType.getId()][(int)oppType.getId()];
		if ((btlType == BtlType.DUEL || btlType == BtlType.SURVIVAL) && (myType != GladType.Hoplomachus) && (oppType != GladType.Hoplomachus)) {
			int [] counter = FT[(int)myType.getId()][(int)oppType.getId()];
			int [] counterToMe = FT[(int)oppType.getId()][(int)myType.getId()];
			antiCounter = new int[]{counter[0], counterToMe[2], 100 - counterToMe[1]};
			return antiCounter;
		}
		return antiCounter;
	}
	// added stubs for 0 elements to make life easier
	public static final int[][][] FT = {
			{
					{0, 0, 0},
					{0, 0, 0},
					{0, 0, 0},
					{0, 0, 0},
					{0, 0, 0},
					{0, 0, 0},
					{0, 0, 0},
					{0, 0, 0},
					{0, 0, 0},
					{0, 0, 0}
			},
			{
					{0, 0, 0},
					{35, 20, 70},
					{100, 20, 70},
					{100, 20, 0},
					{100, 20, 100},
					{100, 20, 20},
					{30, 0, 0},
					{100, 80, 20},
					{0, 0, 0},
					{0, 0, 0},
					{0, 0, 0}
			},
			{
					{0, 0, 0},
					{100, 20, 80},
					{80, 80, 80},
					{80, 0, 0},
					{80, 20, 100},
					{80, 80, 20},
					{30, 0, 0},
					{30, 100, 20},
					{0, 0, 0},
					{0, 0, 0},
					{0, 0, 0}
			},
			{
					{0, 0, 0},
					{0, 100, 30},
					{0, 100, 80},
					{0, 100, 20, 70, 0, 80},
					{0, 100, 100},
					{0, 100, 20},
					{0, 100, 0},
					{0, 100, 30},
					{0, 0, 0},
					{0, 0, 0},
					{0, 0, 0}
			},
			{
					{0, 0, 0},
					{30, 20, 20},
					{100, 20, 80},
					{100, 0, 0},
					{100, 30, 100},
					{100, 30, 20},
					{30, 0, 0},
					{30, 80, 20},
					{0, 0, 0},
					{0, 0, 0},
					{0, 0, 0}
			},
			{
					{0, 0, 0},
					{30, 20, 20},
					{100, 80, 80},
					{100, 0, 0},
					{100, 80, 100},
					{100, 80, 20},
					{30, 0, 0 },
					{30, 100, 20},
					{0, 0, 0},
					{0, 0, 0},
					{0, 0, 0}
			},
			{
					{0, 0, 0},
					{100, 0, 0},
					{100, 100, 80},
					{100, 0, 0},
					{100, 80, 100},
					{100, 100, 0},
					{100, 100, 0},
					{100, 100, 20},
					{0, 0, 0},
					{0, 0, 0},
					{0, 0, 0}
			},
			{
					{0, 0, 0},
					{0, 20, 0},
					{0, 80, 100},
					{0, 80, 100},
					{0, 80, 100},
					{0, 80, 100},
					{0, 0, 100},
					{0, 100, 100},
					{0, 0, 0},
					{0, 0, 0},
					{0, 0, 0}
			},
			{
					{0, 0, 0},
					{0, 0, 0},
					{0, 0, 0},
					{0, 0, 0},
					{0, 0, 0},
					{0, 0, 0},
					{0, 0, 0},
					{0, 0, 0},
					{0, 0, 0},
					{0, 0, 0},
					{0, 0, 0}
			},
			{
					{0, 0, 0},
					{0, 0, 0},
					{0, 0, 0},
					{0, 0, 0},
					{0, 0, 0},
					{0, 0, 0},
					{0, 0, 0},
					{0, 0, 0},
					{0, 0, 0},
					{0, 0, 0},
					{0, 0, 0}
			},
			{
					{0, 0, 0},
					{0, 0, 0},
					{0, 0, 0},
					{0, 0, 0},
					{0, 0, 0},
					{0, 0, 0},
					{0, 0, 0},
					{0, 0, 0},
					{0, 0, 0},
					{0, 0, 0},
					{0, 0, 0}
			},

	};

	public static void main(String[] args) {
		print(antiCounter(GladType.Velit, GladType.Retiarius, null, BtlType.DUEL));
	}
	public static void print(int [] array) {
		StringBuilder sb = new StringBuilder();
		for (int i : array) {
			sb.append(Integer.toString(i));
			sb.append(' ');
		}
		System.out.println(sb.toString());
	}

}
