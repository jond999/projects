all: dev

diff:
	git diff > diff.out

dev:
	# algorithms >> insertion_sort
	node algorithms/insertion-sort.js
	# portuguese-league-2016-2017
	open portuguese-league-2016-2017/index.html 

.PHONY: dev diff
