package xbolt.cmm.framework.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.HtmlEmail;

import xbolt.cmm.framework.val.GlobalVal;


/**
 * SMTP Mail 발송 전용 처리
 * @Modification Information
 * @수정일		수정자		 수정내용
 * @---------	---------	-------------------------------
 * @2017. 11. 16. smartfactory		최초생성
 *
 * @since 2016. 05. 03.
 * @version 1.0
 * @see
 * 
 * Copyright (C) 2017 by SMARTFACTORY All right reserved.
 */
@SuppressWarnings("unused")
public class EmailUtil {	
	
	public static Map sendMail(HashMap cmmEmailMap, HashMap cmmCnts, Map menu) throws Exception {
		Map returnMap = new HashMap();
		try {
			String baseUrl = GlobalVal.EMAIL_TYPE;
			String languageID = StringUtil.checkNull(cmmEmailMap.get("languageID"));
			String mailBody ="";
			
			String outputKey = StringUtil.checkNull(LinkEncUtil.getEncOutputKey(cmmCnts));
			
			cmmCnts.put("outputKey",outputKey);			
			
			if(StringUtil.checkNull(cmmEmailMap.get("dicTypeCode")).equals("SRREQ")){
				mailBody = MakeEmailContents.makeHTML_SRREQ(cmmCnts, menu);					
			} else if(StringUtil.checkNull(cmmEmailMap.get("dicTypeCode")).equals("CSRAPREQ")){
				 mailBody = MakeEmailContents.makeHTML_CSRAPREQ(cmmCnts,menu);
		
			} else if(StringUtil.checkNull(cmmEmailMap.get("dicTypeCode")).equals("SRMRV")){
				mailBody = MakeEmailContents.makeHTML_SRREQ(cmmCnts, menu); 
		
			} else if(StringUtil.checkNull(cmmEmailMap.get("dicTypeCode")).equals("APRVCLS")){
				mailBody = MakeEmailContents.makeHTML_APRVCLS(cmmCnts, menu);  
		
			} else if(StringUtil.checkNull(cmmEmailMap.get("dicTypeCode")).equals("APRVRJT")){
				mailBody = MakeEmailContents.makeHTML_APRVRJT(cmmCnts, menu);  
		
			} else if(StringUtil.checkNull(cmmEmailMap.get("dicTypeCode")).equals("APRVREF")){
				 mailBody = MakeEmailContents.makeHTML_APREQ_CS(cmmCnts, languageID, menu);	
		
			} else if(StringUtil.checkNull(cmmEmailMap.get("dicTypeCode")).equals("BRDMAIL")){
				mailBody = MakeEmailContents.makeHTML_BRDMAIL(cmmCnts, menu); 
		
			}else if(StringUtil.checkNull(cmmEmailMap.get("dicTypeCode")).equals("REQITMRW")){
				mailBody = MakeEmailContents.makeHTML_REQITMRW(cmmCnts, menu); 
		
			} else if(StringUtil.checkNull(cmmEmailMap.get("dicTypeCode")).equals("SRTRP")){
				mailBody = MakeEmailContents.makeHTML_SRREQ(cmmCnts, menu); 
		
			} else if(StringUtil.checkNull(cmmEmailMap.get("dicTypeCode")).equals("SRCMP")){
				mailBody = MakeEmailContents.makeHTML_SRCMP(cmmCnts, menu);
		
			} else if(StringUtil.checkNull(cmmEmailMap.get("dicTypeCode")).equals("CRRCV")){
				mailBody = MakeEmailContents.makeHTML_CRRCV(cmmCnts, menu);
		
			} else if(StringUtil.checkNull(cmmEmailMap.get("dicTypeCode")).equals("CSRCLS")){			
				mailBody = MakeEmailContents.makeHTML_APREQ_CSR(cmmCnts, languageID, menu);	
		
			} else if(StringUtil.checkNull(cmmEmailMap.get("dicTypeCode")).equals("CSRNTC")){
				mailBody = MakeEmailContents.makeHTML_CSRNTC(cmmCnts, menu);
		
			} else if(StringUtil.checkNull(cmmEmailMap.get("dicTypeCode")).equals("APREQ_CS")){			
				mailBody = MakeEmailContents.makeHTML_APREQ_CS(cmmCnts, languageID, menu);	
		
			} else if(StringUtil.checkNull(cmmEmailMap.get("dicTypeCode")).equals("TERMREG")){			
				mailBody = MakeEmailContents.makeHTML_TERMREG(cmmCnts, menu);
		
			} else if(StringUtil.checkNull(cmmEmailMap.get("dicTypeCode")).equals("TERMREL")){			
				mailBody = MakeEmailContents.makeHTML_TERMREL(cmmCnts, menu);				
		
			}else if(StringUtil.checkNull(cmmEmailMap.get("dicTypeCode")).equals("PROPS") && baseUrl.equals("hanwha")){			
				mailBody = MakeEmailContents.makeHTML_SRPROPOSAL(cmmCnts, languageID, menu);
			
			} else if(StringUtil.checkNull(cmmEmailMap.get("dicTypeCode")).equals("SCHDL") || StringUtil.checkNull(cmmEmailMap.get("dicTypeCode")).equals("SCHDLALM")){			
				mailBody = MakeEmailContents.makeHTML_SCHDL(cmmCnts, menu);
			
			} else if(StringUtil.checkNull(cmmEmailMap.get("dicTypeCode")).equals("SRAPREQ")){
				mailBody = MakeEmailContents.makeHTML_SRAPREQ(cmmCnts,menu);
	
			} else if(StringUtil.checkNull(cmmEmailMap.get("dicTypeCode")).equals("SRAPREL")){
				mailBody = MakeEmailContents.makeHTML_SRAPREL(cmmCnts,menu);
	
			} else if(StringUtil.checkNull(cmmEmailMap.get("dicTypeCode")).equals("SRAPRJT")){
				mailBody = MakeEmailContents.makeHTML_SRAPRJT(cmmCnts,menu);
			
			} else if(StringUtil.checkNull(cmmEmailMap.get("dicTypeCode")).equals("CTR")){			
				mailBody = MakeEmailContents.makeHTML_CTR(cmmCnts, menu);
			
			} else if(StringUtil.checkNull(cmmEmailMap.get("dicTypeCode")).equals("SRCNGRDD")){			
				mailBody = MakeEmailContents.makeHTML_SRCNGRDD(cmmCnts, menu);
			} else if(StringUtil.checkNull(cmmEmailMap.get("dicTypeCode")).equals("PIMEM001")){			
				mailBody = MakeEmailContents.makeHTML_PIMEM001(cmmCnts, menu);
			} else if(StringUtil.checkNull(cmmEmailMap.get("dicTypeCode")).equals("REQSYSTEST")){
				mailBody = MakeEmailContents.makeHTML_REQSYSTEST(cmmCnts, menu);
			} else if(StringUtil.checkNull(cmmEmailMap.get("dicTypeCode")).equals("ITSCMP")){
				mailBody = MakeEmailContents.makeHTML_ITSCMP(cmmCnts, menu);
			} else if(StringUtil.checkNull(cmmEmailMap.get("dicTypeCode")).equals("REQSREV")){
				mailBody = MakeEmailContents.makeHTML_REQSREV(cmmCnts, menu);
			} else if(StringUtil.checkNull(cmmEmailMap.get("dicTypeCode")).equals("RQUSRAUTH")){			
				mailBody = MakeEmailContents.makeHTML_RQUSRAUTH(cmmCnts, menu);
			} else if(StringUtil.checkNull(cmmEmailMap.get("dicTypeCode")).equals("SCRAPREQ")){
				mailBody = MakeEmailContents.makeHTML_SCRAPREQ(cmmCnts, menu);
			} else if(StringUtil.checkNull(cmmEmailMap.get("dicTypeCode")).equals("SCRAPREL")){
				mailBody = MakeEmailContents.makeHTML_SCRAPREL(cmmCnts, menu);
			}  else if(StringUtil.checkNull(cmmEmailMap.get("dicTypeCode")).equals("WFARLM")){
				mailBody = MakeEmailContents.makeHTML_WFARLM(cmmCnts, menu);
			} 
			
			returnMap = sendSMTPEmail(cmmEmailMap,mailBody);		
		}
		catch(Exception e) {
			System.out.println(e.toString());
		}
		return returnMap;
	}
	
	private static Map sendSMTPEmail(HashMap cmmEmailMap, String mailBody) throws Exception {
		Map returnMap = new HashMap();
		String emailSender = StringUtil.checkNull(cmmEmailMap.get("Sender"), GlobalVal.EMAIL_SENDER);
		String emailSenderName = StringUtil.checkNull(cmmEmailMap.get("SenderName"), GlobalVal.EMAIL_SENDER_NAME);
	
		try{
			 String mailTitle = StringUtil.checkNull(cmmEmailMap.get("mailSubject"));
			 List refList = (List)cmmEmailMap.get("refList");
			 List receiverList = (List)cmmEmailMap.get("receiverInfoList");
			 
			 String hostIP = GlobalVal.EMAIL_HOST_IP;
			 String SMTP_AUTHENTIFICATION = GlobalVal.SMTP_AUTHENTIFICATION;
			 String SMTP_SSL = GlobalVal.SMTP_SSL;
		
			 HtmlEmail email = new HtmlEmail();	 
			 email.setCharset("UTF-8"); 
			 email.setHostName(hostIP);	 
			 if(SMTP_AUTHENTIFICATION.equals("Y")) {
				 email.setAuthenticator(new DefaultAuthenticator(GlobalVal.EMAIL_SENDER, GlobalVal.SMTP_ACCOUNT_PWD));				 
			 }
			 if(SMTP_SSL.equals("Y")) {
				 email.setSmtpPort(465);
				 email.setSSLOnConnect(true);
				 email.setSSLCheckServerIdentity(true);
			 }else {
				 email.setSmtpPort(25);
				 email.setSSLOnConnect(false);
			 }
			 
			 email.setFrom(emailSender, emailSenderName); 
			 //email.addTo("wjcho@smartfactory.co.kr");
			 
			 //System.out.println("next seq ==> " + receiverList.toString());
			 if(receiverList != null && !receiverList.isEmpty()) {
				 for(int i=0; i<receiverList.size(); i++) {
					 Map receiverListMap = (Map)receiverList.get(i);
					 if(receiverListMap.get("receiptType") == "CC"){
						 email.addCc(StringUtil.checkNull(receiverListMap.get("receiptEmail")));
					 }else{
						 email.addTo(StringUtil.checkNull(receiverListMap.get("receiptEmail"))); 
					 }
				 }
			 }
			 else {		
				 returnMap.put("type", "FAILE");
				 returnMap.put("msg", "No Receive Member");
				 
				 return returnMap;
			 }
			 			 
			 email.setSubject(mailTitle);
			 email.setHtmlMsg(mailBody);	
			 email.send();			
			
			 returnMap.put("type", "SUCESS");
			 //returnMap.put("msg", emailResult);
		}catch(Exception ex){
			returnMap.put("type", "FAILE");
			returnMap.put("msg", ex.toString());
			System.out.println("SEND MAP ::: " + cmmEmailMap.toString());
			System.out.println("SEND EMAIL FAILE ::: "+ex.getMessage());						
		}
		return returnMap;
	}
}
