package com.example.taskReminderStatusChangeBatch.config;

import javax.persistence.EntityManagerFactory;

import org.springframework.batch.core.ItemReadListener;
import org.springframework.batch.core.ItemWriteListener;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.batch.item.database.orm.JpaNativeQueryProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.taskReminderStatusChangeBatch.entity.Tasks;

import lombok.extern.slf4j.Slf4j;


/**
 * クラスのインスタンスをBeanに登録するためのアノテーション。
 * このアノテーションをつけたクラス内では@Beanを使ってBeanを登録できる
 */
@Configuration
/**
 * Sprinバッチの設定をするためのアノテーション
 * このアノテーションをつけたクラスないで以下クラスのインスタンスをDIできる
 * ・JobBuilderFactory（Job生成用クラス）
 * ・StepBuilderFactory（Step生成用クラス）
 */
@EnableBatchProcessing
@Slf4j
public class BatchConfig {
	
	@Autowired
	private JobBuilderFactory jobBuilderFactory;
	
	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	
	@Autowired
	private ItemReadListener<Tasks> readListener;
	
	@Autowired
	private ItemWriteListener<Tasks> writeListener;
	
	@Autowired
	private ItemProcessor<Tasks, Tasks> changeStatusInActiveProcessor;

	@Autowired
	private EntityManagerFactory entityManagerFactory;
	
	
	@Bean
	@StepScope
	public JpaPagingItemReader<Tasks> jpaPagingReader() {
		
		String sql = "select * from tasks order by task_id";
		
		JpaNativeQueryProvider<Tasks> queryProvider = 
				new JpaNativeQueryProvider<>();
		
		queryProvider.setSqlQuery(sql);
		queryProvider.setEntityClass(Tasks.class);

		return new JpaPagingItemReaderBuilder<Tasks>()
				.entityManagerFactory(entityManagerFactory)
				.name("jpaPagingItemReader")
				.queryProvider(queryProvider)
				.pageSize(1000)
				.build();
	}
	
	
	@Bean
	public JpaItemWriter<Tasks> jpaWriter() {
		JpaItemWriter<Tasks> writer = new JpaItemWriter<>();
		writer.setEntityManagerFactory(entityManagerFactory);
		return writer;
	}
	
	
	@Bean
	public Step exportJpaPagingStep() throws Exception {
		
		return stepBuilderFactory.get("ExportJpaPagingStep")
				.<Tasks, Tasks>chunk(1000)
				.reader(jpaPagingReader()).listener(readListener)
				.processor(changeStatusInActiveProcessor)
				.writer(jpaWriter()).listener(writeListener)
				.build();
	}
	
	
	@Bean("JpaPagingJob")
	public Job exportJpaPagingJob() throws Exception {
		
		log.info("incrementer: {}", new RunIdIncrementer());
		
		return jobBuilderFactory.get("ExportJpaPagingJob")
				.incrementer(new RunIdIncrementer())
				.start(exportJpaPagingStep())
				.build();
	}
	


}
