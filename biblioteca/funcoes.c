#include "funcoes.h"

#include <stdio.h>
#include <string.h>

void stringMultiplasPalavras(char *str, int max)
{
	char tmp[100] = "";
		
	scanf("%s", tmp);
	strncat(str, tmp, max);
	
	while(getchar() != '\n')
	{
		scanf("%s", tmp);
		strncat(str, " ", max);
		strncat(str, tmp, max);
	}
}

void imprimeMenu()
{
	printf("\n****BIBLIOTECA DO IST****\n");
	printf("1 - Inserir novo livro\n");
	printf("2 - Listar livros\n");
	printf("3 - Procurar livro por isbn\n");
	printf("4 - Procurar livro por titulo\n");
	printf("5 - Alterar titulo do livro\n");
	printf("6 - Apagar livro pelo isbn\n");
	printf("7 - Registar data de emprestimo de um livro pelo isbn\n");
	printf("8 - Registar data de retorno de um livro pelo isbn\n");
	printf("0 - Sair\n");
	printf("*************************\n\n");
}

void insereLivro()
{	
	char titulo[MAXTITULO]="";
	char autor[MAXNOME]="";
	unsigned long int isbn;
	int anoPublicacao;
	int numeroDaCopia;
	Data dataEmprestimo;
	Data dataRetorno;

	printf("1: Pediu para inserir um novo livro.\n");
	printf("...\n");
	//printf("Insira os seguintes campos:\n");
	
	// printf("Titulo -> ");
	stringMultiplasPalavras(titulo, MAXTITULO);
	
	// printf("Autor -> ");
	stringMultiplasPalavras(autor, MAXNOME);
	
	// printf("ISBN -> ");
	scanf("%lu", &isbn);
	
	// printf("ano de publicacao -> ");
	scanf("%d", &anoPublicacao);
	
	// printf("#copia -> ");
	scanf("%d", &numeroDaCopia);
	
	// printf("data de emprestimo (DD/MM/AAAA) -> "); 
	scanf("%d/%d/%d", &dataEmprestimo.dia, &dataEmprestimo.mes, &dataEmprestimo.ano);
	
	// printf("data de retorno (DD/MM/AAAA) -> ");
	scanf("%d/%d/%d", &dataRetorno.dia, &dataRetorno.mes, &dataRetorno.ano);

	strcpy(livros[numeroDeLivrosRegistados].titulo, titulo);
	
	strcpy(livros[numeroDeLivrosRegistados].autor, autor);
	
	livros[numeroDeLivrosRegistados].isbn = isbn;
	
	livros[numeroDeLivrosRegistados].anoPublicacao = anoPublicacao;
	
	livros[numeroDeLivrosRegistados].numeroDaCopia = numeroDaCopia;
	
	livros[numeroDeLivrosRegistados].dataEmprestimo.dia = dataEmprestimo.dia;
	livros[numeroDeLivrosRegistados].dataEmprestimo.mes = dataEmprestimo.mes;
	livros[numeroDeLivrosRegistados].dataEmprestimo.ano = dataEmprestimo.ano;
	
	livros[numeroDeLivrosRegistados].dataRetorno.dia = dataRetorno.dia;
	livros[numeroDeLivrosRegistados].dataRetorno.mes = dataRetorno.mes;
	livros[numeroDeLivrosRegistados].dataRetorno.ano = dataRetorno.ano;

	numeroDeLivrosRegistados++;

	printf("...\n");
	printf("Livro %lu inserido.\n", isbn);
}

void listarLivros()
{	
	int i;
	
	printf("2: Pediu para listar os livros existentes na biblioteca.\n\n");
	
	for(i = 0; i < numeroDeLivrosRegistados; i++)
	{
		printf("Titulo -> %s\n", livros[i].titulo);
		printf("Autor -> %s\n", livros[i].autor);
		printf("ISBN -> %lu\n", livros[i].isbn);
		printf("ano de publicacao -> %d\n", livros[i].anoPublicacao);
		printf("#copia -> %d\n", livros[i].numeroDaCopia);
		printf("data de emprestimo -> %d/%d/%d\n", livros[i].dataEmprestimo.dia, livros[i].dataEmprestimo.mes, livros[i].dataEmprestimo.ano);
		printf("data de retorno -> %d/%d/%d\n\n", livros[i].dataRetorno.dia, livros[i].dataRetorno.mes, livros[i].dataRetorno.ano);
	}

	if(numeroDeLivrosRegistados > 1)
		printf("Existem %d livros na biblioteca.\n", numeroDeLivrosRegistados);
	else if(numeroDeLivrosRegistados == 1)
		printf("So existe %d livro na biblioteca.\n", numeroDeLivrosRegistados);
	else
		printf("Não existem livros na biblioteca.\n");
}	

void sair()
{
	printf("0: Pediu para sair da aplicação. Adeus.\n");
}
