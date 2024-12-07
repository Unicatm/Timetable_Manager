package com.example.androidproject.jsonHttps;

import com.example.androidproject.clase.Materie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MaterieParser {
    private static final String NUME = "numeMaterie";
    private static final String SALA = "sala";
    private static final String SAPT = "saptamanal";
    private static final String TIPSAPT = "tipSaptamana";
    private static final String ORARID = "orarId";

    public static List<Materie> parseJSON(String json){
        try {
            JSONArray jsonArray = new JSONArray(json);
            return parseArr(jsonArray);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    private static List<Materie> parseArr(JSONArray jsonArray) throws JSONException {
        List<Materie> lista = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            lista.add(parseObj(jsonArray.getJSONObject(i)));
        }

        return lista;
    }

    private static Materie parseObj(JSONObject jsonObject) throws JSONException {
        String nume = jsonObject.getString(NUME);
        String sala = jsonObject.getString(SALA);
        Boolean sapt = jsonObject.getBoolean(SAPT);
        String tipSapt = jsonObject.getString(TIPSAPT);
        Long orarId = jsonObject.getLong(ORARID);

        return new Materie(nume,sala,sapt,tipSapt,orarId);
    }
}
