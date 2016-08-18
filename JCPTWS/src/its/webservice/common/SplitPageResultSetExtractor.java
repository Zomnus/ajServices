/**
 * Copyright(c) SUPCON 2008-2011. 浙江浙大中控信息技术有限公司
 */
package its.webservice.common;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.util.Assert;

/**
 * 系统名称：智能交通WebService服务(ITSWebService)
 * 所属模块：分页处理模块
 * 功能描述：数据查询结果集分页处理
 * 文件名：its.webservice.common.SplitPageResultSetExtractor.java
 * 版本信息：1.00
 * 
 * 开发部门：研发中心
 * 创建者： lzk
 * 创建时间：Jan 25, 2011 4:22:30 PM
 * 修改者： lzk
 * 修改时间：Jan 25, 2011 4:22:30 PM
 */
public class SplitPageResultSetExtractor implements ResultSetExtractor {

	/** 起始行号 */
	private final int startPosition;

    /** 结束行号 */
	private final int endPosition;

    /** 行包装器 */
	private final RowMapper rowMapper;

    /**
     * 构造函数
     *
     * @param rowMapper RowMapper
     * @param startPosition int
     * @param endPosition int
     */
	public SplitPageResultSetExtractor(RowMapper rowMapper, int startPosition, int endPosition) {
		Assert.notNull(rowMapper, "RowMapper is required");
		this.rowMapper = rowMapper;
		this.startPosition = startPosition;
		this.endPosition = endPosition;
	}

    /**
     * 构造函数
     *
     * @param startPosition int
     * @param endPosition int
     */
	public SplitPageResultSetExtractor(int startPosition, int endPosition) {
		this.rowMapper = null;
		this.startPosition = startPosition;
		this.endPosition = endPosition;
	}

	/**
	 * 处理结果集合,被接口自动调用，该类外边不应该调用
	 * 
	 * @param rs ResultSet
	 * @throws SQLException
	 * @throws DataAccessException
	 * @return Object
	 */
	public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
		List<Object> resultList = new ArrayList<Object>();
		Map<String, Object> rowMap = null;
		ResultSetMetaData rsmd = null;

//		int rowNum = 0;
//		point: while (rs.next()) {
//			++rowNum;
//			if (rowNum < startPosition) {
//				continue point;
//			} else if (rowNum >= endPosition) {
//				break point;
//			} else {
//				result.add(this.rowMapper.mapRow(rs, rowNum));
//			}
//		}

        int position = 0;
        while (rs.next()) {
            position++;
            if (position >= startPosition && position <= endPosition) {
            	if (this.rowMapper == null) {
            		rowMap = new HashMap<String, Object>();

            		// 将当前记录封装到rowMap
            		rsmd = rs.getMetaData();
            		for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                        if (rsmd.getColumnType(i) == Types.VARCHAR) {
                			rowMap.put(rsmd.getColumnName(i).toLowerCase(), rs.getString(i));
                        } else if (rsmd.getColumnType(i) == Types.INTEGER) {
                			rowMap.put(rsmd.getColumnName(i).toLowerCase(), rs.getInt(i));
                        } else if (rsmd.getColumnType(i) == Types.DOUBLE) {
                			rowMap.put(rsmd.getColumnName(i).toLowerCase(), rs.getDouble(i));
                        } else if (rsmd.getColumnType(i) == Types.DATE) {
                			rowMap.put(rsmd.getColumnName(i).toLowerCase(), rs.getDate(i));                                                             	
                        } else if (rsmd.getColumnType(i) == Types.TIMESTAMP) {
                			rowMap.put(rsmd.getColumnName(i).toLowerCase(), rs.getTimestamp(i));
                        } else if (rsmd.getColumnType(i) == Types.FLOAT) {
                			rowMap.put(rsmd.getColumnName(i).toLowerCase(), rs.getFloat(i));
                        } else if (rsmd.getColumnType(i) == Types.DECIMAL) {
                			rowMap.put(rsmd.getColumnName(i).toLowerCase(), rs.getBigDecimal(i));
                        } else if (rsmd.getColumnType(i) == Types.NUMERIC) {
                			rowMap.put(rsmd.getColumnName(i).toLowerCase(), rs.getBigDecimal(i));
                        } else {
                			rowMap.put(rsmd.getColumnName(i).toLowerCase(), rs.getObject(i));
                        }
            		}

            		resultList.add(rowMap);
            	} else {
            		resultList.add(this.rowMapper.mapRow(rs, position));
            	}
                if (position == endPosition) {
                    break;
                }
            }
        }

		return resultList;
	}

}
