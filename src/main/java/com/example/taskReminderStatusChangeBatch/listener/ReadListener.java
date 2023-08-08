package com.example.taskReminderStatusChangeBatch.listener;

import org.springframework.batch.core.ItemReadListener;
import org.springframework.stereotype.Component;

import com.example.taskReminderStatusChangeBatch.entity.Tasks;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ReadListener implements ItemReadListener<Tasks> {
	
	@Override
	public void beforeRead() {
		log.debug("BeforeRead");
	}

	@Override
	public void afterRead(Tasks item) {
		log.debug("AfterRead: task_id={}", item.getTask_id());
	}

	@Override
	public void onReadError(Exception ex) {
		log.error("ReadError: errorMessage", ex.getMessage(), ex);
	}

}
