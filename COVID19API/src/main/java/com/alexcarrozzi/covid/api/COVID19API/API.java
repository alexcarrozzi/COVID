package com.alexcarrozzi.covid.api.COVID19API;

import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Configuration
@EnableAutoConfiguration
@RestController
@ComponentScan
@SpringBootApplication
@EnableAsync
public class API extends SpringBootServletInitializer {

	@GetMapping("/totalCases")
	public List<TotalCases> getTotalCases() {
		Service s = new Service();
		return s.getTotalCases();

	}

	@GetMapping("/totalStateData")
	public List<TotalStateData> getTotalStateData() {
		Service s = new Service();
		return s.getTotalStateData();
	}

	@GetMapping("/growthRateAggregate")
	public List<GrowthAggregate> getGrowthRateAggregate() {
		Service s = new Service();
		return s.getGrowthAggregate();
	}

	@GetMapping("/loglog")
	public List<LogLog> getLogLog() {
		Service s = new Service();
		return s.getLogLog();
	}

	@GetMapping("/growthByState")
	public List<GrowthByState> getGrowthByState() {
		Service s = new Service();
		return s.getGrowthByState();
	}

	@GetMapping("/stateGrowthOverTime")
	public List<StateGrowthOverTime> getStateGrowthOverTime() {
		Service s = new Service();
		return s.getStateGrowthOverTime();
	}

	public static void main(String[] args) {
		SpringApplication.run(API.class, args);
	}
}
