package xbolt.cmm.framework.util;

import java.util.List;

import org.apache.commons.collections.map.ListOrderedMap;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 로그인 시 session + 카멜표기법에 의한 컬럼명을 바로 사용하기 위해 생성
 */
public class LoginMap extends ListOrderedMap {
	private final Log _log = LogFactory.getLog(this.getClass());

	private static final String SESSION = "SESSION_";
	private static final long serialVersionUID = 6723434363565852261L;

	/**
	 * key 에 대하여 Camel Case 변환하여 super.put (ListOrderedMap) 을 호출한다.
	 * @param key - '_' 가 포함된 변수명
	 * @param value - 명시된 key 에 대한 값 (변경 없음)
	 * @return previous value associated with specified key, or null if there was no mapping for key
	 */
	@Override
	public Object put(Object key, Object value) {
		return super.put(CamelUtil.convert2CamelCase(SESSION+key), value);
		
	}

	@SuppressWarnings("unchecked")
	public void printAll() {
		List keyList = keyList();
		if(_log.isInfoEnabled())
		{
			_log.info("##################################################");
			_log.info("Class : LoginMap; Function : printAll");
			for (Object object : keyList) {
				System.out.print("| [" + object + "] : \"" + super.get(CamelUtil.convert2CamelCase(SESSION+object)) + "\" |");
			}
			_log.info("##################################################");
		}				
	}
	public void print() {
		List keyList = keyList();
		if(_log.isInfoEnabled())
		{
			_log.info("##################################################");
			_log.info("Class : LoginMap; Function : print");
			_log.info("Info : "+" [super] : \"" + super.toString());
			//_log.info("Info : "+" [sessionUserId] : \"" + super.get("sessionUserId")+"\"|| [sessionUserNm] : \"" + super.get("sessionUserNm")+"\"|| [sessionCompanyId] : \"" + super.get("sessionCompanyId"));
			_log.info("##################################################");
		}	
	}
}
