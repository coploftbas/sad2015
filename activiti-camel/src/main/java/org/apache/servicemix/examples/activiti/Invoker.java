package org.apache.servicemix.examples.activiti;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;

import org.apache.servicemix.examples.domain.Ticket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

public class Invoker {
	private final Logger logger = LoggerFactory.getLogger(Invoker.class);

	public void invokeProcessOrder() {
		// ProcessEngine processEngine;
		// RepositoryService repositoryService;
		// RuntimeService runtimeService;
		// Deployment deployment;
		// TaskService taskService;
		//
		//
		// // This looks for activiti.cfg.xml in the classpath.
		// processEngine = ProcessEngines.getDefaultProcessEngine();
		//
		// // Get Activiti services
		// repositoryService = processEngine.getRepositoryService();
		// runtimeService = processEngine.getRuntimeService();
		//
		// // Deploy the process definition
		// deployment = repositoryService.createDeployment()
		// .addClasspathResource("OSGI-INF/activiti/OrderProcess.bpmn20.xml").deploy();
		//
		// taskService = processEngine.getTaskService();
		//
		// ProcessInstance processInstance =
		// runtimeService.startProcessInstanceByKey("OrderProcess");

		logger.info("=======> Inside invokeProcessOrder()");
	}

	public void pdfGenerator(Ticket ticket) throws DocumentException,
			MalformedURLException, IOException {

		Document document = new Document();
		String url = "";

		RestTemplate restTemplate3 = new RestTemplate();
		// License license = restTemplate3.getForObject(url + "licenses/1",
		// License.class);

		// System.out.println("[id:"+license.getId()+"] Owner name : "+license.getOwnerName()+"  Plate no. => "+license.getNumber());

		PdfWriter.getInstance(
				document,
				new FileOutputStream("[id" + ticket.getId() + "]_"
						+ ticket.getLicense().getNumber() + "_"
						+ ticket.getLicense().getOwnerName() + " ("
						+ ticket.getIssuedDate() + ").pdf"));
		
		Image image = Image.getInstance("police.png");
		image.scaleAbsolute(150f, 150f);

		document.open();
		document.add(image);
		document.add(new Paragraph("id: " + ticket.getId() + "\n"
				+ "Plate number: " + ticket.getLicense().getNumber() + "\n"
				+ "Owner name: " + ticket.getLicense().getOwnerName() + "\n"
				+ "Issued Date: " + ticket.getIssuedDate()));
		document.close();

	}
}
