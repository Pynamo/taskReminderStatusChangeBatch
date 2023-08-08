package com.example.taskReminderStatusChangeBatch.listener;

import org.springframework.batch.core.ItemProcessListener;
import org.springframework.stereotype.Component;

import com.example.taskReminderStatusChangeBatch.entity.Tasks;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ProcessListener implements ItemProcessListener<Tasks, Tasks> {
	
	@Override
	public void beforeProcess(Tasks item) {
		log.debug("BeforeProcess");
	}

	@Override
	public void afterProcess(Tasks item, Tasks result) {
		log.debug("AfterProcess: status_before={} -> status_after={}", item.getStatus(), result.getStatus());
	}

	@Override
	public void onProcessError(Tasks item, Exception e) {
		log.error("ProcessError: item={}, errorMessage={}", item, e.getMessage(), e);
	}

}
