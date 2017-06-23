package com.ran.qxlinechart.fingerManager;

import android.Manifest;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat;
import android.support.v4.os.CancellationSignal;
import android.util.Log;
import android.widget.Toast;
/**
 * Created by houqixin on 2017/6/23.
 */

public class FingerManager {
    private Context mContext;
    private final static String TAG = "FingerManager";
    private FingerprintManagerCompat fingerprintManager;
    private KeyguardManager keyguardManager;
    private static FingerManager instance;
    /**
     * 该对象提供了取消操作的能力。创建该对象也很简单，使用 new CancellationSignal() 就可以了。
     **/
    private CancellationSignal mCancellationSignal;
    private static Object clockFlag = new Object();
    /**
     * 回调方法
     **/
    private FingerprintManagerCompat.AuthenticationCallback mCallback;
    private FingerPrintLinstener mFingerPrintLinstener;

    private FingerManager(Context context) {
        this.mContext = context;
        this.mCancellationSignal = new CancellationSignal();
        this.fingerprintManager=FingerprintManagerCompat.from(context);
        this.keyguardManager=(KeyguardManager)context.getSystemService(Context.KEYGUARD_SERVICE);
        this.mCallback =new FingerprintManagerCompat.AuthenticationCallback(){
            @Override
            public void onAuthenticationError(int errMsgId, CharSequence errString) {
                if(mFingerPrintLinstener!=null)mFingerPrintLinstener.onError(errMsgId,errString);
            }

            @Override
            public void onAuthenticationFailed() {
                if(mFingerPrintLinstener!=null)mFingerPrintLinstener.onFailed();
            }

            @Override
            public void onAuthenticationHelp(int helpMsgId, CharSequence helpString) {
                if(mFingerPrintLinstener!=null)mFingerPrintLinstener.onHelp(helpMsgId,helpString);
            }

            @Override
            public void onAuthenticationSucceeded(FingerprintManagerCompat.AuthenticationResult result) {
                if(mFingerPrintLinstener!=null)mFingerPrintLinstener.onSuccess();
            }
        };
    }

    public static synchronized FingerManager newInstance(Context context) {
        if (instance == null) {
            synchronized (clockFlag) {
                instance = new FingerManager(context);
            }
        }
        return instance;
    }

    /**
     * 如果支持一系列的条件，可以认证回调，参数是加密对象
     **/
    public void startListening(FingerprintManagerCompat.CryptoObject cryptoObject,FingerPrintLinstener listenr) {
        //判断是否添加指纹识别权限
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {

            Toast.makeText(mContext, "没有指纹识别权限", Toast.LENGTH_SHORT).show();
            return;
        }
        this.mFingerPrintLinstener=listenr;
        /**参数分别是:防止第三方恶意攻击的包装类,CancellationSignal对象,flags,回调对象,handle**/
        if(mFingerPrintLinstener!=null)mFingerPrintLinstener.onStart();
        fingerprintManager.authenticate(cryptoObject,0,mCancellationSignal, mCallback, null);
    }

    /**
     * 设备条件判断
     * - 设备是否支持指纹识别
     * - 设备是否处于安全保护中（有指纹识别的手机，在使用指纹识别的时候，还需要强制设置密码或者图案解锁，如果未设置的话是不许使用指纹识别的）
     * - 设备是否已经注册过指纹（如果用户未使用过这个指纹技术，那么只能提示用户到系统设置里面去设置）指纹识别API调用
     **/
    public FingerManagerUserCode isFinger() {
        //判断当前手机版本
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {
                Log.e(TAG, "没有指纹开启指纹识别权限");
                return FingerManagerUserCode.FINGER_NO_PERMISSION;
            }

            //判断硬件是否支持指纹识别
            if (!fingerprintManager.isHardwareDetected()) {
                Log.e(TAG, "没有指纹模块");
                return FingerManagerUserCode.HARDWARE_NO_FINGER;
            }

            //判断 是否开启锁屏密码
            if (!keyguardManager.isKeyguardSecure()) {
                Log.e(TAG, "没有开启锁屏密码");
                return FingerManagerUserCode.CLOCK_SCREEN_PWD_NO_OPEN;
            }

            //判断是否有指纹录入
            if (!fingerprintManager.hasEnrolledFingerprints()) {
                Log.e(TAG, "没有录入指纹");
                return FingerManagerUserCode.NO_ENTRING_FINGER;
            }

            Log.e(TAG, "指纹识别可以正常使用");

            return FingerManagerUserCode.FINGER_MANAGER_CAN_USE;
        } else {
            Log.e(TAG, "手机系统版本不支持指纹识别");
            return FingerManagerUserCode.SYSTEM_NO_SUPPORT;
        }
    }

    public enum FingerManagerUserCode {
        SYSTEM_NO_SUPPORT("系统版本不支持指纹识别"), FINGER_NO_PERMISSION("没有开启指纹识别权限"), HARDWARE_NO_FINGER("手机没有指纹识别模块"),
        CLOCK_SCREEN_PWD_NO_OPEN("没有开启锁屏密码"), NO_ENTRING_FINGER("没有录入指纹"), FINGER_MANAGER_CAN_USE("指纹识别可以开启使用");
        private String msg;

        FingerManagerUserCode(String msg) {
            this.msg = msg;
        }

        public String getMessage() {
            return msg;
        }
    }

    public interface FingerPrintLinstener {
        public void onFailed();

        public void onError(int errorCode, CharSequence errString);

        public void onHelp(int helpCode, CharSequence helpString);

        public void onSuccess();
        public void onStart();
    }
    public  void cancel(){
        if (mCancellationSignal != null)
            mCancellationSignal.cancel();
    }
}
