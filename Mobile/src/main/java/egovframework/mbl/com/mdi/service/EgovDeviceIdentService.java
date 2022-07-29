package egovframework.mbl.com.mdi.service;

import java.util.List;

/**
 * 개요
 * - 모바일기기식별에 대한 Service Interface를 정의한다.
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

public interface EgovDeviceIdentService {

	/**
	 * 모바일 기기 정보를 삭제하는 Service interface 메서드
	 * @param deviceIdent
	 * @throws Exception
	 */
	public void deleteDeviceIdent(DeviceIdent deviceIdent) throws Exception;

	/**
	 * 모바일 기기 정보를 등록하는 Service interface 메서드
	 * @param deviceIdent
	 * @throws Exception
	 */
	public void insertDeviceIdent(DeviceIdent deviceIdent) throws Exception;

	/**
	 * 모바일 기기 상세정보를 조회하는 Service interface 메서드
	 * @param deviceIdent
	 * @return DeviceIdent 모바일기기 정보
	 * @throws Exception
	 */
	public DeviceIdent selectDeviceIdent(DeviceIdent deviceIdent) throws Exception;

	/**
	 * 모바일 기기 정보 목록을 조회하는 Service interface 메서드
	 * @param searchVO
	 * @return List 모바일기기 정보 리스트
	 * @throws Exception
	 */
	public List<?> selectDeviceIdentList(DeviceIdentVO searchVO) throws Exception;

	/**
	 * 모바일 기기 정보를 수정하는 Service interface 메서드
	 * @param deviceIdent
	 * @throws Exception
	 */
	public void updateDeviceIdent(DeviceIdent deviceIdent) throws Exception;

	/**
	 * 모바일 기기 정보의 총 갯수를 조회한다.
	 * @param searchVO
	 * @return int
	 * @throws Exception
	 */
	public int selectDeviceIdentListTotCnt(DeviceIdentVO searchVO) throws Exception;

	/**
	 * 모바일 기기 정보를 XML 파일로 생성한다.
	 * @throws Exception
	 */
	public void createDeviceIndentListToXML() throws Exception;

	/**
	 * User-Agent 값에서 모바일기기 정보를 추출하는 Service interface
	 * 메서드
	 * @param userAgent
	 * @return DeviceIdent 모바일기기 정보
	 * @throws Exception
	 */
	public DeviceIdent getDeviceIdentFromXML(String userAgent) throws Exception;
}
