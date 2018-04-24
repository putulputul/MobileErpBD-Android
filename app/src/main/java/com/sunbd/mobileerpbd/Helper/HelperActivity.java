package com.sunbd.mobileerpbd.Helper;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class HelperActivity {

    public static JSONObject objectToJSONObject(Object object){
        Object json = null;
        JSONObject jsonObject = null;
        try {
            json = new JSONTokener(object.toString()).nextValue();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (json instanceof JSONObject) {
            jsonObject = (JSONObject) json;
        }
        return jsonObject;
    }
}
