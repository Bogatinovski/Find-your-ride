package com.bsod.kidney.extra;

import com.bsod.kidney.R;
import com.bsod.kidney.R.layout;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class DetaliPatuvanje extends DialogFragment {

	private static final String OD = "od";
	private static final String DO = "do";
	private static final String CENA = "cena";
	private static final String DATA = "data";
	private static final String MESTA = "mesta";
	private static final String IME = "ime";
	private static final String TELEFON = "telefon";

	public static DetaliPatuvanje newInstance(Patuvanje patuvanje) {
		Bundle args = new Bundle();
		args.putSerializable(OD, patuvanje.getGradOd());
		args.putSerializable(DO, patuvanje.getGradDo());
		args.putSerializable(CENA, String.valueOf(patuvanje.getCenaPatuvanje()));
		args.putSerializable(DATA, String.valueOf(patuvanje.getDataPatuvanje()));
		args.putSerializable(MESTA, String.valueOf(patuvanje.getSlobodniMesta()));
		args.putSerializable(IME, patuvanje.getImeVozac());
		args.putSerializable(TELEFON, patuvanje.getTelefon());

		DetaliPatuvanje dialogFragment = new DetaliPatuvanje();
		dialogFragment.setArguments(args);

		return dialogFragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
		View v = inflater.inflate(R.layout.detali_dialog_fragment, container,
				false);

		String Od = (String) getArguments().getSerializable(OD);
		String Do = (String) getArguments().getSerializable(DO);
		String Cena = (String) getArguments().getSerializable(CENA);
		String Vreme = (String) getArguments().getSerializable(DATA);
		String Mesta = (String) getArguments().getSerializable(MESTA);
		String Ime = (String) getArguments().getSerializable(IME);
		String Telefon = (String)  getArguments().getSerializable(TELEFON);

		TextView textOd = (TextView) v.findViewById(R.id.textViewOdText);
		TextView textDo = (TextView) v.findViewById(R.id.textViewDoText);
		TextView textCena = (TextView) v.findViewById(R.id.textViewCenaText);
		TextView textVreme = (TextView) v
				.findViewById(R.id.textViewVremePoaganjeText);
		TextView textMesta = (TextView) v.findViewById(R.id.textViewSlobodniMestaText);
		TextView textIme = (TextView) v.findViewById(R.id.textViewImeText);
		final TextView textTelefon = (TextView) v.findViewById(R.id.textViewTelefonText);
		
		// dodadeno od vele, zakazuvanje na termin
		Button callPhone = (Button) v.findViewById(R.id.callPhone);
		callPhone.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + textTelefon.getText().toString()));
				startActivity(intent);
			}
		});
		// dodadeno od vele
		
		textOd.setText(Od);
		textDo.setText(Do);
		textCena.setText(Cena);
		textVreme.setText(Vreme);
		textMesta.setText(Mesta);
		textIme.setText(Ime);
		textTelefon.setText(Telefon);
		
		return v;
	}

}
