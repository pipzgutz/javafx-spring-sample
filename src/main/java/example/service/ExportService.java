package example.service;

import example.entity.Attendee;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

/**
 * @author Philip Mark Gutierrez <pgutierrez@owens.com>
 * @version 1.0
 * @since November 23, 2017
 */
@Service
public class ExportService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExportService.class);

    @Autowired
    private RegistrationService registrationService;

    private List<String> headers = Arrays.asList("First Name", "Last Name", "Organization", "Email Address", "Phone Number", "Looking For", "Trainings Interested In");

    public void exportToExcel(File file) {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet();

        XSSFCellStyle headerCellStyle = createHeaderCellStyle(workbook);

        // Create the headers
        XSSFRow headerRow = sheet.createRow(0);
        IntStream.range(0, headers.size()).forEach(i -> {
            XSSFCell cell = headerRow.createCell(i);
            cell.setCellValue(headers.get(i));
            cell.setCellStyle(headerCellStyle);
        });

        List<Attendee> attendees = registrationService.findAll();

        XSSFCellStyle contentStyle = createContentStyle(workbook);

        IntStream.range(0, attendees.size()).forEach(i -> {
            // start at the 2nd row
            int rowStart = i + 1;
            XSSFRow attendeeRow = sheet.createRow(rowStart);
            Attendee attendee = attendees.get(i);

            // enter the attendee details
            createContentCell(attendeeRow.createCell(0), attendee.getFirstName(), contentStyle);
            createContentCell(attendeeRow.createCell(1), attendee.getLastName(), contentStyle);
            createContentCell(attendeeRow.createCell(2), attendee.getOrganization(), contentStyle);
            createContentCell(attendeeRow.createCell(3), attendee.getEmail(), contentStyle);
            createContentCell(attendeeRow.createCell(4), attendee.getPhoneNumber(), contentStyle);
            createContentCell(attendeeRow.createCell(5), attendee.getLookingFor(), contentStyle);
            createContentCell(attendeeRow.createCell(6), attendee.getTrainingsInterestedIn(), contentStyle);
        });

        // auto size columns
        IntStream.range(0, headers.size()).forEach(sheet::autoSizeColumn);

        createExcelFile(workbook, file);
    }

    private void createContentCell(XSSFCell cell, String content, XSSFCellStyle style) {
        cell.setCellValue(content);
        cell.setCellStyle(style);
    }

    private XSSFCellStyle createHeaderCellStyle(XSSFWorkbook workbook) {
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeightInPoints((short) 14);

        XSSFCellStyle style = workbook.createCellStyle();
        style.setFillBackgroundColor(IndexedColors.AQUA.getIndex());
        style.setFont(font);

        return style;
    }

    private XSSFCellStyle createContentStyle(XSSFWorkbook workbook) {
        XSSFFont font = workbook.createFont();
        font.setFontHeightInPoints((short) 12);

        XSSFCellStyle style = workbook.createCellStyle();
        style.setFont(font);

        return style;
    }

    private void createExcelFile(XSSFWorkbook workbook, File file) {
        Path excelFile = Paths.get(file.getPath());

        try {
            OutputStream outputStream = Files.newOutputStream(excelFile);
            workbook.write(outputStream);
        } catch (IOException e) {
            LOGGER.error("Unable to create excel file");
        }
    }
}
