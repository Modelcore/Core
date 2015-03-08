package com.outlook.brunox64.dao;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.outlook.brunox64.model.WorksheetEntity;

public class WorksheetDAO {

	private WorksheetEntity worksheet;
	
	public WorksheetDAO(WorksheetEntity worksheet) {
		this.worksheet = worksheet;
	}
	
	public void load() {
		MongoClient mongoClient = new MongoClient();
		MongoDatabase db = mongoClient.getDatabase("local");
		MongoCollection<Document> mColRanges = db.getCollection("ranges");
		if (mColRanges != null) {
			Bson filter = Filters.and(Filters.eq("spreadsheet", worksheet.getSpreadsheetId()), Filters.eq("worksheet", worksheet.getId()));
			Document worksheetDoc = mColRanges.find(filter).first();
			worksheet.setRanges(worksheetDoc.getString("ranges"));
		}
		mongoClient.close();
	}
	
	public void save() {
		MongoClient mongoClient = new MongoClient();
		MongoDatabase db = mongoClient.getDatabase("local");
		MongoCollection<Document> mColRanges = db.getCollection("ranges");
		if (mColRanges == null) db.createCollection("ranges");
		Document worksheetDoc = new Document();
		worksheetDoc.put("worksheet", worksheet.getId());
		worksheetDoc.put("spreadsheet", worksheet.getSpreadsheetId());
		worksheetDoc.put("ranges", worksheet.getRanges());
		mColRanges.insertOne(worksheetDoc);
		mongoClient.close();
	}
	
}
