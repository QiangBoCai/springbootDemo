package com.samples.sample1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import us.codecraft.webmagic.pipeline.Pipeline;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;

public class GithubRepoPipeline implements Pipeline{
	 protected Logger logger = LoggerFactory.getLogger(getClass());
	
	public GithubRepoPipeline() {
		super();
	}

	@Override
	public void process(ResultItems resultItems, Task task) {

		GithubRepo githubRepo = resultItems.get("repo");
		logger.error("githubRepo:"+githubRepo.getUrl().toString());
	}

	
	
}
