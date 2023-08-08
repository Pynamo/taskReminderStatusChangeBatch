package com.example.taskReminderStatusChangeBatch.processor;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import com.example.taskReminderStatusChangeBatch.entity.Tasks;

import lombok.extern.slf4j.Slf4j;

@Component("ChangeStatusInActiveProcessor")
@StepScope
@Slf4j
public class ChangeStatusInActiveProcessor implements ItemProcessor<Tasks, Tasks> {
	
	@Override
	public Tasks process(Tasks item) throws Exception {
		
		try {
			item.statusInActive();
		} catch(Exception e) {
			log.warn(e.getLocalizedMessage(), e);
			// skip
			return null;
		}
		
		return item;
	}

	
}
