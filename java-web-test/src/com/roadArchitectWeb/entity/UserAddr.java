package com.roadArchitectWeb.entity;

public class UserAddr extends IdEntity {
	private String country;
	private String city;
	private String userid;
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	@Override
	public String toString() {
		return "UserAddr [country=" + country + ", city=" + city + ", userid=" + userid + ", id=" + id
				+ ", getCountry()=" + getCountry() + ", getCity()=" + getCity() + ", getUserid()=" + getUserid()
				+ ", getId()=" + getId() + ", toString()=" + super.toString() + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + "]";
	}
	
}
