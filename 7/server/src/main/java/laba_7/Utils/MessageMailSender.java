package laba_7.Utils;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class MessageMailSender {
    private static String orgmail;
    private static String orgpsw;
    private static String MessageForSend;

    public MessageMailSender(String mail, String pswMail){
        orgmail=mail;
        orgpsw=pswMail;
    }
    public static void SendMessage(String login, String psw, String mail) throws MessagingException {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.yandex.ru");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        Session session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(orgmail,orgpsw);
                    }
                });

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(orgmail));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mail));
        message.setSubject("Ваш логин и пароль от базы данных");
        setMessageForSend(login,psw);
        message.setText(MessageForSend);

        Transport.send(message);
    }

    public static void setMessageForSend(String login, String psw) {
        MessageForSend = "login: "+ login+"\n"
                        +"Password: "+psw+ "\n"
                        +"Никому не сообщайте данные.\n";
    }
}
