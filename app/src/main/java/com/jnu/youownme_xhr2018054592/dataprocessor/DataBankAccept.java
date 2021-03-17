package com.jnu.youownme_xhr2018054592.dataprocessor;

import android.content.Context;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class DataBankAccept {
    private ArrayList<Accept> arrayListAccepts =new ArrayList<>();
    private Context context;
    private final String ACCEPT_FILE_NAME="Accepts.txt";
    public DataBankAccept(Context context)
    {
        this.context=context;
    }
    public ArrayList<Accept> getAccepts() {
        return arrayListAccepts;
    }


    public void Save()
    {
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(context.openFileOutput(ACCEPT_FILE_NAME,Context.MODE_PRIVATE));
            oos.writeObject(arrayListAccepts);
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void Load()
    {
        ObjectInputStream ois = null;
        arrayListAccepts =new ArrayList<>();
        try {
            ois = new ObjectInputStream(context.openFileInput(ACCEPT_FILE_NAME));
            arrayListAccepts = (ArrayList<Accept>) ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}