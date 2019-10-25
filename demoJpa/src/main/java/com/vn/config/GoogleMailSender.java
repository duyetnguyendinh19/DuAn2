package com.vn.config;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.File;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Properties;

public class GoogleMailSender {
    protected String host = "smtp.gmail.com";
    protected Integer sslPort = 465;
    protected Integer smtpPort = 465;
    protected boolean smtpAuth = true;
    protected String socketClass = "javax.net.ssl.SSLSocketFactory";
    protected String authUserName = "tanbv.dev@gmail.com";
    protected String authPassword = "abcABC1234";

    public void sendSimpleMailWarning(String from, String to, String subject, String message)
            throws Exception {

        Properties props = new Properties();
        props.put("mail.smtp.host", host); //SMTP Host
        props.put("mail.smtp.socketFactory.port", sslPort); //SSL Port
        props.put("mail.smtp.socketFactory.class",socketClass); //SSL Factory Class
        props.put("mail.smtp.auth", smtpAuth); //Enabling SMTP Authentication
        props.put("mail.smtp.port", smtpPort); //SMTP Port

        Session session = Session.getInstance(props, new DefaultAuthenticator(authUserName, authPassword));

        MimeMessage msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(from));
        msg.setRecipients(Message.RecipientType.TO, to);
        msg.setSubject(subject);
        msg.setContent(message, "text/html; charset=utf-8");
//		msg.setText(message);

        Transport.send(msg);
    }

    public void sendHtmlEmailWithAttachment(String from, String to, String subject, String htmlEmail, File attachment,
                                            List<InternetAddress> lst)
            throws EmailException, MalformedURLException, AddressException, MessagingException {

        Properties props = new Properties();
        props.put("mail.smtp.host", host); //SMTP Host
        props.put("mail.smtp.socketFactory.port", sslPort); //SSL Port
        props.put("mail.smtp.socketFactory.class",socketClass); //SSL Factory Class
        props.put("mail.smtp.auth", smtpAuth); //Enabling SMTP Authentication
        props.put("mail.smtp.port", smtpPort); //SMTP Port

        Session session = Session.getInstance(props, new DefaultAuthenticator(authUserName, authPassword));

        MimeMessage msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(from));
        msg.setRecipients(Message.RecipientType.TO, to);
        msg.setSubject(subject);
        if (lst != null) {
            msg.setRecipients(Message.RecipientType.CC, (InternetAddress[]) lst.toArray());
        }
        msg.setContent(htmlEmail, "text/html; charset=UTF-8");

        Multipart multipart = new MimeMultipart();

        BodyPart messageBodyPart = new MimeBodyPart();


        messageBodyPart.setContent(htmlEmail, "text/html; charset=UTF-8");
        multipart.addBodyPart(messageBodyPart);
        if (attachment != null) {
            BodyPart attachmentBodyPart = new MimeBodyPart();
            DataSource source = new FileDataSource(attachment);
            attachmentBodyPart.setDataHandler(new DataHandler(source));
            attachmentBodyPart.setFileName(attachment.getName());
            multipart.addBodyPart(attachmentBodyPart);
        }

        msg.setContent(multipart);
        Transport.send(msg);
    }

    public void sendSimpleMailWarningTLS(String from, String to, String subject, String message)
            throws Exception {

        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(prop, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(authUserName, authPassword);
            }
        });

        MimeMessage msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(from));
        msg.setRecipients(Message.RecipientType.TO, to);
        msg.setSubject(subject, "UTF-8");
        msg.setContent(message, "text/html; charset=UTF-8");

        Transport.send(msg);
    }
}
