package vn.iotstar.giuaki.util;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class EmailUtil_24110248 {
    public static void sendOTP(String toEmail, String otp) {
        System.out.println("====== MÃ OTP CỦA EMAIL " + toEmail + " LÀ: " + otp + " ======");

        final String from = "coolhaijn@gmail.com";
        final String password = "aogc wntv voqp pzlz";
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject("Mã OTP Đăng Ký Tài Khoản");
            message.setText("Mã OTP của bạn là: " + otp);
            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}