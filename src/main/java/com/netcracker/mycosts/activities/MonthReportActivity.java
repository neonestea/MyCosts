package com.netcracker.mycosts.activities;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.netcracker.mycosts.entities.Currency;
import com.netcracker.mycosts.entities.MonthCosts;
import com.netcracker.mycosts.entities.User;
//import com.netcracker.mycosts.entities.UserEmailView;
import com.netcracker.mycosts.services.MonthCostsService;
import com.netcracker.mycosts.services.UserService;

import java.util.Comparator;
import java.util.List;
import lombok.SneakyThrows;
import org.apache.commons.codec.CharEncoding;
import org.apache.pdfbox.io.IOUtils;
import org.flywaydb.core.internal.resource.filesystem.FileSystemResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.mail.internet.MimeMessage;
import java.io.*;
import java.time.LocalDate;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component

public class MonthReportActivity {

    private JavaMailSender emailSender;
    private UserService userService;
    private MonthCostsService monthCostsService;

    //TODO Cron - the last day of each month
    /*@SneakyThrows
    @Scheduled(fixedRate = 60000000)
    public void first() {
        List<String> usersEmails = userService.getUsersEmails().stream()
                .map(userEmailView -> userEmailView.getEmail())
                .collect(Collectors.toList());

        usersEmails.parallelStream()
                .forEach(this::sendEmail);
    }*/

    @SneakyThrows
    private InputStream createDocument(List<MonthCosts> userMonthCosts) {
        Document document = new Document(PageSize.A4, 20, 20, 20, 20);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, outputStream);

        String month = LocalDate.now().getMonth().toString();
        String year = String.valueOf(LocalDate.now().getYear());
        document.open();
        Font fontbold = FontFactory.getFont("Times-Roman", 24, Font.BOLD);
        Paragraph title = new Paragraph("Costs by " + month + " " + year, fontbold);
        title.setSpacingAfter(20);
        title.setAlignment(1);
        document.add(title);
        PdfPTable table = new PdfPTable(4);
        addTableHeader(table);
        userMonthCosts.forEach(monthCosts ->  addRow(table, monthCosts));
        document.add(table);
        document.close();

        return new ByteArrayInputStream(outputStream.toByteArray());
    }

    @SneakyThrows
    private void sendEmail(String userEmail){
        String month = LocalDate.now().getMonth().toString();
        String year = String.valueOf(LocalDate.now().getYear());
        User user = userService.getUserByEmail(userEmail);
        List<MonthCosts> userMonthCosts = monthCostsService.findMonthCostsByUserAndStartDate(user, LocalDate.of(
                LocalDate.now().getYear(), LocalDate.now().getMonth(), 1)
        );
        userMonthCosts.sort(Comparator.comparing(monthCosts -> monthCosts.getCategory().getName()));
        Map<Currency, Double> totalCostsByCurrency = userMonthCosts.stream()
                .collect(Collectors.groupingBy(monthCosts -> monthCosts.getAccount().getCurrency(),
                        Collectors.summingDouble(monthCost -> monthCost.getAmount())));


        InputStream is = createDocument(userMonthCosts);
        String filename = "Costs by " + month + " " + year + ".pdf";

        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, CharEncoding.UTF_8);

        helper.setTo(userEmail);
        helper.setSubject("Costs by " + month + " " + year);
        helper.setText("Here is your costs file.");
        helper.addAttachment(filename, new ByteArrayResource(IOUtils.toByteArray(is)));



        emailSender.send(message);
    }

    @Autowired
    public void setEmailSender(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    private void addTableHeader(PdfPTable table) {
        Stream.of("Category", "Account", "Currency", "Total amount")
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

    private void addRow(PdfPTable table, MonthCosts monthCosts) {
        table.addCell(monthCosts.getCategory().getName());
        table.addCell(monthCosts.getAccount().getName());
        table.addCell(monthCosts.getAccount().getCurrency().name());
        table.addCell(String.valueOf(monthCosts.getAmount()));
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setMonthCostsService(MonthCostsService monthCostsService) {
        this.monthCostsService = monthCostsService;
    }
}
