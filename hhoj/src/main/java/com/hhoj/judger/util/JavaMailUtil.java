package com.hhoj.judger.util;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message.RecipientType;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.sun.mail.util.MailSSLSocketFactory;

/**
 * 邮件发送工具类
 * @author zhu
 *
 */
public class JavaMailUtil {
	/** 
     * 发送邮件工具类:通过qq邮件发送,因为具有ssl加密,采用的是smtp协议 
     * @param mailServer 邮件服务器的主机名:如 "smtp.qq.com" 
     * @param loginAccount 登录邮箱的账号:如 "1974544863@qq.com" 
     * @param loginAuthCode 登录qq邮箱时候需要的授权码:可以进入qq邮箱,账号设置那里"生成授权码" 
     * @param sender 发件人 
     * @param recipients 收件人:支持群发 
     * @param emailSubject 邮件的主题 
     * @param emailContent 邮件的内容 
     * @param emailContentType 邮件内容的类型,支持纯文本:"text/plain;charset=utf-8";,带有Html格式的内容:"text/html;charset=utf-8"  
     * @return 
     */  
    public static boolean sendEmail(String mailServer,final String loginAccount,final String loginAuthCode,String sender,String[] recipients,  
            String emailSubject,String emailContent,String emailContentType){  
        boolean res=false;  
          
       try {  
            //跟smtp服务器建立一个连接  
            Properties p = new Properties();  
            //设置邮件服务器主机名  
            p.setProperty("mail.host",mailServer);  
            //发送服务器需要身份验证,要采用指定用户名密码的方式去认证  
            p.setProperty("mail.smtp.auth", "true");  
            //发送邮件协议名称  
            p.setProperty("mail.transport.protocol", "smtp");  
  
            //开启SSL加密，否则会失败  
            MailSSLSocketFactory sf = new MailSSLSocketFactory();  
            sf.setTrustAllHosts(true);  
            p.put("mail.smtp.ssl.enable", "true");  
            p.put("mail.smtp.ssl.socketFactory", sf);  
  
            // 创建session  
            Session session = Session.getDefaultInstance(p, new Authenticator() {  
                protected PasswordAuthentication getPasswordAuthentication() {  
                    //用户名可以用QQ账号也可以用邮箱的别名:第一个参数为邮箱账号,第二个为授权码  
                    PasswordAuthentication pa = new PasswordAuthentication(loginAccount,loginAuthCode);  
                    return pa;  
                }  
            });  
  
            //设置打开调试状态  
            session.setDebug(true);  
  
            //可以发送几封邮件:可以在这里 for循环多次  
            //声明一个Message对象(代表一封邮件),从session中创建  
            MimeMessage msg = new MimeMessage(session);  
            //邮件信息封装  
            //1发件人  
            msg.setFrom(new InternetAddress(sender));  
              
            //2收件人:可以多个  
            //一个的收件人  
            //msg.setRecipient(RecipientType.TO, new InternetAddress("linsenzhong@126.com"));  
              
            InternetAddress[] receptientsEmail=new InternetAddress[recipients.length];  
            for(int i=0;i<recipients.length;i++){  
                receptientsEmail[i]=new InternetAddress(recipients[i]);  
            }  
              
            //多个收件人  
            msg.setRecipients(RecipientType.TO,receptientsEmail);  
              
            //3邮件内容:主题、内容  
            msg.setSubject(emailSubject);  
            //msg.setContent("Hello, 我是debug!!!", );//纯文本  
            msg.setContent(emailContent,emailContentType);//发html格式的文本  
            //发送动作  
            Transport.send(msg);  
            res=true;  
              
        } catch (Exception e) {  
            res=false;  
        }  
        return res;  
    }  
      
    
    public static boolean sendActiveInfo(String mailServer,final String loginAccount,final String loginAuthCode,String sender,String[] recipients,  
            String emailSubject,String emailContent,String emailContentType) {
    	return sendEmail(mailServer, loginAccount, loginAuthCode, sender, recipients, emailSubject, emailContent, emailContentType);
    }
    public static void main(String[] args) throws Exception {  
       /*   
        int res=sendEmail("smtp.qq.com", "lp995968535@qq.com", "pyonipdgzluobfgc", "lp995968535@qq.com", new String[]{  
                "956281507@qq.com" 
        }, "节日祝福", "祝你国庆节快乐,欢迎来我的blog: <a href='http://blog.csdn.net/u013871100'>我的blog</a>,祝您生活愉快!","text/html;charset=utf-8");  
          
        System.out.println("\n发送结果:"+res);  */
    }  
    
    
}
