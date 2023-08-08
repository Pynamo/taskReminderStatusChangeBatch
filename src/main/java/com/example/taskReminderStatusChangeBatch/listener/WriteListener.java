package com.example.taskReminderStatusChangeBatch.listener;

import java.util.List;

import org.springframework.batch.core.ItemWriteListener;
import org.springframework.stereotype.Component;

import com.example.taskReminderStatusChangeBatch.entity.Tasks;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class WriteListener implements ItemWriteListener<Tasks> {
	
	@Override
	public void beforeWrite(List<? extends Tasks> items) {
		log.debug("BeforeWrite");
	}

	@Override
	public void afterWrite(List<? extends Tasks> items) {
		log.debug("AfterWrite: count={}", items.size());
	}

	@Override
	public void onWriteError(Exception exception, List<? extends Tasks> items) {
		log.error("WriteError: errorMessage={}", exception.getMessage(), exception);
	}

}
