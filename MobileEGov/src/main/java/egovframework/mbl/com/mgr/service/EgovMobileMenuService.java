package egovframework.mbl.com.mgr.service;

import java.util.Map;

/**
 * @Class Name : EgovMobileMenuService.java
 * @Description : 모바일 템플릿을 위한 서비스 인터페이스
 * @Modification Information
 * 
 *   수정일          수정자              수정내용
 *  ----------		--------	---------------------------
 *  2014.09.11		 표준프레임워크              최초 생성
 *
 *  @author 표준프레임워크
 *  @since 2014.09.11
 *  @version 1.0
 *  
 */

public interface EgovMobileMenuService {

	public void deleteMenu(MenuVO vo) throws Exception;

	public MenuVO selectMenuDetail(MenuVO vo) throws Exception;

	public Map<String, Object> selectUseList(MenuVO vo) throws Exception;

	public Map<String, Object> selectMenuList(MenuVO vo) throws Exception;

	public void insertMenu(MenuVO vo) throws Exception;

	public void updateMenu(MenuVO vo) throws Exception;

	public Map<String, Object> selectUpperMenuList(MenuVO menuVO) throws Exception;

}
