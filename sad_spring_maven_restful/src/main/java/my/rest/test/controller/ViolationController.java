package my.rest.test.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import my.rest.test.persistence.License;
import my.rest.test.persistence.Violation;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class ViolationController {

	String uploadLocation = "/home/suhas/Desktop/";
	@Autowired
	SessionFactory sessionFactory;

	private String fileLocation = "/home/suhas/Desktop/";

	@RequestMapping(value = "/violation", method = RequestMethod.GET)
	public ResponseEntity<Violation> getViolation() {
		// License li = new License("Ben", "ฎท 9897", "Pathumthani", "RED",
		// "Nissan", "VAN");
		Violation violation = new Violation(
				"Thu_Apr__2_09-26-55_2015.before_violation.jpg",
				"Thu_Apr__2_09-26-55_2015.before_violation.jpg",
				"Thu_Apr__2_09-26-55_2015.before_violation.jpg",
				"Thu_Apr__2_09-26-55_2015.before_violation.jpg",
				"Thu_Apr__2_09-26-55_2015.avi",
				"Thu_Apr__2_09-26-55_2015.licenseplate.jpg", "กรุงเทพมหานคร",
				"Honda", "White", "SUV", "UnEvaluated", "2015-04-02 09:26:55",
				"Sutthisan");
		return new ResponseEntity<Violation>(violation, HttpStatus.OK);
	}
//
//	@RequestMapping(value = "/violations/{id}", method = RequestMethod.GET)
//	public ResponseEntity<Violation> getViolation(@PathVariable("id") int id) {
//		Session session = null;
//		Violation violation = null;
//		try {
//			session = sessionFactory.openSession();
//			violation = (Violation) session.get(Violation.class, id);
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			if (session != null && session.isOpen()) {
//				session.close();
//			}
//		}
//
//		return new ResponseEntity<Violation>(violation, HttpStatus.OK);
//	}


	@RequestMapping(value = "/violations/{id}", method = RequestMethod.GET)
	public ResponseEntity<Violation> getViolation(@PathVariable("id") int id) {
		Session session = sessionFactory.openSession();
		org.hibernate.Query query = session.createQuery("from Violation where id=" +id);
		List<Violation> violations = query.list();

		return new ResponseEntity<Violation>(violations.get(0), HttpStatus.OK);
	}

	
	@RequestMapping(value = "/violations", method = RequestMethod.GET)
	public ResponseEntity<List<Violation>> getLicenses() {
		Session session = sessionFactory.openSession();
		org.hibernate.Query query = session.createQuery("from Violation");
		List<Violation> violations = query.list();

		return new ResponseEntity<List<Violation>>(violations, HttpStatus.OK);
	}

	@RequestMapping(value = "/violations", method = RequestMethod.POST)
	public ResponseEntity<Violation> addviolation(@RequestBody Violation violation) {
		// Violation violation = new Violation();
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		int violationId = 0;

		try {
			tx = session.beginTransaction();
			violationId = (Integer) session.save(violation);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}

		boolean f = new File(fileLocation + violationId + "/").mkdirs();

		return new ResponseEntity<Violation>(violation, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/violations/{id}/{name}", method = RequestMethod.GET)
	public String download(@PathVariable("id") int id,
			@PathVariable("name") String name, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String filename = uploadLocation + id + "/" + name + ".jpg";

		File file = new File(filename);
		FileInputStream is = new FileInputStream(file);

		response.setContentType("image/jpeg");
		response.setContentLength((int) file.length());
		response.setHeader("Content-Disposition", "attachment; filename=\""
				+ file.getName() + "\"");

		FileCopyUtils.copy(is, response.getOutputStream());
		return "Downloading!!";
	}

	@RequestMapping(value = "/violations/{id}/images", method = RequestMethod.POST)
	public ResponseEntity<String> addviolation(
			@PathVariable("id") int id,
			@RequestParam("beforeViolation2Image") MultipartFile beforeViolation2Image,
			@RequestParam("beforeViolationImage") MultipartFile beforeViolationImage,
			@RequestParam("afterViolationImage") MultipartFile afterViolationImage,
			@RequestParam("afterViolation2Image") MultipartFile afterViolation2Image,
			@RequestParam("video") MultipartFile video,
			@RequestParam("licensePlateImage") MultipartFile licensePlateImage) {

		saveFile(id, beforeViolationImage);
		saveFile(id, beforeViolation2Image);
		saveFile(id, afterViolationImage);
		saveFile(id, afterViolation2Image);
		saveFile(id, video);
		saveFile(id, licensePlateImage);
		// response.setStatus(HttpServletResponse.SC_OK);
		return new ResponseEntity<String>("Files uploaded", HttpStatus.CREATED);
	}

	private void saveFile(int id, MultipartFile file) {
		String name = fileLocation + id + "/" + file.getOriginalFilename();
		if (!file.isEmpty()) {
			try {
				byte[] bytes = file.getBytes();
				name += file.getOriginalFilename();
				BufferedOutputStream stream = new BufferedOutputStream(
						new FileOutputStream(new File(name)));
				stream.write(bytes);
				stream.close();
				System.out.println("You successfully uploaded " + name + "!");
			} catch (Exception e) {
				System.out.println("You failed to upload " + name + " => "
						+ e.getMessage());
			}
		} else {
			System.out.println("You failed to upload " + name
					+ " because the file was empty.");
		}
	}

}
