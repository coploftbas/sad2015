package org.apache.servicemix.examples.activiti;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Invoker {
	private final Logger logger = LoggerFactory.getLogger(Invoker.class);
	
	public void invokeProcessOrder() {
		ProcessEngine processEngine;
		RepositoryService repositoryService;
		RuntimeService runtimeService;
		Deployment deployment;
		TaskService taskService;
		
		logger.info("=======> Inside invokeProcessOrder()");
		
		// This looks for activiti.cfg.xml in the classpath.
		processEngine = ProcessEngines.getDefaultProcessEngine();

		// Get Activiti services
		repositoryService = processEngine.getRepositoryService();
		runtimeService = processEngine.getRuntimeService();

		// Deploy the process definition
		deployment = repositoryService.createDeployment()
				.addClasspathResource("OSGI-INF/activiti/OrderProcess.bpmn20.xml").deploy();

		taskService = processEngine.getTaskService();
		
		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("OrderProcess");
	}
}
