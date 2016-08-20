#include "funcoes.h"

#include <stdio.h>

int main()
{		
	numeroDeLivrosRegistados = 0;

	while(1)
	{							
		int option;
	
		imprimeMenu();	
		
		scanf("%d", &option);
				
		switch(option)
		{
			case 1: insereLivro();
					break;
			
			case 2: listarLivros();
					break;		
		
			case 0: sair();
					return 0;			
		}
	}
}
