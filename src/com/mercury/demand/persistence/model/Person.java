package com.mercury.demand.persistence.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="SS_PERSON")
public class Person implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2748948831285204760L;
	private int personId;
	private String username;
	private String password;
	private String authority;
	private boolean enabled;
    
	// Constructors
	/** default constructor */
    public Person() {}

    // Property accessors
    @Id
    @Column(name="PERSON_ID", nullable = false)
    public int getPersonId() {
        return this.personId;
    }
    public void setPersonId(int personId) {
        this.personId = personId;
    }

    @Column
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	@Column
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	@Column
	public String getAuthority() {
		return authority;
	}
	public void setAuthority(String authority) {
		this.authority = authority;
	}
	
	@Column
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
  
}
