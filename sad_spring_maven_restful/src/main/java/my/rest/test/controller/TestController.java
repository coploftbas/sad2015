package my.rest.test.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import my.rest.test.persistence.User;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class TestController {

	@Autowired
	SessionFactory sessionFactory;

	String testJ = "/home/suhas/Desktop/" + "test.jpg";

	@RequestMapping(value = "/greet", method = RequestMethod.GET)
	public String hello() {
		return "apple!";
	}

	@RequestMapping(value = "/adduser", method = RequestMethod.GET)
	public User addUser() {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		User user = null;
		int userId;
		try {
			tx = session.beginTransaction();

			user = new User();
			user.setName("aasdasd");
			userId = (Integer) session.save(user);

			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return user;
	}

	@RequestMapping(value = "/download", method = RequestMethod.GET)
	public String download(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String filename = testJ;

		File file = new File(filename);
		FileInputStream is = new FileInputStream(file);

		response.setContentType("image/jpeg");
		response.setContentLength((int) file.length());
		response.setHeader("Content-Disposition", "attachment; filename=\""
				+ file.getName() + "\"");
		//
		
		
		FileCopyUtils.copy(is, response.getOutputStream());

		return "Downloading!!";
	}

	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public @ResponseBody String handleFileUpload(
			@RequestParam("name") String name,
			@RequestParam("file") MultipartFile file) {
		// String name = "test.jpg";
		if (!file.isEmpty()) {
			try {
				byte[] bytes = file.getBytes();
				name += file.getOriginalFilename();
				BufferedOutputStream stream = new BufferedOutputStream(
						new FileOutputStream(new File("/home/suhas/Desktop/"
								+ name)));
				stream.write(bytes);
				stream.close();
				return "You successfully uploaded " + name + "!";
			} catch (Exception e) {
				return "You failed to upload " + name + " => " + e.getMessage();
			}
		} else {
			return "You failed to upload " + name
					+ " because the file was empty.";
		}
	}

	@RequestMapping(value = "/upload", method = RequestMethod.GET)
	public String greet() {
		return "<html><body>	<form method=\"POST\" enctype=\"multipart/form-data\"		action=\"upload\">		File to upload: <input type=\"file\" name=\"file\"><br /> Name: <input			type=\"text\" name=\"name\"><br /> <br /> <input type=\"submit\"			value=\"Upload\"> Press here to upload the file!	</form></body></html>";
	}

}
