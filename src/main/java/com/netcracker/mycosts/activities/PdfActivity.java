package com.netcracker.mycosts.activities;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.SneakyThrows;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

@Component

public class PdfActivity {
    @SneakyThrows
    @Scheduled(fixedRate = 60000)
    public void createDocument(/*String userEmail, String month, String year*/) {
        Document document = new Document(PageSize.A4, 20, 20, 20, 20);
        PdfWriter.getInstance(document, new FileOutputStream("file.pdf"));
        String month = "April";
        String year = "2021";
        document.open();
        Font fontbold = FontFactory.getFont("Times-Roman", 24, Font.BOLD);
        Paragraph title = new Paragraph("Costs by " + month + " " + year, fontbold);
        title.setSpacingAfter(20);
        title.setAlignment(1); // Center
        document.add(title);
        PdfPTable table = new PdfPTable(3);
        addTableHeader(table);
        addRow(table);
        document.add(table);
        document.close();
    }


    private void addTableHeader(PdfPTable table) {
        Stream.of("Category", "Account", "Total amount")
                .forEach(columnTitle -> {
                    PdfPCell header = new PdfPCell();
                    header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    header.setBorderWidth(2);
                    header.setPhrase(new Phrase(columnTitle));
                    header.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    header.setHorizontalAlignment(Element.ALIGN_CENTER);
                    table.addCell(header);
                });
    }

    private void addRow(PdfPTable table) {
        table.addCell("row 1, col 1");
        table.addCell("row 1, col 2");
        table.addCell("row 1, col 3");
    }
}
