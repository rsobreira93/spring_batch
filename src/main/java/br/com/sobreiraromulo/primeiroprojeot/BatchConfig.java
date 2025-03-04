package br.com.sobreiraromulo.primeiroprojeot;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
                .build();
    }

    @Bean
    public Step imprimeOlaStep() {
        return new StepBuilder("imprimeOlaStep", jobRepository)
                .tasklet((contribution, chunkContext) -> {
                    System.out.println("Olá mundo");
                    return RepeatStatus.FINISHED;
                }, transactionManager)
                .build();
    }

}
