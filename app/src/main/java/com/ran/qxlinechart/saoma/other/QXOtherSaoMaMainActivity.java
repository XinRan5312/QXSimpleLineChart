
/*
 * ZBar4Android
 * ���ߣ���Ԫ��
 * ʱ�䣺2013��12��21��
 * ��Ҫ����������У�
 * 1���������ݵ��������
 * 2�����������򿪳���ᱨ��
 * 3��û��У���򣬻���������������
 * 4�����ܻ����������Ӧ�ó�ͻ����΢��
 * 5�������뻹�Ƕ�������
 */
package com.ran.qxlinechart.saoma.other;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.Button;
import android.widget.PopupWindow;
import android.hardware.Camera;
import android.hardware.Camera.PreviewCallback;
import android.hardware.Camera.AutoFocusCallback;
import android.hardware.Camera.Size;

import com.ran.qxlinechart.R;

import net.sourceforge.zbar.ImageScanner;
import net.sourceforge.zbar.Image;
import net.sourceforge.zbar.Symbol;
import net.sourceforge.zbar.SymbolSet;
import net.sourceforge.zbar.Config;

public class QXOtherSaoMaMainActivity extends Activity
{
	//���ڰ�ť
	private Button BtnAbout;
	//���
    private Camera mCamera;
    //Ԥ����ͼ
    private CameraPreview mPreview;
    //�Զ��۽�
    private Handler mAutoFocusHandler;
    //ͼƬɨ����
    private ImageScanner mScanner;
    //��������
    private PopupWindow mPopupWindow;
    //�Ƿ�ɨ�����
    private boolean IsScanned = false;
    //�Ƿ���Ԥ��״̬
    private boolean IsPreview = true;
    //�Ƿ���ʾ������
    private boolean IsShowPopup=false;

    //����iconvlib
    static
    {
        System.loadLibrary("iconv");
    }
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        //ȥ��������
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.layout_main_saoma);
        //������Ļ����Ϊ����
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //�Զ��۽��߳�
        mAutoFocusHandler = new Handler();
        //��ȡ���ʵ��
        mCamera = getCameraInstance();
		if(mCamera == null)
		{
			//������д�»�ȡ���ʧ�ܵĴ���
			AlertDialog.Builder mBuilder=new AlertDialog.Builder(this);
			mBuilder.setTitle("ZBar4Android");
			mBuilder.setMessage("ZBar4Android��ȡ���ʧ�ܣ������ԣ�");
			mBuilder.setPositiveButton("ȷ��", new DialogInterface.OnClickListener()
			{

				@Override
				public void onClick(DialogInterface mDialogInterface, int mIndex) 
				{
					QXOtherSaoMaMainActivity.this.finish();
				}
			});
			AlertDialog mDialog=mBuilder.create();
			mDialog.show();
		}
        //ʵ����Scanner
        mScanner = new ImageScanner();
        mScanner.setConfig(0, Config.X_DENSITY, 3);
        mScanner.setConfig(0, Config.Y_DENSITY, 3);
       //�������Ԥ����ͼ
        mPreview = new CameraPreview(this, mCamera, previewCb, autoFocusCB);
        FrameLayout preview = (FrameLayout)findViewById(R.id.cameraPreview);
        preview.addView(mPreview);
        if (IsScanned)
          {
             IsScanned = false;
             mCamera.setPreviewCallback(previewCb);
             mCamera.startPreview();
             IsPreview = true;
             mCamera.autoFocus(autoFocusCB);
          }
        //��ȡBtnAbout����ʾ������Ϣ
        BtnAbout=(Button)findViewById(R.id.BtnAbout);
        BtnAbout.setOnClickListener(new OnClickListener()
        {
			@Override
			public void onClick(View v)
			{
				//����������Ѵ�,���ٵ�����
				if(IsShowPopup)
				{
					mPopupWindow.dismiss();
					IsShowPopup=false;
				}
				else
				{
					//������ʾ������
					mPopupWindow=new PopupWindow();
					LayoutInflater mInflater=LayoutInflater.from(getApplicationContext());
					View view=mInflater.inflate(R.layout.layout_about, null);
					mPopupWindow.setContentView(view);
					mPopupWindow.setWidth(LayoutParams.WRAP_CONTENT);
					mPopupWindow.setHeight(LayoutParams.WRAP_CONTENT);
					mPopupWindow.showAtLocation(mPreview, 0, 100, 100);
					IsShowPopup=true;
				}
			} 
        });
    }
    //ʵ��Pause����
    public void onPause()
    {
        super.onPause();
        releaseCamera();
    }
    //��ȡ������ķ���
    public static Camera getCameraInstance()
    {
        Camera mCamera = null;
        try
        {
            mCamera = Camera.open();
			//û�к�������ͷ�����Դ�ǰ������ͷ*******************
			if (mCamera == null)
            {
                Camera.CameraInfo mCameraInfo = new Camera.CameraInfo();
                int cameraCount = Camera.getNumberOfCameras(); 
                for (int camIdx = 0; camIdx < cameraCount; camIdx++)
                {
                    Camera.getCameraInfo(camIdx, mCameraInfo); 
                    if (mCameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT)
                    {
                        mCamera = Camera.open(camIdx);                        
                    }
            }
        }
	}
        catch (Exception e)
        {
        	e.printStackTrace();
        }
        return mCamera;
    }

    //�ͷ������
    private void releaseCamera()
    {
        if (mCamera != null)
        {
            IsPreview = false;
            mCamera.setPreviewCallback(null);
            mCamera.release();
            mCamera = null;
        }
    }

    private Runnable doAutoFocus = new Runnable()
    {
        public void run()
        {
            if (IsPreview)
                mCamera.autoFocus(autoFocusCB);
        }
    };

    PreviewCallback previewCb = new PreviewCallback()
    {
        public void onPreviewFrame(byte[] data, Camera camera)
        {
            Camera.Parameters parameters = camera.getParameters();
            //��ȡɨ��ͼƬ�Ĵ�С
            Size mSize = parameters.getPreviewSize();
            //����洢ͼƬ��Image
            Image mResult = new Image(mSize.width, mSize.height, "Y800");//������������֪���Ǹ����
            //����Image��������Դ
            mResult.setData(data);
            //��ȡɨ�����Ĵ���
            int mResultCode = mScanner.scanImage(mResult);
            //������벻Ϊ0����ʾɨ��ɹ�
            if (mResultCode != 0)
            {
            	//ֹͣɨ��
                IsPreview = false;
                mCamera.setPreviewCallback(null);
                mCamera.stopPreview();
                //��ʼ����ɨ��ͼƬ
                SymbolSet Syms = mScanner.getResults();
                for (Symbol mSym : Syms)
                {
                	//mSym.getType()�������Ի�ȡɨ������ͣ�ZBar֧�ֶ���ɨ������,����ʵ���������롢��ά�롢ISBN���ʶ��
                    if (mSym.getType() == Symbol.CODE128 || mSym.getType() == Symbol.QRCODE || 
                    	mSym.getType() == Symbol.CODABAR ||	mSym.getType() == Symbol.ISBN10 ||
                    	mSym.getType() == Symbol.ISBN13|| mSym.getType()==Symbol.DATABAR ||
                    	mSym.getType()==Symbol.DATABAR_EXP || mSym.getType()==Symbol.I25)
                    		 
                    {
                    	//�����Ч������ʾ�û�ɨ�����
                        Vibrator mVibrator=(Vibrator)getSystemService(VIBRATOR_SERVICE);
      				    mVibrator.vibrate(400);
                        Intent intent=new Intent(QXOtherSaoMaMainActivity.this,ResultActivity.class);
                        intent.putExtra("ScanResult", "ɨ������:"+GetResultByCode(mSym.getType())+"\n"+ mSym.getData());
                        //������Ҫע����ǣ�getData�����������շ���ʶ�����ķ���
                        //������������Ƿ���һ����ʶ�͵��ַ���������֮�����ص�ֵ�а���ÿ���ַ����ĺ���
                        //����N����������URL����һ��Web��ַ�ȵȣ���������ʱ�������������Զ��������һ���Ϻõķָ�
                        //Ч������ã������Ҫ����ɨ���ͼƬ�����Զ�Image��һ�����ʵĴ���
                        startActivity(intent);
                        IsScanned = true;
                    }
                    else
                    {
                    	//�������ɨ��
                        IsScanned = false;
                        mCamera.setPreviewCallback(previewCb);
                        mCamera.startPreview();
                        IsPreview = true;
                        mCamera.autoFocus(autoFocusCB);
                    }
                }
            }
        }
    };

    //����ˢ���Զ��۽��ķ���
    AutoFocusCallback autoFocusCB = new AutoFocusCallback()
    {
        public void onAutoFocus(boolean success, Camera camera)
        {
            mAutoFocusHandler.postDelayed(doAutoFocus, 1000);
        }
    };
    
    //���ݷ��صĴ���ֵ��������Ӧ�ĸ�ʽ������
    public String GetResultByCode(int CodeType)
    {
    	String mResult="";
    	switch(CodeType)
    	{
    	  //������
    	  case Symbol.CODABAR:
    		  mResult="������";
    		  break;
    	  //128�����ʽ��ά��)
    	  case Symbol.CODE128:
    		  mResult="��ά��";
    		  break;
    	  //QR���ά��
    	  case Symbol.QRCODE:
    		  mResult="��ά��";
    		  break;
          //ISBN10ͼ���ѯ
    	  case Symbol.ISBN10:
    		  mResult="ͼ��ISBN��";
    		  break;
    	  //ISBN13ͼ���ѯ
    	  case Symbol.ISBN13:
    		  mResult="ͼ��ISBN��";
    		  break;
    	}
		return mResult;
    }
 
    
}
