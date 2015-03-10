package com.outlook.brunox64;

import java.io.IOException;
import java.net.URL;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gdata.client.spreadsheet.SpreadsheetQuery;
import com.google.gdata.client.spreadsheet.SpreadsheetService;
import com.google.gdata.data.spreadsheet.CellEntry;
import com.google.gdata.data.spreadsheet.CellFeed;
import com.google.gdata.data.spreadsheet.SpreadsheetEntry;
import com.google.gdata.data.spreadsheet.SpreadsheetFeed;
import com.google.gdata.data.spreadsheet.WorksheetEntry;
import com.google.gdata.data.spreadsheet.WorksheetFeed;
import com.outlook.brunox64.dao.SpreadsheetAppDAO;
import com.outlook.brunox64.dao.WorksheetDAO;
import com.outlook.brunox64.model.WorksheetEntity;

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
		
		int spreadsheet = params.getInt("spreadsheet");
		int worksheet = params.getInt("worksheet");
		String operation = params.getString("operation");
		
		WorksheetEntity worksheetEntity = new WorksheetEntity(spreadsheet, worksheet);
		SpreadsheetAppDAO spreadsheetAppDAO = new SpreadsheetAppDAO(worksheetEntity);
		WorksheetDAO worksheetDAO = new WorksheetDAO(worksheetEntity);
		
		if ("save".equals(operation)) {
			spreadsheetAppDAO.load();
			worksheetDAO.save();
		} else if ("load".equals(operation)) {
			worksheetDAO.load();
			spreadsheetAppDAO.save();
		}
	}
	
	
	public static void main(String[] args) throws Exception {
		Main m = new Main();
		m.load();
	}
	
	private void clearCollection() throws Exception {
		
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
		
		SpreadsheetQuery queryWorksheet = new SpreadsheetQuery(entry.getWorksheetFeedUrl());
		queryWorksheet.setTitleQuery("Pagina1");
		queryWorksheet.setTitleExact(true);
		
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
	}
	
	private void save(String a1Annotation) {
		
	}

}
