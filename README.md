Trabalho (Programação) de Avaliação de Desempenho

O objetivo do trabalho é fazer um programa em qualquer linguagem que simule o comportamento de uma fila em um roteador com somente um servidor (porta de roteamento). A simulação resultará em uma planilha (tabela) com as seguintes colunas:
1)  Número do pacote (ordem de chegada do pacote). No máximo 20 pacotes.
2) Tempo desde a última chegada do pacote anterior (microssegundos)
3) Tempo de chegada no relógio
4) Tempo de serviço ou tempo de roteamento (microssegundos) 
5) Tempo de início do roteamento (microssegundos) 
6) Tempo do pacote na fila do roteador (microssegundos)
7) Tempo final do roteamento no relógio
8) Tempo do pacote no roteador (microssegundos), ou seja, fila + roteamento 
9) Tempo livre do servidor do roteador ou tempo que o servidor do roteador ficou ocupado (microssegundos) 

A geração dos tempos das colunas 2 e 4 será realizada pelos seguinte algoritmo:
a) Para gerar os tempos entre chegadas (TEC) de pacotes, desenvolver um Gerador de Variáveis Aleatórias (GVA) usando o Método da Transformação Inversa (pág. 474 do livro texto) para a Distribuição Exponencial com média 12 microssegundos para o tempo desde a última chegada do pacote anterior (coluna 2).

b) Para gerar os tempos de serviço ou de roteamento (TS), desenvolver um GVA usando o Método da Rejeição (pág. 494 do livro texto) para a Distribuição Normal com média de 10 microssegundos para o tempo de roteamento (coluna 4) e desvio padrão igual a 1 (um).
 
Entregar o código fonte do programa e o resultado da simulação usando o formato de tabela ou planilha conforme modelo dado em aula. 

