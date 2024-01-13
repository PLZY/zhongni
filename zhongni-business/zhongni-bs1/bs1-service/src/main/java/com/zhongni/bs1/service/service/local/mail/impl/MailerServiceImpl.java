package com.zhongni.bs1.service.service.local.mail.impl;

import com.zhongni.bs1.common.enums.BusinessExceptionEnum;
import com.zhongni.bs1.common.enums.MailTemplateContentEnum;
import com.zhongni.bs1.common.exception.BusinessException;
import com.zhongni.bs1.service.service.local.mail.MailService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.MessageFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 发送邮件功能具体实现类
 */
@Service
@Slf4j
public class MailerServiceImpl implements MailService {
    private static final String EMAIL_PATH_PATTERN = "^\\s*\\w+(?:\\.{0,1}[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$";

    //一个Pattern对象和一个正则表达式相关联
    private static Pattern pattern = Pattern.compile(EMAIL_PATH_PATTERN);

    //默认编码
    public static final String DEFAULT_ENCODING = "UTF-8";

    //本身邮件的发送者，来自邮件配置
    @Value("${spring.mail.username}")
    private String userName;
    @Value("${mail.nickname}")
    private String nickname;

    @Value("${mail.subject}")
    private String subject;

    @Value("${mail.register.first.content}")
    private String mailRegisterFirstContent;

    @Value("${mail.register.end.content}")
    private String mailRegisterEndContent;

    @Value("${mail.login.first.content}")
    private String mailLoginFirstContent;

    @Value("${mail.login.end.content}")
    private String mailLoginEndContent;

    @Value("${mail.resetpassword.first.content}")
    private String mailResetpasswordFirstContent;

    @Value("${mail.resetpassword.end.content}")
    private String mailResetpasswordEndContent;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private ThreadPoolTaskExecutor businessThreadPool;

    private static String mailHtmlTemplate;

    static
    {
        Resource resource = new ClassPathResource("templates/MailTemplates.html");
        StringBuilder sb = new StringBuilder();
        String line = "";
        try(InputStream inputStream = resource.getInputStream();
            BufferedReader fileReader = new BufferedReader(new InputStreamReader(inputStream)))
        {
            while ((line = fileReader.readLine()) != null)
            {
                sb.append(line);
            }

            String templateStrTemp = sb.toString().replaceAll("'", "''").replaceAll("\\{", "'{'").replaceAll("}", "'}'");
            mailHtmlTemplate = templateStrTemp.replace("'{'0'}'", "{0}")
                    .replace("'{'1'}'", "{1}")
                    .replace("'{'2'}'", "{2}");
        }
        catch (Exception e)
        {
            log.info("发送邮件读取模板失败 {} ", e.getMessage(), e);
        }
    }

    @Override
    public void sendHtmlMail(String emailAddress, String verificationCode, String mailTemplateContent)
    {
        if(StringUtils.isBlank(mailHtmlTemplate))
        {
            throw new BusinessException(BusinessExceptionEnum.SYSTEM_EXCEPTION_EMAIL_TEMPLATE_GET_FAIL);
        }

        Matcher matcher = pattern.matcher(emailAddress);
        if(!matcher.matches())
        {
            throw new BusinessException(BusinessExceptionEnum.PARAMS_EXCEPTION_EMAIL_NOT_RIGHT, emailAddress);
        }

        // html
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        try
        {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,true, DEFAULT_ENCODING);
            String[] toWho = {emailAddress};
            //设置邮件的基本信息
            handleBasicInfo(helper, subject, getSendMailContent(verificationCode, mailTemplateContent), toWho);
            // 邮件异步发送
            businessThreadPool.execute(() -> {
                mailSender.send(mimeMessage);
                log.info("send E-mail success...");
            });

        }
        catch (MessagingException e)
        {
            log.error("send email is MessagingException -> {}", e.getMessage(), e);
        }
    }

    private String getSendMailContent(String verificationCode, String mailTemplateContent)
    {
        if(MailTemplateContentEnum.REGISTER.getCode().equals(mailTemplateContent))
        {
            return MessageFormat.format(mailHtmlTemplate, mailRegisterFirstContent, verificationCode, mailRegisterEndContent);
        }
        else if(MailTemplateContentEnum.LOGIN.getCode().equals(mailTemplateContent))
        {
            return MessageFormat.format(mailHtmlTemplate, mailLoginFirstContent, verificationCode, mailLoginEndContent);
        }
        else if(MailTemplateContentEnum.RESET.getCode().equals(mailTemplateContent))
        {
            return MessageFormat.format(mailHtmlTemplate, mailResetpasswordFirstContent, verificationCode, mailResetpasswordEndContent);
        }

        throw new BusinessException(BusinessExceptionEnum.PARAMS_EXCEPTION_UNSUPPORTED_MAIL_OPER_TYPE);
    }

    private void handleBasicInfo(MimeMessageHelper mimeMessageHelper, String subject, String content, String[] toWho) throws MessagingException
    {
        String fromPerson = nickname + '<' + userName + '>';
        //设置发件人
        mimeMessageHelper.setFrom(fromPerson);
        //设置邮件的主题
        mimeMessageHelper.setSubject(subject);
        //设置邮件的内容
        mimeMessageHelper.setText(content,true);
        mimeMessageHelper.addInline("p01", new ClassPathResource("static/img/logo.png"));
        mimeMessageHelper.addInline("p02", new ClassPathResource("static/img/user.png"));
        mimeMessageHelper.setTo(toWho);
        log.info("send E-mail：from is [{}] subject is [{}]，toWho is [{}]",fromPerson, subject, toWho);
    }
}
