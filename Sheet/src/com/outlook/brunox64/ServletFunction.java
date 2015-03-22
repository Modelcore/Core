package com.outlook.brunox64;

import java.io.IOException;
import java.io.Writer;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/ServletFunction")
public class ServletFunction extends HttpServlet {

	private static final long serialVersionUID = 1l;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String nomeRel = req.getParameter("nomerel");
		if ("Parceiro".equals(nomeRel)) {
			JsonObject obj = Json.createObjectBuilder()
					.add("NOMEPARC", "Bruno Moreira Mota razao")
					.add("DESCRICAO", "Bruno Moreira Mota")
					.add("CNPJ", "11.111.1111/111-11").build();
			JsonObject obj2 = Json.createObjectBuilder()
					.add("NOMEPARC", "Bruno Moreira Mota razao2")
					.add("DESCRICAO", "Bruno Moreira Mota2")
					.add("CNPJ", "11.111.1111/111-11").build();
			JsonObject obj3 = Json.createObjectBuilder()
					.add("NOMEPARC", "Bruno Moreira Mota razao3")
					.add("DESCRICAO", "Bruno Moreira Mota3")
					.add("CNPJ", "11.111.1111/111-11").build();
			JsonObject obj4 = Json.createObjectBuilder()
					.add("NOMEPARC", "Bruno Moreira Mota razao4")
					.add("DESCRICAO", "Bruno Moreira Mota4")
					.add("CNPJ", "11.111.1111/111-11").build();
			JsonArray entities = Json.createArrayBuilder()
					.add(obj)
					.add(obj2).add(obj3).add(obj4).build();
			JsonArray metadata = Json.createArrayBuilder()
					.add("NOMEPARC")
					.add("DESCRICAO")
					.add("CNPJ")
					.build();
			JsonObject response = Json.createObjectBuilder()
					.add("entidades", entities)
					.add("metadata", metadata)
					.build();
			Writer wr = resp.getWriter();
			wr.append(response.toString());
			wr.close();
		}
	}
}
