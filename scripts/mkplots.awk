# mkplots: read plots.md and generate content of PlotGroup.init.
# input: pipe-separated (name, price, rents, rents, ..., house price, color/type, pos) tuples from refmt-plots.awk.
# output: Java initialisers for ALL THE THINGS, to be stuffed into Model.initPlots

BEGIN {
	FS = "( |\t)*\|( |\t)*"

	"date" | getline d
	printf("\t\t/* generated at %s */\n", d)

	emitpgrp("stations", 1)
	groups["Bahnhof"] = 1    # don't generate another line
}

NF >= 10 {
	name = $1
	price = $2
	houseprice = $9
	pgrp = $10
	pos = $11

	# remove non-Java identifier characters (spaces, hash signs)
	sub(/#/, "", pgrp)


	groups[pgrp]++
	if(groups[pgrp] == 1)  # first encounter
		emitpgrp(pgrp)

	if(name ~ /[Bb]ahnhof/)
		emitstation(name, price, pos)
	else
		emithouseplot(pgrp, name, price, srents($3, $4, $5, $6, $7, $8), houseprice, pos)
}

END {
}

# srents: take rents for 0..4 houses or hotel, return string with equivalent Java array
function srents(r0, r1, r2, r3, r4, rh) {
	return sprintf("new int[]{%d, %d, %d, %d, %d}", r0, r1, r2, r3, r4, rh)
}

# sjname: return Java identifier without spaces or hyphens
function sjname(nm,         jname) {
	jname = nm
	gsub(/(-| |\t)+/, "", jname)
	return jname
}

# emitaddplot: print addPlot line for that position and name
# prefix is "HP" (house plot) or "TS" (trainstation)
function emitaddplot(pos, prefix, nm) {
	if(positions[pos]++ > 1) {
		print "FATAL ERROR: duplicate position ", pos
		exit 1
	}

	printf("\t\taddPlot(%d, %s%s);\n", pos, prefix, sjname(nm))
}

# emitstation: print instructions for train station
function emitstation(nm, pri, pos) {
	printf("\t\tTrainStation TS%s = new TrainStation(PGstations, \"%s\", %d);\n", sjname(nm), nm, pri)
	printf("\t\tPGstations.add(TS%s);\n", sjname(nm), nm, pri)
	emitaddplot(pos, "TS", sjname(nm))
}

# emithouseplot: print instructions for houseplot
function emithouseplot(pg, nm, pri, rents, hp, pos) {
	printf("\t\tHousePlot HP%s = new HousePlot(PG%s, \"%s\", %d, %s, %d);\n", sjname(nm), pg, nm, pri, rents, hp)
	printf("\t\tPG%s.add(HP%s);\n", pg, sjname(nm))
	emitaddplot(pos, "HP", sjname(nm))
}

# emitpgrp: print instructions for plot group
function emitpgrp(pg, nobuild, nobuy,           attr) {
	attr = "0"
	if(nobuild)
		attr = attr " | PlotGroup.NoBuild"
	if(nobuy)
		attr = attr " | PlotGroup.NoBuy"

	# initial newline for blank line
	printf("\n\t\tPlotGroup PG%s = new PlotGroup(%s);\n", pg, attr)
}
