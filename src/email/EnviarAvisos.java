/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package email;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import map.Aviso;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import map.Smtp;
import org.hibernate.Query;
import org.hibernate.Transaction;

/**
 *
 * @author Raul
 */
public class EnviarAvisos implements Runnable {

    private org.hibernate.Session session;

    public EnviarAvisos(org.hibernate.Session session) {
        this.session = session;
    }

    @Override
    public void run() {
        //Trae todos los avisos en la bd
        Query consulta = session.createQuery("from Aviso");
        List<Aviso> avisos = consulta.list();

        //Toma el servidor seleccionado
        Query consulta2 = session.createQuery("from Smtp where opcion = :opcion").setParameter("opcion", "Seleccionado");
        Smtp smtp = (Smtp) consulta2.uniqueResult();

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
                    prop.put("mail.smtp.host", smtp.getHost());
                    prop.put("mail.smtp.port", smtp.getPuerto());
                    prop.put("mail.smtp.auth", "true");
                    prop.put("mail.smtp.starttls.enable", "true"); //TLS
                    prop.put("mail.smtp.user", smtp.getEmail());
                    prop.put("mail.smtp.clave", smtp.getContrasena());
                    prop.put("mail.smtp.ssl.trust", "*");
                    prop.put("mail.debug", "true");

                    /*MailSSLSocketFactory sf = new MailSSLSocketFactory();
                    sf.setTrustAllHosts(true);
                    prop.put("mail.smtp.ssl.socketFactory", sf);*/
                    Session sessionMail = Session.getInstance(prop,
                            new javax.mail.Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(smtp.getEmail(), smtp.getContrasena());
                        }
                    });
                    MimeMessage message = new MimeMessage(sessionMail);

                    message.setFrom(new InternetAddress(smtp.getEmail()));

                    //Destinatario del email
                    message.setRecipient(
                            Message.RecipientType.TO,
                            new InternetAddress(aviso.getEmail())
                    );

                    //Asunto
                    message.setSubject("Recordatorio de evento");

                    String texto = "Usted tiene un evento con fechas "+aviso.getFecha()
                            +" que contiene la siguiente descripción: "+aviso.getDescripcion();
                    
                    //Contenido del email
                    message.setText(texto);

                    Transport.send(message);

                    //Elimina aviso enviado
                    Transaction tx = session.getTransaction();
                    tx.begin();
                    session.delete(aviso);
                    tx.commit();
                } catch (AddressException ex) {
                    Logger.getLogger(EnviarAvisos.class.getName()).log(Level.SEVERE, null, ex);
                } catch (MessagingException ex) {
                    Logger.getLogger(EnviarAvisos.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }
    }

}
