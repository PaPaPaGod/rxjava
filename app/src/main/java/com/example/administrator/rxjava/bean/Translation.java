package com.example.administrator.rxjava.bean;

import android.util.Log;

public class Translation {
	private int status;
	private Content content;
	
	public Content getContent() {
		return content;
	}

	public void setContent(Content content) {
		this.content = content;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
	public void show(){
		Log.e("Rxjava-Translation",content.getOut());
	}
}
