#ifndef FUNCOES_H_
#define FUNCOES_H_

#define NUMEROLIVROS 100
#define MAXTITULO 200
#define MAXNOME 200

typedef struct
{
  int dia;
  int mes;
  int ano;
} Data;

typedef struct
{
  char titulo[MAXTITULO];
  char autor[MAXNOME];
  unsigned long int isbn;
  int anoPublicacao;
  int numeroDaCopia;
  Data dataEmprestimo;
  Data dataRetorno;
} Livro;

int numeroDeLivrosRegistados;
Livro livros[NUMEROLIVROS];

// le do standard input uma linha
void stringMultiplasPalavras(char *str, int max);

// imprime o Menu
void imprimeMenu();

// le um livro do standard input e insere-o no array livros
void insereLivro();

// escreve no standard output todos os livros no array livros
void listarLivros();

// escreve no standard output todos os livros no array livros
void sair();

#endif /* FUNCOES_H_ */
