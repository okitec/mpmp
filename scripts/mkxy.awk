# mkxy.awk: prepare pos-to-xy lookup table for mpmp
# The positions start at 0 and increase clockwise.
# output: pos x y x2 y2

BEGIN {
	x = 0
	y = 0

	for(pos = 0; pos < 40; pos++) {
		pos2xy(pos)
		swaporigin(x, y)
		printf("%2d    %2d %2d    %2d %2d\n", pos, x, y, x2, y2)
	}
}

# pos2xy: convert position to coordinates
# The coordinate system's origin is (0|0) at pos 0 (start)
# and becomes (10|10) at pos 20 (free parking).
function pos2xy(pos) {
	pos %= 40

	if(pos <= 10) {
		x = pos; y = 0
	} else if(pos > 10 && pos < 20) {
		x = 10; y = pos%10
	} else if(pos > 20 && pos < 30) {
		x = 10 - pos%10; y = 10
	} else if(pos > 30 && pos < 40) {
		x = 0; y = 10 - pos%10
	} else if(pos == 20) {
		x = 10; y = 10
	} else if(pos == 30) {
		x = 0; y = 10
	}
}

# swaporigin: translate the axes so that free parking at pos 20 is the new (0|0)
function swaporigin(x, y) {
	x2 = abs(x - 10)
	y2 = abs(y - 10)
}

# abs: return absolute value
function abs(a) {
	if(a < 0)
		return -a
	return a
}
