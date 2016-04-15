package com.klower.db;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author
 * 
 */
public class FilterCondition {

	public enum MODE {
		EQ, LIKE, LE, LT, GE, GT, IN, BETWEEN, RAW
	}

	public enum DIRECTION {
		DESC, ASC
	}

	public static class SortCondition {
		private DIRECTION direction;
		private String columnName;
		private String rawSql;

		private SortCondition() {}
		
		private SortCondition(DIRECTION direction, String columnName) {
			this.direction = direction;
			this.columnName = columnName;
		}

		public static SortCondition getInstance(String columnName) {
			return new SortCondition(DIRECTION.ASC, columnName);
		}

		public static SortCondition getInstance(DIRECTION direction, String columnName) {
			return new SortCondition(direction, columnName);
		}

		public static SortCondition getRawInstance(String rawSql) {
			SortCondition ret = new SortCondition();
			ret.setRawSql(rawSql);
			return ret;
		}

		public DIRECTION getDirection() {
			return direction;
		}

		public void setDirection(DIRECTION direction) {
			this.direction = direction;
		}

		public String getColumnName() {
			return columnName;
		}

		public void setColumnName(String columnName) {
			this.columnName = columnName;
		}

		public String getRawSql() {
			return rawSql;
		}

		public void setRawSql(String rawSql) {
			this.rawSql = rawSql;
		}
	}

	/**
	 * 
	 * @author
	 * 
	 */
	public static class WhereCondition {
		private MODE mode;

		private String columnName;
		private Object columnValue;
		private Object columnValue2;

		public static WhereCondition newIntance(MODE mode, String columnName,
				Object columnValue) {
			WhereCondition ret = new WhereCondition();

			ret.setMode(mode);
			ret.setColumnName(columnName);
			ret.setColumnValue(columnValue);

			return ret;
		}

		public static WhereCondition newEqIntance(String columnName, Object columnValue) {
			WhereCondition ret = new WhereCondition();

			ret.setMode(MODE.EQ);
			ret.setColumnName(columnName);
			ret.setColumnValue(columnValue);

			return ret;
		}

		public static WhereCondition newLeIntance(String columnName, Object columnValue) {
			WhereCondition ret = new WhereCondition();

			ret.setMode(MODE.LE);
			ret.setColumnName(columnName);
			ret.setColumnValue(columnValue);

			return ret;
		}

        public static WhereCondition newLtIntance(String columnName, Object columnValue) {
            WhereCondition ret = new WhereCondition();

            ret.setMode(MODE.LT);
            ret.setColumnName(columnName);
            ret.setColumnValue(columnValue);

            return ret;
        }

        public static WhereCondition newRawIntance(String columnName) {
            WhereCondition ret = new WhereCondition();

            ret.setMode(MODE.RAW);
            ret.setColumnName(columnName);

            return ret;
        }

		public static WhereCondition newGeIntance(String columnName, Object columnValue) {
			WhereCondition ret = new WhereCondition();

			ret.setMode(MODE.GE);
			ret.setColumnName(columnName);
			ret.setColumnValue(columnValue);

			return ret;
		}

		public static WhereCondition newGtIntance(String columnName,
				Object columnValue) {
			WhereCondition ret = new WhereCondition();

			ret.setMode(MODE.GT);
			ret.setColumnName(columnName);
			ret.setColumnValue(columnValue);

			return ret;
		}

		public static WhereCondition newLikeIntance(String columnName,
				String columnValue) {
			WhereCondition ret = new WhereCondition();

			ret.setMode(MODE.LIKE);
			ret.setColumnName(columnName);
			ret.setColumnValue(columnValue);

			return ret;
		}

		public static WhereCondition newInIntance(String columnName,
				List<Object> columnValues) {
			WhereCondition ret = new WhereCondition();

			ret.setMode(MODE.IN);
			ret.setColumnName(columnName);
			ret.setColumnValue(columnValues);

			return ret;
		}

		public static WhereCondition newBetweenIntance(String columnName,
				Object columnValue, Object columnValue2) {
			WhereCondition ret = new WhereCondition();

			ret.setMode(MODE.BETWEEN);
			ret.setColumnName(columnName);
			ret.setColumnValue(columnValue);
			ret.setColumnValue2(columnValue2);

			return ret;
		}

		public MODE getMode() {
			return mode;
		}

		public void setMode(MODE mode) {
			this.mode = mode;
		}

		public String getColumnName() {
			return columnName;
		}

		public void setColumnName(String columnName) {
			this.columnName = columnName;
		}

		public Object getColumnValue() {
			return columnValue;
		}

		public void setColumnValue(Object columnValue) {
			this.columnValue = columnValue;
		}

		public Object getColumnValue2() {
			return columnValue2;
		}

		public void setColumnValue2(Object columnValue2) {
			this.columnValue2 = columnValue2;
		}

	}

	private List<SortCondition> sorts;

	private List<WhereCondition> conditions;

	private List<String> selColumns;

	private List<String> rawColumns;

	private Long limit;

	private Integer pageIndex;
	
	private List<String> groupBys;

	public FilterCondition() {

	}

	public FilterCondition(List<WhereCondition> conditions) {
		this.conditions = conditions;
	}
	
	public FilterCondition addSort(SortCondition sortCon) {
		if(sorts == null) {
			sorts = new ArrayList<SortCondition>();
		}
		sorts.add(sortCon);
		return this;
	}

	public FilterCondition addGroupBy(String groupBy) {
		if(groupBys == null) {
			groupBys = new ArrayList<String>();
		}
		groupBys.add(groupBy);
		return this;
	}
	public FilterCondition addCondition(WhereCondition condition) {
		if(conditions == null) {
			conditions = new ArrayList<WhereCondition>();
		}
		conditions.add(condition);
		return this;
	}
	public FilterCondition addColumn(String columnName) {
		if(selColumns == null) {
			selColumns = new ArrayList<String>();
		}
		selColumns.add(columnName);
		return this;
	}
	
	public List<SortCondition> getSorts() {
		return sorts;
	}

	public void setSorts(List<SortCondition> sorts) {
		this.sorts = sorts;
	}

	public List<WhereCondition> getConditions() {
		return conditions;
	}

	public void setConditions(List<WhereCondition> conditions) {
		this.conditions = conditions;
	}

	public Long getLimit() {
		return limit;
	}

	public void setLimit(Long limit) {
		this.limit = limit;
	}

	public Integer getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(Integer pageIndex) {
		this.pageIndex = pageIndex;
	}

	public List<String> getGroupBys() {
		return groupBys;
	}

	public void setGroupBys(List<String> groupBys) {
		this.groupBys = groupBys;
	}

	public List<String> getSelColumns() {
		return selColumns;
	}

	public void setSelColumns(List<String> selColumns) {
		this.selColumns = selColumns;
	}

	public List<String> getRawColumns() {
		return rawColumns;
	}

	public void setRawColumns(List<String> rawColumns) {
		this.rawColumns = rawColumns;
	}	
}
