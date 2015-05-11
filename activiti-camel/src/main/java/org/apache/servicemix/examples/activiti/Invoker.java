package org.apache.servicemix.examples.activiti;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Map;

import org.apache.servicemix.examples.domain.License;
import org.apache.servicemix.examples.domain.Ticket;
import org.apache.servicemix.examples.domain.Violation;
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
	private static String url = "http://localhost:8080/sad_spring_maven_restful/";
	
	public void validateViolationData(Map map) {
		RestTemplate restTemplate = new RestTemplate();
		Violation violation = restTemplate.getForObject(url + "violations/" + map.get("violationId"), Violation.class);
		
		License license = restTemplate.getForObject(url + "licenses/search/" + violation.getRecognizedLicenseNumber() , License.class);
		if (license.getNumber().equals("NULL")) {
			map.put("isValid", false);
		} else {
			map.put("isValid", true);
			map.put("licenseId", license.getId());
		}
	}
	
	public void createTicket(Map map) throws MalformedURLException, DocumentException, IOException {
		RestTemplate restTemplate = new RestTemplate();
		
		logger.info("=====> createTicket");
		logger.info(map.get("licenseId").toString());
		logger.info(map.get("violationId").toString());
		
		License license = restTemplate.getForObject(
				url + "licenses/" + map.get("licenseId"), License.class);
		
		logger.info("#1");

		Violation violation = restTemplate.getForObject(
				url + "violations/" + map.get("violationId"), Violation.class);
		
		logger.info("#2");

		Ticket ticket = restTemplate.getForObject(
				url + "ticket", Ticket.class);
		
		logger.info("#3");
	
		//ticket.getViolation().setId(violationID);
		//ticket.setExpiryDate("Thu_Apr__2_09-26-55_2015");
		
		ticket.setFine(1000);
		ticket.setId(100);
		
		//ticket.setId(20);
		//ticket.setIssuedBy(issuedBy);
		//ticket.setIssuedDate("Thu_Apr__2_09-26-55_2015");
		// System.out.println(violation.getId() + ", " +
		// violation.getOwnerName());
		
		ticket.setLicense(license);
		ticket.setViolation(violation);
		
		restTemplate.postForEntity(url + "tickets", ticket, Ticket.class);
		
		logger.info("#4");
		
		pdfGenerator(ticket);
	}

	public void pdfGenerator(Ticket ticket) throws DocumentException,
			MalformedURLException, IOException {

		logger.info("=====> pdfGenerator");
		
	    String FILENAME = 
	    		"[" + ticket.getId() + "]-["
				+ ticket.getIssuedDate() + "]-["
				+ ticket.getLicense().getNumber() + "]-["
				+ ticket.getLicense().getOwnerName() + "].pdf";
	    String PATH = "%s";
	    String RESULT = String.format(PATH, FILENAME);
		
		Document document = new Document();
		
//		RestTemplate restTemplate3 = new RestTemplate();
//		License license = restTemplate3.getForObject(url + "licenses/1",
//	    License.class);

		// System.out.println("[id:"+license.getId()+"] Owner name : "+license.getOwnerName()+"  Plate no. => "+license.getNumber());

		PdfWriter.getInstance(
				document,
				new FileOutputStream("/home/poom/" + FILENAME));

		document.open();
		
		Image image = Image.getInstance("police.png");
		image.scaleAbsolute(150f, 150f);
		
		document.add(image);
		document.add(new Paragraph("id: " + ticket.getId() + "\n"
				+ "Plate number: " + ticket.getLicense().getNumber() + "\n"
				+ "Owner name: " + ticket.getLicense().getOwnerName() + "\n"
				+ "Fine: " + ticket.getFine() + "\n"
				+ "Issued Date: " + ticket.getIssuedDate()));
		document.close();
	}
}
