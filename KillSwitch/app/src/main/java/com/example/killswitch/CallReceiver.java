package com.example.killswitch;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.telephony.TelephonyManager;
import android.util.Log;

public class CallReceiver extends BroadcastReceiver {

    // Константы для логирования и работы с SharedPreferences
    private static final String TAG = "CallReceiver";
    private static final String PREFS_NAME = "KillSwitchPrefs";
    private static final String PREF_CALL_ACTIVE = "call_active";

        @SuppressLint("UnsafeProtectedBroadcastReceiver")
        @Override
    public void onReceive(Context context, Intent intent) {
        // Получение состояния телефона из интента
        String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
        Log.d(TAG, "Phone state changed: " + state);

        // Доступ к SharedPreferences для сохранения состояния звонка
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        // Проверка состояния телефона (звонок активен или завершен)
        if (TelephonyManager.EXTRA_STATE_RINGING.equals(state) || TelephonyManager.EXTRA_STATE_OFFHOOK.equals(state)) {
            Log.d(TAG, "Call active");
            editor.putBoolean(PREF_CALL_ACTIVE, true);
        } else if (TelephonyManager.EXTRA_STATE_IDLE.equals(state)) {
            Log.d(TAG, "Call ended");
            editor.putBoolean(PREF_CALL_ACTIVE, false);
        }
        // Сохранение изменений в SharedPreferences
        editor.apply();
    }
}
