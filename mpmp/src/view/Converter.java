package view;

import java.awt.Point;

/**
 * Converter converts the position of a field to the left upper corner pixel of that field
 * relative to the gameboard. wfld and hfld are the width and height of an unrotated field
 * in pixels.
 *
 * @author oki
 */
public class Converter {
	private Point pos2xypx[];
	private int hfld;
	private int wfld;

	/* generated at Sun 26 Jun 16:42:11 CEST 2016 */
	public Converter(int wfld, int hfld) {
		pos2xypx = new Point[model.Field.Nfields];

		pos2xypx[ 0] = mkpt(10, 10);
		pos2xypx[ 1] = mkpt( 9, 10);
		pos2xypx[ 2] = mkpt( 8, 10);
		pos2xypx[ 3] = mkpt( 7, 10);
		pos2xypx[ 4] = mkpt( 6, 10);
		pos2xypx[ 5] = mkpt( 5, 10);
		pos2xypx[ 6] = mkpt( 4, 10);
		pos2xypx[ 7] = mkpt( 3, 10);
		pos2xypx[ 8] = mkpt( 2, 10);
		pos2xypx[ 9] = mkpt( 1, 10);
		pos2xypx[10] = mkpt( 0, 10);
		pos2xypx[11] = mkpt( 0,  9);
		pos2xypx[12] = mkpt( 0,  8);
		pos2xypx[13] = mkpt( 0,  7);
		pos2xypx[14] = mkpt( 0,  6);
		pos2xypx[15] = mkpt( 0,  5);
		pos2xypx[16] = mkpt( 0,  4);
		pos2xypx[17] = mkpt( 0,  3);
		pos2xypx[18] = mkpt( 0,  2);
		pos2xypx[19] = mkpt( 0,  1);
		pos2xypx[20] = mkpt( 0,  0);
		pos2xypx[21] = mkpt( 1,  0);
		pos2xypx[22] = mkpt( 2,  0);
		pos2xypx[23] = mkpt( 3,  0);
		pos2xypx[24] = mkpt( 4,  0);
		pos2xypx[25] = mkpt( 5,  0);
		pos2xypx[26] = mkpt( 6,  0);
		pos2xypx[27] = mkpt( 7,  0);
		pos2xypx[28] = mkpt( 8,  0);
		pos2xypx[29] = mkpt( 9,  0);
		pos2xypx[30] = mkpt(10,  0);
		pos2xypx[31] = mkpt(10,  1);
		pos2xypx[32] = mkpt(10,  2);
		pos2xypx[33] = mkpt(10,  3);
		pos2xypx[34] = mkpt(10,  4);
		pos2xypx[35] = mkpt(10,  5);
		pos2xypx[36] = mkpt(10,  6);
		pos2xypx[37] = mkpt(10,  7);
		pos2xypx[38] = mkpt(10,  8);
		pos2xypx[39] = mkpt(10,  9);
	}

	/**
	 * mkpt takes a field in the left-handed coordinate system where (0|0) is at free parking
	 * and returns the pixel of the upper left corner of the field relative to the gameboard origin. 
	 */
	private Point mkpt(int fx, int fy) {
		int pxx, pxy; /* pixel x and y */

		if(fx == 0)
			pxx = 0;
		else
			pxx = hfld + (fx-1) * wfld;

		if(fy == 0)
			pxy = 0;
		else
			pxy = hfld + (fy-1) * wfld;

		return new Point(pxx, pxy); 
	}
}
