package com.example.killswitch;

import android.Manifest;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.accessibility.AccessibilityManager;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {

    // Константы для запросов разрешений и работы с SharedPreferences
    private static final int REQUEST_CODE_PERMISSIONS = 1;
    private static final String PREFS_NAME = "KillSwitchPrefs";
    private static final String PREF_BLOCKING_ENABLED = "blocking_enabled";

    // Метод, вызываемый при создании активности
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Запрос разрешений и настройка главного экрана
        requestPermissions();
        setupMainScreen();

        // Проверка, включена ли служба доступности
        if (!isAccessibilityServiceEnabled()) {
            Toast.makeText(this, "Please enable KillSwitch Accessibility Service", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
            startActivity(intent);
        }
    }

    // Метод для запроса разрешений
    private void requestPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_PHONE_STATE}, REQUEST_CODE_PERMISSIONS);
        }
    }

    // Метод, вызываемый при результате запроса разрешений
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permission denied. App may not work correctly.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // Метод для настройки главного экрана
    private void setupMainScreen() {
        @SuppressLint("UseSwitchCompatOrMaterialCode") Switch blockingSwitch = findViewById(R.id.blocking_switch);
        Button enableAccessibilityButton = findViewById(R.id.enable_accessibility_button);

        // Загрузка текущего состояния блокировки из SharedPreferences
        boolean isBlockingEnabled = getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
                .getBoolean(PREF_BLOCKING_ENABLED, false);
        blockingSwitch.setChecked(isBlockingEnabled);
        blockingSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
                    .edit()
                    .putBoolean(PREF_BLOCKING_ENABLED, isChecked)
                    .apply();

            Toast.makeText(this, isChecked ? "Browser blocking enabled" : "Browser blocking disabled", Toast.LENGTH_SHORT).show();
        });

        // Настройка кнопки для перехода в настройки доступности
        enableAccessibilityButton.setOnClickListener(v -> {
            Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
            startActivity(intent);

            Toast.makeText(this, "Enable KillSwitch Accessibility Service", Toast.LENGTH_LONG).show();
        });
    }

    // Метод для проверки, включена ли служба доступности
    private boolean isAccessibilityServiceEnabled() {
        AccessibilityManager am = (AccessibilityManager) getSystemService(ACCESSIBILITY_SERVICE);
        if (am != null) {
            for (AccessibilityServiceInfo service : am.getEnabledAccessibilityServiceList(AccessibilityServiceInfo.FEEDBACK_ALL_MASK)) {
                if (service.getId().contains(getPackageName())) {
                    return true;
                }
            }
        }
        return false;
    }
}
