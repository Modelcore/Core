package com.outlook.brunox64;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bson.Document;

import com.google.gdata.client.spreadsheet.SpreadsheetQuery;
import com.google.gdata.client.spreadsheet.SpreadsheetService;
import com.google.gdata.data.spreadsheet.SpreadsheetEntry;
import com.google.gdata.data.spreadsheet.SpreadsheetFeed;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

/**
 * Servlet implementation class Main
 */
@WebServlet("/Main")
public class Main extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Main() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
//		String sheet = req.getParameter("sheet");
//		MongoClient mongoClient = new MongoClient();
//		MongoDatabase db = mongoClient.getDatabase("local");
//		MongoCollection<Document> mColRanges = db.getCollection("ranges");
//		if (mColRanges == null) db.createCollection("ranges");
//		Document sheetDoc = mColRanges.find(Filters.eq("sheet", sheet)).first();
//		String ranges = sheetDoc.getString("ranges");
//		mongoClient.close();
//		PrintWriter wr = res.getWriter();
//		wr.append(ranges);
//		wr.close();
//		System.out.println(ranges);
		try {
			load();
		} catch (Exception exc) {
			exc.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		JsonReader jsonReader = Json.createReader(req.getInputStream());
		JsonObject params = jsonReader.readObject();
		String sheet = params.getString("sheet");
		JsonArray rangesArr = params.getJsonArray("ranges");
		
		MongoClient mongoClient = new MongoClient();
		MongoDatabase db = mongoClient.getDatabase("local");
		MongoCollection<Document> mColRanges = db.getCollection("ranges");
		if (mColRanges == null) db.createCollection("ranges");
		Document sheetDoc = new Document("sheet", sheet);
		sheetDoc.put("ranges", rangesArr.toString());
		mColRanges.insertOne(sheetDoc);
		mongoClient.close();
		PrintWriter wr = res.getWriter();
		wr.append("sucesso!!!");
		wr.close();
		System.out.println(rangesArr.toString());
	}
	
	
	public static void main(String[] args) throws Exception {
		Main m = new Main();
		m.load();
	}
	
	private void load() throws Exception {
//		String clientId = "724005170397-720qiupce61tfpem214phaqb2k79l2lu.apps.googleusercontent.com";
//		String clientSecret = "notasecret";
//		
//		String accountId = "724005170397-720qiupce61tfpem214phaqb2k79l2lu@developer.gserviceaccount.com";
//		
//		String accountUser = "brunx64@gmail.com";
//		
//		String filePrivateKey = "META-INF/ProjetoSheet-f0aa3177d268.p12";
//		String sheetScopeFull = "https://spreadsheets.google.com/feeds/spreadsheets/private/basic";
//		
//		HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
//		JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
//		
//		GoogleCredential credentials = new GoogleCredential.Builder()
//			.setServiceAccountId(accountId)
//			.setServiceAccountPrivateKeyFromP12File(new File(filePrivateKey))
//			.setServiceAccountUser(accountUser)
//			.setServiceAccountScopes(Collections.singleton(sheetScopeFull))
//			.setClientSecrets(clientId, clientSecret)
//			.setTransport(httpTransport)
//			.setJsonFactory(jsonFactory)
//		
//			.build();
//		
		
		SpreadsheetService service = new SpreadsheetService("ProjetoSheet");
		//service.setOAuth2Credentials(credentials);
		service.setUserCredentials("brunowsws@gmail.com", "senhadaplanilha");
		
		URL SPREADSHEET_FEED_URL = new URL("https://spreadsheets.google.com/feeds/spreadsheets/private/full");

		SpreadsheetQuery query = new SpreadsheetQuery(SPREADSHEET_FEED_URL);
		query.setTitleQuery("Planilha01");
		query.setTitleExact(true);
		SpreadsheetFeed feed = service.getFeed(query, SpreadsheetFeed.class);
		SpreadsheetEntry entry = feed.getEntries().get(0);
		
		
	}
	
	private void save(String a1Annotation) {
		
	}

}
