package com.yasiulevichnikita.VacationSolution.services;

import com.yasiulevichnikita.VacationSolution.models.User;
import com.yasiulevichnikita.VacationSolution.models.VacationRequest;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeUtility;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;


@Service
@RequiredArgsConstructor
public class EmailService {

    public final JavaMailSender emailSender;
    public final DocumentService documentService;

    public void sendStatusEmail(String toAddress, String subject, String name, String reqDate, String message) {

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

        simpleMailMessage.setFrom("nikitayasiulevich@gmail.com");

        simpleMailMessage.setTo(toAddress);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(String.format("Здравствуйте, %s! Статус Вашей заявки на отпуск от %s был изменён на: %s", name, reqDate, message));

        emailSender.send(simpleMailMessage);
    }

    public void sendApproveEmailWithAttachment(User user, VacationRequest request) throws Exception {

        MimeMessage mimeMessage = emailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

        mimeMessageHelper.setFrom("nikitayasiulevich@gmail.com");
        mimeMessageHelper.setTo(user.getEmail());
        mimeMessageHelper.setSubject("Vacation Solution");

        documentService.fillFileWithData(user, request);

        String fileName = MimeUtility.encodeText(new File(documentService.getOUTPUT_FILE()).getName(), "UTF-8", "B");
        mimeMessageHelper.addAttachment(fileName, new FileSystemResource(documentService.getOUTPUT_FILE()));

        mimeMessageHelper.setText(String.format("Здравствуйте, %s! Статус Вашей заявки на отпуск от %s был изменён на: Approved. Распечатайте приложенное заявление, подпишите его, укажите дату и передайте в отдел кадров. ", user.getName(), request.getRequestDate()));

        emailSender.send(mimeMessage);

        documentService.deleteFile(documentService.getOUTPUT_FILE());
    }
}
