package com.rain;

import com.rain.model.Customer;
import com.rain.model.DocumentGenerator;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

class DocumentGeneratorTest {

    private final DocumentGenerator generator = new DocumentGenerator();

    @Test
    void rendersHtmlContainingCustomerName() {
        Customer c = new Customer();
        c.setId("C-9001");
        c.setName("Test Person");
        Customer.LineItem item = new Customer.LineItem();
        item.setDescription("Sample");
        item.setPrice(new BigDecimal("10.00"));
        c.setItems(List.of(item));

        String html = generator.renderHtml(c);

        assertTrue(html.contains("Test Person"), "HTML should contain the customer name");
    }

    @Test
    void generatedPdfIsNonEmpty() throws Exception {
        Customer c = new Customer();
        c.setId("C-9002");
        c.setName("Pdf Person");
        c.setItems(List.of());

        String html = generator.renderHtml(c);
        Path out = Files.createTempFile("invoice-", ".pdf");
        generator.writePdf(html, out);

        assertTrue(Files.size(out) > 500, "Generated PDF should be non-trivial in size");
        Files.deleteIfExists(out);
    }
}