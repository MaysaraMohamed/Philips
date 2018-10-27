package com.philips.backend.dao;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import com.philips.backend.encryption.Encryption;

/**
 * @author maysara.mohamed
 * @version 1.0
 * @since 2018-10-06
 */

@Entity
public class User implements Serializable {

	/**
	 * 
	 */

	private static final long serialVersionUID = 1L;
	private String userName;
	private String password;
	private Date birthDate;

	private String name;
	private String title;
	private String mail;
	private String phone;
	private String address;
	private String profileImage;
	private String userType; 

	private Integer id;

	private Set<Location> location;

	public User() {

	}

	/**
	 * @param name
	 * @param title
	 * @param mail
	 * @param phone
	 * @param role
	 * @param groups
	 */
	public User(String name, String userName, String password, String phone, String mail, Date birthDate) {
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
	public User(String userName, String password, Date birthDate, String name, String title, String mail, String phone,
			Integer id, String address, String userType) {
		this(name, userName, password, phone, mail, birthDate);
		this.title = title;
		this.address = address;
		this.id = id;
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
	 * @return the id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the userName
	 */
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
	 * @return the userType
	 */
	public String getUserType() {
		return userType;
	}

	/**
	 * @param userType the userType to set
	 */
	public void setUserType(String userType) {
		this.userType = userType;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "User [userName=" + userName + ", password=" + password + ", birthDate=" + birthDate + ", name=" + name
				+ ", title=" + title + ", mail=" + mail + ", phone=" + phone + ", address=" + address
				+ ", profileImage=" + profileImage + ", id=" + id + "]";
	}

}