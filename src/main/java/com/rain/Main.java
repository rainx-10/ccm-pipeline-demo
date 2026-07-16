package com.rain;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import com.rain.model.Customer;
import com.rain.model.DocumentGenerator;

public class Main {
    public static void main(String[] args) throws Exception {
        DocumentGenerator generator = new DocumentGenerator();

        List<Customer> customers = generator.loadCustomers("data/customers.json");
        Path outputDir = Paths.get("output");

        int count = 0;
        for (Customer customer : customers) {
            String html = generator.renderHtml(customer);
            Path pdfPath = outputDir.resolve(customer.getId() + ".pdf");
            generator.writePdf(html, pdfPath);
            System.out.println("Generated: " + pdfPath.toAbsolutePath());
            count++;
        }
        // This summary line is deliberate — it reads nicely in Jenkins console logs later.
        System.out.println("Done. Generated " + count + " document(s) in " + outputDir.toAbsolutePath());
    }
}