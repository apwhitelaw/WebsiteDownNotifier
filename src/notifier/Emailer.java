package notifier;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class Emailer {

    String recipient;
    String sender;
    String host = "in-v3.mailjet.com";
    Properties properties;
    Session session;

    public Emailer(String recipient, String sender) {
        this.recipient = recipient;
        this.sender = sender;

        // setup smtp server properties
        properties = new Properties();
        properties.put("mail.smtp.host", host); // SMTP Host
        properties.put("mail.smtp.port", "587"); // TLS Port
        properties.put("mail.smtp.auth", "true"); // enable authentication
        properties.put("mail.smtp.starttls.enable", "true"); // enable STARTTLS

        // smtp server auth
        // keys contained in separate class (not available on github)
        Authenticator auth = new Authenticator() {
            //override the getPasswordAuthentication method
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(AuthKeys.user, AuthKeys.pass);
            }
        };

        session = Session.getDefaultInstance(properties, auth);
    }

    public void sendEmail(String website) {
        System.out.println("Sending email...");

        // try to create message and send it
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(sender)); // sender field
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient)); // to field
            message.setSubject("Website is Down!"); // subject field
            message.setText(String.format("WebsiteDownNotifier has detected that %s is down!", website)); // body field

            Transport.send(message);
            System.out.println("Email successfully sent");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }

    }

}
