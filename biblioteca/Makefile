all: clean setup
	
	
compiling:
	gcc -Wall -Wextra -std=c11 -c main.c -o main.o
	gcc -Wall -Wextra -std=c11 -c funcoes.c -o funcoes.o
	
linking: 
	gcc -o main main.o funcoes.o

setup: compiling linking
	
	
exe:
	./main
	
exein:
	./main < 1.in
	
exeinout:
	./main < 1.in > 1.out	

clean:
	rm -f *.o *.exe main 
	
