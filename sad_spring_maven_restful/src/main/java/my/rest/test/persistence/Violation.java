package my.rest.test.persistence;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Violation {

	@GeneratedValue
	@Id
	private int id;
	// file names
	private String beforeViolationImage;
	private String beforeViolation2Image;
	private String afterViolationImage;
	private String afterViolation2Image;
	private String video;
	private String licensePlateImage;

	// license details
	private String licensePlateProvince;
	private String vehicleBrand;
	private String vehicleColor;
	private String vehicleType;
	private String recognizedLicenseNumber;

	public String getRecognizedLicenseNumber() {
		return recognizedLicenseNumber;
	}

	public void setRecognizedLicenseNumber(String recognizedLicenseNumber) {
		this.recognizedLicenseNumber = recognizedLicenseNumber;
	}

	// voilation details
	private String violationStatus;
	private String violationTime;
	private String location;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "violation")
	private List<Ticket> tickets;
	
//	@OneToOne(mappedBy = "violation", cascade = CascadeType.ALL)
//	private Ticket ticket;
//
//	public Ticket getTicket() {
//		return ticket;
//	}
//
//	public void setTicket(Ticket ticket) {
//		this.ticket = ticket;
//	}

	public Violation() {

	}

	public Violation(String beforeViolationImage, String beforeViolation2Image,
			String afterViolationImage, String afterViolation2Image,
			String video, String licensePlateImage,
			String licensePlateProvince, String vehicleBrand,
			String vehicleColor, String vehicleType, String violationStatus,
			String violationTime, String location) {
		super();
		this.beforeViolationImage = beforeViolationImage;
		this.beforeViolation2Image = beforeViolation2Image;
		this.afterViolationImage = afterViolationImage;
		this.afterViolation2Image = afterViolation2Image;
		this.video = video;
		this.licensePlateImage = licensePlateImage;
		this.licensePlateProvince = licensePlateProvince;
		this.vehicleBrand = vehicleBrand;
		this.vehicleColor = vehicleColor;
		this.vehicleType = vehicleType;
		this.violationStatus = violationStatus;
		this.violationTime = violationTime;
		this.location = location;
	}

	public String getBeforeViolationImage() {
		return beforeViolationImage;
	}

	public void setBeforeViolationImage(String beforeViolationImage) {
		this.beforeViolationImage = beforeViolationImage;
	}

	public String getBeforeViolation2Image() {
		return beforeViolation2Image;
	}

	public void setBeforeViolation2Image(String beforeViolation2Image) {
		this.beforeViolation2Image = beforeViolation2Image;
	}

	public String getAfterViolationImage() {
		return afterViolationImage;
	}

	public void setAfterViolationImage(String afterViolationImage) {
		this.afterViolationImage = afterViolationImage;
	}

	public String getAfterViolation2Image() {
		return afterViolation2Image;
	}

	public void setAfterViolation2Image(String afterViolation2Image) {
		this.afterViolation2Image = afterViolation2Image;
	}

	public String getVideo() {
		return video;
	}

	public void setVideo(String video) {
		this.video = video;
	}

	public String getLicensePlateImage() {
		return licensePlateImage;
	}

	public void setLicensePlateImage(String licensePlateImage) {
		this.licensePlateImage = licensePlateImage;
	}

	public String getLicensePlateProvince() {
		return licensePlateProvince;
	}

	public void setLicensePlateProvince(String licensePlateProvince) {
		this.licensePlateProvince = licensePlateProvince;
	}

	public String getVehicleBrand() {
		return vehicleBrand;
	}

	public void setVehicleBrand(String vehicleBrand) {
		this.vehicleBrand = vehicleBrand;
	}

	public String getVehicleColor() {
		return vehicleColor;
	}

	public void setVehicleColor(String vehicleColor) {
		this.vehicleColor = vehicleColor;
	}

	public String getVehicleType() {
		return vehicleType;
	}

	public void setVehicleType(String vehicleType) {
		this.vehicleType = vehicleType;
	}

	public String getViolationStatus() {
		return violationStatus;
	}

	public void setViolationStatus(String violationStatus) {
		this.violationStatus = violationStatus;
	}

	public String getViolationTime() {
		return violationTime;
	}

	public void setViolationTime(String violationTime) {
		this.violationTime = violationTime;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public int getId() {
		return id;
	}
}
