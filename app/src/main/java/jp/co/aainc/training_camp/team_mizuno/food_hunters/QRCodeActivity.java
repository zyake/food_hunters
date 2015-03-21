package jp.co.aainc.training_camp.team_mizuno.food_hunters;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.hardware.Camera;
import android.hardware.Camera.AutoFocusCallback;
import android.hardware.Camera.PreviewCallback;
import android.hardware.Camera.Size;
import android.hardware.Camera.CameraInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;
import android.view.Surface;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.PlanarYUVLuminanceSource;
import com.google.zxing.Reader;
import com.google.zxing.Result;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.common.HybridBinarizer;

public class QRCodeActivity extends Activity {

    private SurfaceView mSurfaceView;
    private Camera mCamera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSurfaceView = new SurfaceView(this);
        mSurfaceView.setOnClickListener(onClickListener);
        setContentView(mSurfaceView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        SurfaceHolder holder = mSurfaceView.getHolder();
        holder.addCallback(callback);
    }

    private SurfaceHolder.Callback callback = new SurfaceHolder.Callback() {
        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            // 生成されたとき
            int cameraId = 0;
            mCamera = Camera.open(cameraId);
            // ディスプレイの向き設定
            setCameraDisplayOrientation(cameraId);
            try {
                // プレビューをセットする
                mCamera.setPreviewDisplay(holder);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            // 変更されたとき
            Camera.Parameters parameters = mCamera.getParameters();
            List<Camera.Size> previewSizes = parameters.getSupportedPreviewSizes();
            Camera.Size previewSize = previewSizes.get(0);
            parameters.setPreviewSize(previewSize.width, previewSize.height);
            // width, heightを変更する
            mCamera.setParameters(parameters);
            mCamera.startPreview();
        }
        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            // 破棄されたとき
            mCamera.release();
            mCamera = null;
        }
    };

    private OnClickListener onClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            // オートフォーカス
            if (mCamera != null) {
                mCamera.autoFocus(autoFocusCallback);
            }
        }
    };

    private AutoFocusCallback autoFocusCallback = new AutoFocusCallback() {
        @Override
        public void onAutoFocus(boolean success, Camera camera) {
            if (success) {
                // 現在のプレビューをデータに変換
                camera.setOneShotPreviewCallback(previewCallback);
            }
        }
    };

    private PreviewCallback previewCallback = new PreviewCallback() {
        @Override
        public void onPreviewFrame(byte[] data, Camera camera) {
            // 読み込む範囲
            Log.i("QRCode","onPreviewFrame");
            int previewWidth = camera.getParameters().getPreviewSize().width;
            int previewHeight = camera.getParameters().getPreviewSize().height;

            // プレビューデータから BinaryBitmap を生成
            PlanarYUVLuminanceSource source = new PlanarYUVLuminanceSource(
                    data, previewWidth, previewHeight, 0, 0, previewWidth, previewHeight, false);
            BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

            Log.i("QRCode","onPreviewFrame: " + previewWidth + " " + previewHeight);

            // バーコードを読み込む
            Reader reader = new MultiFormatReader();
            Result result = null;
            try {
                result = reader.decode(bitmap);
                String text = result.getText();
                Log.i("QRCode", text);
                Intent intent = new Intent();
                intent.setClassName("jp.co.aainc.training_camp.team_mizuno.food_hunters", "jp.co.aainc.training_camp.team_mizuno.food_hunters.HuntedActivity");
                startActivity(intent);

                finish();
//                Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG).show();
            } catch (Exception e) {
//                Toast.makeText(getApplicationContext(), "Not Found", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.setClassName("jp.co.aainc.training_camp.team_mizuno.food_hunters", "jp.co.aainc.training_camp.team_mizuno.food_hunters.HuntedActivity");
                startActivity(intent);

                finish();
            }
        }
    };

    public void setCameraDisplayOrientation(int cameraId) {
        // カメラの情報取得
        CameraInfo cameraInfo = new CameraInfo();
        Camera.getCameraInfo(cameraId, cameraInfo);
        // ディスプレイの向き取得
        int rotation = getWindowManager().getDefaultDisplay().getRotation();
        int degrees = 0;
        switch (rotation) {
            case Surface.ROTATION_0:
                degrees = 0; break;
            case Surface.ROTATION_90:
                degrees = 90; break;
            case Surface.ROTATION_180:
                degrees = 180; break;
            case Surface.ROTATION_270:
                degrees = 270; break;
            }
        // プレビューの向き計算
        int result;
        if (cameraInfo.facing == cameraInfo.CAMERA_FACING_FRONT) {
            result = (cameraInfo.orientation + degrees) % 360;
            result = (360 - result) % 360; // compensate the mirror
            }
        else {// back-facing
            result = (cameraInfo.orientation - degrees + 360) % 360;
            }
        // ディスプレイの向き設定
        mCamera.setDisplayOrientation(result);
        }
}

