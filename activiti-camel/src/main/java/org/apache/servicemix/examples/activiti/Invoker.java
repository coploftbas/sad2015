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
import org.springframework.http.ResponseEntity;
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
		
		License license = restTemplate.getForObject(url + "licenses/search/" + , License.class);
		if (license.getNumber().equals("NULL")) {
			map.put("isValid", false);
		} else {
			map.put("isValid", true);
		}
	}
	
	public void createTicket(Map map) {
		
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
				new FileOutputStream("[" + ticket.getId() + "]-["
						+ ticket.getIssuedDate() + "]-["
						+ ticket.getLicense().getNumber() + "]-["
						+ ticket.getLicense().getOwnerName() + "].pdf"));

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
