package com.jnu.youownme_xhr2018054592.dataprocessor;

import android.content.Context;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class DataBankGive {
    private ArrayList<Give> arrayListGives =new ArrayList<>();
    private Context context;
    private final String GIVE_FILE_NAME="Gives.txt";
    public DataBankGive(Context context)
    {
        this.context=context;
    }
    public ArrayList<Give> getGives() {
        return arrayListGives;
    }


    public void Save()
    {
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(context.openFileOutput(GIVE_FILE_NAME,Context.MODE_PRIVATE));
            oos.writeObject(arrayListGives);
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void Load()
    {
        ObjectInputStream ois = null;
        arrayListGives =new ArrayList<>();
        try {
            ois = new ObjectInputStream(context.openFileInput(GIVE_FILE_NAME));
            arrayListGives = (ArrayList<Give>) ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}