package com.tilldawn.model.Service;

import com.badlogic.gdx.files.FileHandle;
import com.tilldawn.view.ProfileMenuView;

public class ProfileModel {
    private FileHandle avatarFile;

    public void setAvatarFile(FileHandle avatarFile) {
        this.avatarFile = avatarFile;
    }

    public FileHandle getAvatarFile() {
        return avatarFile;
    }
}
