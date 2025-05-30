package com.tilldawn.model.Service;

import com.badlogic.gdx.files.FileHandle;
import com.tilldawn.controller.ProfileMenuController;

import javax.swing.*;
import java.io.File;

public class DesktopFileChooser {
    public static void openImageFile(ProfileMenuController controller) {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        chooser.setDialogTitle("انتخاب تصویر آواتار");

        int result = chooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            FileHandle handle = new FileHandle(file);
            controller.onAvatarImageSelected(handle);
        }
    }

}
