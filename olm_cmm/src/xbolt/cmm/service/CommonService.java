package xbolt.cmm.service;

import java.util.List;
import java.util.Map;

/**
 * @Class Name : CommonService.java
 * @Description : 기본서비스 틀을 잡기 위함
 * @Modification Information
 * 수정일			수정자		수정내용
 * ---------	---------	------------------------------
 * 2010. 9. 9.	swchoi		최초생성
 *
 * @since 2010. 9. 9.
 * @version 1.0
 * @see
 */
@SuppressWarnings("unchecked")
public interface CommonService {

	/**
	 * 등록한다.
	 * @param map - 등록할 정보가 담긴 Map
	 * @return 등록 결과
	 * @exception Exception
	 */
	String insert(Map commandMap) throws Exception;
	String insert(String sqlId, Map commandMap) throws Exception;
	String insertReturnParam(String sqlId, Map vo) throws Exception;

	/**
	 * 수정한다.
	 * @param vo - 수정할 정보가 담긴 Map
	 * @return void형
	 * @exception Exception
	 */
	void update(Map commandMap) throws Exception;
	void update(String sqlId, Map commandMap) throws Exception;

	/**
	 * 삭제한다.
	 * @param map - 삭제할 정보가 담긴 Map
	 * @return void형
	 * @exception Exception
	 */
	void delete(Map commandMap) throws Exception;
	void delete(String sqlId, Map commandMap) throws Exception;
	String deleteReturnString(String sqlId, Map commandMap) throws Exception;

	/**
	 * 조회한다.
	 * @param map - 조회할 정보가 담긴 Map
	 * @return 조회한 결과
	 * @exception Exception
	 */
	Map select(Map commandMap) throws Exception;
	Map select(String sqlId, Map commandMap) throws Exception;

	/**
	 * 목록을 조회한다.
	 * @param searchMap - 조회할 정보가 담긴 VO
	 * @return 목록
	 * @exception Exception
	 */
	List selectList(Map searchMap) throws Exception;
	List selectList(String sqlId, Map searchMap) throws Exception;

	/**
	 * 총 갯수를 조회한다.
	 * @param searchMap - 조회할 정보가 담긴 VO
	 * @return 글 총 갯수
	 * @exception
	 */
	int selectListTotCnt(Map searchMap) throws Exception;
	int selectListTotCnt(String sqlId, Map searchMap) throws Exception;

	Object selectToObject(String sqlId, Map commandMap) throws Exception;

	String nextId(String tableName) throws Exception;
	String nextId(String tableName, boolean needDate) throws Exception;
	String nextId(String tableName, int needDate) throws Exception;
	String nextId(String tableName, boolean needDate, int length) throws Exception;
	String selectString(String sqlId, Map searchMap) throws Exception;
	/**
	 * 컬럼명에 해당되는 시퀀스 값 반환
	 * @param commandMap
	 * @param COL_NAME
	 * @return
	 * @throws Exception
	 */
	Object getNextId(String COL_NAME) throws Exception;

	/**
	 * 그리드에서 넘어온 데이터를 저장한다.(삭제 제외)
	 * @param sqlId
	 * @param map
	 * @throws Exception
	 */
	void save(String sqlId, Map commandMap) throws Exception;
	void save(Map commandMap) throws Exception;
	void save(Map commandMap, String key) throws Exception;
	void save(List lst) throws Exception;
	void save(List lst1, List lst2) throws Exception;
	void save(List lst, Map commandMap) throws Exception;

	/**
	 * 그리드에서 넘어온 데이터를 저장한다.(삭제 제외)
	 * @param sqlId
	 * @param map
	 * @throws Exception
	 */
	void saveGrid(String sqlId, Map commandMap) throws Exception;
	void saveGrid(Map commandMap) throws Exception;
	void saveGrid(Map map, String key) throws Exception;
	int saveGridReturn(Map map, String key) throws Exception;

	/**
	 * 그리드에서 넘어온 데이터를 삭제한다.
	 * @param sqlId
	 * @param map
	 * @throws Exception
	 */
	void deleteGrid(String sqlId, Map commandMap) throws Exception;
	void deleteGrid(Map commandMap) throws Exception;
	Map fetchData(Map map, int rowNum, String col) throws Exception;

}
