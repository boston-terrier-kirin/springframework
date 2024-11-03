package com.example.demo.listener;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.stereotype.Component;

@Component
public class FirstStepListener implements StepExecutionListener {

    @Override
    public void beforeStep(StepExecution stepExecution) {
		System.out.println("■Before Step: " + stepExecution.getStepName());
		System.out.println("Job Exec Ctx: " + stepExecution.getJobExecution().getExecutionContext());
		System.out.println("Step Exec Ctx: " + stepExecution.getExecutionContext());

		stepExecution.getExecutionContext().put("step value", "ABC");
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
		System.out.println("■After Step: " + stepExecution.getStepName());
		System.out.println("Job Exec Ctx: " + stepExecution.getJobExecution().getExecutionContext());
		System.out.println("Step Exec Ctx: " + stepExecution.getExecutionContext());
        return null;
    }

}
