package de.vendor.vendorbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// FiXME: What? Warum gibt es zwei mains?
@SpringBootApplication
public class VendorBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(VendorBackendApplication.class, args);
    }

}
