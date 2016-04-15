package com.klowerbase.test;

import android.os.Bundle;
import android.widget.EditText;

import com.klower.titlebar.BaseException;

public class MailSendActivity extends ActionBarActivity {

	EditText serverHost, serverPort, senderMail, senderPassword, accepterMail,
			subject, content;

	@Override
	protected void onCreateEqually(Bundle savedInstanceState)
			throws BaseException {
		super.onCreateEqually(savedInstanceState);
		setCustomContentView(R.layout.mail);

		serverHost = (EditText) findViewById(R.id.mail_serverip);
		serverPort = (EditText) findViewById(R.id.mail_serveport);
		senderMail = (EditText) findViewById(R.id.mail_sendermail);
		senderPassword = (EditText) findViewById(R.id.mail_senderpassword);
		accepterMail = (EditText) findViewById(R.id.mail_acceptermail);
		subject = (EditText) findViewById(R.id.mail_subject);
		content = (EditText) findViewById(R.id.mail_content);
	}

//	@Override
//	public void onRightTitleClick() {
//		new Thread(){
//			public void run() {
//				// 这个类主要是设置邮件
//				MailSenderInfo mailInfo = new MailSenderInfo();
//				mailInfo.setMailServerHost(serverHost.getText().toString());
//				mailInfo.setMailServerPort(serverPort.getText().toString());
//				mailInfo.setValidate(true);
//				mailInfo.setUserName(senderMail.getText().toString());
//				mailInfo.setPassword(senderPassword.getText().toString());// 您的邮箱密码
//				mailInfo.setFromAddress(senderMail.getText().toString());
//				mailInfo.setToAddress(accepterMail.getText().toString());
//				mailInfo.setSubject(subject.getText().toString());
//				mailInfo.setContent(content.getText().toString());
//				// 这个类主要来发送邮件
//				SimpleMailSender sms = new SimpleMailSender();
//				sms.sendTextMail(mailInfo);// 发送文体格式
//				// sms.sendHtmlMail(mailInfo);//发送html格式
//			};
//		}.start();
//
//	}
}
