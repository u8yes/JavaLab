package egovframework.mbl.com.mdi.service.impl;

import java.util.List;

import egovframework.com.cmm.service.impl.EgovComAbstractDAO;
import egovframework.mbl.com.mdi.service.DeviceIdent;
import egovframework.mbl.com.mdi.service.DeviceIdentVO;

import org.springframework.stereotype.Repository;

/**
 * 개요
 * - 모바일기기식별에 대한 DB상의 접근, 변경을 처리한다.
 *
 * 상세내용
 * - 모바일기기 식별정보에 대한 등록, 수정, 삭제, 조회 기능과 User-Agent값 조회, 모바일기기 정보 추출 기능을 제공한다.
 * - 모바일기기 식별정보에 대한 조회기능은 목록, 상세조회로 구분된다.
 * @author 정홍규
 * @since 2011.08.19
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 *   2011.08.19  정홍규          최초 생성
 *
 * </pre>
 */

@Repository("DeviceIdentDAO")
public class DeviceIdentDAO extends EgovComAbstractDAO {

	/**
	 * 모바일 기기 정보 DB 삭제 메서드
	 * @param deviceIdent
	 * @throws Exception
	 */
	public void deleteDeviceIdent(DeviceIdent deviceIdent) throws Exception {
		delete("DeviceIdentDAO.deleteDeviceIdent", deviceIdent);
	}

	/**
	 * 모바일 기기 정보 DB 등록 메서드
	 * @param deviceIdent
	 * @throws Exception
	 */
	public void insertDeviceIdent(DeviceIdent deviceIdent) throws Exception {
		insert("DeviceIdentDAO.insertDeviceIdent", deviceIdent);
	}

	/**
	 * 모바일 기기 정보 상세 DB 조회 메서드
	 * @param deviceIdent
	 * @return DeviceIdent 모바일 기기 정보
	 * @throws Exception
	 */
	public DeviceIdent selectDeviceIdent(DeviceIdent deviceIdent) throws Exception {
		return (DeviceIdent) select("DeviceIdentDAO.selectDeviceIdent", deviceIdent);
	}

	/**
	 * 모바일 기기 목록 DB 조회 메서드
	 * @param searchVO
	 * @return List 모바일 기기 목록
	 * @throws Exception
	 */
	public List<?> selectDeviceIdentList(DeviceIdentVO searchVO) throws Exception {
		return list("DeviceIdentDAO.selectDeviceIdentList", searchVO);
	}

	/**
	 * 모바일 기기 정보 DB 수정 메서드
	 * @param deviceIdent
	 * @throws Exception
	 */
	public void updateDeviceIdent(DeviceIdent deviceIdent) throws Exception {
		update("DeviceIdentDAO.updateDeviceIdent", deviceIdent);
	}

	/**
	 * 모바일 기기 정보의 총 갯수를 조회한다.
	 * @param searchVO
	 * @return int
	 * @throws Exception
	 */
	public int selectDeviceIdentListTotCnt(DeviceIdentVO searchVO) throws Exception {
		return (Integer) select("DeviceIdentDAO.selectDeviceIdentListTotCnt", searchVO);
	}
}
