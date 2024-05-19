package com.fit2081.fit2081_a2_tamekalougoon_32507356.provider;

import androidx.room.TypeConverter;

import com.fit2081.fit2081_a2_tamekalougoon_32507356.Item;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Converters {

    @TypeConverter
    public static ArrayList<Item> fromString(String value) {
        Type listType = new TypeToken<ArrayList<Item>>() {
        }.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromArrayList(ArrayList<Item> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }

}
