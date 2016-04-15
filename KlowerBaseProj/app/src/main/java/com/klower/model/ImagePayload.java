package com.klower.model;

import java.util.ArrayList;

public class ImagePayload {
	int totalNum;

	int start_index;

	ArrayList<ImageInfo> data;

	public int getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(int totalNum) {
		this.totalNum = totalNum;
	}

	public int getStart_index() {
		return start_index;
	}

	public void setStart_index(int start_index) {
		this.start_index = start_index;
	}

	public ArrayList<ImageInfo> getData() {
		return data;
	}

	public void setData(ArrayList<ImageInfo> data) {
		this.data = data;
	}

}
