	/**
	 * cornerRelPx takes a field position and returns the upper left corner pixel of the field
	 * relative to the gameboard origin. The Point must then be translated to absolute coordinates
	 * and rotated.
	 */
	public Point cornerRelPx(int pos) {
		pos %= Field.Nfields;
		return pos2xypx[pos];
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
