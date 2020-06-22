package com.razerockztech.SkuPackageApproval;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SkuPackageApprovalApplication {

	public static void main(String[] args) {
		SpringApplication.run(SkuPackageApprovalApplication.class, args);
	}

}
