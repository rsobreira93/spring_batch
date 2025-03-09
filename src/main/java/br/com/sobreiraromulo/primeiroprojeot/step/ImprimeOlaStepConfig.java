package br.com.sobreiraromulo.primeiroprojeot.step;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class ImprimeOlaStepConfig {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private PlatformTransactionManager transactionManager;

    @Bean
    public Step imprimeOlaStep(Tasklet imprimeOlaTasklet) {
        return new StepBuilder("imprimeOlaStep", jobRepository)
                .tasklet(imprimeOlaTasklet, transactionManager)
                .build();
    }

}
