package com.outlook.brunox64.model;


public class WorksheetEntity {
	
	private int id;
	private int spreadsheetId;
	private String ranges;
	
	public WorksheetEntity(int spreadsheetId, int worksheetId) {
		this.id = worksheetId;
		this.spreadsheetId = spreadsheetId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRanges() {
		return ranges;
	}

	public void setRanges(String ranges) {
		this.ranges = ranges;
	}

	public int getSpreadsheetId() {
		return spreadsheetId;
	}

	public void setSpreadsheetId(int spreadsheetId) {
		this.spreadsheetId = spreadsheetId;
	}
	
	
}
