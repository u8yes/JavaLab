package egovframework.mbl.com.mcg.service;

import java.util.List;

/**
 * 개요
 * - 모바일 차트/그래프에 대한 Service Interface를 정의한다.
 *
 * 상세내용
 * - 차트/그래프 데이터에 대한 등록, 수정, 삭제, 조회 기능을 제공한다.
 * - 차트/그래프 데이터에 대한 조회기능은 목록, 상세조회로 구분된다.
 * @author 정홍규
 * @since 2011.08.16
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 *   2011.08.16  정홍규          최초 생성
 *
 * </pre>
 */

public interface EgovChartGraphService {

	/**
	 * 차트/그래프 데이터를 삭제하는 Service interface 메서드
	 * @param chartGraph
	 * @throws Exception
	 */
	public void deleteChartGraph(ChartGraph chartGraph) throws Exception;

	/**
	 * 차트/그래프 데이터를 등록하는 Service interface 메서드
	 * @param chartGraph
	 * @throws Exception
	 */
	public void insertChartGraph(ChartGraph chartGraph) throws Exception;

	/**
	 * 차트/그래프 데이터를 상세 조회하는 Service interface 메서드
	 * @return ChartGraphVO 차트/그래프 데이터
	 * @param chartGraphVO
	 * @throws Exception
	 */
	public ChartGraph selectChartGraph(ChartGraph chartGraph) throws Exception;

	/**
	 * 차트/그래프 데이터 목록을 조회하는 Service interface 메서드
	 * @return List 차트/그래프 데이터 목록
	 * @param chartGraphVO
	 * @throws Exception
	 */
	public List<?> selectChartGraphList(ChartGraphVO chartGraphVO) throws Exception;

	/**
	 * 차트/그래프 데이터를 수정하는 Service interface 메서드
	 * @param chartGraph
	 * @throws Exception
	 */
	public void updateChartGraph(ChartGraph chartGraph) throws Exception;

	/**
	 * 차트/그래프 데이터의 총 갯수를 조회한다.
	 * @param searchVO
	 * @return int
	 * @throws Exception
	 */
	public int selectChartGraphListTotCnt(ChartGraphVO searchVO) throws Exception;
}
