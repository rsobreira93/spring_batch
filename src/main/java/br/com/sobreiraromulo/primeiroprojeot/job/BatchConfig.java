package br.com.sobreiraromulo.primeiroprojeot.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class BatchConfig {

    private JobRepository jobRepository;

    public BatchConfig(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        this.jobRepository = jobRepository;
    }

    @Bean
    public Job imprimeOlaJob(Step imprimeOlaStep) {
        return new JobBuilder("imprimeOlaJob", jobRepository)
                .start(imprimeOlaStep)
                .incrementer(new RunIdIncrementer())
                .build();
    }

}
