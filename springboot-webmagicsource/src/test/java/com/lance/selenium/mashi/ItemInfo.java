package com.lance.selenium.mashi;

public class ItemInfo {
	private int uuid;
	private String itemName ;
	private String about;
	private int price;
	private String category;
	private String itemUrl;
	private String itemState;
	private String timeLimit;
	private int applyNum;
	public ItemInfo() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ItemInfo(int uuid, String projectName, String about, int price,
			String category, String itemUrl, String itemState,
			String timeLimit, int applyNum) {
		super();
		this.uuid = uuid;
		this.itemName = projectName;
		this.about = about;
		this.price = price;
		this.category = category;
		this.itemUrl = itemUrl;
		this.itemState = itemState;
		this.timeLimit = timeLimit;
		this.applyNum = applyNum;
	}
	public int getUuid() {
		return uuid;
	}
	public void setUuid(int uuid) {
		this.uuid = uuid;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getAbout() {
		return about;
	}
	public void setAbout(String about) {
		this.about = about;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getItemUrl() {
		return itemUrl;
	}
	public void setItemUrl(String itemUrl) {
		this.itemUrl = itemUrl;
	}
	public String getItemState() {
		return itemState;
	}
	public void setItemState(String itemState) {
		this.itemState = itemState;
	}
	public String getTimeLimit() {
		return timeLimit;
	}
	public void setTimeLimit(String timeLimit) {
		this.timeLimit = timeLimit;
	}
	public int getApplyNum() {
		return applyNum;
	}
	public void setApplyNum(int applyNum) {
		this.applyNum = applyNum;
	}
	@Override
	public String toString() {
		return "ItemInfo [uuid=" + uuid + ", itemName=" + itemName
				+ ", about=" + about + ", price=" + price + ", category="
				+ category + ", itemUrl=" + itemUrl + ", itemState="
				+ itemState + ", timeLimit=" + timeLimit + ", applyNum="
				+ applyNum + "]";
	}
	
	
	
	
}
