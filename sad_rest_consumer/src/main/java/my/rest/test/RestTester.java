package my.rest.test;

import java.io.File;
import java.io.IOException;

import my.rest.test.persistence.License;
import my.rest.test.persistence.Ticket;
import my.rest.test.persistence.Violation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class RestTester {

	public static void main(String[] args) {

		// ObjectMapper mapper = new ObjectMapper();
		// try {
		//
		// // convert user object to json string, and save to a file
		// mapper.writeValue(new File("/Users/Charlie/Desktop/license.json"),
		// license);
		//
		// // display to console
		// System.out.println(mapper.writeValueAsString(license));
		//
		// } catch (JsonGenerationException e) {
		//
		// e.printStackTrace();
		//
		// } catch (JsonMappingException e) {
		//
		// e.printStackTrace();
		//
		// } catch (IOException e) {
		//
		// e.printStackTrace();
		//
		// }

		int n = 5;
		System.out.println(">>> POSTing licenses");
		addLicenses(n);
		addLicenses();
		System.out.println(">>> done with licenses");
		System.out.println(">>> POSTing violations");
		addViolation(n);
		System.out.println(">>> done with violation");
		//System.out.println(">>> POSTing tickets");
		//addTicket(n);
		//System.out.println(">>> done with ticket");

		/*RestTemplate restTemplate = new RestTemplate();
		RestTemplate restTemplate3 = new RestTemplate();

		License license = restTemplate3.getForObject(url + "licenses/1", License.class);
		System.out.println("[id:"+license.getId()+"] Owner name : "+license.getOwnerName()+"   Plate no. => "+license.getNumber());*/
	}

	private static int licenseID;
	private static int violationID;
	private static String url = "http://localhost:8080/sad_spring_maven_restful/";
	
	public static void addLicenses(int n) {
		RestTemplate restTemplate = new RestTemplate();
		RestTemplate restTemplate3 = new RestTemplate();

		License license = restTemplate.getForObject(url + "license",
				License.class);
		for (int i = 0; i < n; i++) {
			ResponseEntity<License>  l =
			restTemplate3.postForEntity(
					url + "licenses",
					license, License.class);
			if (i == 0) {
			    licenseID = l.getBody().getId();
				System.out.println(licenseID);
			}
		}
	}
	
	public static void addLicenses(){
		RestTemplate restTemplate = new RestTemplate();
		License license = restTemplate.getForObject(url + "license", License.class);
		
		RestTemplate rt = new RestTemplate();
		license.setNumber( "ฎท 9897");
		license.setProvince("กรุงเทพมหานคร");
		license.setVehicleBrand("Honda");
		license.setVehicleColor("White");
		license.setVehicleType("SUV");
		license.setOwnerName("Jim");
		License  l = rt.postForObject(url + "licenses", license, License.class);
		licenseID = l.getId();
		System.out.println(licenseID);
		
		RestTemplate rts = new RestTemplate();
		license.setNumber( "ทส 1134");
		license.setProvince("กรุงเทพมหานคร");
		license.setVehicleBrand("Toyota");
		license.setVehicleColor("Green");
		license.setVehicleType("Sedan");
		license.setOwnerName("Tim");
		rts.postForObject(url + "licenses", license, License.class);
		
		RestTemplate rtv = new RestTemplate();
		license.setNumber( "3กษ 3683");
		license.setProvince("กรุงเทพมหานคร");
		license.setVehicleBrand("Nissan");
		license.setVehicleColor("Black");
		license.setVehicleType("Sedan");
		license.setOwnerName("Pop");
		rtv.postForObject(url + "licenses", license, License.class);
		
		RestTemplate rtx = new RestTemplate();
		license.setNumber( "ชค 1905");
		license.setProvince("กรุงเทพมหานคร");
		license.setVehicleBrand("Toyota");
		license.setVehicleColor("Red");
		license.setVehicleType("Sedan");
		license.setOwnerName("Poom");
		rtx.postForObject(url + "licenses", license, License.class);
		
		RestTemplate rty = new RestTemplate();
		license.setNumber( "หส 4767");
		license.setProvince("กรุงเทพมหานคร");
		license.setVehicleBrand("Toyota");
		license.setVehicleColor("Yellow");
		license.setVehicleType("Sedan");
		license.setOwnerName("Sornak");
		rty.postForObject(url + "licenses", license, License.class);
		
		RestTemplate rtw = new RestTemplate();
		license.setNumber( "ฌค 5736");
		license.setProvince("กรุงเทพมหานคร");
		license.setVehicleBrand("Toyota");
		license.setVehicleColor("White");
		license.setVehicleType("Sedan");
		license.setOwnerName("Chirag");
		rtw.postForObject(url + "licenses", license, License.class);
		
		RestTemplate rta = new RestTemplate();
		license.setNumber( "1กด 7389");
		license.setProvince("กรุงเทพมหานคร");
		license.setVehicleBrand("Honda");
		license.setVehicleColor("White");
		license.setVehicleType("Sedan");
		license.setOwnerName("Manish");
		rtw.postForObject(url + "licenses", license, License.class);
		
		RestTemplate rtb = new RestTemplate();
		license.setNumber( "ฎท 9897");
		license.setProvince("กรุงเทพมหานคร");
		license.setVehicleBrand("Honda");
		license.setVehicleColor("White");
		license.setVehicleType("SUV");
		license.setOwnerName("Sanin");
		rtb.postForObject(url + "licenses", license, License.class);
		
		RestTemplate rtc = new RestTemplate();
		license.setNumber( "ทว 1508");
		license.setProvince("กรุงเทพมหานคร");
		license.setVehicleBrand("Toyata");
		license.setVehicleColor("Green");
		license.setVehicleType("SUV");
		license.setOwnerName("Akila");
		rtc.postForObject(url + "licenses", license, License.class);
		
		RestTemplate rtd = new RestTemplate();
		license.setNumber( "ทล1 9358");
		license.setProvince("กรุงเทพมหานคร");
		license.setVehicleBrand("Toyata");
		license.setVehicleColor("Pink");
		license.setVehicleType("Sedan");
		license.setOwnerName("Harshani");
		rtd.postForObject(url + "licenses", license, License.class);
		
		RestTemplate rte = new RestTemplate();
		license.setNumber( "ขต 95");
		license.setProvince("กรุงเทพมหานคร");
		license.setVehicleBrand("Honda");
		license.setVehicleColor("Black");
		license.setVehicleType("Sedan");
		license.setOwnerName("Kevin");
		rte.postForObject(url + "licenses", license, License.class);
		
		RestTemplate rtf = new RestTemplate();
		license.setNumber( "ถบ 4561");
		license.setProvince("กรุงเทพมหานคร");
		license.setVehicleColor("Gray");
		license.setVehicleType("Pickup");
		license.setOwnerName(" ");
		rtf.postForObject(url + "licenses", license, License.class);
		

		RestTemplate rtg = new RestTemplate();
		license.setNumber( "หล 2483");
		license.setProvince("กรุงเทพมหานคร");
		license.setVehicleBrand("Toyato");
		license.setVehicleColor("Pink");
		license.setVehicleType("Sedan");
		license.setOwnerName(" ");
		rtg.postForObject(url + "licenses", license, License.class);
		
		RestTemplate rth = new RestTemplate();
		license.setNumber( "อฉฬ 534");
		license.setProvince("กรุงเทพมหานคร");
		license.setVehicleBrand(" ");
		license.setVehicleColor(" ");
		license.setVehicleType("Motorcycle");
		license.setOwnerName(" ");
		rth.postForObject(url + "licenses", license, License.class);
		
		RestTemplate rti = new RestTemplate();
		license.setNumber( "อจว 680");
		license.setProvince("กรุงเทพมหานคร");
		license.setVehicleBrand(" ");
		license.setVehicleColor(" ");
		license.setVehicleType("Motorcycle");
		license.setOwnerName(" ");
		rti.postForObject(url + "licenses", license, License.class);
		
		RestTemplate rtj = new RestTemplate();
		license.setNumber( "11 1121");
		license.setProvince("กรุงเทพมหานคร");
		license.setVehicleBrand(" ");
		license.setVehicleColor("Black");
		license.setVehicleType("Truck");
		license.setOwnerName(" ");
		rtj.postForObject(url + "licenses", license, License.class);
		
		RestTemplate rtk = new RestTemplate();
		license.setNumber( "82 7780");
		license.setProvince("กรุงเทพมหานคร");
		license.setVehicleBrand(" ");
		license.setVehicleColor("Black");
		license.setVehicleType("Truck");
		license.setOwnerName(" ");
		rtk.postForObject(url + "licenses", license, License.class);
		
		RestTemplate rtl = new RestTemplate();
		license.setNumber( " ทบ 2117");
		license.setProvince("กรุงเทพมหานคร");
		license.setVehicleBrand(" ");
		license.setVehicleColor("Orange");
		license.setVehicleType("Sedan");
		license.setOwnerName(" ");
		rtl.postForObject(url + "licenses", license, License.class);
		
		RestTemplate rtm = new RestTemplate();
		license.setNumber( "ขต 96");
		license.setProvince("กรุงเทพมหานคร");
		license.setVehicleBrand(" ");
		license.setVehicleColor("Black");
		license.setVehicleType("Sedan");
		license.setOwnerName(" ");
		rtm.postForObject(url + "licenses", license, License.class);
		System.out.println(licenseID);
	}

	private static void addTicket(int n) {
		RestTemplate restTemplate = new RestTemplate();
		RestTemplate restTemplate3 = new RestTemplate();

		Ticket ticket = restTemplate.getForObject(
				url + "ticket",
				Ticket.class);
		ticket.getLicense().setId(licenseID);
		ticket.getViolation().setId(violationID);
		// System.out.println(violation.getId() + ", " +
		// violation.getOwnerName());
		for (int i = 0; i < n; i++) {
			// System.out.println(i);
			restTemplate3.postForEntity(
					url + "tickets",
					ticket, Ticket.class);
		}

	}


	public static void addViolation(int n) {
		RestTemplate restTemplate = new RestTemplate();
		RestTemplate restTemplate3 = new RestTemplate();
		Violation violation = restTemplate.getForObject(
				url + "violation",
				Violation.class);
		for (int i = 0; i < n; i++) {
			Violation  v =
			restTemplate3
					.postForObject(
							url + "violations",
							violation, Violation.class);
			v.setRecognizedLicenseNumber("ฎท 9897");
			if (i == 0) {
				violationID = v.getId();
			}
		}
	}

}
