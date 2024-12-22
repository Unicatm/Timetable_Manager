package com.example.androidproject.jsonHttps;

import com.example.androidproject.clase.Categorie;
import com.example.androidproject.clase.Materie;
import com.example.androidproject.clase.Task;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TaskParser {
    private static final String NUMETASK = "numeTask";
    private static final String DENMAT = "denMaterie";
    private static final String DATADDL = "dataDeadline";
    private static final String TIPDDL = "tipDdl";
    private static final String DESC = "descriere";
    private static final String MATERIEID = "materieId";

    public static List<Task> parseJSON(String json){
        try {
            JSONArray jsonArray = new JSONArray(json);
            return parseArr(jsonArray);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    private static List<Task> parseArr(JSONArray jsonArray) throws JSONException {
        List<Task> lista = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            lista.add(parseObj(jsonArray.getJSONObject(i)));
        }

        return lista;
    }

    private static Task parseObj(JSONObject jsonObject) throws JSONException {
        String nume = jsonObject.getString(NUMETASK);
        String denMat = jsonObject.getString(DENMAT);

        String ddl = jsonObject.getString(DATADDL);

        String tipDdlString = jsonObject.getString(TIPDDL);
        String descr = jsonObject.getString(DESC);

        Long mat_id = Long.valueOf(jsonObject.getInt(MATERIEID));

        return new Task(nume,denMat,ddl,tipDdlString,descr,mat_id);
    }
}
