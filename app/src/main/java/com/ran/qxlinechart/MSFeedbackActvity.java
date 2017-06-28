package com.ran.qxlinechart;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.load.engine.executor.FifoPriorityThreadPoolExecutor;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by houqixin on 2017/5/31.
 */
public class MSFeedbackActvity extends MSBaseActivity {
    private EditText mFeedBackEt;
    private TextView mFeedBackTextCountTv;
    private LinearLayout mLinearLayout;
    private AtomicInteger mCameraImgCount = new AtomicInteger(0);
    private Map<Integer, ImageView> mMapImg = new HashMap<>();
    private int mTSelectTag;
    private ExecutorService mExecutorService;
    private List<Runnable> mTasks = new ArrayList<>();
    private final String mBaseUrl = "";
    private static final int maxLengh = 200;
    private RelativeLayout mImgContainerOne;
    private RelativeLayout mImgContainerTwo;
    private RelativeLayout mImgContainerTree;
    private RelativeLayout mImgContainerFour;
    private ImageView mImgViewOne;
    private ImageView mImgViewTwo;
    private ImageView mImgViewThree;
    private ImageView mImgViewFour;
    private TextView mImgMaskOne;
    private TextView mImgMaskTwo;
    private TextView mImgMaskThree;
    private TextView mImgMaskFour;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        initView();
    }

    private void initView() {
        mFeedBackEt = $(R.id.feedback_et);
        mFeedBackTextCountTv = $(R.id.feedback_text_count_hint);
        mLinearLayout = $(R.id.camera_container);
        mImgContainerOne=$(R.id.feedback_img_container_one);
        mImgContainerTwo=$(R.id.feedback_img_container_two);
        mImgContainerTree=$(R.id.feedback_img_container_three);
        mImgContainerFour=$(R.id.feedback_img_container_four);
        mImgViewOne=$(R.id.feedback_img_one);
        mImgViewTwo=$(R.id.feedback_img_two);
        mImgViewThree=$(R.id.feedback_img_three);
        mImgViewFour=$(R.id.feedback_img_four);

        mImgMaskOne=$(R.id.feedback_img_close_one);
        mImgMaskTwo=$(R.id.feedback_img_close_two);
        mImgMaskThree=$(R.id.feedback_img_close_three);
        mImgMaskFour=$(R.id.feedback_img_close_four);

        createOneImageViewTwo(mCameraImgCount.getAndDecrement());

        mFeedBackEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                int len = Selection.getSelectionEnd(mFeedBackEt.getText());
                if (len > maxLengh) {
                    Selection.setSelection(mFeedBackEt.getText(), maxLengh);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable != null) {
                    String content = editable.toString();

                    if (content != null) {
                        if (content.length() > maxLengh) {

                            editable.delete(maxLengh, maxLengh + 1);

                        } else {
                            mFeedBackTextCountTv.setText("" + content.length() + "/" + maxLengh);
                        }
                    }
                }
            }
        });
    }

    private void createOneImageViewTwo(final Integer tag) {

        if (mMapImg.containsKey(tag) || tag== 4) {
            return;
        }


    }


    private void createOneImageView(final Integer tag) {

        if (mMapImg.containsKey(tag) || mLinearLayout.getChildCount() == 4) {
            return;
        }
        final ImageView imageView = new ImageView(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(-2, -2);
        params.leftMargin = dip2px(16);
        params.topMargin = dip2px(16);
        params.bottomMargin = dip2px(16);
        params.width = dip2px(60);
        params.height = dip2px(60);

        imageView.setLayoutParams(params);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setImageResource(R.mipmap.camera);
        imageView.setTag(tag);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int viewTag = (int) view.getTag();
                if (viewTag == tag) {
                    if (!mMapImg.containsKey(tag)) {
                        mMapImg.put(tag, imageView);
                        mTSelectTag = tag;
                        startActivityForResult(new Intent(MSFeedbackActvity.this, MSPictureCheckActivity.class), 1);
                    }
                }
            }
        });
        mLinearLayout.addView(imageView);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == 1) {
            if (data != null) {
                Uri mImageCaptureUri = data.getData();
                if (mImageCaptureUri != null) {
                    Bitmap image;
                    try {
                        image = BitmapFactory.decodeStream(getContentResolver().openInputStream(mImageCaptureUri));
                        Log.e("Tag", "拍照");
                        if (image != null) {

                            mMapImg.get(mTSelectTag).setImageBitmap(image);

                            saveImgSD(image);

                            createOneImageViewTwo(mCameraImgCount.getAndDecrement());

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } else if (resultCode == -101) {
            if (mMapImg.containsKey(mTSelectTag))
                mMapImg.remove(mTSelectTag);
        }
    }

    private void submitUploadFile(String srcpath) {
        if (mExecutorService == null) {
            mExecutorService = Executors.newFixedThreadPool(2, new FifoPriorityThreadPoolExecutor.DefaultThreadFactory());
        }
        final File file = new File(srcpath);
        if (file == null || (!file.exists())) {
            return;
        }
        final Map<String, String> params = new HashMap<String, String>();

        mTasks.add(new Runnable() {
            @Override
            public void run() {
                uploadFile(file, mBaseUrl, params);
            }
        });
    }

    private void saveImgSD(Bitmap image) {
        String name = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
        String fileNmae = Environment.getExternalStorageDirectory().toString() + File.separator + "mjs/image/" + name + ".jpg";
        submitUploadFile(fileNmae);
        File myCaptureFile = new File(fileNmae);

        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            if (!myCaptureFile.getParentFile().exists()) {
                myCaptureFile.getParentFile().mkdirs();
            }
            BufferedOutputStream bos;
            try {
                bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
                image.compress(Bitmap.CompressFormat.JPEG, 80, bos);
                bos.flush();
                bos.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {

            Toast toast = Toast.makeText(MSFeedbackActvity.this, "保存失败，SD卡无效", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
    }


    /**
     * 上传文件到服务器
     *
     * @param file       需要上传的文件
     * @param urlRequest 请求的rul
     * @return 返回响应的内容
     */
    private String uploadFile(File file, String urlRequest, Map<String, String> param) {
        String result = null;
        String BOUNDARY = UUID.randomUUID().toString();  //边界标识   随机生成
        String PREFIX = "--", LINE_END = "\r\n";
        String CONTENT_TYPE = "multipart/form-data";   //内容类型
        // 显示进度框
//      showProgressDialog();
        try {
            URL url = new URL(urlRequest);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(5000);
            conn.setConnectTimeout(5000);
            conn.setDoInput(true);  //允许输入流
            conn.setDoOutput(true); //允许输出流
            conn.setUseCaches(false);  //不允许使用缓存
            conn.setRequestMethod("POST");  //请求方式
            conn.setRequestProperty("Charset", "UTF-8");  //设置编码
            conn.setRequestProperty("connection", "keep-alive");
            conn.setRequestProperty("Content-Type", CONTENT_TYPE + ";boundary=" + BOUNDARY);
            if (file != null) {
                /**
                 * 当文件不为空，把文件包装并且上传
                 */
                DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
                StringBuffer sb = new StringBuffer();

                String params = "";
                if (param != null && param.size() > 0) {
                    Iterator<String> it = param.keySet().iterator();
                    while (it.hasNext()) {
                        sb = null;
                        sb = new StringBuffer();
                        String key = it.next();
                        String value = param.get(key);
                        sb.append(PREFIX).append(BOUNDARY).append(LINE_END);
                        sb.append("Content-Disposition: form-data; name=\"").append(key).append("\"").append(LINE_END).append(LINE_END);
                        sb.append(value).append(LINE_END);
                        params = sb.toString();
                        dos.write(params.getBytes());
//                      dos.flush();
                    }
                }
                sb = new StringBuffer();
                sb.append(PREFIX);
                sb.append(BOUNDARY);
                sb.append(LINE_END);
                /**
                 * 这里重点注意：
                 * name里面的值为服务器端需要key   只有这个key 才可以得到对应的文件
                 * filename是文件的名字，包含后缀名的   比如:abc.png
                 */
                sb.append("Content-Disposition: form-data; name=\"upfile\";filename=\"" + file.getName() + "\"" + LINE_END);
                sb.append("Content-Type: image/pjpeg; charset=UTF-8" + LINE_END);
                sb.append(LINE_END);
                dos.write(sb.toString().getBytes());
                InputStream is = new FileInputStream(file);
                byte[] bytes = new byte[1024];
                int len = 0;
                while ((len = is.read(bytes)) != -1) {
                    dos.write(bytes, 0, len);
                }
                is.close();
                dos.write(LINE_END.getBytes());
                byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINE_END).getBytes();
                dos.write(end_data);

                dos.flush();
                /**
                 * 获取响应码  200=成功
                 * 当响应成功，获取响应的流
                 */

                int res = conn.getResponseCode();
                System.out.println("res=========" + res);
                if (res == 200) {
                    InputStream input = conn.getInputStream();
                    StringBuffer sb1 = new StringBuffer();
                    int ss;
                    while ((ss = input.read()) != -1) {
                        sb1.append((char) ss);
                    }
                    result = sb1.toString();
//                 // 移除进度框
//                  removeProgressDialog();
                    finish();
                } else {
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }


    private int dip2px(float dipValue) {
        final float scale = this.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    public void dealImg(InputStream inputStream) {

    }

    /**
     * 获取剪切后的图片
     */
    public static Intent getImageClipIntent() {

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
        intent.setType("image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);//裁剪框比例
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 80);//输出图片大小
        intent.putExtra("outputY", 80);
        intent.putExtra("return-data", true);
        return intent;
    }
}
