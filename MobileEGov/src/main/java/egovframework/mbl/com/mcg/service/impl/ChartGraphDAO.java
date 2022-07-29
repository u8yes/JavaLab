package egovframework.mbl.com.mcg.service.impl;

import java.util.List;

import egovframework.com.cmm.service.impl.EgovComAbstractDAO;
import egovframework.mbl.com.mcg.service.ChartGraph;
import egovframework.mbl.com.mcg.service.ChartGraphVO;

import org.springframework.stereotype.Repository;

/**
 * 개요
 * - 차트/그래프 데이터에 대한 DB상의 접근, 변경을 처리한다.
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

@Repository("ChartGraphDAO")
public class ChartGraphDAO extends EgovComAbstractDAO {

    /**
     * 차트/그래프 데이터 DB 삭제 메서드
     * @param chartGraph
     * @throws Exception
     */
    public void deleteChartGraph(ChartGraph chartGraph) throws Exception {
        delete("ChartGraphDAO.deleteChartGraph", chartGraph);
    }

    /**
     * 차트/그래프 데이터 DB 등록 메서드
     * @param chartGraph
     * @throws Exception
     */
    public void insertChartGraph(ChartGraph chartGraph) throws Exception {
        insert("ChartGraphDAO.insertChartGraph", chartGraph);
    }

    /**
     * 차트/그래프 데이터 상세 DB 조회 메서드
     * @param chartGraphVO
     * @return ChartGraphVO 차트/그래프 데이터
     * @throws Exception
     */
    public ChartGraph selectChartGraph(ChartGraph chartGraph) throws Exception {
        return (ChartGraph) select("ChartGraphDAO.selectChartGraph",
            chartGraph);
    }

    /**
     * 차트/그래프 데이터 목록 DB 조회 메서드
     * @param chartGraphVO
     * @return List 차트/그래프 데이터 목록
     * @throws Exception
     */
    public List<?> selectChartGraphList(ChartGraphVO searchVO) throws Exception {
        return list("ChartGraphDAO.selectChartGraphList", searchVO);
    }

    /**
     * 차트/그래프 데이터 DB 수정 메서드
     * @param chartGraph
     * @throws Exception
     */
    public void updateChartGraph(ChartGraph chartGraph) throws Exception {
        update("ChartGraphDAO.updateChartGraph", chartGraph);
    }

    /**
     * 차트/그래프 데이터의 총 갯수를 조회한다.
     * @param searchVO
     * @return int
     * @throws Exception
     */
    public int selectChartGraphListTotCnt(ChartGraphVO searchVO)
            throws Exception {
        return (Integer) select(
            "ChartGraphDAO.selectChartGraphListTotCnt", searchVO);
    }
}
