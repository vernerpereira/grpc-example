# Exemplo gRPC com Kotlin
Esta solução tem 3 projetos:
- **REST**: Api REST que recebe um número e devolve o seu quadrado;
- **gRPC**: Serviço utilizando gRPC que disponibiliza quatro endpoints.
    - **findSquareUnary**: endpoint unário semelhante que tem o comportamento semelhante a um método de api REST, recebe um número e devolve o seu quadrado;
    - **findSquareServerStream**: endpoint Server Stream, recebe um número e devolve o quadrado de cada número de 1 até o número passado como parâmetro de entrada por stream;
    - **findSquareClientStream**: endpoint Client Stream, recebe um stream de números e devolve o maior quadrado dos números passados;
    - **findSquareFullDuplexStream**: endpoint Full-duplex, recebe um stream de números e devolve um stream que compõe o quadrado de cada número de 1 até o número passado como parâmetro de entrada por stream (faz isso para cada número enviado pelo client);
- **Aggregator**: Api REST agregadora de resultados com dois endpoints.
  - **/grpc/unary/{number}**: Chama o endpoint gRPC findSquareUnary na quantidade de vezes correspondente ao número passado como parâmetro, agregando os resultados num array;  
  - **/rest/unary/{number}**: Chama o endpoint REST /rest/square/unary/{number} na quantidade de vezes correspondente ao número passado como parâmetro, agregando os resultados num array;

## Apache Bench
ab -n 1000 -c 100 http://localhost:9011/rest/unary/100

ab -n 1000 -c 100 http://localhost:9011/grpc/unary/100

Os testes com o Apache Bench funcionam da seguinte maneira:
- o parâmetro n é a quantidade que deverá ser realizada de requisições ao endpoint
- o parâmetro c é a quantidade de paralelismo que irá ocorrer (ex: -n 1000 -c 100, irá disparar 1000 requisições em grupos de 100 requisições, ou seja 100 requisições 10 vezes)
- o número no final da URL é a quantidade de vezes que o agregador vai chamar o endpoint REST ou gRPC

O que aconteceria se eu chamar o Apache Bench desta forma:
ab -n 1000 -c 100 http://localhost:9011/grpc/unary/100
Seriam disparadas 1000 requisições para essa URL de 100 em 100, e cada requisição iria chamar 100 vezes o endpoint gRPC, ou seja 100.000 requisições ao endpoint gRPC.

**Oservações durantes os testes**: o endpoint REST começou a dar timeout quando utilizamos o parâmetro 200, já o gRPC foi testado até 2000 e não teve timeout. 