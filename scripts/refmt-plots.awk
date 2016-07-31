# refmt-plots.awk: reformat the plots.md file and print to stdout
# input: plots.md
# output: name, price, rents, price per house, color/type, pos; all pipe-separated

BEGIN {
	FS = "( |\t)*\|( |\t)*"
}

NF >= 10 {
	# hacky, but works: ignore header line and divider (more than two hyphens)
	if($1 == "Straßenname" || $1 ~ /--+/)
		next

	print $0
}
