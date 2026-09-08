package de.baumann.browser.unit;



import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import androidx.core.content.ContextCompat;

public class PermissionUtils {

    public enum PermissionType {
        LOCATION, CAMERA, MICROPHONE
    }

    public static boolean hasPermission(Context context, PermissionType type) {
        String permission = getManifestString(type);
        return ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED;
    }

    public static String getManifestString(PermissionType type) {
        switch (type) {
            case LOCATION: return Manifest.permission.ACCESS_FINE_LOCATION;
            case CAMERA: return Manifest.permission.CAMERA;
            case MICROPHONE: return Manifest.permission.RECORD_AUDIO;
            default: throw new IllegalArgumentException("Unbekannter Typ");
        }
    }
}
