# Step no Spring Batch

Um Step define sua lógica com base no seu tipo: **Tasklet** ou **Chunk**.

## Tasklet

Tasklets são usadas para pequenas tarefas, geralmente para pré-processamento ou ações que precisam de um único comando para executar, como:  
- Limpeza de arquivos  
- Criação de diretórios  

A Tasklet executa repetidamente até atingir o status de concluído.

## Chunk

Os Chunks são utilizados para processamentos mais complexos e precisam ser realizados em pedaços. Esses pedaços são divididos em três etapas:  
1. **Leitura** (*ItemReader*)  
2. **Processamento** (*ItemProcessor*)  
3. **Escrita** (*ItemWriter*)  

Cada Chunk possui sua própria transação, o que significa que, se ocorrer um erro durante o processamento, todas as operações anteriores serão preservadas.  

O que define o tamanho do Chunk é o **commitInterval**.
