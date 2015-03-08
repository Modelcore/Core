package com.outlook.brunox64.dao;

import java.net.URL;

import com.google.gdata.client.spreadsheet.SpreadsheetQuery;
import com.google.gdata.client.spreadsheet.SpreadsheetService;
import com.google.gdata.data.spreadsheet.CellEntry;
import com.google.gdata.data.spreadsheet.CellFeed;
import com.google.gdata.data.spreadsheet.SpreadsheetEntry;
import com.google.gdata.data.spreadsheet.SpreadsheetFeed;
import com.google.gdata.data.spreadsheet.WorksheetEntry;
import com.google.gdata.data.spreadsheet.WorksheetFeed;
import com.outlook.brunox64.model.WorksheetEntity;

public class SpreadsheetAppDAO {
	
	private SpreadsheetService service;
	
	public SpreadsheetAppDAO() {
		service = new SpreadsheetService("ProjetoSheet");
		try {
			service.setUserCredentials("brunowsws@gmail.com", "senhadaplanilha");
		} catch (Exception exc) {
			exc.printStackTrace();
		}
		
	}
	
	public void load(WorksheetEntity worksheetEntity) {
		try {
			
			URL SPREADSHEET_FEED_URL = new URL("https://spreadsheets.google.com/feeds/spreadsheets/private/full");

			SpreadsheetQuery query = new SpreadsheetQuery(SPREADSHEET_FEED_URL);
			query.setIntegerCustomParameter("key", worksheetEntity.getSpreadsheetId());
			
			SpreadsheetFeed feed = service.getFeed(query, SpreadsheetFeed.class);
			
			SpreadsheetEntry entry = feed.getEntries().get(0);
			
			SpreadsheetQuery queryWorksheet = new SpreadsheetQuery(entry.getWorksheetFeedUrl());
			queryWorksheet.setIntegerCustomParameter("key", worksheetEntity.getId());
			
			WorksheetFeed feedWorksheet = service.getFeed(queryWorksheet, WorksheetFeed.class);
			
			WorksheetEntry worksheetEntry = feedWorksheet.getEntries().get(0);
			
			URL cellFeedUrl = worksheetEntry.getCellFeedUrl();
			
			CellFeed cellFeed = service.getFeed(cellFeedUrl, CellFeed.class);
			
			String cellName = null;
			String listNamesCellToEdit = "";
			for (int i = 1; i <= 5; i++) {
				listNamesCellToEdit += "A" + i + "B" + i + "C" + i;
			}
			for (CellEntry cellEntry : cellFeed.getEntries()) {
				cellName = cellEntry.getTitle().getPlainText();
				if (listNamesCellToEdit.indexOf(cellName) > -1) {
					
				}
			}
		} catch (Exception exc) {
			exc.printStackTrace(); // Tratar depois de alguma forma tipo arquivo de log ou outros.
		}
	}
	
	public void save(WorksheetEntity worksheetEntity) {
		
	}

}
