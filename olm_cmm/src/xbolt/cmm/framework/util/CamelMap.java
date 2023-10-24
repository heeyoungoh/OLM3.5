package xbolt.cmm.framework.util;

import org.apache.commons.collections.map.ListOrderedMap;

/**
 * 공통 서블릿 처리
 * @Class Name : CamelMap.java
 * @Description : 카멜표기법을 바로 사용하기 위해 제공한다.
 * @Modification Information
 * @수정일		수정자		 수정내용
 * @---------	---------	-------------------------------
 * @2012. 09. 01. smartfactory		최초생성
 *
 * @since 2012. 09. 01.
 * @version 1.0
 * @see
 * 
 * Copyright (C) 2012 by SMARTFACTORY All right reserved.
 */
public class CamelMap extends ListOrderedMap {

	private static final long serialVersionUID = 1217930917741845866L;

	/**
	 * key 에 대하여 Camel Case 변환하여 super.put (ListOrderedMap) 을 호출한다.
	 * @param key - '_' 가 포함된 변수명
	 * @param value - 명시된 key 에 대한 값 (변경 없음)
	 * @return previous value associated with specified key, or null if there was no mapping for key
	 */
	@Override
	public Object put(Object key, Object value) {
		return super.put(CamelUtil.convert2CamelCase((String)key), value);
	}

}
