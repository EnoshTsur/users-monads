package com.enosh.users.remote;

import io.vavr.Function1;
import org.json.JSONObject;

import java.util.function.Supplier;

public interface JsonUtils {

    String FIRST = "first";
    String LAST = "last";
    String NAME = "name";
    String AGE = "age";
    String DOB = "dob";

    static Function1<String, JSONObject> resultFromJson(JSONObject json) {
        return key -> json
                .getJSONArray("results")
                .getJSONObject(0)
                .getJSONObject(key);
    }

    static Function1<JSONObject, String> stringFromJson(String key) {
        return json -> json.getString(key);
    }

    static Function1<JSONObject, Integer> intFromJson(String key) {
        return json -> json.getInt(key);
    }

    static Function1<String, String> getNameAttribute(JSONObject json) {
      return nameKey -> resultFromJson(json)
                .andThen(stringFromJson(nameKey))
                .apply(NAME);
    }

    static int ageFromJson(JSONObject json) {
        return resultFromJson(json)
                .andThen(intFromJson(AGE))
                .apply(DOB);
    }

}
