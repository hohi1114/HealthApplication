package com.example.healthapplication;


import java.util.ArrayList;

public class Utils {

    private static String[] IMGS = {
            "https://images.unsplash.com/photo-1593642703055-4b72c180d9b5?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1950&q=80",
            "https://images.unsplash.com/photo-1444090542259-0af8fa96557e?q=80&fm=jpg&w=1080&fit=max&s=4b703b77b42e067f949d14581f35019b",
            "https://images.unsplash.com/photo-1439546743462-802cabef8e97?dpr=2&fit=crop&fm=jpg&h=725&q=50&w=1300",
                "https://images.unsplash.com/photo-1594402455509-547265652842?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1350&q=80",
    };

    public static ArrayList<ImageModel> getData() {
        ArrayList<ImageModel> arrayList = new ArrayList<>();
        for (int i = 0; i < IMGS.length; i++) {
            ImageModel imageModel = new ImageModel();
            imageModel.setName("Image " + i);
            imageModel.setUrl(IMGS[i]);
            arrayList.add(imageModel);
        }
        return arrayList;
    }
}
