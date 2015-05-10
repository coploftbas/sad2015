package org.apache.servicemix.examples.activiti;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Invoker {
	private final Logger logger = LoggerFactory.getLogger(Invoker.class);
	
	public void invokeProcessOrder() {
//		ProcessEngine processEngine;
//		RepositoryService repositoryService;
//		RuntimeService runtimeService;
//		Deployment deployment;
//		TaskService taskService;
//		
//		
//		// This looks for activiti.cfg.xml in the classpath.
//		processEngine = ProcessEngines.getDefaultProcessEngine();
//
//		// Get Activiti services
//		repositoryService = processEngine.getRepositoryService();
//		runtimeService = processEngine.getRuntimeService();
//
//		// Deploy the process definition
//		deployment = repositoryService.createDeployment()
//				.addClasspathResource("OSGI-INF/activiti/OrderProcess.bpmn20.xml").deploy();
//
//		taskService = processEngine.getTaskService();
//		
//		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("OrderProcess");
		
		logger.info("=======> Inside invokeProcessOrder()");
	}
}
