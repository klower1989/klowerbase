
package com.klowerbase.test.helper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import com.j256.ormlite.stmt.QueryBuilder;
import com.klower.db.FilterCondition;
import com.klower.db.MemoryPage;

/*
 * 内存分页基类
 */
@SuppressWarnings("hiding")
public abstract class MemoryPageDao<T, Integer> extends BaseDao<T, Integer> {

    public MemoryPageDao(Context context) {
        super(context);
    }

    /**
     * 数据库分页功能
     * 
     * @param pageIndex 页面索引，从0开始
     * @param pageSize 页面大小，不能为0
     * @param pageingHandler
     * @param conditions 检索条件
     * @return
     * @throws SQLException
     * @throws InvalidParamsException
     */
    public MemoryPage<T> searchWithPaging(FilterCondition con) throws SQLException {
    	MemoryPage<T> memoryPage = new MemoryPage<T>();
    	
    	if(con.getPageIndex() == null) {
    		con.setPageIndex(0);
    	}
    	if(con.getLimit() == null) {
    		con.setLimit(10l);
    	}
    	QueryBuilder<T, Integer> queryBuilder = createQueryBuilder(con, true);
    	
    	long total = queryBuilder.countOf();
    	
    	// No more records
    	if(total <= con.getPageIndex() * con.getLimit()) {
    		memoryPage.setData(new ArrayList<T>());
    	}
    	else {
    	    // Re-create data
    	    queryBuilder = createQueryBuilder(con);
    		memoryPage.setData(query(queryBuilder.prepare()));
    	}
    	
    	 if (total % con.getLimit().intValue() == 0) {
             int totalPage = Long.valueOf(total).intValue() / con.getLimit().intValue();
             memoryPage.setTotalPage(totalPage);
         } else {
             int totalPage = Long.valueOf(total).intValue() / con.getLimit().intValue();
             totalPage = totalPage + 1;
             memoryPage.setTotalPage(totalPage);
         }
        memoryPage.setCurrentPage(con.getPageIndex());
        memoryPage.setPageSize(con.getLimit().intValue());

        return memoryPage;
    }
    
//    /**
//     * 内存分页功能
//     * 
//     * @param pageIndex 页面索引，从0开始
//     * @param pageSize 页面大小，不能为0
//     * @param pageingHandler
//     * @param conditions 检索条件
//     * @return
//     * @throws SQLException
//     * @throws InvalidParamsException
//     */
//    public MemoryPage<T> paging(int pageIndex, int pageSize, PageHandler<T> pageingHandler,
//            Object... conditions) throws SQLException, InvalidParamsException {
//        if (pageSize == 0) {
//            throw new InvalidParamsException("page size can not be 0");
//        }
//        List<T> list = queryAll();
//        if (null != pageingHandler) {
//            pageingHandler.beforePaging(list, conditions);
//        }
//        int size = list.size();
//        MemoryPage<T> memoryPage = new MemoryPage<T>();
//        if (null != list && !list.isEmpty()) {
//            int start = pageIndex * pageSize;
//            int end = (pageIndex + 1) * pageSize;
//            if (start > size) {
//                start = size;
//            } else if (start < 0) {
//                start = 0;
//            }
//            if (end > size) {
//                end = size;
//            } else if (end < 0) {
//                end = 0;
//            }
//            if (start >= 0 && end <= size && start <= end) {
//                List<T> tempList = new ArrayList<T>(list.subList(start, end));
//                memoryPage.setData(tempList);
//                memoryPage.setCurrentPage(pageIndex);
//                memoryPage.setPageSize(pageSize);
//                if (size % pageSize == 0) {
//                    int totalPage = size / pageSize;
//                    memoryPage.setTotalPage(totalPage);
//                } else {
//                    int totalPage = size / pageSize;
//                    totalPage = totalPage + 1;
//                    memoryPage.setTotalPage(totalPage);
//                }
//            }
//        }
//        return memoryPage;
//    }

    public interface PageHandler<T> {
        public void beforePaging(List<T> list, Object... conditions);
    }
}
