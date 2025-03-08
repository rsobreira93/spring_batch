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

    @Bean
    public Step imprimeOlaStep() {
        return new StepBuilder("imprimeOlaStep", jobRepository)
                .tasklet(imprimeOlaTasklet(null), transactionManager)
                .build();
    }

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

}
