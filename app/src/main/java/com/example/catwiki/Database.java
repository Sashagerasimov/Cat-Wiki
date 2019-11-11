package com.example.catwiki;

import com.example.catwiki.Model.Cat;

import java.util.ArrayList;
import java.util.HashMap;

public class Database {

    public static HashMap<String, Cat> cats = new HashMap<>();

    public static void saveCatsToFakeDatabase(ArrayList<Cat> catsToSave) {
        for(int i = 0; i < catsToSave.size(); i++) {
            Cat cat = catsToSave.get(i);
            cats.put(cat.getId(),cat);
        }
    }
    public static Cat getCatById(String id) {return cats.get(id);}
}
