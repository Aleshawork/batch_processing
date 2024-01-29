package org.example.config;

import org.example.ent.PersonCarData;
import org.example.listener.JobExecutionStatusListener;
import org.example.tasklet.SimpleEndTasklet;
import org.example.tasklet.SimpleStartTasklet;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.validation.BindException;

import java.util.List;


@Configuration
public class SimpleJobConfiguration {

    @Value("${simplejob.param.dataPath}")
    private String dataUrl;

    @Bean
    public Job simpleJob(
            @Qualifier("startStep") Step helloStep,
            @Qualifier("endStep") Step endStep,
            @Qualifier("myJobRepository") JobRepository jobRepository,
            JobExecutionStatusListener jobExecutionStatusListener) {
        return new JobBuilder("simpleJob")
                .repository(jobRepository)
                .start(helloStep)
                .next(endStep)
                .listener(jobExecutionStatusListener)
                .build();
    }

    @Bean
    public Step startStep(
            @Qualifier("myJobRepository") JobRepository jobRepository,
            @Qualifier("myTransactionManager") PlatformTransactionManager transactionManager,
            @Qualifier("personCarDataFlatFileItemReader")FlatFileItemReader flatFileItemReader,
            @Qualifier("personCarDataFlatFileItemWriter") ItemWriter stdOutWriter) {
        return new StepBuilder("helloStep")
                .repository(jobRepository)
                .transactionManager(transactionManager)
                .chunk(2)
                .reader(flatFileItemReader)
                .writer(stdOutWriter)
                .build();
    }

    @Bean
    public Step endStep(SimpleEndTasklet simpleTasklet, @Qualifier("myJobRepository") JobRepository jobRepository, @Qualifier("myTransactionManager") PlatformTransactionManager transactionManager) {
        return new StepBuilder("endStep")
                .tasklet(simpleTasklet)
                .repository(jobRepository)
                .transactionManager(transactionManager)
                .build();
    }

    @Bean
    public FlatFileItemReader<PersonCarData> personCarDataFlatFileItemReader(
            @Qualifier("personCarDataLineMapper") LineMapper<PersonCarData> lineMapper
    ) {
        FlatFileItemReader<PersonCarData> flatFileItemReader = new FlatFileItemReader<>();
        ResourceLoader resourceLoader = new DefaultResourceLoader();
        Resource resource = resourceLoader.getResource(dataUrl);
        flatFileItemReader.setResource(resource);
        flatFileItemReader.setLinesToSkip(1);
        flatFileItemReader.setLineMapper(lineMapper);
        return flatFileItemReader;
    }

    @Bean
    public ItemWriter<PersonCarData> personCarDataFlatFileItemWriter() {
        ItemWriter<PersonCarData> stdOutWriter = new ItemWriter<PersonCarData>() {
            @Override
            public void write(List<? extends PersonCarData> items) throws Exception {
                items.stream().forEach(System.out::println);
            }
        };
        return stdOutWriter;
    }

    @Bean
    public LineMapper<PersonCarData> personCarDataLineMapper(
            @Qualifier("personCarDataLineTokenizer") DelimitedLineTokenizer lineTokenizer,
            @Qualifier("personCarDataFieldSetMapper") FieldSetMapper fieldSetMapper) {
        DefaultLineMapper<PersonCarData> personCarDataLineMapper = new DefaultLineMapper<>();
        personCarDataLineMapper.setLineTokenizer(lineTokenizer);
        personCarDataLineMapper.setFieldSetMapper(fieldSetMapper);
        return personCarDataLineMapper;
    }

    @Bean
    public FieldSetMapper<PersonCarData> personCarDataFieldSetMapper() {
        FieldSetMapper<PersonCarData> fieldSetMapper = new FieldSetMapper<PersonCarData>() {
            @Override
            public PersonCarData mapFieldSet(FieldSet fieldSet) throws BindException {
                PersonCarData personCarData = new PersonCarData();
                personCarData.setName(fieldSet.readString(0));
                personCarData.setSurName(fieldSet.readString(1));
                personCarData.setMarks(fieldSet.readString(2));
                return personCarData;
            }
        };
        return fieldSetMapper;
    }


    @Bean
    public DelimitedLineTokenizer personCarDataLineTokenizer() {
        DelimitedLineTokenizer delimitedLineTokenizer = new DelimitedLineTokenizer();
        delimitedLineTokenizer.setDelimiter(";");
        return delimitedLineTokenizer;
    }
}
