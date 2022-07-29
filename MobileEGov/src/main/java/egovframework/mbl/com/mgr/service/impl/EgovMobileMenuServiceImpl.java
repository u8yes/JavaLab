package egovframework.mbl.com.mgr.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import egovframework.mbl.com.mgr.service.EgovMobileMenuService;
import egovframework.mbl.com.mgr.service.MenuVO;

import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

/**
 * @Class Name : EgovMobileMenuServiceImpl.java
 * @Description : 모바일 템플릿 정보 관리를 위한 구현 클래스
 * @Modification Information
 *
 *   수정일          수정자              수정내용
 *  ----------		--------	---------------------------
 *  2014.09.11		표준프레임워크              최초 생성
 *
 *  @author 표준프레임워크
 *  @since 2014.09.11
 *  @version 1.0
 *
 */

@Service("egovMobileMenuService")
public class EgovMobileMenuServiceImpl extends EgovAbstractServiceImpl implements EgovMobileMenuService {

	/** userManageDAO */
	@Resource(name = "menuDAO")
	private MenuDAO menuDAO;

	/**
	 * 메뉴를 삭제한다.
	 */
	@Override
	public void deleteMenu(MenuVO vo) throws Exception {
		menuDAO.deleteMenu(vo);
	}

	/**
	 * 메뉴 정보를 입력한다.
	 */
	@Override
	public void insertMenu(MenuVO vo) throws Exception {
		menuDAO.insertMenu(vo);
	}

	/**
	 * 상세메뉴를 조회한다.
	 */
	@Override
	public MenuVO selectMenuDetail(MenuVO vo) throws Exception {
		return menuDAO.selectMenuDetail(vo);
	}

	/**
	 * 메뉴목록을 조회한다.
	 */
	@Override
	public Map<String, Object> selectMenuList(MenuVO vo) throws Exception {

		List<MenuVO> result = menuDAO.selectMenuList(vo);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("resultList", result);

		return map;
	}

	/**
	 * 메뉴목록을 조회한다.
	 */
	@Override
	public Map<String, Object> selectUpperMenuList(MenuVO vo) throws Exception {

		List<MenuVO> result = menuDAO.selectUpperMenuList(vo);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("resultList", result);

		return map;
	}

	/**
	 * 사용여부가 참인 목록만 조회한다.
	 */
	@Override
	public Map<String, Object> selectUseList(MenuVO vo) throws Exception {

		List<MenuVO> result = menuDAO.selectUseList(vo);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("resultList", result);

		return map;
	}

	/**
	 * 메뉴를 수정한다.
	 */
	@Override
	public void updateMenu(MenuVO vo) throws Exception {
		menuDAO.updateMenu(vo);
	}

}
