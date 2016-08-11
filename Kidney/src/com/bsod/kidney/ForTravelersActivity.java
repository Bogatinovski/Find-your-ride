package com.bsod.kidney;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bsod.kidney.extra.DetaliPatuvanje;
import com.bsod.kidney.extra.GetDestinationIds;
import com.bsod.kidney.extra.Patuvanje;
import com.bsod.kidney.extra.SendDestinationIds;

public class ForTravelersActivity extends FragmentActivity {

	private static final String DEBUG_TAG = "ForTravelersActivity";
	
	private static final String[] Gradovi = new String[] { "Skopje", "Bitola",
			"Veles", "Stip", "Ohrid", "Strumica", "Tetovo", "Gostivar" };
	
	private AutoCompleteTextView OdAutoCompleteText, DoAutoCompleteText;
	private ListView listaPatuvanja;
	private ImageView prati;
	public static ArrayList<String> destinations;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_for_travelers);
		
		GetDestinationIds getDesIds = new GetDestinationIds();
		String reaspone = getDesIds.GetDestinationIds_function("http://192.168.1.129/kidney/public/scripts/get_all_destinations.php");
		
		destinations = getDesIds.parshDestinationIds(reaspone);
		
		prati = (ImageView) findViewById(R.id.traveler_send_button);
		
		OdAutoCompleteText = (AutoCompleteTextView) findViewById(R.id.traveler_doEditText);
		DoAutoCompleteText = (AutoCompleteTextView) findViewById(R.id.traveler_odEditText);

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_dropdown_item_1line, Gradovi);
		OdAutoCompleteText.setAdapter(adapter);
		DoAutoCompleteText.setAdapter(adapter);

		listaPatuvanja = (ListView) findViewById(R.id.traveler_ListView);
		final ArrayList<Patuvanje> lista = new ArrayList<Patuvanje>();
	
		prati.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				SendDestinationIds sendDesIds = new SendDestinationIds();
				String from_ = OdAutoCompleteText.getText().toString();
				String to_ = DoAutoCompleteText.getText().toString();
				String parm1 = ""; String parm2 = "";
				for(int i = 0; i < destinations.size(); i=i+2){
					 if(from_.equals(destinations.get(i))){
						 parm1 = destinations.get(i+1);
						 break;
					 }
				}
				for(int i = 0; i < destinations.size(); i=i+2){
					 if(to_.equals(destinations.get(i))){
						 parm2 = destinations.get(i+1);
						 break;
					 }
				}
				
				String reaspone = sendDesIds.SendDestinationIds_function(
						"http://192.168.1.129/kidney/public/scripts/get_trip.php", 
						parm1, 
						parm2);
				
				ArrayList<String> list = sendDesIds.parshDestinationIds(reaspone);
				
				for(int i = 0; i < list.size(); i=i+7){
					Patuvanje nekoj_put = new Patuvanje();
					nekoj_put.setGradDo(list.get(i));
					nekoj_put.setGradOd(list.get(i+1));
					nekoj_put.setSlobodniMesta(Integer.parseInt(list.get(i+2)));
					Date d = new Date();
					nekoj_put.setDataPatuvanje(d);
					nekoj_put.setTelefon(list.get(i+4));
					nekoj_put.setCenaPatuvanje(Integer.parseInt(list.get(i+5)));
					nekoj_put.setImeVozac(list.get(i+6));
					
					lista.add(nekoj_put);
					
					PatuvanjeAdapter patuvanjeAdapter = new PatuvanjeAdapter(lista);
					listaPatuvanja.setAdapter(patuvanjeAdapter);
				}			
			}
		});
		
		listaPatuvanja.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				FragmentManager fm = getSupportFragmentManager();
				DetaliPatuvanje dialog = DetaliPatuvanje.newInstance(lista
						.get(position));
				dialog.show(fm, "DETALI_DIALOG");
			}
		});
		
	}

	private class PatuvanjeAdapter extends ArrayAdapter<Patuvanje> {

		public PatuvanjeAdapter(ArrayList<Patuvanje> lista) {
			super(getBaseContext(), android.R.layout.simple_list_item_1, lista);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// if we weren't given a view, inflate one
			if (null == convertView) {
				convertView = getLayoutInflater().inflate(
						R.layout.patuvanja_lista, null);
			}
			Patuvanje c = getItem(position);
			// configure the view for this Crime
			TextView Od = (TextView) convertView
					.findViewById(R.id.patuvanja_Od);
			TextView Do = (TextView) convertView
					.findViewById(R.id.patuvanja_Do);
			TextView Vreme = (TextView) convertView
					.findViewById(R.id.patuvanja_Cena);
			TextView Cena = (TextView) convertView
					.findViewById(R.id.patuvanja_Vreme);
			Od.setText("Од: " + c.getGradOd());
			Do.setText("До: " + c.getGradDo());
			Cena.setText(String.valueOf(c.getCenaPatuvanje()) + " денари");
			Vreme.setText(String.valueOf(c.getDataPatuvanje()));

			return convertView;
		}

		@SuppressWarnings("unused")
		public ArrayList<Patuvanje> patuvanjaOdJson(InputStream jsonOdServer)
				throws IOException, JSONException {
			ArrayList<Patuvanje> patuvanja = new ArrayList<Patuvanje>();
			BufferedReader reader = null;
			try {
				// open and read the file into a StringBuilder
				reader = new BufferedReader(new InputStreamReader(jsonOdServer));
				StringBuilder jsonString = new StringBuilder();
				String line = null;
				while ((line = reader.readLine()) != null) {
					// line breaks are omitted and irrelevant
					jsonString.append(line);
				}
				// parse the JSON using JSONTokener
				JSONArray array = (JSONArray) new JSONTokener(
						jsonString.toString()).nextValue();
				// build the array of crimes from JSONObjects
				for (int i = 0; i < array.length(); i++) {
					patuvanja.add(new Patuvanje(array.getJSONObject(i)));
				}
			} catch (FileNotFoundException e) {
				// we will ignore this one, since it happens when we start fresh
			} finally {
				if (reader != null)
					reader.close();
			}
			return patuvanja;
		}
	}

}
