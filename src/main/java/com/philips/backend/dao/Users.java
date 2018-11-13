package com.philips.backend.dao;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.philips.backend.encryption.Encryption;

/**
 * @author maysara.mohamed
 * @version 1.0
 * @since 2018-10-06
 */

@Entity
public class Users implements Serializable {

	/**
	 * 
	 */

	private static final long serialVersionUID = 1L;
	private String userName;
	private String password;
	private Date birthDate;
	private double totalPoints; 

	private String name;
	private String title;
	private String mail;
	private String phone;
	private String address;
	private String profileImage;
	private String userType;
	private String extras;

	// private Integer id;

	private Set<Location> location;
	private Set<SubmitedInvoice> submitedInvoice;
	private Set<PhilipsInvoice> philipsInvoice;
	private Set<PointsHistory> pointsHistory;

	public Users() {

	}

	/**
	 * @param name
	 * @param title
	 * @param mail
	 * @param phone
	 * @param role
	 * @param groups
	 */
	public Users(String name, String userName, String password, String phone, String mail, Date birthDate) {
		super();
		this.name = name;
		this.userName = userName;
		this.mail = mail;
		this.phone = phone;
		this.birthDate = birthDate;
		this.password = Encryption.encrypt(password);
	}

	/**
	 * @param userName
	 * @param password
	 * @param birthDate
	 * @param name
	 * @param title
	 * @param mail
	 * @param phone
	 * @param id
	 * @param address
	 */
	public Users(String userName, String password, Date birthDate, String name, String title, String mail, String phone,
			String address, String userType) {
		this(name, userName, password, phone, mail, birthDate);
		this.title = title;
		this.address = address;
		// this.id = id;
		this.userType = userType;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title
	 *            the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the mail
	 */
	public String getMail() {
		return mail;
	}

	/**
	 * @param mail
	 *            the mail to set
	 */
	public void setMail(String mail) {
		this.mail = mail;
	}

	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * @param phone
	 *            the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * @return the userName
	 */
	@Id
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName
	 *            the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the birthDate
	 */
	public Date getBirthDate() {
		return birthDate;
	}

	/**
	 * @param birthDate
	 *            the birthDate to set
	 */
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address
	 *            the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @return the profileImage
	 */
	public String getProfileImage() {
		return profileImage;
	}

	/**
	 * @param profileImage
	 *            the profileImage to set
	 */
	public void setProfileImage(String profileImage) {
		this.profileImage = profileImage;
	}

	/**
	 * @return the location
	 */
	@JsonIgnore
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	public Set<Location> getLocation() {
		return location;
	}

	/**
	 * @param location
	 *            the location to set
	 */
	public void setLocation(Set<Location> location) {
		this.location = location;
	}

	/**
	 * @return the submitedInvoice
	 */
	@JsonIgnore
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	public Set<SubmitedInvoice> getSubmitedInvoice() {
		return submitedInvoice;
	}

	/**
	 * @param submitedInvoice
	 *            the submitedInvoice to set
	 */
	public void setSubmitedInvoice(Set<SubmitedInvoice> submitedInvoice) {
		this.submitedInvoice = submitedInvoice;
	}

	/**
	 * @return the philipsInvoice
	 */
	@JsonIgnore
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	public Set<PhilipsInvoice> getPhilipsInvoice() {
		return philipsInvoice;
	}

	/**
	 * @param philipsInvoice
	 *            the philipsInvoice to set
	 */
	public void setPhilipsInvoice(Set<PhilipsInvoice> philipsInvoice) {
		this.philipsInvoice = philipsInvoice;
	}

	
	
	/**
	 * @return the pointsHistory
	 */
	@JsonIgnore
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	public Set<PointsHistory> getPointsHistory() {
		return pointsHistory;
	}

	/**
	 * @param pointsHistory the pointsHistory to set
	 */
	public void setPointsHistory(Set<PointsHistory> pointsHistory) {
		this.pointsHistory = pointsHistory;
	}

	/**
	 * @return the userType
	 */
	public String getUserType() {
		return userType;
	}

	/**
	 * @param userType
	 *            the userType to set
	 */
	public void setUserType(String userType) {
		this.userType = userType;
	}

	/**
	 * @return the extras
	 */
	public String getExtras() {
		return extras;
	}

	/**
	 * @param extras
	 *            the extras to set
	 */
	public void setExtras(String extras) {
		this.extras = extras;
	}

	/**
	 * @return the totalPoints
	 */
	public double getTotalPoints() {
		return totalPoints;
	}

	/**
	 * @param totalPoints the totalPoints to set
	 */
	public void setTotalPoints(double totalPoints) {
		this.totalPoints = totalPoints;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Users [userName=" + userName + ", password=" + password + ", birthDate=" + birthDate + ", totalPoints="
				+ totalPoints + ", name=" + name + ", title=" + title + ", mail=" + mail + ", phone=" + phone
				+ ", address=" + address + ", profileImage=" + profileImage + ", userType=" + userType + ", extras="
				+ extras + ", location=" + location + ", submitedInvoice=" + submitedInvoice + ", philipsInvoice="
				+ philipsInvoice + ", pointsHistory=" + pointsHistory + "]";
	}

}