package com.yasiulevichnikita.VacationSolution.services;

import com.aspose.words.Document;
import com.aspose.words.FindReplaceDirection;
import com.aspose.words.FindReplaceOptions;
import com.yasiulevichnikita.VacationSolution.models.User;
import com.yasiulevichnikita.VacationSolution.models.VacationRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFFooter;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@NoArgsConstructor
@Getter
public class DocumentService {
    private final String INPUT_FILE = "D:\\Nikita Yasiulevich\\BSUIR 172301\\6 семестр\\Курсовой проект\\Код\\VacationSolution\\src\\main\\resources\\static\\template.docx";
    private final String OUTPUT_FILE = "D:\\Nikita Yasiulevich\\BSUIR 172301\\6 семестр\\Курсовой проект\\Код\\VacationSolution\\src\\main\\resources\\static\\statement.docx";

    public void fillFileWithData(User user, VacationRequest request) throws Exception {


        Document doc = openTemplateDocument(INPUT_FILE);
        replaceText(doc, user, request);
        XWPFDocument xwpfDoc = new XWPFDocument(new FileInputStream(OUTPUT_FILE));
        removeEvaluationText(xwpfDoc);
        extractText(xwpfDoc, OUTPUT_FILE);
        xwpfDoc.close();
    }

    private Document openTemplateDocument(String filePath) throws Exception {
        return new Document(filePath);
    }

    private void replaceText(Document doc, User user, VacationRequest request) throws Exception {
        doc.getRange().replace("__name__", user.getName(), new FindReplaceOptions(FindReplaceDirection.FORWARD));
        doc.getRange().replace("__d__", daysBetween(request.getStartDate(), request.getEndDate()) + "", new FindReplaceOptions(FindReplaceDirection.FORWARD));
        doc.getRange().replace("__start__", request.getStartDate(), new FindReplaceOptions(FindReplaceDirection.FORWARD));
        doc.getRange().replace("__end__", request.getEndDate(), new FindReplaceOptions(FindReplaceDirection.FORWARD));
        doc.save(OUTPUT_FILE);
    }

    private void removeEvaluationText(XWPFDocument doc) {
        removeParagraphs(doc, "Evaluation Only. Created with Aspose.Words. Copyright 2003-2020 Aspose Pty Ltd.");
        removeFooterText(doc, "Created with an evaluation");
    }

    private void removeParagraphs(XWPFDocument doc, String textToRemove) {
        List<XWPFParagraph> paragraphs = doc.getParagraphs();
        for (int i = 0; i < paragraphs.size(); i++) {
            XWPFParagraph paragraph = paragraphs.get(i);
            if (paragraph.getText().equals(textToRemove)) {
                doc.removeBodyElement(i);
                i--;
            }
        }
    }

    private void removeFooterText(XWPFDocument doc, String textToRemove) {
        List<XWPFFooter> footers = doc.getFooterList();
        for (XWPFFooter footer : footers) {
            List<XWPFParagraph> footerParagraphs = footer.getParagraphs();
            for (int i = 0; i < footerParagraphs.size(); i++) {
                XWPFParagraph footerParagraph = footerParagraphs.get(i);
                if (footerParagraph.getText().contains(textToRemove)) {
                    footer.removeParagraph(footerParagraph);
                    i--;
                }
            }
        }
    }

    private void extractText(XWPFDocument doc, String outputFile) throws Exception {
        XWPFDocument newDoc = new XWPFDocument();
        for (XWPFParagraph para : doc.getParagraphs()) {
            XWPFParagraph newPara = newDoc.createParagraph();
            for (XWPFRun run : para.getRuns()) {
                XWPFRun newRun = newPara.createRun();
                newRun.setText(run.getText(run.getTextPosition()));
            }
        }
        try (FileOutputStream out = new FileOutputStream(outputFile)) {
            newDoc.write(out);
        }
    }

    public void deleteFile(String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            try {
                Files.delete(Paths.get(filePath));
            } catch (IOException e) {
                System.err.println("Failed to delete file: " + filePath);
                e.printStackTrace();
            }
        } else {
            System.err.println("File does not exist: " + filePath);
        }
    }

    public long daysBetween(String date1Str, String date2Str) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate date1 = null;
        LocalDate date2 = null;
        try {
            date1 = LocalDate.parse(date1Str, formatter);
            date2 = LocalDate.parse(date2Str, formatter);
        } catch (DateTimeException e) {
            formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            try {
                date1 = LocalDate.parse(date1Str, formatter);
                date2 = LocalDate.parse(date2Str, formatter);
            } catch (DateTimeException e2) {
                throw new DateTimeException("Invalid date format. Use dd.mm.yyyy or dd-mm-yyyy.");
            }
        }
        return ChronoUnit.DAYS.between(date1, date2);
    }
}
