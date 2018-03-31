package com.group.ag.ag_group_tensorflow4android_v11;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Trace;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.flurgle.camerakit.CameraListener;
import com.flurgle.camerakit.CameraView;

import org.tensorflow.contrib.android.TensorFlowInferenceInterface;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private CameraView cameraView;
    private ImageView imageViewResult;
    TensorFlowInferenceInterface inferenceInterface;

    private static final int INPUT_SIZE = 256;
    private int[] intValues;
    private float[] floatValues;

    private static final String INPUT_NAME = "input";
    private static final String OUTPUT_NAME = "output_new";
    private static final String MODEL_FILE_B = "file:///android_asset/bossK_float.pb";
    private static final String MODEL_FILE_C = "file:///android_asset/cubist_float.pb";
    private static final String MODEL_FILE_D = "file:///android_asset/denoised_starry_float.pb";
    private static final String MODEL_FILE_F = "file:///android_asset/feathers_float.pb";
    private static final String MODEL_FILE_M = "file:///android_asset/mosaic_float.pb";
    private static final String MODEL_FILE_S = "file:///android_asset/scream_float.pb";
    private static final String MODEL_FILE_U = "file:///android_asset/udnie_float.pb";
    private static final String MODEL_FILE_W = "file:///android_asset/wave_float.pb";
    private static final String MODEL_FILE_CR = "file:///android_asset/crayon_float.pb";
    private static final String MODEL_FILE_I = "file:///android_asset/ink_float.pb";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        intValues = new int[INPUT_SIZE * INPUT_SIZE];
        floatValues = new float[INPUT_SIZE * INPUT_SIZE * 3];
        final Matrix matrix=new Matrix();
        matrix.postScale(1f, 1f);
        matrix.postRotate(90);

        cameraView = (CameraView) findViewById(R.id.camera);
        imageViewResult = (ImageView) findViewById(R.id.Result);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);

        ArrayList<Model> datas = initData();
        HomeAdapter homeAdapter = new HomeAdapter(MainActivity.this, datas);
        mRecyclerView.setAdapter(homeAdapter);

        cameraView.setCameraListener(new CameraListener() {
            @Override
            public void onPictureTaken(byte[] picture) {
                super.onPictureTaken(picture);

                Bitmap bitmap0 = BitmapFactory.decodeByteArray(picture, 0, picture.length);
                Bitmap bitmap = Bitmap.createScaledBitmap(bitmap0, INPUT_SIZE, INPUT_SIZE, false);
                bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix,true);
                //float time0 = (float) System.currentTimeMillis();
                Bitmap bitmap_out = stylizeImage(bitmap);
                //float time1 = (float) System.currentTimeMillis();

                //bitmap_out = Bitmap.createScaledBitmap(bitmap_out, bitmap0.getWidth(), bitmap0.getHeight(), false);
                imageViewResult.setImageBitmap(bitmap_out);
                imageViewResult.setVisibility(View.VISIBLE);
                //Log.i(TAG, "Time before: " + time0);
                //Log.i(TAG, "Time after: " + time1);
            }
        });

        homeAdapter.buttonSetOnclick(new HomeAdapter.ButtonInterface() {
            @Override
            public void onclick(View view, MainActivity.Model model) {

                Toast.makeText(MainActivity.this, "迁移中,请稍候,打一局农药等等", Toast.LENGTH_SHORT).show();
                if (model.type == 0){
                    cameraView.toggleFacing();
                    imageViewResult.setVisibility(View.GONE);
                }
                else if(model.type == 1){
                    inferenceInterface = new TensorFlowInferenceInterface(getAssets(), MODEL_FILE_B);
                    cameraView.captureImage();
                }
                else if(model.type == 2){
                    inferenceInterface = new TensorFlowInferenceInterface(getAssets(), MODEL_FILE_CR);
                    cameraView.captureImage();
                }
                else if(model.type == 3){
                    inferenceInterface = new TensorFlowInferenceInterface(getAssets(), MODEL_FILE_C);
                    cameraView.captureImage();
                }
                else if(model.type == 4){
                    inferenceInterface = new TensorFlowInferenceInterface(getAssets(), MODEL_FILE_D);
                    cameraView.captureImage();
                }
                else if(model.type == 5){
                    inferenceInterface = new TensorFlowInferenceInterface(getAssets(), MODEL_FILE_F);
                    cameraView.captureImage();
                }
                else if(model.type == 6){
                    inferenceInterface = new TensorFlowInferenceInterface(getAssets(), MODEL_FILE_I);
                    cameraView.captureImage();
                }
                else if(model.type == 7){
                    inferenceInterface = new TensorFlowInferenceInterface(getAssets(), MODEL_FILE_M);
                    cameraView.captureImage();
                }
                else if(model.type == 8){
                    inferenceInterface = new TensorFlowInferenceInterface(getAssets(), MODEL_FILE_S);
                    cameraView.captureImage();
                }
                else if(model.type == 9){
                    inferenceInterface = new TensorFlowInferenceInterface(getAssets(), MODEL_FILE_U);
                    cameraView.captureImage();
                }
                else if(model.type == 10){
                    inferenceInterface = new TensorFlowInferenceInterface(getAssets(), MODEL_FILE_W);
                    cameraView.captureImage();
                }
            }
        });

        cameraView.setCropOutput(true);
    }

    protected ArrayList<Model> initData() {
        ArrayList<Model> mDatas = new ArrayList<>();
        for (int i = 0; i < 11; i++) {
            Model model = new Model();
            model.type = i;
            model.resId = resIds[i];
            mDatas.add(model);
        }
        return  mDatas;
    }

    private int[] resIds= {R.drawable.ic_launcher, R.drawable.bossk, R.drawable.crayon, R.drawable.cubist,
                            R.drawable.denoised_starry, R.drawable.feathers, R.drawable.ink, R.drawable.mosaic, R.drawable.scream,
                            R.drawable.udnie, R.drawable.wave};

    public class Model {
        public int type;
        public int resId;
    }

    @Override
    protected void onResume() {
        super.onResume();
        cameraView.start();
    }

    @Override
    protected void onPause() {
        cameraView.stop();
        super.onPause();
    }

    private Bitmap scaleBitmap(Bitmap origin, int newWidth, int newHeight) {
        if (origin == null) {
            return null;
        }
        int height = origin.getHeight();
        int width = origin.getWidth();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap newBitmap = Bitmap.createBitmap(origin, 0, 0, width, height, matrix, false);
        if (!origin.isRecycled()) {
            origin.recycle();
        }
        return newBitmap;
    }

    private Bitmap stylizeImage(Bitmap bitmap) {
        Bitmap scaledBitmap = scaleBitmap(bitmap, INPUT_SIZE, INPUT_SIZE);
        scaledBitmap.getPixels(intValues, 0, scaledBitmap.getWidth(), 0, 0, scaledBitmap.getWidth(), scaledBitmap.getHeight());

        for (int i = 0; i < intValues.length; ++i) {
            final int val = intValues[i];
            floatValues[i * 3 + 0] = ((val >> 16) & 0xFF) * 1.0f;
            floatValues[i * 3 + 1] = ((val >> 8) & 0xFF) * 1.0f;
            floatValues[i * 3 + 2] = (val & 0xFF) * 1.0f;
        }

        Trace.beginSection("feed");
        inferenceInterface.feed(INPUT_NAME, floatValues, INPUT_SIZE, INPUT_SIZE, 3);
        Trace.endSection();

        Trace.beginSection("run");
        inferenceInterface.run(new String[]{OUTPUT_NAME});
        Trace.endSection();

        Trace.beginSection("fetch");
        inferenceInterface.fetch(OUTPUT_NAME, floatValues);
        Trace.endSection();

        for (int i = 0; i < intValues.length; ++i) {
            intValues[i] =
                    0xFF000000
                            | (((int) (floatValues[i * 3 + 0])) << 16)
                            | (((int) (floatValues[i * 3 + 1])) << 8)
                            | ((int) (floatValues[i * 3 + 2]));
        }
        scaledBitmap.setPixels(intValues, 0, scaledBitmap.getWidth(), 0, 0, scaledBitmap.getWidth(), scaledBitmap.getHeight());
        return scaledBitmap;
    }

}
