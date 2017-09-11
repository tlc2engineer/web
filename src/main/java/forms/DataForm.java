package forms;

public class DataForm {
	
	boolean filter;
	boolean dateFilter;
	String lastMessage,firstMessage,prevMessage,nextMessage;
	String delBtn;
	public String getDelBtn() {
		return delBtn;
	}
	public void setDelBtn(String delBtn) {
		this.delBtn = delBtn;
	}
	public String getFirstMessage() {
		return firstMessage;
	}
	public void setFirstMessage(String firstMessage) {
		this.firstMessage = firstMessage;
	}
	public String getPrevMessage() {
		return prevMessage;
	}
	public void setPrevMessage(String prevMessage) {
		this.prevMessage = prevMessage;
	}
	public String getNextMessage() {
		return nextMessage;
	}
	public void setNextMessage(String nextMessage) {
		this.nextMessage = nextMessage;
	}
	public String getLastMessage() {
		return lastMessage;
	}
	public void setLastMessage(String lastMessage) {
		this.lastMessage = lastMessage;
	}
	public boolean isDateFilter() {
		return dateFilter;
	}
	public void setDateFilter(boolean dateFilter) {
		this.dateFilter = dateFilter;
	}
	String messDate;
	public String getMessDate() {
		return messDate;
	}
	public void setMessDate(String messDate) {
		this.messDate = messDate;
	}
	String brigade,place,object;
	int messPageCount;
	int currPage;
	String sort;
	boolean sortUp;
	
	public boolean isFilter() {
		return filter;
	}
	public void setFilter(boolean filter) {
		this.filter = filter;
	}
	public String getDate() {
		return messDate;
	}
	public void setDate(String date) {
		this.messDate = date;
	}
	public String getBrigade() {
		return brigade;
	}
	public void setBrigade(String brigade) {
		this.brigade = brigade;
	}
	public String getPlace() {
		return place;
	}
	public void setPlace(String place) {
		this.place = place;
	}
	public String getObject() {
		return object;
	}
	public void setObject(String object) {
		this.object = object;
	}
	public int getMessPageCount() {
		return messPageCount;
	}
	public void setMessPageCount(int messPageCount) {
		this.messPageCount = messPageCount;
	}
	public int getCurrPage() {
		return currPage;
	}
	public void setCurrPage(int currPage) {
		this.currPage = currPage;
	}
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	public boolean isSortUp() {
		return sortUp;
	}
	public void setSortUp(boolean sortUp) {
		this.sortUp = sortUp;
	}
	
	

}
