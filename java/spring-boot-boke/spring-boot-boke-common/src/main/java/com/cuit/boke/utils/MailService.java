package com.cuit.boke.utils;

import com.yinjk.web.core.exception.BizException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;

@Service
public class MailService {

    /** logger */
    private static final Logger LOGGER = LoggerFactory.getLogger(MailService.class);

    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private MailProperties mailProperties;

    /**
     * 发送邮件
     * @param toMail 发送给谁
     * @param subject 邮件标题
     * @param text 发送内容
     * @throws BizException
     */
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
            LOGGER.error(e.getMessage(), e);
            throw new BizException("发送邮件失败");
        }
    }

}
