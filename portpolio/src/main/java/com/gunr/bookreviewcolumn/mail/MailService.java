package com.gunr.bookreviewcolumn.mail;

import java.util.Properties;
import java.util.Random;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.stereotype.Component;

@Component
public class MailService {
	
	public String sendMail(String to) {
		String host = "smtp.gmail.com";
		String user = "gunr0410@gmail.com";
		String password = "aebhhkhnzqnfzdpw";  // 앱 비밀번호

		Properties props = new Properties();
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "587");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.ssl.trust", host);
		props.put("mail.smtp.ssl.protocols", "TLSv1.2");

		Session session = Session.getInstance(props, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(user, password);
			}
		});

		// 인증번호 생성
		String number = "";
		Random rand = new Random();
		for (int i = 0; i < 6; i++) {
			number += Integer.toString(rand.nextInt(10));
		}

		try {
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(user));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			message.setSubject("[가온누리] 인증번호가 도착했습니다.");
			message.setContent(
					"<div  style='border-radius: 8px; width: 500px; padding: 48px; '>" +
							"<div style='background-color: #889F69; border-radius: 20px 20px 0px 0px; padding: 25px;'>"+
								"<h2 style='font-weight: bold; color: white; text-align: center; margin: 0px 35px 0px 25px;'>GaonNuri</h2>"+
							"</div>" +
							"<div  style='background-color: #ffffff; border: 1px solid #D9D9D9; border-radius: 0px 0px 20px 20px; padding: 20px 50px 20px 50px;'>"+
									"<h3 style='text-align: center;'> 요청하신 인증번호입니다. </h2>" +
									"<hr>"+
								"<div style='width: 80%; height: auto; border: none; vertical-align: middle; align-items: center; background-color: D9D9D9; padding: 10px; margin: 0 auto;'>"+
									"<p style='font-weight: bold; color:#889F69; text-align: center; margin: 0 auto; padding: 10px;'>" + number + "</p>" +
								"</div>" +
							"</div>" +
						"</div>", "text/html; charset=UTF-8");

			long start = System.currentTimeMillis();
			Transport.send(message);
			System.out.println("전송 시간: " + (System.currentTimeMillis() - start) + "ms");

		 } catch (Exception e) {
		        System.err.println("메일 전송 실패: " + e.getMessage());
		        e.printStackTrace();
		    }

		return number;
	}

}
