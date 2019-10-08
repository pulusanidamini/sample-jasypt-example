package com.example.demo;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.lang.ProcessBuilder;


@Component
class MyTest {

	    private Logger logger = LogManager.getLogger(MyTest.class);
		ProcessBuilder builder = new ProcessBuilder();
		Map<String, String> env = builder.environment();
		
		
	    @Value("${userdemo.name}")
	    private String username;

	    @Value("${usercity.name}")
	    private String cityname;

	    public void testPrint() {
	        logger.info("##############################");
	        logger.info("Username is --------> {}", username);
	        logger.info("Cityname is --------> {}", cityname);
	        logger.info("##############################");
	    }

	}
