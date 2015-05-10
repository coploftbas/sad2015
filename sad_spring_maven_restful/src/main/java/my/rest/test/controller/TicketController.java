package my.rest.test.controller;

import java.util.List;

import my.rest.test.persistence.License;
import my.rest.test.persistence.Ticket;
import my.rest.test.persistence.Violation;

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
public class TicketController {

	@Autowired
	SessionFactory sessionFactory;

	@RequestMapping(value = "/ticket", method = RequestMethod.GET)
	public ResponseEntity<Ticket> getTicket() {
		License li = new License("Ben", "ฎท 9897", "Pathumthani", "RED",
				"Nissan", "VAN");
		Violation violation = new Violation(
				"Thu_Apr__2_09-26-55_2015.before_violation.jpg",
				"Thu_Apr__2_09-26-55_2015.before_violation.jpg",
				"Thu_Apr__2_09-26-55_2015.before_violation.jpg",
				"Thu_Apr__2_09-26-55_2015.before_violation.jpg",
				"Thu_Apr__2_09-26-55_2015.avi",
				"Thu_Apr__2_09-26-55_2015.licenseplate.jpg", "กรุงเทพมหานคร",
				"Honda", "White", "SUV", "UnEvaluated", "2015-04-02 09:26:55",
				"Sutthisan");
		Ticket ticket = new Ticket(1000,"Chirag","10/03/2015","10/04/2015","Unpaid",li, violation);
		return new ResponseEntity<Ticket>(ticket, HttpStatus.OK);
	}

	@RequestMapping(value = "/tickets", method = RequestMethod.GET)
	public ResponseEntity<List<Ticket>> getTickets() {
		Session session = sessionFactory.openSession();
		org.hibernate.Query query = session.createQuery("from Ticket");
		List<Ticket> tickets = query.list();

		return new ResponseEntity<List<Ticket>>(tickets, HttpStatus.OK);
	}

	@RequestMapping(value = "/tickets/{id}", method = RequestMethod.GET)
	public ResponseEntity<Ticket> getTicket(@PathVariable("id") int id) {
		Session session = sessionFactory.openSession();
		org.hibernate.Query query = session.createQuery("from Ticket where id="
				+ id);
		List<Ticket> tickets = query.list();

		return new ResponseEntity<Ticket>(tickets.get(0), HttpStatus.OK);
	}

	@RequestMapping(value = "/tickets", method = RequestMethod.POST)
	public ResponseEntity<Ticket> addTicket(@RequestBody Ticket ticket) {

		Session session = sessionFactory.openSession();
		Transaction tx = null;
		int ticketId;

		try {
			tx = session.beginTransaction();
			ticketId = (Integer) session.save(ticket);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}

		return new ResponseEntity<Ticket>(ticket, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/tickets/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteTicket(@PathVariable("id") int id) {

		Session session = sessionFactory.openSession();
		Transaction tx = null;
		Ticket ticket = (Ticket) session.get(Ticket.class, id);

		try {
			tx = session.beginTransaction();
			// licenseId = (Integer) session.save(license);
			session.delete(ticket);
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
