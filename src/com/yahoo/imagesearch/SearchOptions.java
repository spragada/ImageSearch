package com.yahoo.imagesearch;

import java.io.Serializable;

public class SearchOptions implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private String imageSize;
	private String imageType;
	private String imageColor;
	private String filter;
	
	public String getImageSize() {
		return imageSize;
	}
	public void setImageSize(String imageSize) {
		this.imageSize = imageSize;
	}
	public String getImageType() {
		return imageType;
	}
	public void setImageType(String imageType) {
		this.imageType = imageType;
	}
	public String getImageColor() {
		return imageColor;
	}
	public void setImageColor(String imageColor) {
		this.imageColor = imageColor;
	}
	public String getFilter() {
		return filter;
	}
	public void setFilter(String filter) {
		this.filter = filter;
	}
	
	public String toString()
	{
		return this.imageSize+" "+this.imageColor+" "+this.imageType+" "+this.filter;
	}

}
