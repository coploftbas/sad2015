package my.rest.test.controller;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.List;

import javax.management.Query;

import my.rest.test.persistence.License;
import my.rest.test.persistence.Violation;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LicenseController {

	@Autowired
	SessionFactory sessionFactory;

	@RequestMapping(value = "/license", method = RequestMethod.GET)
	public ResponseEntity<License> getLicense() {
		License li = new License("Ben", "ฎท 9897", "Pathumthani", "RED",
				"Nissan", "VAN");

		return new ResponseEntity<License>(li, HttpStatus.OK);
	}

	@RequestMapping(value = "/licenses", method = RequestMethod.GET)
	public ResponseEntity<List<License>> getLicenses() {
		Session session = sessionFactory.openSession();
		org.hibernate.Query query = session.createQuery("from License");
		List<License> licenses = query.list();

		return new ResponseEntity<List<License>>(licenses, HttpStatus.OK);
	}

	// @RequestMapping(value = "/licenses/{id}", method = RequestMethod.GET)
	// public ResponseEntity<License> getLicense(@PathVariable("id") int id) {
	// Session session = null;
	// License license = null;
	// try {
	// session = sessionFactory.openSession();
	//
	// license = (License) session.get(License.class, id);
	// } catch (Exception e) {
	// e.printStackTrace();
	// } finally {
	// if (session != null && session.isOpen()) {
	// session.close();
	// }
	// }
	//
	// return new ResponseEntity<License>(license, HttpStatus.OK);
	// }

	@RequestMapping(value = "/licenses/{id}", method = RequestMethod.GET)
	public ResponseEntity<License> getLicense(@PathVariable("id") int id) {
		Session session = sessionFactory.openSession();
		org.hibernate.Query query = session
				.createQuery("from License where id=" + id);
		List<License> licenses = query.list();

		return new ResponseEntity<License>(licenses.get(0), HttpStatus.OK);
	}
	
	@RequestMapping(value="/licenses/search/{licenseNumber}", method = RequestMethod.GET)
	public ResponseEntity<License> searchLicenseByNumber(@PathVariable("licenseNumber") String licenseNumber) throws UnsupportedEncodingException
	{
		System.out.println("[DEBUG1] " + licenseNumber);
		byte[] b1 = licenseNumber.getBytes("ISO-8859-1");
		licenseNumber = new String(b1, "UTF-8");
		System.out.println("[DEBUG2] " + licenseNumber);
		Session session = sessionFactory.openSession();
		org.hibernate.Query query = session.createQuery("from License where number = '" + licenseNumber + "'");
		
		List<License> licenses = query.list();
		if(licenses.size() < 1)
		{
			License invalidLicense = new License();
			invalidLicense.setNumber("NULL");
			invalidLicense.setOwnerName("NULL");
			
			licenses.add(invalidLicense);
		}
		
		return new ResponseEntity<License>(licenses.get(0), HttpStatus.OK);
	}
	@RequestMapping(value = "/licenses", method = RequestMethod.POST)
	public ResponseEntity<License> addLicense(@RequestBody License license) {

		Session session = sessionFactory.openSession();
		Transaction tx = null;
		int licenseId;

		try {
			tx = session.beginTransaction();
			licenseId = (Integer) session.save(license);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return new ResponseEntity<License>(license, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/licenses/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteLicense(@PathVariable("id") int id) {

		Session session = sessionFactory.openSession();
		Transaction tx = null;
		License license = (License) session.get(License.class, id);

		try {
			tx = session.beginTransaction();
			// licenseId = (Integer) session.save(license);
			session.delete(license);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}

		return new ResponseEntity<String>("id: " + id, HttpStatus.OK);
	}
}
