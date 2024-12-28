package com.example.killswitch;

import android.accessibilityservice.AccessibilityService;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;

public class AccessibilityServiceKillSwitch extends AccessibilityService {

    // Константы для логирования и работы с SharedPreferences
    private static final String TAG = "AccessibilityService";
    private static final String PREFS_NAME = "KillSwitchPrefs";
    private static final String PREF_CALL_ACTIVE = "call_active";

    // Метод, вызываемый при получении события AccessibilityEvent
    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        // Проверка типа события (изменение состояния окна)
        if (event.getEventType() == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
            // Получение имени текущего пакета (приложения на переднем плане)
            CharSequence packageName = event.getPackageName();
            Log.d(TAG, "Foreground app: " + packageName);

            // Доступ к SharedPreferences для проверки состояния звонка
            SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
            boolean isCallActive = prefs.getBoolean(PREF_CALL_ACTIVE, false);
            Log.d(TAG, "Is call active: " + isCallActive);

            // Если звонок активен и открыто браузерное приложение, выполняются действия
            if (isCallActive && isBrowserApp(packageName)) {
                Log.d(TAG, "Attempting to close browser: " + packageName);

                // Попытка закрыть браузер с помощью трех последовательных действий "Назад"
                boolean backAction1 = performGlobalAction(GLOBAL_ACTION_BACK);
                boolean backAction2 = performGlobalAction(GLOBAL_ACTION_BACK);
                boolean backAction3 = performGlobalAction(GLOBAL_ACTION_BACK);

                // Логирование результатов выполнения действий
                Log.d(TAG, "Back action results: " + backAction1 + ", " + backAction2 + ", " + backAction3);
            }
        }
    }

    // Метод, вызываемый при прерывании сервиса
    @Override
    public void onInterrupt() {
        Log.d(TAG, "Accessibility Service Interrupted");
    }

    // Метод для проверки, является ли приложение браузером
    private boolean isBrowserApp(CharSequence packageName) {
        if (packageName == null) return false;
        String packageNameStr = packageName.toString().toLowerCase();

        // Список общих названий пакетов браузеров
        return packageNameStr.contains("browser") ||
                packageNameStr.contains("chrome") ||
                packageNameStr.contains("firefox") ||
                packageNameStr.contains("opera") ||
                packageNameStr.contains("samsung") ||
                packageNameStr.contains("edge");
    }
}
