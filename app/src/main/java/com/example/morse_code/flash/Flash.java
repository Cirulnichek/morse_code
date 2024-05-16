package com.example.morse_code.flash;

import android.content.Context;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;

public class Flash {
    private boolean status;
    private Context context;

    public Flash(Context context) {
        this.context = context;
    }

    public void flashOn() {
        CameraManager cameraManager = (CameraManager) context.getSystemService(Context.CAMERA_SERVICE);
        try {
            assert cameraManager != null;
            String cameraId = cameraManager.getCameraIdList()[0];
            cameraManager.setTorchMode(cameraId, true);
            status = true;
        } catch (CameraAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public void flashOff() {
        CameraManager cameraManager = (CameraManager) context.getSystemService(Context.CAMERA_SERVICE);
        try {
            assert cameraManager != null;
            String cameraId = cameraManager.getCameraIdList()[0];
            cameraManager.setTorchMode(cameraId, false);
            status = false;
        } catch (CameraAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isStatus() {
        return status;
    }
}
