package com.vhorus.androidsample;

import android.content.Context;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Utils {

    public static final int ANIMATION_TYPE_TRANSLATION = 0;
    public static final int ANIMATION_TYPE_FADE = 1;

    private static CustomProgressDialog customProgressDialog;

    public static FragmentTransaction setAnimations(FragmentTransaction originalFragmentTransaction, int animationType){
        switch (animationType){
            case ANIMATION_TYPE_TRANSLATION:
                originalFragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
                return originalFragmentTransaction;
            case ANIMATION_TYPE_FADE:
                originalFragmentTransaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out, R.anim.fade_in, R.anim.fade_out);
                return originalFragmentTransaction;

            default:
                return originalFragmentTransaction;
        }
    }

    public static String getDateString(long timestamp){
        String dateString = "";
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timestamp);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH) + 1;
        int year = calendar.get(Calendar.YEAR);
        dateString = day + "/" + month + "/" + year;

        return dateString;
    }

    private static SimpleDateFormat simpleDateFormatDateHoursMinute;
    public static String getTimeStringDateHoursMinutes(long timestamp){
        String timeString = "";
        /*Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timestamp);
        int hour = calendar.get(Calendar.HOUR);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);
        String amPM = "";
        if(calendar.get(Calendar.AM_PM) == Calendar.PM){
            amPM = "PM";
        }else{
            amPM = "AM";
        }
        timeString = hour + ":" + minute + ":" + second + " " + amPM;*/
        if(simpleDateFormatDateHoursMinute == null) {
            simpleDateFormatDateHoursMinute = new SimpleDateFormat("dd/MM/yy  h:mm a");
        }
        timeString = simpleDateFormatDateHoursMinute.format(timestamp);
        return timeString;
    }

    public static boolean validateInputs(EditText... editTexts){
        boolean inputsValid = true;

        for (EditText editText:editTexts) {
            if(editText == null || editText.getText().toString() == null || editText.getText().toString().length() < 1){
                inputsValid = false;
                YoYo.with(Techniques.Shake)
                        .duration(700)
                        .repeat(1)
                        .playOn(editText);
            }
        }

        return inputsValid;
    }

    public static boolean validateInputsWithoutYoyo(EditText... editTexts){
        boolean inputsValid = true;

        for (EditText editText:editTexts) {
            if(editText == null || editText.getText().toString() == null || editText.getText().toString().length() < 1){
                inputsValid = false;
            }
        }

        return inputsValid;
    }

    public static void setButtonEnabled(Button button, boolean enabled){
        if(enabled){
            button.setBackgroundResource(R.drawable.button_background_enabled);
            button.setEnabled(true);
        }else{
            button.setBackgroundResource(R.drawable.button_background_disabled);
            button.setEnabled(false);
        }
    }

    public static void showLoading(Context context){
        customProgressDialog = CustomProgressDialog.show(context, "", "");
    }

    public static void dismissLoading(){
        if(customProgressDialog != null && customProgressDialog.isShowing()){
            customProgressDialog.dismiss();
        }
    }

    public static void showToast(Context context, String text, boolean longDuration){
        if(context != null){
            if(longDuration){
                Toast.makeText(context, text, Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
            }
        }
    }

    public static String getString(Context context, int resID){
        if(context != null){
            return context.getResources().getString(resID);
        }else {
            return "";
        }
    }

    public static String getStringExtraInt(Context context, int resID, Integer... params){
        if(context != null){
            if(params != null){
                int length = params.length;
                switch (length){
                    case 1:
                        return context.getResources().getString(resID, params[0]);
                    case 2:
                        return context.getResources().getString(resID, params[0], params[1]);
                    case 3:
                        return context.getResources().getString(resID, params[0], params[1], params[2]);
                    case 4:
                        return context.getResources().getString(resID, params[0], params[1], params[2], params[3]);
                    default:
                        return context.getResources().getString(resID);
                }
            }else{
                return context.getResources().getString(resID);
            }
        }else {
            return "";
        }
    }

    public static String getStringExtraText(Context context, int resID, String... params){
        if(context != null){
            if(params != null){
                int length = params.length;
                switch (length){
                    case 1:
                        return context.getResources().getString(resID, params[0]);
                    case 2:
                        return context.getResources().getString(resID, params[0], params[1]);
                    case 3:
                        return context.getResources().getString(resID, params[0], params[1], params[2]);
                    case 4:
                        return context.getResources().getString(resID, params[0], params[1], params[2], params[3]);
                    default:
                        return context.getResources().getString(resID);
                }
            }else{
                return context.getResources().getString(resID);
            }
        }else {
            return "";
        }
    }

    public static void log(String tag, String message){
        Log.d(tag, message);
    }
}
