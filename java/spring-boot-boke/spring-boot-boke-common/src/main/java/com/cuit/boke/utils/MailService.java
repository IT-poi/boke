package com.cuit.boke.utils;

import com.yinjk.web.core.exception.BizException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;

@Service
public class MailService {

    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private MailProperties mailProperties;

    public void sendMail(String toMail, String subject, String text) throws BizException {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom(mailProperties.getUsername());
            helper.setTo(toMail);//发送给谁
            helper.setSubject(subject);//邮件标题
            helper.setText(text, true);
            mailSender.send(mimeMessage);
        } catch (Exception e) {
            throw new BizException("发送邮件失败");
        }
    }

}
