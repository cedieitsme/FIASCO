package com.example.dell.fiascov2.tools;

import android.support.design.widget.Snackbar;
import android.view.View;

/**
 * Created by dell on 2/28/2018.
 */

public final class SnackbarCreator {

    private static String message;
    private static int resId;

    public static void set(String message) {
        SnackbarCreator.message = message;
    }

    public static void set(int resId) {
        SnackbarCreator.resId = resId;
    }

    public static void show(View view, boolean lengthLong) {
        try {
            if (!(message.isEmpty() || message.matches("[\\s]+"))) {
                Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show();
            }
        } catch (Exception e) {

        }

        try {
            Snackbar.make(view, resId, (lengthLong ? Snackbar.LENGTH_LONG : Snackbar.LENGTH_SHORT)).show();
        } catch (Exception e) {

        }

        message = null;
        resId = -1;
    }
}
