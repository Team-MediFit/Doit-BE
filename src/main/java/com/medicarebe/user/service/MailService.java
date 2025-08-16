package com.medicarebe.user.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender mailSender;

    public void sendVerificationCode(String to, String code) {
        String subject = "[MediCare] Email Verification Code";
        String html = """
            <div style="font-family:Arial,sans-serif;font-size:14px;line-height:1.6">
              <h2 style="margin:0 0 12px 0">MediCare Email Verification</h2>
              <p>Please enter the verification code below within <b>5 minutes</b>.</p>
              <div style="font-size:24px;font-weight:700;letter-spacing:6px;padding:12px 16px;background:#f5f7fa;border:1px solid #e5e8eb;border-radius:8px;display:inline-block">
                %s
              </div>
              <p style="color:#8a8f98;margin-top:12px">Do not share this code with anyone.</p>
            </div>
        """.formatted(code);

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, "UTF-8");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(html, true); // HTML
            mailSender.send(message);
        } catch (MessagingException e) {
            throw new IllegalStateException("MAIL_SEND_FAILED", e);
        }
    }
}