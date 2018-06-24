package com.rohit.hello;

public interface HelloService{
	public String getAllInsurer();
	public String addInsurer(String insurer);
	public String removeInsurer(String insurer);
	public String clearInsurerCache();
}