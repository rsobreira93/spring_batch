package br.com.sobreiraromulo.primeiroprojeot;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.Nullable;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class BatchConfig {

    private JobRepository jobRepository;
    private PlatformTransactionManager transactionManager;

    public BatchConfig(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        this.jobRepository = jobRepository;
        this.transactionManager = transactionManager;
    }

    @Bean
    public Job imprimeOlaJob() {
        return new JobBuilder("imprimeOlaJob", jobRepository)
                .start(imprimeOlaStep())
                .incrementer(new RunIdIncrementer())
                .build();
    }

    /*
     * Um step define sua lógica com base no seu tipo
     * tasklet ou chunk
     */

    @Bean
    public Step imprimeOlaStep() {
        return new StepBuilder("imprimeOlaStep", jobRepository)
                .tasklet(imprimeOlaTasklet(null), transactionManager)
                .build();
    }

    /*
     * Tasklets são usadas para pequenas tarefas,
     * geralmente para tarefas de pré-processamento
     * e que precisam de um único comando para executar,
     * como: Limpeza de aquivos, criação de diretórios, etc.
     * 
     * A tasklet executa repetidamente até o status de concluido
     */

    @Bean
    @StepScope
    public Tasklet imprimeOlaTasklet(@Value("#{jobParameters['name']}") String name) {
        return new Tasklet() {

            @Override
            @Nullable
            public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                System.out.println(String.format("Olá %s!", name));

                return RepeatStatus.FINISHED;
            }
        };
    }

    /*
     * Os chunks por sua vez, são utilizados para processamentos
     * mais complexos e precisam ser realizados em pedeçaos,
     * esses pedaços são divididos em tarefas:
     * leitura (ItemReader), Processamento (ItemProcess) e
     * Escrita(ItemWriter) e cada chunk possui a sua propria
     * trnasação, isso siginifica que se ocorrer um erro durante
     * o processamento, todo o trabalho que foi executado nos anteriores
     * estará salvo. O que define o tamanho do chunk é o commitInterval
     */

}
