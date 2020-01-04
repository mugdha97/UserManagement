package com.bridgelabz.usermanagement.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer userId;
	private String firstName;
	private String middleName;
	private String lastName;
	private String DoB;
	private String country;
	private String Gender;
	private String phone;
	private String email;
	private String address;
	private String userName;
	private String password;
	private String userRole;
	private String profilePicture;
	private boolean verifyStatus;
	private boolean status;
	@CreationTimestamp
	private Date createDate;
	private LocalDateTime lastAccessDate;
	
	@UpdateTimestamp
	private Date updateDate;
	@OneToMany(cascade = CascadeType.ALL)
	private List<LoginHistory>loginList=new ArrayList<LoginHistory>();
	
	@OneToMany(cascade = CascadeType.ALL)
	private List<Permissions>permissionList=new ArrayList<Permissions>();
	
	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getGender() {
		return Gender;
	}

	public void setGender(String gender) {
		Gender = gender;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserRole() {
		return userRole;
	}

	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}

	public String getProfilePicture() {
		return profilePicture;
	}

	public void setProfilePicture(String profilePicture) {
		this.profilePicture = profilePicture;
	}

	public boolean isVerifyStatus() {
		return verifyStatus;
	}

	public void setVerifyStatus(boolean verifyStatus) {
		this.verifyStatus = verifyStatus;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getDoB() {
		return DoB;
	}

	public void setDoB(String doB) {
		DoB = doB;
	}

	public LocalDateTime getLastAccessDate() {
		return lastAccessDate;
	}

	public void setLastAccessDate(LocalDateTime lastAccessDate) {
		this.lastAccessDate = lastAccessDate;
	}

	public List<LoginHistory> getLoginList() {
		return loginList;
	}

	public void setLoginList(List<LoginHistory> loginList) {
		this.loginList = loginList;
	}

	
	public List<Permissions> getPermissionList() {
		return permissionList;
	}

	public void setPermissionList(List<Permissions> permissionList) {
		this.permissionList = permissionList;
	}

	

	@Override
	public String toString() {
		return "User [userId=" + userId + ", firstName=" + firstName + ", middleName=" + middleName + ", lastName="
				+ lastName + ", DoB=" + DoB + ", country=" + country + ", Gender=" + Gender + ", phone=" + phone
				+ ", email=" + email + ", address=" + address + ", userName=" + userName + ", password=" + password
				+ ", userRole=" + userRole + ", profilePicture=" + profilePicture + ", verifyStatus=" + verifyStatus
				+ ", status=" + status + ", createDate=" + createDate + ", lastAccessDate=" + lastAccessDate
				+ ", updateDate=" + updateDate + ", loginList=" + loginList + ", permissionList=" + permissionList
				+ "]";
	}
	

	public User(Integer userId, String firstName, String middleName, String lastName, String doB, String country,
			String gender, String phone, String email, String address, String userName, String password,
			String userRole, String profilePicture, boolean verifyStatus, boolean status, Date createDate,
			LocalDateTime lastAccessDate, Date updateDate, List<LoginHistory> loginList,
			List<Permissions> permissionList) {
		super();
		this.userId = userId;
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
		DoB = doB;
		this.country = country;
		Gender = gender;
		this.phone = phone;
		this.email = email;
		this.address = address;
		this.userName = userName;
		this.password = password;
		this.userRole = userRole;
		this.profilePicture = profilePicture;
		this.verifyStatus = verifyStatus;
		this.status = status;
		this.createDate = createDate;
		this.lastAccessDate = lastAccessDate;
		this.updateDate = updateDate;
		this.loginList = loginList;
		this.permissionList = permissionList;
	}

	public User() {
		super();
	}

	

	
}
