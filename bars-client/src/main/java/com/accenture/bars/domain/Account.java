package com.accenture.bars.domain;
 
import java.sql.Timestamp;
 
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.servlet.ModelAndView;
 
 
/**
 * Account Entity
 *
 */
 
public class Account {
 
 
	private int accountId;
	private String accountName;
	private Timestamp dateCreated;
	private String isActive;
	private String lastEdited;
 
	public Account() {
	}
	
	public Account(int accountId, String accountName, String isActive,
			String lastEdited) {
		this.accountId = accountId;
		this.accountName = accountName;
		this.isActive = isActive;
		this.lastEdited = lastEdited;
	}
 
	public Account(int accountId, String accountName, Timestamp dateCreated, String isActive,
			String lastEdited) {
		this.accountId = accountId;
		this.accountName = accountName;
		this.dateCreated = dateCreated;
		this.isActive = isActive;
		this.lastEdited = lastEdited;
	}
	
	public Account(String jsonObject) throws JSONException {
		JSONObject accountJson = new JSONObject(jsonObject);
		this.accountId = (int) accountJson.getInt("accountId");
		this.accountName = (String) accountJson.getString("accountName");
		this.dateCreated = Timestamp.valueOf(accountJson.getString("dateCreated"));
		this.isActive = (String) accountJson.getString("isActive");
		this.lastEdited = (String) accountJson.getString("lastEdited");
	}
 
	public void addModelAndView(ModelAndView mv) {
		mv.addObject("accountId", this.accountId);
		mv.addObject("accountName", this.accountName);
		mv.addObject("dateCreated", this.dateCreated);
		mv.addObject("isActive", this.isActive);
		mv.addObject("lastEdited", this.lastEdited);
	}
 
	public String toJson() throws JSONException {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("accountId", this.accountId);
		jsonObject.put("accountName", this.accountName);
		jsonObject.put("dateCreated", this.dateCreated);
		jsonObject.put("isActive", this.isActive);
		jsonObject.put("lastEdited", this.lastEdited);
		return jsonObject.toString();
	}
 
	public int getAccountId() {
		return accountId;
	}
 
	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}
 
	public String getAccountName() {
		return accountName;
	}
 
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
 
	public Timestamp getDateCreated() {
		return dateCreated;
	}
 
	public void setDateCreated(Timestamp dateCreated) {
		this.dateCreated = dateCreated;
	}
 
	public String getIsActive() {
		return isActive;
	}
 
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
 
	public String getLastEdited() {
		return lastEdited;
	}
 
	public void setLastEdited(String lastEdited) {
		this.lastEdited = lastEdited;
	}
 
}