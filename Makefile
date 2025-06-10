all: diff

diff:
	git diff > diff.out

.PHONY: diff
