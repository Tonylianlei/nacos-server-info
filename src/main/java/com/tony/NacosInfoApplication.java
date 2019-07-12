package com.tony;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RefreshScope
@SpringBootApplication
@RestController
@EnableDiscoveryClient
public class NacosInfoApplication {

	@Value("${db.user}")
	private String info;

	public static void main(String[] args) {
		SpringApplication.run(NacosInfoApplication.class, args);
	}

	@LoadBalanced
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@RequestMapping(value = "/getUser" , method = RequestMethod.GET)
	public void getInfo(){

        //String forObject = restTemplate().getForObject(info + "/getFund", String.class);
        System.out.println(info);
	}

}
