package its.webservice.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * ITS common page turning bean<br>
 * 翻页用
 * 
 * @author SUPCON
 * @version $Revision$ $Date$
 */

public class PageInformation implements Serializable {

	private static final long serialVersionUID = 330088777001L;

	/**
	 * Current page number
	 */
	private int currentPageNo;

	/**
	 * Get it form ItsConstants.java This is max lines which shows in
	 * one page
	 */
	private int maxDisplayRowCount;

	/**
	 * Get it form ItsConstants.java This is max numbers of page links
	 * which shows in listdata page
	 */
	private int maxDisplayPageNumbers;

	/**
	 * Select count(1) from table
	 */
	private int totalCount;

	/**
	 * currentRowCount = resultList.size();
	 */
	private int currentRowCount;

	// *****************************************************************************
	// END
	// *****************************************************************************

	// Added by Xujin.Jiao on 11-20-2008
	/**
	 * 一行HTML TR里显示多少条查询结果集记录,默认为1<br>
	 * 在setMaxDisplayRowCount()之前赋值
	 */
	private int recordCountsOfOneTrRow = 1;

	/**
	 * total pages
	 */
	private int totalPageCount = 0;

    /**
     * 序号基数 
     */
    private int baseRownum = 0;

	/**
	 * current page number list
	 */
	private List currentPageNumberList;
	
    /**
     * 构造函数
     */
	public PageInformation () {
		
	}

    /**
     * 构造函数
     */
	public PageInformation (boolean constructor) {
		if (constructor) {
	        this.setMaxDisplayPageNumbers(10);
	        this.setMaxDisplayRowCount(10);
	        this.setCurrentPageNo("1");
	        this.setTotalCount(1);
	        this.setCurrentRowCount(1);
		}
	}

    /**
     * 构造函数
     * 
     * @param maxdisplayrowcount Device_ID个数
     */
	public PageInformation (int maxdisplayrowcount) {
        this.setMaxDisplayPageNumbers(10);
        this.setMaxDisplayRowCount(maxdisplayrowcount);
        this.setCurrentPageNo("1");
        this.setTotalCount(maxdisplayrowcount);
        this.setCurrentRowCount(maxdisplayrowcount);
	}

	/**
	 * @param currentPageNumberList
	 *            the currentPageNumberList to set
	 */
	public void setCurrentPageNumberList(List currentPageNumberList) {
		this.currentPageNumberList = currentPageNumberList;
	}

	/**
	 * @param totalPageCount
	 *            the totalPageCount to set
	 */
	public void setTotalPageCount(int totalPageCount) {
		this.totalPageCount = totalPageCount;
	}

	/**
	 * @return the currentPageNo
	 */
	public int getCurrentPageNo() {
		return currentPageNo;
	}

	/**
	 * @param currentPageNo
	 *            the currentPageNo to set
	 */
	public void setCurrentPageNo(String currentPageNo) {
		try {
		    this.currentPageNo = Integer.parseInt(currentPageNo);
		} catch (Exception e) {
			this.currentPageNo = 1;
		}
	}

	/**
	 * @return the currentRowCount
	 */
	public int getCurrentRowCount() {
		return currentRowCount;
	}

	/**
	 * @param currentRowCount
	 *            the currentRowCount to set
	 */
	public void setCurrentRowCount(int currentRowCount) {
		this.currentRowCount = currentRowCount;
        setBaseRownum((currentPageNo - 1) * maxDisplayRowCount + 1);
	}

	/**
	 * @return the hasNext
	 */
	public boolean getHasNext() {
		boolean hasNext = false;
		int currentBiggestPageNo = 0;

		List currentPageNoList = getCurrentPageNumberList();
		Iterator currentPageNoIterator = currentPageNoList.iterator();
		while (currentPageNoIterator.hasNext()) {
			currentBiggestPageNo = Integer.parseInt(currentPageNoIterator
					.next().toString());
		}

		if (getTotalPageCount() > currentBiggestPageNo) {
			return true;
		}
		return hasNext;
	}

	/**
	 * @return the hasPrevious
	 */
	public boolean getHasPrevious() {
		boolean hasPrevious = false;
		int currentSmallestPageNo = 0;

		List currentPageNoList = getCurrentPageNumberList();
		Iterator currentPageNoIterator = currentPageNoList.iterator();
		while (currentPageNoIterator.hasNext()) {
			currentSmallestPageNo = Integer.parseInt(currentPageNoIterator
					.next().toString());
			break;
		}

		if (currentSmallestPageNo > 1) {
			return true;
		}
		return hasPrevious;
	}

	/**
	 * @return the maxDisplayRowCount
	 */
	public int getMaxDisplayRowCount() {
		return maxDisplayRowCount;
	}

	/**
	 * @param maxDisplayRowCount
	 *            the maxDisplayRowCount to set
	 */
	public void setMaxDisplayRowCount(int maxDisplayRowCount) {
		this.maxDisplayRowCount = maxDisplayRowCount * getRecordCountsOfOneTrRow();
	}

	/**
	 * @return the totalCount
	 */
	public int getTotalCount() {
		return totalCount;
	}

	/**
	 * @param totalCount
	 *            the totalCount to set
	 */
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	/**
	 * @return the totalPageCount
	 */
	public int getTotalPageCount() {
		if (this.totalPageCount == 0) {
			int totalPages = 0;
			if (this.totalCount % this.maxDisplayRowCount > 0) {
				totalPages = this.totalCount / this.maxDisplayRowCount + 1;
			} else {
				totalPages = this.totalCount / this.maxDisplayRowCount;
			}
			setTotalPageCount(totalPages);
		}
		return this.totalPageCount;
	}

	/**
	 * @return the maxDisplayPageNumbers
	 */
	public int getMaxDisplayPageNumbers() {
		return maxDisplayPageNumbers;
	}

	/**
	 * @param maxDisplayPageNumbers
	 *            the maxDisplayPageNumbers to set
	 */
	public void setMaxDisplayPageNumbers(int maxDisplayPageNumbers) {
		this.maxDisplayPageNumbers = maxDisplayPageNumbers;
	}

	/**
	 * @return the current Display Page Number List
	 */
	public List getCurrentPageNumberList() {
		if (this.currentPageNumberList == null) {
			int currentDisplayPageNumbers = 0;
			List<String> pageLinkList = new ArrayList<String>();

			if (getTotalPageCount() % this.maxDisplayPageNumbers == 0) {
				currentDisplayPageNumbers = this.maxDisplayPageNumbers;
			} else if (isEndPageGroup()) {
				currentDisplayPageNumbers = getTotalPageCount()
						% this.maxDisplayPageNumbers;
			} else {
				currentDisplayPageNumbers = this.maxDisplayPageNumbers;
			}

			for (int i = 1; i < currentDisplayPageNumbers + 1; i++) {
				pageLinkList.add(String.valueOf(i
						+ ((int) Math.ceil(Float.parseFloat(String
								.valueOf(this.currentPageNo))
								/ this.maxDisplayPageNumbers) - 1)
						* this.maxDisplayPageNumbers));
			}
			setCurrentPageNumberList(pageLinkList);
		}
		return this.currentPageNumberList;
	}

	/**
	 * @return the current page's stauts whether is end page
	 */
	public boolean isEndPage() {
		int biggerTotalLineCount = (int) (Math.ceil(Float.parseFloat(String
				.valueOf(getTotalCount()))
				/ this.maxDisplayRowCount))
				* this.maxDisplayRowCount;
		if (biggerTotalLineCount == this.currentPageNo
				* getMaxDisplayRowCount()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * @return the current pagegroup's stauts whether is end pagegroup
	 */
	public boolean isEndPageGroup() {
		int biggerTotalPageCount = (int) (Math.ceil(Float.parseFloat(String
				.valueOf(getTotalPageCount()))
				/ this.maxDisplayPageNumbers))
				* this.maxDisplayPageNumbers;
		if (biggerTotalPageCount - this.currentPageNo < this.maxDisplayPageNumbers) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * @return the start position which in the ResultSet
	 */
	public int getStartPosition() {
		return (this.currentPageNo - 1) * getMaxDisplayRowCount() + 1;
	}

	/**
	 * @return the end position which in the ResultSet
	 */
	public int getEndPosition() {
		if (isEndPage()) {
			if (getTotalCount() % getMaxDisplayRowCount() != 0) {
				return (this.currentPageNo - 1) * getMaxDisplayRowCount()
						+ getTotalCount() % getMaxDisplayRowCount();
			} else {
				return this.currentPageNo * getMaxDisplayRowCount();
			}
		} else {
			return this.currentPageNo * getMaxDisplayRowCount();
		}
	}

	/**
	 * @return Previous page number
	 */
	public String getPreviousPageNumber() {
		if (this.currentPageNo % this.maxDisplayPageNumbers == 0) {
			return String.valueOf(this.currentPageNo - this.currentPageNo
					% this.maxDisplayPageNumbers - this.maxDisplayPageNumbers);
		} else {
			return String.valueOf(this.currentPageNo - this.currentPageNo
					% this.maxDisplayPageNumbers);
		}
	}

	/**
	 * @return Next page number
	 */
	public String getNextPageNumber() {
		return String.valueOf((int) (Math.ceil(Float.parseFloat(String
				.valueOf(this.currentPageNo))
				/ this.maxDisplayPageNumbers))
				* this.maxDisplayPageNumbers + 1);
	}

	/**
	 * @return link style string
	 */
	public String getLinkStyle(String linkNumberStr) {
		if (linkNumberStr.equals(String.valueOf(this.currentPageNo))) {
			return "text-decoration:none; color:#6699CC";
		} else {
			return "";
		}
	}

    /**
     * @return the baseRownum
     */
    public int getBaseRownum() {
        // (currentPageNo - 1) * maxDisplayRowCount + 1;
        return baseRownum;
    }

    /**
     * @param baseRownum the baseRownum to set
     */
    public void setBaseRownum(int baseRownum) {
        this.baseRownum = baseRownum;
    }

	/**
	 * @return the recordCountsOfOneTrRow
	 */
	public int getRecordCountsOfOneTrRow() {
		return recordCountsOfOneTrRow;
	}

	/**
	 * @param recordCountsOfOneTrRow the recordCountsOfOneTrRow to set
	 */
	public void setRecordCountsOfOneTrRow(int recordCountsOfOneTrRow) {
		this.recordCountsOfOneTrRow = recordCountsOfOneTrRow;
	}

}
