
package com.klowerbase.test.helper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import android.content.Context;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.GenericRawResults;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.PreparedDelete;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;
import com.klower.db.FilterCondition;
import com.klower.db.FilterCondition.DIRECTION;
import com.klower.db.FilterCondition.MODE;
import com.klower.db.FilterCondition.SortCondition;
import com.klower.db.FilterCondition.WhereCondition;
import com.klower.utilities.StringUtils;

@SuppressWarnings("hiding")
public abstract class BaseDao<T, Integer> {
    protected DatabaseHelper mDatabaseHelper;

    protected Context mContext;
    
    public final static int PAGE_SIZE = 10;

    public BaseDao(Context context) {
        mContext = context;
        getHelper();
    }

    public DatabaseHelper getHelper() {
        if (mDatabaseHelper == null) {
            mDatabaseHelper = OpenHelperManager.getHelper(mContext, DatabaseHelper.class);
        }
        return mDatabaseHelper;
    }

    public abstract Dao<T, Integer> getDao() throws SQLException;

    public int save(T t) throws SQLException {
        return getDao().create(t);
    }
    
    public int save(List<T> lst) throws SQLException {
        if (null != lst && !lst.isEmpty()) {
            for (T t : lst) {
                save(t);
            }
            return lst.size();
        } else {
            return 0;
        }
    }

    public List<T> query(PreparedQuery<T> preparedQuery) throws SQLException {
        Dao<T, Integer> dao = getDao();
        return dao.query(preparedQuery);
    }

    public List<T> query(String attributeName, Object attributeValue) throws SQLException {
        QueryBuilder<T, Integer> queryBuilder = getDao().queryBuilder();
        queryBuilder.where().eq(attributeName, attributeValue);
        PreparedQuery<T> preparedQuery = queryBuilder.prepare();
        return query(preparedQuery);
    }


    public List<T> queryAll() throws SQLException {
        Dao<T, Integer> dao = getDao();
        return dao.queryForAll();
    }

    public T queryById(String idName, Object idValue) throws SQLException {
        List<T> lst = query(idName, idValue);
        if (null != lst && !lst.isEmpty()) {
            return lst.get(0);
        } else {
            return null;
        }
    }

    public int delete(PreparedDelete<T> preparedDelete) throws SQLException {
        Dao<T, Integer> dao = getDao();
        return dao.delete(preparedDelete);
    }

    public int delete(T t) throws SQLException {
        Dao<T, Integer> dao = getDao();
        return dao.delete(t);
    }
    

    public int delete(List<T> lst) throws SQLException {
        Dao<T, Integer> dao = getDao();
        return dao.delete(lst);
    }
    
    public int delete(String attributeName, Object attributeValue) throws SQLException {
        List<T> lst = query(attributeName, attributeValue);
        if (null != lst && !lst.isEmpty()) {
            return delete(lst);
        }
        return 0;
    }


    public int update(T t) throws SQLException {
        Dao<T, Integer> dao = getDao();
        return dao.update(t);
    }
    
    public int update(List<T> lst) throws SQLException {
        if (null != lst && !lst.isEmpty()) {
            for (T t : lst) {
                update(t);
            }
            return lst.size();
        } else {
            return 0;
        }
    }

    public boolean isTableExsits() throws SQLException {
        return getDao().isTableExists();
    }

    public long countOf() throws SQLException {
        return getDao().countOf();
    }

    public List<T> query(Map<String, Object> map) throws SQLException {
        QueryBuilder<T, Integer> queryBuilder = getDao().queryBuilder();

        if (!map.isEmpty()) {
            Where<T, Integer> wheres = queryBuilder.where();
            Set<String> keys = map.keySet();
            ArrayList<String> keyss = new ArrayList<String>();
            keyss.addAll(keys);
            for (int i = 0; i < keyss.size(); i++) {
                if (i == 0) {
                    wheres.eq(keyss.get(i), map.get(keyss.get(i)));
                } else {
                    wheres.and().eq(keyss.get(i), map.get(keyss.get(i)));
                }
            }
        }
        PreparedQuery<T> preparedQuery = queryBuilder.prepare();
        return query(preparedQuery);
    }

    public List<T> query(List<String> groupColumns, List<String> columns, Map<String, Object> map,
            Long limit) throws SQLException {
        QueryBuilder<T, Integer> queryBuilder = getDao().queryBuilder();
        for (String grpColumn : groupColumns) {
            queryBuilder.groupBy(grpColumn);
        }
        queryBuilder.selectColumns(columns);

        if (!map.isEmpty()) {
            Where<T, Integer> wheres = queryBuilder.where();
            Set<String> keys = map.keySet();
            ArrayList<String> keyss = new ArrayList<String>();
            keyss.addAll(keys);
            for (int i = 0; i < keyss.size(); i++) {
                if (i == 0) {
                    wheres.eq(keyss.get(i), map.get(keyss.get(i)));
                } else {
                    wheres.and().eq(keyss.get(i), map.get(keyss.get(i)));
                }
            }
        }
        // if(limit != null) {
        // queryBuilder.limit(limit);
        // }

        PreparedQuery<T> preparedQuery = queryBuilder.prepare();
        return query(preparedQuery);
    }

    public List<T> query(Map<String, Object> map, Map<String, Object> lowMap,
            Map<String, Object> highMap) throws SQLException {
        QueryBuilder<T, Integer> queryBuilder = getDao().queryBuilder();
        Where<T, Integer> wheres = queryBuilder.where();
        if (!map.isEmpty()) {
            Set<String> keys = map.keySet();
            ArrayList<String> keyss = new ArrayList<String>();
            keyss.addAll(keys);
            for (int i = 0; i < keyss.size(); i++) {
                if (i == 0) {
                    wheres.eq(keyss.get(i), map.get(keyss.get(i)));
                } else {
                    wheres.and().eq(keyss.get(i), map.get(keyss.get(i)));
                }
            }
        }
        if (!lowMap.isEmpty()) {
            Set<String> keys = lowMap.keySet();
            ArrayList<String> keyss = new ArrayList<String>();
            keyss.addAll(keys);
            for (int i = 0; i < keyss.size(); i++) {
                if (map.isEmpty()) {
                    wheres.gt(keyss.get(i), lowMap.get(keyss.get(i)));
                } else {
                    wheres.and().gt(keyss.get(i), lowMap.get(keyss.get(i)));
                }
            }
        }

        if (!highMap.isEmpty()) {
            Set<String> keys = highMap.keySet();
            ArrayList<String> keyss = new ArrayList<String>();
            keyss.addAll(keys);
            for (int i = 0; i < keyss.size(); i++) {
                wheres.and().lt(keyss.get(i), highMap.get(keyss.get(i)));
            }
        }
        PreparedQuery<T> preparedQuery = queryBuilder.prepare();
        return query(preparedQuery);
    }

    public int delete(FilterCondition condition) throws SQLException {
        DeleteBuilder<T, Integer> queryBuilder = getDao().deleteBuilder();

        if (condition.getConditions() != null && condition.getConditions().size() > 0) {
            Where<T, Integer> wheres = queryBuilder.where();
            boolean secondFlag = false;
            for (WhereCondition con : condition.getConditions()) {
                if (secondFlag) {
                    wheres.and();
                } else {
                    secondFlag = true;
                }
                if (con.getMode() == MODE.EQ) {
                    wheres.eq(con.getColumnName(), con.getColumnValue());
                } else if (con.getMode() == MODE.LE) {
                    wheres.le(con.getColumnName(), con.getColumnValue());
                } else if (con.getMode() == MODE.LT) {
                    wheres.lt(con.getColumnName(), con.getColumnValue());
                } else if (con.getMode() == MODE.LIKE) {
                    wheres.like(con.getColumnName(), con.getColumnValue());
                } else if (con.getMode() == MODE.IN) {
                    wheres.in(con.getColumnName(), (List<?>)con.getColumnValue());
                } else if (con.getMode() == MODE.GE) {
                    wheres.ge(con.getColumnName(), con.getColumnValue());
                } else if (con.getMode() == MODE.GT) {
                    wheres.gt(con.getColumnName(), con.getColumnValue());
                } else if (con.getMode() == MODE.BETWEEN) {
                    wheres.between(con.getColumnName(), con.getColumnValue(), con.getColumnValue2());
                }
            }
        }
        PreparedDelete<T> preparedQuery = queryBuilder.prepare();
        return delete(preparedQuery);
    }
    
	public int deleteAll() throws SQLException {
		List<T> items = getDao().queryForAll();
		if (null != items && !items.isEmpty()) {
			int size = items.size();
			getDao().delete(items);
			return size;
		} else {
			return 0;
		}
	}

    public List<T> query(FilterCondition condition) throws SQLException {
    	QueryBuilder<T, Integer> queryBuilder = createQueryBuilder(condition);
    	
    	if (condition.getRawColumns() != null && condition.getRawColumns().size() > 0) {
            queryBuilder.selectRaw(condition.getRawColumns().toArray(new String[0]));
        } else if (condition.getSelColumns() != null && condition.getSelColumns().size() > 0) {
            queryBuilder.selectColumns(condition.getSelColumns());
        }
    	
        return query(queryBuilder.prepare());
    }

    protected QueryBuilder<T, Integer> createQueryBuilder(FilterCondition condition) throws SQLException {
        return this.createQueryBuilder(condition, false);
    }

    protected QueryBuilder<T, Integer> createQueryBuilder(FilterCondition condition, boolean forSummary) throws SQLException {
        QueryBuilder<T, Integer> queryBuilder = getDao().queryBuilder();

        if (condition.getConditions() != null && condition.getConditions().size() > 0) {
            Where<T, Integer> wheres = queryBuilder.where();
            boolean secondFlag = false;
            for (WhereCondition con : condition.getConditions()) {
                if (secondFlag) {
                    wheres.and();
                } else {
                    secondFlag = true;
                }
                if (con.getMode() == MODE.EQ) {
                    wheres.eq(con.getColumnName(), con.getColumnValue());
                } else if (con.getMode() == MODE.LE) {
                    wheres.le(con.getColumnName(), con.getColumnValue());
                } else if (con.getMode() == MODE.LT) {
                    wheres.lt(con.getColumnName(), con.getColumnValue());
                } else if (con.getMode() == MODE.LIKE) {
                    wheres.like(con.getColumnName(), con.getColumnValue());
                } else if (con.getMode() == MODE.IN) {
                    wheres.in(con.getColumnName(), (List<?>)con.getColumnValue());
                } else if (con.getMode() == MODE.GE) {
                    wheres.ge(con.getColumnName(), con.getColumnValue());
                } else if (con.getMode() == MODE.GT) {
                    wheres.gt(con.getColumnName(), con.getColumnValue());
                } else if (con.getMode() == MODE.BETWEEN) {
                    wheres.between(con.getColumnName(), con.getColumnValue(), con.getColumnValue2());
                } else if(con.getMode() == MODE.RAW) {
                    wheres.raw(con.getColumnName());
                }
                
                
            }
        }
        if (condition.getGroupBys() != null && condition.getGroupBys().size() > 0) {
            for (String grpBy : condition.getGroupBys()) {
                queryBuilder.groupBy(grpBy);
            }
        }
        if(forSummary == false) {
            if (condition.getLimit() != null) {
                queryBuilder.limit(condition.getLimit());
            }
            if (condition.getPageIndex() != null && condition.getLimit() != null) {
                queryBuilder.offset((long)condition.getPageIndex() * condition.getLimit());
            }
        }
        if (forSummary == false && condition.getSorts() != null) {
            for (SortCondition sort : condition.getSorts()) {
                if (StringUtils.isNotEmpty(sort.getRawSql())) {
                    queryBuilder.orderByRaw(sort.getRawSql());
                } else {
                    if (sort.getDirection() != null && sort.getDirection() == DIRECTION.DESC) {
                        queryBuilder.orderBy(sort.getColumnName(), false);
                    } else {
                        queryBuilder.orderBy(sort.getColumnName(), true);
                    }
                }
            }
        }
    	return queryBuilder;
    }
    
    public List<String[]> queryByRaw(String rawSql) throws SQLException {
        List<String[]> ret = new ArrayList<String[]>();

        GenericRawResults<String[]> rawResults = null;
        try {
            rawResults = getDao().queryRaw(rawSql);
            List<String[]> ret2 = rawResults.getResults();
            if(ret2 != null) {
                ret.addAll(ret2);
            }
        } finally {
            if (rawResults != null) {
                rawResults.close();
            }
        }
        return ret;
    }
    
    public List<Object[]> queryByRaw(String rawSql, DataType[] params) throws SQLException {
        List<Object[]> ret = new ArrayList<Object[]>();

        GenericRawResults<Object[]> rawResults = null;
        try {
            rawResults = getDao().queryRaw(rawSql, params);
            List<Object[]> ret2 = rawResults.getResults();
            if(ret2 != null) {
                ret.addAll(ret2);
            }
        } finally {
            if (rawResults != null) {
                rawResults.close();
            }
        }
        return ret;
    }
    
    public void close(){
    	getHelper().close();
    }
}
