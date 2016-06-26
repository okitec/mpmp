# xy2java.awk: convert lookup table to a constructor of Converter.

BEGIN {
	"date" | getline d

	printf("\t/* generated at %s */\n", d)
	printf("\tpublic Converter(int wfld, int hfld) {\n")
	printf("\t\tpos2xypx = new Point[Field.Nfields];\n\n")
}

NF == 5 {
	printf("\t\tpos2xypx[%2d] = mkpt(%2d, %2d);\n", $1, $4, $5)
}

END {
	printf("\t}\n\n")
}
