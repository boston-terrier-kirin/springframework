package com.example.spring_batch.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.util.Map;

@Service
public class BatchExecutorTask implements Tasklet {

	private Logger logger = LoggerFactory.getLogger(getClass());


	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {

		Map params = chunkContext.getStepContext().getJobParameters();
		String jobName = (String) params.get("jobName");
		Long processId = (Long) params.get("processId");

		try {
			logger.info("■開始 jobName: " + jobName + " processId: " + processId);
			logger.info("　--- 10秒スリープ");
			Thread.sleep(10000);

			String currentDir = Paths.get("")
									.toAbsolutePath()
									.toString();

			ProcessBuilder processBuilder = new ProcessBuilder();
			processBuilder.command(currentDir + File.separator + jobName + ".bat");

			Process process = processBuilder.start();
			StringBuilder output = new StringBuilder();
			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

			String line = null;
			while ((line = reader.readLine()) != null) {
				output.append(line + "\n");
			}

			int exitValue = process.waitFor();
			if (exitValue == 0) {
				logger.info("成功");
				System.out.println(output);
			} else {
				logger.error("失敗");
			}

		} catch (IOException e) {
			logger.error(e.toString());
		} catch (InterruptedException e) {
			logger.error(e.toString());
		}

		logger.info("■終了 jobName: " + jobName + " processId: " + processId);

		return RepeatStatus.FINISHED;
	}
}
