package com.tilldawn.model.Service;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;
import com.tilldawn.model.App;
import com.tilldawn.model.User;

import java.util.ArrayList;

public class UserSaveManager {
    private static final String USERS_FILE = "assets/users.json";

    public static void saveUsersToJson() {
        Json json = new Json();
        json.setIgnoreUnknownFields(false);
        json.setUsePrototypes(false); // از پروتوتایپ استفاده نکن تا همه چیز ذخیره بشه
        json.setOutputType(JsonWriter.OutputType.json); // برای خروجی واضح‌تر

        FileHandle file = Gdx.files.local(USERS_FILE);

        ArrayList<User> users = App.getApp().getUsers();
        file.writeString(json.toJson(users, ArrayList.class, User.class), false);
    }

    public static void loadUsersFromJson() {
        FileHandle file = Gdx.files.local(USERS_FILE);

        if (!file.exists()) {
            App.getApp().getUsers().clear(); // حالت خالی
            return;
        }

        Json json = new Json();
        ArrayList<User> loadedUsers = json.fromJson(ArrayList.class, User.class, file.readString());

        App.getApp().getUsers().clear();
        App.getApp().getUsers().addAll(loadedUsers);
    }
}
