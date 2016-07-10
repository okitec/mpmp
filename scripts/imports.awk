# imports.awk: print list of (user-pkg, user-class, imported-pkg, imported-class) tuples
# input: class-name import|package line;
#     e.g.: Controller package controller;
#     and : Controller import model.Player;
# output: user-pkg user-class imported-pkg imported-class
#     e.g.: controller Controller model Player

{
	sub(/\;/, "", $3)

	if($2 ~ /package/) {
		pkg[$1] = $3
	} else if($2 ~ /import/) {
		imp[$1] = imp[$1] "\n" pkg[$1] " " $1 " " $3
	}
}

END {
	for(f in imp)
		print imp[f]
}
