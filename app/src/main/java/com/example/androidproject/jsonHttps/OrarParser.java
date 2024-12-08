package com.example.androidproject.jsonHttps;

import com.example.androidproject.clase.Orar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrarParser {
    private static final String FACULTATE = "facultate";
    private static final String AN = "an";
    private static final String SEM = "semestru";
    private static final String ORAS = "oraStart";
    private static final String ORAF = "oraFinal";

    public static List<Orar> parseJson(String json){
        try {
            JSONArray jsonArray = new JSONArray(json);
            return parseArr(jsonArray);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    private static List<Orar> parseArr(JSONArray jsonArray) throws JSONException {
        List<Orar> list = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            list.add(parseObj(jsonArray.getJSONObject(i)));
        }

        return list;
    }

    private static Orar parseObj(JSONObject jsonObject) throws JSONException {
        String fac = jsonObject.getString(FACULTATE);
        String an = jsonObject.getString(AN);
        String sem = jsonObject.getString(SEM);
        String oraS = jsonObject.getString(ORAS);
        String oraF = jsonObject.getString(ORAF);

        Date OraS = null;
        Date OraF = null;

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

        try {
            OraS = sdf.parse(oraS);
            OraF = sdf.parse(oraF);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        return new Orar(fac,an,sem,OraS,OraF);
    }
}
