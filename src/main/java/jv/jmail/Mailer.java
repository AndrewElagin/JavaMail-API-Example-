package jv.jmail;

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.*;
import javax.mail.internet.*;

/**
 * Example of a simple JavaMail API program. Uncomment strings in method to send
 * attached file
 *
 * @author Andrew Elagin
 */
public class Mailer {

    /**
     *
     * @param from sender email address (from which letter will be sent)
     * @param password sender email password
     * @param to recipient email address
     * @param sub subject of the letter
     * @param msg message body
     */
    public static void send(String from, String password, String to, String sub, String msg) {
        //Get properties object
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");
        //get Session
        Session session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        });
        //compose message
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject(sub);
            message.setText(msg);
            message.setContent(msg, "text/html; charset=utf-8");

//            uncomment below to attach some file
//            MimeBodyPart part=new MimeBodyPart();
//            part.attachFile("pom.xml");
//            MimeMultipart mp=new MimeMultipart();
//            mp.addBodyPart(part);
//            message.setContent(mp);
            //send message
            Transport.send(message);
            System.out.println("message sent successfully");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        } catch (Exception ex) {
            Logger.getLogger(Mailer.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void main(String[] args) {
        Mailer.send("enter email from", "enter password", "enter email to", "subject", "letter body");
    }

}
