package org.apache.servicemix.examples.activiti;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.apache.servicemix.examples.domain.License;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import org.springframework.web.client.RestTemplate;

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
	
	
	public void pdfGenerator() throws DocumentException, FileNotFoundException{
		
		Document document = new Document();
		String url = "";
		
		
		RestTemplate restTemplate3 = new RestTemplate();
		License license = restTemplate3.getForObject(url + "licenses/1", License.class);
		System.out.println("[id:"+license.getId()+"] Owner name : "+license.getOwnerName()+"  Plate no. => "+license.getNumber());
		
		PdfWriter.getInstance(document, new FileOutputStream(license.getNumber()+".pdf"));
		
		document.open();
		document.add(new Paragraph("A Hello World PDF document."));
		document.close();
		
		
	}
}
