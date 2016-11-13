	/**
	 * cornerRelPx takes a field position and returns the upper left corner pixel of the field
	 * relative to the gameboard origin. The Point must then be translated to absolute coordinates
	 * and rotated.
	 */
	public Point cornerRelPx(int pos) {
		pos %= Field.Nfields;
		Point pt = pos2xypx[pos];
		return new Point(pt.x, pt.y);
	}

	public Point middleRelPx(int pos) {
		Point p = cornerRelPx(pos);

		if(isPortrait(pos))
			return new Point(p.x + wfld/2, p.y + hfld/2);
		else if(isLandscape(pos))
			return new Point(p.x + hfld/2, p.y + wfld/2);
		else                                                    /* corners */
			return new Point(p.x + hfld/2, p.y + hfld/2);
	}

	/**
	 * isPortrait: returns true if the field in question is in 'portrait' orientation,
	 * that is, has more height than width, as is the case at the top and at the bottom.
	 */
	public boolean isPortrait(int pos) {
		return pos > 0 && pos < 10 || pos > 20 && pos < 30;
	}

	/**
	 * isLandscape: returns true if the field in question is in 'landscale' orientation,
	 * that is, has more width than height, as is the case on the left and the right.
	 */
	public boolean isLandscape(int pos) {
		return pos > 10 && pos < 20 || pos > 30 && pos <= 39;
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
