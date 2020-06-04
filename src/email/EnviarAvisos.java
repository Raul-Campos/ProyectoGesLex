/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package email;

import com.sun.mail.smtp.SMTPTransport;
import com.sun.mail.util.MailSSLSocketFactory;
import java.security.GeneralSecurityException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import map.Aviso;
import javax.mail.Session;
import org.hibernate.Query;
import org.hibernate.Transaction;

/**
 *
 * @author Raul
 */
public class EnviarAvisos implements Runnable {

    private String user;
    private String password;
    private org.hibernate.Session session;

    public EnviarAvisos(String user, String password, org.hibernate.Session session) {
        this.user = user;
        this.password = password;
        this.session = session;
    }

    @Override
    public void run() {
        //Trae todos los avisos en la bd
        Query consulta = session.createQuery("from Aviso");
        List<Aviso> avisos = consulta.list();

        //Toma la fecha actual
        Date fechaActual = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(fechaActual);
        cal.add(Calendar.DATE, 6);
        fechaActual = cal.getTime();

        for (Aviso aviso : avisos) {

            //Si quedan menos de 5 días para un evento envía un email
            if (fechaActual.after(aviso.getFecha())) {
                
                try {
                    Properties prop = new Properties();
                    prop.put("mail.smtp.host", "smtp.gmail.com");
                    prop.put("mail.smtp.port", "587");
                    prop.put("mail.smtp.auth", "true");
                    prop.put("mail.smtp.starttls.enable", "true"); //TLS
                    prop.put("mail.smtp.user", user);
                    prop.put("mail.smtp.clave", password);
                    prop.put("mail.debug", "true");

                    MailSSLSocketFactory sf = new MailSSLSocketFactory();
                    sf.setTrustAllHosts(true);
                    prop.put("mail.smtp.ssl.socketFactory", sf);

                    Session sessionMail = javax.mail.Session.getDefaultInstance(prop);
                    MimeMessage message = new MimeMessage(sessionMail);

                    try {

                        message.setFrom(new InternetAddress("raul14cl@gmail.com"));
                        
                        //Destinatario del email
                        message.setRecipient(
                                Message.RecipientType.TO,
                                new InternetAddress(aviso.getEmail())
                        );
                        
                        //Asunto
                        message.setSubject("Recordatorio de evento");
                        
                        //Contenido del email
                        message.setText(aviso.getDescripcion());

                        SMTPTransport t = (SMTPTransport) sessionMail.getTransport();
                        t.connect("smtp.gmail.com", user, password);
                        t.sendMessage(message, message.getAllRecipients());
                        t.close();
                        
                        //Elimina aviso enviado
                        Transaction tx = session.getTransaction();
                        tx.begin();
                        session.delete(aviso);
                        tx.commit();

                    } catch (MessagingException e) {
                        e.printStackTrace();
                    }
                } catch (GeneralSecurityException ex) {
                    Logger.getLogger(EnviarAvisos.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
        }
    }

}
