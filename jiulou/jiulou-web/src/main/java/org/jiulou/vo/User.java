package org.jiulou.vo;

/**
 * 用户表：记录所有站内用户
 * 
 * @author Gentool
 * @date 2011-11-11 20:24:00
 */
public class User {

	private long id;
	private String password;
	private String email;
	private String username;
	private int loginCount;
	private int status;
	private java.util.Date registerDate;
	private java.util.Date trialFrom;
	private java.util.Date trialTo;
	private java.util.Date purchaseDate;
	private java.util.Date validDateFrom;
	private java.util.Date validDateTo;
	private String uuid;
	private String hash;
	private String locale;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getLoginCount() {
		return loginCount;
	}

	public void setLoginCount(int loginCount) {
		this.loginCount = loginCount;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public java.util.Date getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(java.util.Date registerDate) {
		this.registerDate = registerDate;
	}

	public java.util.Date getTrialFrom() {
		return trialFrom;
	}

	public void setTrialFrom(java.util.Date trialFrom) {
		this.trialFrom = trialFrom;
	}

	public java.util.Date getTrialTo() {
		return trialTo;
	}

	public void setTrialTo(java.util.Date trialTo) {
		this.trialTo = trialTo;
	}

	public java.util.Date getPurchaseDate() {
		return purchaseDate;
	}

	public void setPurchaseDate(java.util.Date purchaseDate) {
		this.purchaseDate = purchaseDate;
	}

	public java.util.Date getValidDateFrom() {
		return validDateFrom;
	}

	public void setValidDateFrom(java.util.Date validDateFrom) {
		this.validDateFrom = validDateFrom;
	}

	public java.util.Date getValidDateTo() {
		return validDateTo;
	}

	public void setValidDateTo(java.util.Date validDateTo) {
		this.validDateTo = validDateTo;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

}