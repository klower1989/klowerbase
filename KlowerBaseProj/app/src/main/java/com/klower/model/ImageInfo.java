package com.klower.model;

public class ImageInfo {
	String image_url;

	int image_width;

	int image_height;

	String thumbnail_url;

	String abs;

	String desc;

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getAbs() {
		return abs;
	}

	public void setAbs(String abs) {
		this.abs = abs;
	}

	public String getImage_url() {
		return image_url;
	}

	public void setImage_url(String image_url) {
		this.image_url = image_url;
	}

	public int getImage_width() {
		return image_width;
	}

	public void setImage_width(int image_width) {
		this.image_width = image_width;
	}

	public int getImage_height() {
		return image_height;
	}

	public void setImage_height(int image_height) {
		this.image_height = image_height;
	}

	public String getThumbnail_url() {
		return thumbnail_url;
	}

	public void setThumbnail_url(String thumbnail_url) {
		this.thumbnail_url = thumbnail_url;
	}

}
