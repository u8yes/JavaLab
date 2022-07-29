package egovframework.mbl.com.mcg.service.impl;

import java.util.List;

import egovframework.mbl.com.mcg.service.ChartGraph;
import egovframework.mbl.com.mcg.service.ChartGraphVO;
import egovframework.mbl.com.mcg.service.EgovChartGraphService;

import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.egovframe.rte.fdl.idgnr.EgovIdGnrService;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

/**
 * 개요
 * - 모바일 차트/그래프에 대한 Service Interface를 구현한다.
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

@Service("ChartGraphService")
public class EgovChartGraphServiceImpl extends EgovAbstractServiceImpl implements
        EgovChartGraphService {

    /**
     * ChartGraphDAO
     */
    @Resource(name = "ChartGraphDAO")
    private ChartGraphDAO chartGraphDAO;

    /** ID Generation */
    @Resource(name = "egovChartGraphIdGnrService")
    private EgovIdGnrService idgenService;

    /**
     * 차트/그래프 데이터 삭제 관련 비즈니스 구현 메서드
     * @param chartGraph
     * @throws Exception
     */
    @Override
	public void deleteChartGraph(ChartGraph chartGraph) throws Exception {
        chartGraphDAO.deleteChartGraph(chartGraph);
    }

    /**
     * 차트/그래프 데이터 등록 관련 비즈니스 구현 메서드
     * @param chartGraph
     * @throws Exception
     */
    @Override
	public void insertChartGraph(ChartGraph chartGraph) throws Exception {
    	egovLogger.debug(chartGraph.toString());

        int sn = idgenService.getNextIntegerId();

        chartGraph.setSn(sn);

        chartGraphDAO.insertChartGraph(chartGraph);
    }

    /**
     * 차트/그래프 데이터 상세 조회 관련 비즈니스 구현 메서드
     * @param chartGraphData
     * @return Chart/Graph 차트/그래프 데이터
     * @throws Exception
     */
    @Override
	public ChartGraph selectChartGraph(ChartGraph chartGraph) throws Exception {
        ChartGraph ret =
            chartGraphDAO.selectChartGraph(chartGraph);
        return ret;
    }

    /**
     * 차트/그래프 데이터 목록 조회 관련 비즈니스 구현 메서드
     * @param searchVO
     * @return List 차트/그래프 데이터 목록
     * @throws Exception
     */
    @Override
	public List<?> selectChartGraphList(ChartGraphVO searchVO) throws Exception {
        return chartGraphDAO.selectChartGraphList(searchVO);
    }

    /**
     * 차트/그래프 데이터 수정 관련 비즈니스 구현 메서드
     * @param chartGraph
     * @throws Exception
     */
    @Override
	public void updateChartGraph(ChartGraph chartGraph) throws Exception {
        chartGraphDAO.updateChartGraph(chartGraph);
    }

    /**
     * 차트/그래프 데이터의 총 갯수를 조회한다.
     * @param searchVO
     * @return int
     */
    @Override
	public int selectChartGraphListTotCnt(ChartGraphVO searchVO)
            throws Exception {
        return chartGraphDAO.selectChartGraphListTotCnt(searchVO);
    }
}
