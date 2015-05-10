package org.apache.servicemix.examples.domain;

public class Ticket {

	private int id;
	
	private int fine; 
	private String issuedBy; 
	private String issuedDate; 
	private String expiryDate; 
	private String status;

	private License license;
	
	private Violation violation;

	public Violation getViolation() {
		return violation;
	}

	public void setViolation(Violation violation) {
		this.violation = violation;
	}

	public Ticket() {

	}

	public Ticket(License license) {
		this.license = license;
	}

	public Ticket(int fine, String issuedBy,String issuedDate,String expiryDate, String status, License li, Violation violation2) {
		this.fine = fine;
		this.issuedBy = issuedBy;
		this.issuedDate = issuedDate; 
		this.expiryDate = expiryDate;
		this.status = status;
		license = li;
		violation = violation2;
	}

	public License getLicense() {
		return license;
	}

	public void setLicense(License license) {
		this.license = license;
	}

	public int getFine() {
		return fine;
	}

	public void setFine(int fine) {
		this.fine = fine;
	}

	public String getIssuedBy() {
		return issuedBy;
	}

	public void setIssuedBy(String issuedBy) {
		this.issuedBy = issuedBy;
	}

	public String getIssuedDate() {
		return issuedDate;
	}

	public void setIssuedDate(String issuedDate) {
		this.issuedDate = issuedDate;
	}

	public String getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getId() {
		return id;
	}

}
