package example.service;

import example.entity.Attendee;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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

        // Create the headers
        XSSFRow headerRow = sheet.createRow(0);
        IntStream.range(0, headers.size()).forEach(i -> headerRow.createCell(i).setCellValue(headers.get(i)));

        List<Attendee> attendees = registrationService.findAll();

        IntStream.range(0, attendees.size()).forEach(i -> {
            // start at the 2nd row
            int rowStart = i + 1;
            XSSFRow attendeeRow = sheet.createRow(rowStart);
            Attendee attendee = attendees.get(i);

            // enter the attendee details
            attendeeRow.createCell(0).setCellValue(attendee.getFirstName());
            attendeeRow.createCell(1).setCellValue(attendee.getLastName());
            attendeeRow.createCell(2).setCellValue(attendee.getOrganization());
            attendeeRow.createCell(3).setCellValue(attendee.getEmail());
            attendeeRow.createCell(4).setCellValue(attendee.getPhoneNumber());
            attendeeRow.createCell(5).setCellValue(attendee.getLookingFor());
            attendeeRow.createCell(6).setCellValue(attendee.getTrainingsInterestedIn());
        });

        // auto size columns
        IntStream.range(0, headers.size()).forEach(sheet::autoSizeColumn);

        createExcelFile(workbook, file);
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
