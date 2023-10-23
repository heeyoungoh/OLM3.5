package xbolt.api.web;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.text.StringEscapeUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import xbolt.cmm.controller.XboltController;
import xbolt.cmm.framework.handler.MessageHandler;
import xbolt.cmm.framework.util.StringUtil;
import xbolt.cmm.framework.val.GlobalVal;
import xbolt.cmm.service.CommonService;

import net.sf.json.JSONArray;
import xbolt.cmm.controller.XboltController;
import xbolt.cmm.framework.util.ExceptionUtil;
import xbolt.cmm.framework.util.StringUtil;
import xbolt.cmm.service.CommonService;

@Controller
public class ExtApiActionController extends XboltController{
	private final Log _log = LogFactory.getLog(this.getClass());
	
	
	@Autowired
    @Qualifier("commonService")
    private CommonService commonService;
	
	@Resource(name = "commonOraService")
	private CommonService commonOraService;
		
	

	@RequestMapping(value="/searchNhnOpenApi.do")
	public String searchNhnOpenApi(HttpServletRequest request, ModelMap model, HashMap cmmMap) throws Exception {
		System.out.println("searchNhnOpenAPI.do.... ");
		
		String url = "/cmm/restApiTest/restOpenApiTest";
		Map target = new HashMap();	
		String clientId = "SL6ZKFuSzknFRtc4tjte"; //애플리케이션 클라이언트 아이디값"
        String clientSecret = "h_11_8946h"; //애플리케이션 클라이언트 시크릿값"
        String text = null;
        String str = StringUtil.checkNull(request.getParameter("searchValue"), "");
       
        System.out.println("str :"+str);
        try {
        	
        	if(!str.equals("")){
		        try {
		            text = URLEncoder.encode(str, "UTF-8");
		        } catch (UnsupportedEncodingException e) {
		            throw new RuntimeException("검색어 인코딩 실패",e);
		        }
		
		        String apiURL = "https://openapi.naver.com/v1/search/kin.json?query=" + text+"&display=10&start=1&sort=sim";    // json 결과
		
		        Map<String, String> requestHeaders = new HashMap<>();
		        requestHeaders.put("X-Naver-Client-Id", clientId);
		        requestHeaders.put("X-Naver-Client-Secret", clientSecret);
		        
		        String responseBody = get(apiURL,requestHeaders) ;
		        
		        System.out.println("responseBody   : "+  responseBody );
		        
	            JSONParser parser = new JSONParser();
	            Object obj = parser.parse(responseBody);
	
	
	            JSONObject jsonObject = (JSONObject) obj;
	            JSONArray getArray = (JSONArray) jsonObject.get("items");
	            for (int i = 0; i < getArray.size(); i++) {
	                JSONObject object = (JSONObject) getArray.get(i);
	
	                String title = StringUtil.checkNull( object.get("title")).replaceAll("<b>", "").replaceAll("</b>", "");
	                String description = StringUtil.checkNull(  object.get("description")).replaceAll("<b>", "").replaceAll("</b>", "");
	                String link =  StringUtil.checkNull(  object.get("link"));
	                String orgiginallink =  StringUtil.checkNull(  object.get("orgiginallink"));
	
	                System.out.println("title :"+title);
	                System.out.println("*********************************");
	                System.out.println("description :"+description);
	                System.out.println("*********************************");
	                System.out.println("link :"+link);
	                System.out.println("*********************************");
	                System.out.println("orgiginallink :"+orgiginallink);
	                System.out.println("*********************************");
	                 
	             }
	            
	            model.put("searchResultList",  getArray);
        	}

		
        }catch (Exception e) {
			//if(_log.isInfoEnabled()){_log.info("MainActionController::menuURL::Error::"+e);}
			throw new ExceptionUtil(e.toString());
		}		
        
		model.addAttribute(AJAX_RESULTMAP,target);
		return nextUrl(url);
	}
	
	private static String get(String apiUrl, Map<String, String> requestHeaders){
        HttpURLConnection con = connect(apiUrl);
        try {
            con.setRequestMethod("GET");
            for(Map.Entry<String, String> header :requestHeaders.entrySet()) {
                con.setRequestProperty(header.getKey(), header.getValue());
            }
 
            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) { // 정상 호출
               
            	System.out.println();
            	
            	
            	return readBody(con.getInputStream());
            } else { // 에러 발생
                return readBody(con.getErrorStream());
            }
        } catch (IOException e) {
            throw new RuntimeException("API 요청과 응답 실패", e);
        } finally {
            con.disconnect();
        }
    }
  


	// 네이버 회원 정보 조회 
	@RequestMapping(value = "/restApiGetNhnMember.do")
	public String restApiGetNhnMember(HttpServletRequest request, HashMap cmmMap, ModelMap model) throws Exception {
		HashMap target = new HashMap();
		String token = "YOUR_ACCESS_TOKEN";// 접근 토큰 값";
        String header = "Bearer " + token; // Bearer 다음에 공백 추가
        try {
            String apiURL = "https://openapi.naver.com/v1/nid/me";
            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Authorization", header);
            int responseCode = con.getResponseCode();
            
            BufferedReader br;
            if(responseCode==200) { // 정상 호출
                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            } else {  // 에러 발생
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
            br.close();
            System.out.println(response.toString());
        } catch (Exception e) {
            System.out.println(e);
        }
        model.addAttribute(AJAX_RESULTMAP, target);
        return nextUrl(AJAXPAGE);
    }
	
	// 미세먼지 정보 조회 
	@RequestMapping(value = "/restApiFineDust.do")
	public String restApiFineDust(HttpServletRequest request, HashMap cmmMap, ModelMap model) throws Exception {
		HashMap target = new HashMap();
        try {
        	// 인증키
            String serviceKey = "인증키값";
            
            String urlStr = "http://openapi.airkorea.or.kr/openapi/services/rest/UlfptcaAlarmInqireSvc/getUlfptcaAlarmInfo";
            urlStr += "?"+ URLEncoder.encode("ServiceKey","UTF-8") +"=" + serviceKey;
            urlStr += "&"+ URLEncoder.encode("numOfRows","UTF-8") +"=200";
            urlStr += "&"+ URLEncoder.encode("pageNo","UTF-8") +"=1";
            urlStr += "&"+ URLEncoder.encode("year","UTF-8") +"=2019";
            urlStr += "&"+ URLEncoder.encode("_returnType","UTF-8") +"=json";
            
            URL url = new URL(urlStr);
            
            String line = "";
            String result = "";
            
            BufferedReader br;
            br = new BufferedReader(new InputStreamReader(url.openStream()));
            while ((line = br.readLine()) != null) {
                result = result.concat(line);
                //System.out.println(line);                
            }            
            
            // JSON parser 만들어 문자열 데이터를 객체화한다.
            JSONParser parser = new JSONParser();
            JSONObject obj = (JSONObject)parser.parse(result);
            
            // list 아래가 배열형태로
            // {"list" : [ {"returnType":"json","clearDate":"--",.......} ] 
            JSONArray parse_listArr = (JSONArray)obj.get("list");
            
            String miseType = "";
            
            // 객체형태로
            // {"returnType":"json","clearDate":"--",.......},... 
            for (int i=0;i< parse_listArr.size();i++) {
                JSONObject weather = (JSONObject) parse_listArr.get(i);
                String dataDate = (String) weather.get("dataDate");            // 발령날짜
                String districtName = (String) weather.get("districtName");    // 발령지역
                String moveName = (String) weather.get("moveName");            // 발령권역
                String issueDate = (String) weather.get("issueDate");        // 발령일자
                String issueTime = (String) weather.get("issueTime");        // 발령시간
                String issueVal  = (String) weather.get("issueVal");        // 발령농도
                String itemCode  = (String) weather.get("itemCode");        // 미세먼지 구분 PM10, PM25
                String issueGbn  = (String) weather.get("issueGbn");        // 경보단계 : 주의보/경보
                String clearDate = (String) weather.get("clearDate");        // 해제일자
                String clearTime = (String) weather.get("clearTime");        // 해제시간
                String clearVal = (String) weather.get("clearVal");            // 해제시 미세먼지농도
                
                if (itemCode.equals("PM10")) {            
                    miseType = "";
                } else if (itemCode.equals("PM25")) {    
                    miseType = "초미세먼지";
                }
                StringBuffer sb = new StringBuffer();
                sb.append("발령날짜 : " + dataDate + ", 지역 : " + districtName + " ("+ moveName +"), " + "발령시간 : "+ issueDate + " " + issueTime + ", 농도 : " + issueVal + " ("+ issueGbn +") " + miseType);
                System.out.println(sb.toString());     
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        model.addAttribute(AJAX_RESULTMAP, target);
        return nextUrl(AJAXPAGE);
    }
	
	/**
	 * 카카오
	 * */
	/*
	@RequestMapping(value="/custom/KaKaoWorkTest.do")
	public void KaKaoWorkTest(HttpServletRequest request) {
		

		String[] Emails = request.getParameterValues("email");
		List<String> emailList = Arrays.asList(Emails);

		List<Integer> userIDs = getUserInfoByEmail(emailList);
		String conversation_id = exeConversationOpen(userIDs);
		
		Map cmmMap = new HashMap();
		cmmMap.put("title", "TEST TITLE !");
		cmmMap.put("subject", "TEST subject !");
		cmmMap.put("content", "TEST content !");
		cmmMap.put("conversation_id", conversation_id);
		
		try {
			messagesSend(cmmMap);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	//@RequestMapping(value="/custom/getUserInfoByEmail.do")
	public List<Integer> getUserInfoByEmail(List<String> emailList) {
		String targetUrl = "https://api.kakaowork.com/v1/users.find_by_email?";
		String parameters = "email=";
		
		List<Integer> returnIDs = new ArrayList();

		JSONParser parser = new JSONParser();
		Object obj = new Object();
		JSONObject jsonObj = new JSONObject();
		Boolean success;
		Object obj1 = new Object();
		JSONObject jsonObj1 = new JSONObject();
		
		String result = "";
		try {
			
			if(emailList.size() > 0) {
				for(int i=0; i<emailList.size(); i++) {
					parameters = "email="+emailList.get(i);
					result = bacthActionController.sendReturnGet(targetUrl + parameters, "", tokenKey);
					obj = parser.parse( result );
					jsonObj = (JSONObject) obj;
					success = (Boolean) jsonObj.get("success");
					if(success == true) {
						obj1 = jsonObj.get("user");
						jsonObj1 = (JSONObject) obj1;
						System.out.println(jsonObj1.get("id"));
						System.out.println(jsonObj1.get("name"));
						returnIDs.add(Integer.valueOf((String) jsonObj1.get("id")));
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(result);
		//{"success":true,"user":{"avatar_url":null,"department":null,"id":"2511015","mobiles":[],"name":"?씠?썑李?","nickname":null,"position":null,"responsibility":null,"space_id":"141287","tels":[],"vacation_end_time":null,"vacation_start_time":null,"work_end_time":null,"work_start_time":null}}
		return returnIDs;
	}

	public String exeConversationOpen(List<Integer> userIDs) {
		String result = "";
		String conversation_id = "";
		String targetUrl = "https://api.kakaowork.com/v1/conversations.open";
		String parameters = "{ \"user_ids\" : " + userIDs.toString() + "}";
		
		JSONParser parser = new JSONParser();
		Object obj = new Object();
		JSONObject jsonObj = new JSONObject();
		Boolean success;
		Object obj1 = new Object();
		JSONObject jsonObj1 = new JSONObject();
		
		try {
			result = bacthActionController.sendReturnPost(targetUrl, parameters, "", tokenKey);
			obj = parser.parse( result );
			jsonObj = (JSONObject) obj;
			success = (Boolean) jsonObj.get("success");
			if(success == true) {
				obj1 = jsonObj.get("conversation");
				jsonObj1 = (JSONObject) obj1;
				System.out.println(jsonObj1.get("id"));
				System.out.println(jsonObj1.get("name"));
				conversation_id = (String) jsonObj1.get("id");
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//{"conversation":{"avatar_url":null,"id":"1084977","name":"이후창, 조우진","type":"group","users_count":3},"success":true}
		return conversation_id;
	}
	
	public void  messagesSend(Map cmmMap) throws Exception {
		String targetUrl = "https://api.kakaowork.com/v1/messages.send ";
		
		String returnText = "{" + 
				"  \"title\": \"OLM 검색\"," + 
				"  \"accept\": \"검색\"," + 
				"  \"decline\": \"취소\"," + 
				"  \"value\": \"test=true\"," + 
				"  \"blocks\": [" + 
				"    {" + 
				"      \"type\": \"label\"," + 
				"      \"text\": \"검색어\"," + 
				"      \"markdown\": false" + 
				"    }," + 
				"    {" + 
				"      \"type\": \"input\"," + 
				"      \"name\": \"text_reason\"," + 
				"      \"required\": true," + 
				"      \"placeholder\": \"검색어를 입력해주세요. (최대 10자)\"" + 
				"    }" + 
				"  ]" + 
				"}";
		String parameters = "{ \"conversation_id\": \""+cmmMap.get("conversation_id")+"\", \"text\": \"Hello test.\", "
				+ "\"blocks\": [" +
				"{" + 
					" \"type\": \"header\"," + 
					" \"text\": \" "+ StringUtil.checkNull(cmmMap.get("title"), "Test title") +" \"," + 
					" \"style\": \"blue\"" + 
				" }," + 
				" {" + 
					" \"type\": \"text\"," + 
					" \"text\": \" " +
					" Subject : " + StringUtil.checkNull(cmmMap.get("subject"), "TEST subject") + 
					" Content : "+ StringUtil.checkNull(cmmMap.get("content"), "TEST Content") + " \"," + 
					" \"markdown\": true" + 
				"  }"
				+ "]"
				+ "}";
		
		System.out.println(parameters);
		bacthActionController.sendPost(targetUrl, parameters, "", tokenKey);
	}
	
	@RequestMapping(value="/custom/kakaoRequest.do")
	public void  kakaoRequest(Map cmmMap, HttpServletRequest request) throws Exception {
		
		String returnText = "{" + 
				"  \"title\": \"OLM 검색\"," + 
				"  \"accept\": \"검색\"," + 
				"  \"decline\": \"취소\"," + 
				"  \"value\": \"test=true\"," + 
				"  \"blocks\": [" + 
				"    {" + 
				"      \"type\": \"label\"," + 
				"      \"text\": \"검색어\"," + 
				"      \"markdown\": false" + 
				"    }," + 
				"    {" + 
				"      \"type\": \"input\"," + 
				"      \"name\": \"text_reason\"," + 
				"      \"required\": true," + 
				"      \"placeholder\": \"검색어를 입력해주세요. (최대 10자)\"" + 
				"    }" + 
				"  ]" + 
				"}";
		String url = "https://api.kakaowork.com/v1/messages.send ";
		String token = "ceb1473d.53a95db76acc419ab6f3976f099287fb";
		String parameters = "{ \"conversation_id\": \"1040538\", \"text\": \"Hello test.\", "
				+ "\"blocks\": [" +
				"{" + 
				"      \"type\": \"header\"," + 
				"      \"text\": \" "+ StringUtil.checkNull(cmmMap.get("title"), "Test title") +" \"," + 
				"      \"style\": \"blue\"" + 
				"    }," + 
				"    {" + 
				"      \"type\": \"text\"," + 
				"      \"text\": \" " +
				"	Subject : " + StringUtil.checkNull(cmmMap.get("subject"), "TEST subject") + "\\n" + 
				"	Content : "+ StringUtil.checkNull(cmmMap.get("content"), "TEST Content") + " \"," + 
				"      \"markdown\": true" + 
				"    }"
				+ "]"
				+ " }";
		parameters = StringEscapeUtils.unescapeHtml4(parameters);
		System.out.println(parameters);
		BacthActionController bacthActionController = new BacthActionController();
		bacthActionController.sendPost(url, parameters, "", token);
	}
	
	
	@RequestMapping(value="/custom/kakaoCallback.do")
	public ModelAndView kakaoCallback(Map cmmMap, ModelAndView mv) throws Exception {
		String returnText = "{" + 
				"  \"type\": \"submission\"," + 
				"  \"action_time\": \"{요청 액션이 발생한 카카오워크 서버의 시간 값}\"," + 
				"  \"actions\": {" + 
				"    \"text_reason\": \"qq\"" + 
				"  }," + 
				"  \"message\": \"{액션이 발생한 채팅 메시지 원본}\"," + 
				"  \"value\": \"{request_modal의 응답으로 전송한 value 값}\"" + 
				"}";
		mv.addObject("result", returnText);
	    mv.setViewName("jsonView");
		return mv;
	}*/

	// TODO : 개행 코드 삭제
	private String removeAllTag(String str) {

		str = str.replaceAll("&lt;", "<");//201610 new line :: Excel To DB 
		str = str.replaceAll("&gt;", ">");//201610 new line :: Excel To DB 
		str = str.replaceAll("\n", "&&rn");//201610 new line :: Excel To DB 
		str = str.replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", "").replace("&#10;", " ").replace("&#xa;", "").replace("&nbsp;", " ");
		return StringEscapeUtils.unescapeHtml4(str);
	}
	private String removeAllTag(String str,String type) { 
		if(type.equals("DbToEx")){//201610 new line :: DB To Excel
			str = str.replaceAll("<br/>", "&&rn").replaceAll("<br />", "&&rn").replaceAll("\r\n", "&&rn").replaceAll("&gt;", ">").replaceAll("&lt;", "<").replaceAll("&#40;", "(").replaceAll("&#41;", ")").replace("&sect;","-");//20161024 bshyun Test
		}else{
			str = str.replaceAll("\n", "&&rn");//201610 new line :: Excel To DB 
		}
		str = str.replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", "").replace("&#10;", " ").replace("&#xa;", "").replace("&nbsp;", " ").replace("&amp;", "&");
		if(type.equals("DbToEx")){
			str = str.replaceAll("&&rn", "\n");
		}
		return StringEscapeUtils.unescapeHtml4(str);
	}	
	/*
	 * naver papago
	 * */
	@RequestMapping(value="/getPapagoTrans.do")
	public String getPapagoTrans(HttpServletRequest request, HashMap commandMap, ModelMap model) throws Exception {
		Map setMap = new HashMap();
		
		String clientId = "vSbssCvKOibi1OJSfZ9q";//애플리케이션 클라이언트 아이디값";
        String clientSecret = "6sWg6PB1Rp";//애플리케이션 클라이언트 시크릿값";
        String apiURL = "https://openapi.naver.com/v1/papago/n2mt";
        
        String text = "";
        String transPlainText = "";
        
        String itemID = StringUtil.checkNull(request.getParameter("itemID"),"");
        setMap.put("itemID",itemID);
        setMap.put("languageID", StringUtil.checkNull(request.getParameter("languageID")));
        setMap.put("attrTypeCode", "AT00003");
        
        String plainText = StringUtil.checkNull(commonService.selectString("item_SQL.getItemAttrPlainText", setMap));
        //text = StringEscapeUtils.unescapeHtml4(plainText);
        text = plainText;
        try {
            text = URLEncoder.encode(text, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("인코딩 실패", e);
        }

        Map<String, String> requestHeaders = new HashMap<>();
        requestHeaders.put("X-Naver-Client-Id", clientId);
        requestHeaders.put("X-Naver-Client-Secret", clientSecret);
        String postParams = "source=ko&target=en&text=" + text; //원본언어: 한국어 (ko) -> 목적언어: 영어 (en)
        
        String responseBody = post(apiURL, requestHeaders, postParams);
        // esca String responseBody = "{\"message\":{\"result\":{\"srcLangType\":\"ko\",\"tarLangType\":\"en\",\"translatedText\":\"&lt;p style=&quot;text-align:left;margin-top: 0pt;margin-bottom: 0pt;margin-left: 0in;unicode-bidi: embed;direction: ltr; -ms-word&break:keep-all; tongue:p-top:p-spanct; tongue:p-top:p-spanct; tongue:p-top:p-spoon:p-spoony:p-top:p-bidi:p-spoony-bidi:p-bid;p-bid;p-bid;p-bidt;sales&lt;/span&gt;&lt;span style=&gt;color: #33333333;font-family: 'Hyundai Harmony L';&gt;&gt;1&lt;/span&gt;&lt;span style=&gt;color: #33333333;font-style=&gt;color: Hyundai Harmony:&gt;&lt;span style=&gt;color: #333333;font-family: 'Hyundai Harmony L';&gt;2&lt;/span&gt;&lt;span style=&lt;color: #33333333;font-family: 'Modern Harmony L'=&gt;Font-family: 'Modern Harmony L'&gt;&gt;&gt;Harmony L';&lt;/span&gt;&lt;span style=&lt;color: #333333;font-family: System&lt;/span&gt;=&lt;span style=&gt;&gt;&lt;span&gt;&gt;&gt;&gt;&lt;&gt;&lt;span style=&gt;&gt;&gt;&lt;&gt;&gt;&lt;&gt;&lt;&gt;&gt;&lt;&gt;&gt;&lt;&lt;&gt;&gt;&gt;&Hyundai Harmony L';&gt;. &lt;/span&gt;&lt;span style=&gt;color: #333333;font-family: 'Hyundai Harmony L';&gt;&lt;/span&gt;&lt;span style=&gt;color: #333333;font-family: 'Hyundai Harmony L'&lt;&gt;&gt;&gt;&gt;&gt;&lt;&lt;&lt;&lt;&lt;&lt;&lt;&lt;&lt;&lt;&gt;&lt;&lt;&lt;&gt;&gt;&gt;&&quot;color: #333333;font-family: 'Modern Harmony L';&gt;.&lt;/span&gt;&lt;/span&gt;&lt;/p&gt;\",\"engineType\":\"UNDEF_MULTI_SENTENCE\",\"pivot\":null,\"dict\":null,\"tarDict\":null},\"@type\":\"response\",\"@service\":\"naverservice.nmt.proxy\",\"@version\":\"1.0.0\"}}";
        //String responseBody = "{\"message\":{\"result\":{\"srcLangType\":\"ko\",\"tarLangType\":\"en\",\"translatedText\":\"<p style=\\\"text-align:left;margin-top:0pt;margin-bottom:0pt;margin-left:0in;unicode-bidi:embed;direction:ltr; -ms-word-break:keep-all;language:ko-spanctop:twine=spanctip;strip:strain\\\"ily: 'Hyundai Harmony L';\\\">1</span><span style=\\\"color: #333333;font-family: 'Hyundai Harmony L';\\\">Team</span><span style=\\\"color: #33333333;font-family: 'Hyundai Harmony L'=\\\"color: 3333;font-family\\\"<span=\\\"color:33333;fontainless=\\\" sales: 3333\\\"\\\"Color: #333333;font-family: 'Hyundai Harmony L';\\\">Based on new project information recognized through sales activities, our applicable item </span><span style=\\\"color: #33333333;font-family: 'Hyundai Harmony L';\\\">(</span><span>=33333;fontainless:33\\\" Systeman><span style=\\\"color: #333333;font-family: 'Modern Harmony L';\\\"> </span><span style=\\\"color: #333333;font-family: 'Hyundai Harmony L';\\\">In addition, the business opportunity rating</span><span style=\\\"color: #333333;font-family: 'Hyundai Harmony L';> can be used to select a limited number of resources.</span></span></p>\",\"engineType\":\"UNDEF_MULTI_SENTENCE\",\"pivot\":null,\"dict\":null,\"tarDict\":null},\"@type\":\"response\",\"@service\":\"naverservice.nmt.proxy\",\"@version\":\"1.0.0\"}}";
        JSONParser jsonParser = new JSONParser();
        Object obj = jsonParser.parse(responseBody);
        JSONObject jsonObj = (JSONObject) obj;
        
        
        if (jsonObj !=null && jsonObj.size() > 0) {
        	transPlainText = StringUtil.checkNull(((JSONObject)((JSONObject)jsonObj.get("message")).get("result")).get("translatedText"));
        }
		model.put("transPlainText",transPlainText);
		model.put("plainText",plainText);
		return nextUrl("/report/papagotranstext");
	}
	
	private static String post(String apiUrl, Map<String, String> requestHeaders, String postParams){
        HttpURLConnection con = connect(apiUrl);
        
        try {
            con.setRequestMethod("POST");
            for(Map.Entry<String, String> header :requestHeaders.entrySet()) {
                con.setRequestProperty(header.getKey(), header.getValue());
            }

            con.setDoOutput(true);
            try (DataOutputStream wr = new DataOutputStream(con.getOutputStream())) {
                wr.write(postParams.getBytes());
                wr.flush();
            }

            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) { // 정상 응답
                return readBody(con.getInputStream());
            } else {  // 에러 응답
                return readBody(con.getErrorStream());
            }
        } catch (IOException e) {
            throw new RuntimeException("API 요청과 응답 실패", e);
        } finally {
            con.disconnect();
        }
    }

    private static HttpURLConnection connect(String apiUrl){
        try {
            URL url = new URL(apiUrl);
            return (HttpURLConnection)url.openConnection();
        } catch (MalformedURLException e) {
            throw new RuntimeException("API URL이 잘못되었습니다. : " + apiUrl, e);
        } catch (IOException e) {
            throw new RuntimeException("연결이 실패했습니다. : " + apiUrl, e);
        }
    }

    private static String readBody(InputStream body){
        InputStreamReader streamReader = new InputStreamReader(body);

        try (BufferedReader lineReader = new BufferedReader(streamReader)) {
            StringBuilder responseBody = new StringBuilder();

            String line;
            while ((line = lineReader.readLine()) != null) {
                responseBody.append(line);
            }

            return responseBody.toString();
        } catch (IOException e) {
            throw new RuntimeException("API 응답을 읽는데 실패했습니다.", e);
        }
    }
}
	
