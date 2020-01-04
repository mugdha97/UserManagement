package com.bridgelabz.usermanagement.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
public class LoginHistory {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long loginHistoryId;
	
	private LocalDateTime loginDate;
	@JsonIgnoreProperties(value="loginList")
	@ManyToOne(cascade = CascadeType.ALL)
	private User user;
	public long getLoginHistoryId() {
		return loginHistoryId;
	}
	public void setLoginHistoryId(long loginHistoryId) {
		this.loginHistoryId = loginHistoryId;
	}
	
	public LocalDateTime getLoginDate() {
		return loginDate;
	}
	public void setLoginDate(LocalDateTime loginDate) {
		this.loginDate = loginDate;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	public LoginHistory(long loginHistoryId, LocalDateTime loginDate, User user) {
		super();
		this.loginHistoryId = loginHistoryId;
		this.loginDate = loginDate;
		this.user = user;
	}
	public LoginHistory() {
		super();
	} 
	
}
