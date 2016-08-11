package com.bsod.kidney.extra;

import java.io.Serializable;
import java.util.Date;
import org.json.JSONException;
import org.json.JSONObject;

public class Patuvanje {
	
	private static final String JSON_OD = "od";
	private static final String JSON_DO = "do";
	private static final String JSON_CENA = "cena";
	private static final String JSON_SLOBODNI_MESTA = "slobodni_mesta";
	private static final String JSON_IME = "ime";
	private static final String JSON_DATA = "data";
	private static final String JSON_TELEFON = "telefon";
	private static final String JSON_ID = "id";
	
	
	private String gradOd;
	private String gradDo;
	private int cenaPatuvanje;
	private int slobodniMesta;
	private String imeVozac;
	private Date dataPatuvanje;
	private String telefon;
	private String id;
	
	
	public Patuvanje(JSONObject json) throws JSONException {
		gradOd = json.getString(JSON_OD);
		gradDo = json.getString(JSON_DO);
		cenaPatuvanje = json.getInt(JSON_CENA);
		slobodniMesta = json.getInt(JSON_SLOBODNI_MESTA);
		imeVozac = json.getString(JSON_IME);
		telefon = json.getString(JSON_TELEFON);
		dataPatuvanje = new Date(json.getLong(JSON_DATA));
		id = json.getString(JSON_ID);
	}
	
	public Patuvanje() {
		// TODO Auto-generated constructor stub
	}


	public JSONObject toJSON() throws JSONException {
		JSONObject json = new JSONObject();
		json.put(JSON_OD, gradOd);
		json.put(JSON_DO, gradDo);
		json.put(JSON_CENA, cenaPatuvanje);
		json.put(JSON_SLOBODNI_MESTA, slobodniMesta);
		json.put(JSON_IME, imeVozac);
		json.put(JSON_TELEFON, telefon);
		json.put(JSON_DATA, dataPatuvanje.getTime());
		json.put(JSON_ID, id);
		return json;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String asd) {
		this.id = asd;
	}
	
	public String getGradOd() {
		return gradOd;
	}

	public void setGradOd(String gradOd) {
		this.gradOd = gradOd;
	}

	public String getGradDo() {
		return gradDo;
	}

	public void setGradDo(String gradDo) {
		this.gradDo = gradDo;
	}

	public int getCenaPatuvanje() {
		return cenaPatuvanje;
	}

	public void setCenaPatuvanje(int cenaPatuvanje) {
		this.cenaPatuvanje = cenaPatuvanje;
	}

	public int getSlobodniMesta() {
		return slobodniMesta;
	}

	public void setSlobodniMesta(int slobodniMesta) {
		this.slobodniMesta = slobodniMesta;
	}

	public String getImeVozac() {
		return imeVozac;
	}

	public void setImeVozac(String imeVozac) {
		this.imeVozac = imeVozac;
	}

	public Date getDataPatuvanje() {
		return dataPatuvanje;
	}

	public void setDataPatuvanje(Date dataPatuvanje) {
		this.dataPatuvanje = dataPatuvanje;
	}

	public String getTelefon() {
		return telefon;
	}

	public void setTelefon(String telefon) {
		this.telefon = telefon;
	}
	
	

}
