package com.example.killswitch;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class KillSwitchService extends Service {

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Запуск сервиса AccessibilityServiceKillSwitch
        Intent accessibilityIntent = new Intent(this, AccessibilityServiceKillSwitch.class);
        startService(accessibilityIntent);

        // Возвращение флага START_STICKY для автоматического перезапуска сервиса
        return START_STICKY;
    }

    // Метод для привязки сервиса (не используется)
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
