package com.fibeso.captacion.pfuneraria;

import java.util.TimeZone;

import javax.annotation.PostConstruct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PfunerariaApplication {

	public static void main(String[] args) {
		SpringApplication.run(PfunerariaApplication.class, args);
	}
	
	/*
	@PostConstruct
    void setUTCTimeZone(){
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }
	*/

}
