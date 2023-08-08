package com.example.taskReminderStatusChangeBatch.launcher;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class ScheduledJobBean {
	
	@Autowired
	JobLauncher jobLauncher;
	
	@Autowired
	Job job;
	
	// 秒　分　時
	@Scheduled(cron = "00 00 00 * * *")
	public void execute() throws Exception {
		
		JobParameters params = new JobParametersBuilder()
				.addString("JobID", String.valueOf(System.currentTimeMillis()))
				.toJobParameters();
		
		jobLauncher.run(job, params);
		
	}
}

