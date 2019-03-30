package hackton.health.eir;


import android.graphics.Color;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.huawei.hiar.ARBody;
import com.huawei.hiar.ARBodyTrackingConfig;
import com.huawei.hiar.ARCamera;
import com.huawei.hiar.AREnginesApk;
import com.huawei.hiar.AREnginesSelector;
import com.huawei.hiar.ARFrame;
import com.huawei.hiar.ARSession;
import com.huawei.hiar.ARTrackable;
import com.huawei.hiar.exceptions.ARUnSupportedConfigurationException;
import com.huawei.hiar.exceptions.ARUnavailableClientSdkTooOldException;
import com.huawei.hiar.exceptions.ARUnavailableDeviceNotCompatibleException;
import com.huawei.hiar.exceptions.ARUnavailableEmuiNotCompatibleException;
import com.huawei.hiar.exceptions.ARUnavailableServiceApkTooOldException;
import com.huawei.hiar.exceptions.ARUnavailableServiceNotInstalledException;
import com.huawei.hiar.exceptions.ARUnavailableUserDeclinedInstallationException;

import junit.framework.Test;

import hackton.health.eir.rendering.BackgroundRenderer;
import hackton.health.eir.rendering.BodySkeletonRenderer;
import hackton.health.eir.rendering.SkeletonLineRenderer;
import hackton.health.eir.rendering.StandardSkeletonRenderer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ArrayBlockingQueue;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;


public class MainActivity extends AppCompatActivity implements GLSurfaceView.Renderer {

    private static final String TAG = MainActivity.class.getSimpleName();
    private ARSession mSession;
    private GLSurfaceView mSurfaceView;
    private GestureDetector mGestureDetector;
    private Snackbar mLoadingMessageSnackbar = null;
    private DisplayRotationHelper mDisplayRotationHelper;

    private BackgroundRenderer mBackgroundRenderer = new BackgroundRenderer();

    private BodySkeletonRenderer mBodySkeleton = new BodySkeletonRenderer();
    private SkeletonLineRenderer mSkeletonConnection = new SkeletonLineRenderer();
    private StandardSkeletonRenderer mStandardRenderer = new StandardSkeletonRenderer();
    // Tap handling and UI.
    private ArrayBlockingQueue<MotionEvent> mQueuedSingleTaps = new ArrayBlockingQueue<>(2);

    private TextView cameraPoseTextView;

    private float width;
    private float height;
    private boolean installRequested;
    private float updateInterval = 0.5f;
    private long lastInterval;
    private int frames = 0;
    private float fps;
    private int stdPoseNow;
    private int type=0;
    private TextView progressIndicator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cameraPoseTextView = (TextView) findViewById(R.id.textView);
        mSurfaceView = (GLSurfaceView) findViewById(R.id.surfaceview);
        progressIndicator = findViewById(R.id.progress);
        mDisplayRotationHelper = new DisplayRotationHelper(this);
        mGestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                //onSingleTap(e);
                return true;
            }

            @Override
            public boolean onDown(MotionEvent e) {
                return true;
            }
        });
        stdPoseNow = 0;
//        mSurfaceView.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                Log.d(TAG, "onTouched!");
//                return mGestureDetector.onTouchEvent(event);
//            }
//        });

        // Set up renderer.
        mSurfaceView.setPreserveEGLContextOnPause(true);
        mSurfaceView.setEGLContextClientVersion(2);
        mSurfaceView.setEGLConfigChooser(8, 8, 8, 8, 16, 0); // Alpha used for plane blending.
        mSurfaceView.setRenderer(this);
        mSurfaceView.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
        installRequested = false;

    }

    @Override
    protected void onResume() {
        Log.d(TAG, "onResume");
        super.onResume();
        Exception exception = null;
        String message = null;

        if (mSession == null) {
            try {
                AREnginesSelector.AREnginesAvaliblity enginesAvaliblity = AREnginesSelector
                        .checkAllAvailableEngines(this);
                if ((enginesAvaliblity.ordinal() &
                        AREnginesSelector.AREnginesAvaliblity.HWAR_ENGINE_SUPPORTED.ordinal()) !=
                        0) {

                    AREnginesSelector.setAREngine(AREnginesSelector.AREnginesType.HWAR_ENGINE);

                    switch (AREnginesApk.requestInstall(this, !installRequested)) {
                        case INSTALL_REQUESTED:
                            installRequested = true;
                            return;
                        case INSTALLED:
                            break;
                    }
                    Log.d(TAG, "onResume: AREnginesSelector.getCreatedEngine()=" +
                            AREnginesSelector.getCreatedEngine());
                    if (!CameraPermissionHelper.hasPermission(this)) {
                        CameraPermissionHelper.requestPermission(this);
                        return;
                    }
                    mSession = new ARSession(this);

                    ARBodyTrackingConfig config = new ARBodyTrackingConfig(mSession);
                    Log.d(TAG, "onResume: config=" + config.toString());

                    mSession.configure(config);
                } else {
                    message = "This device does not support Huawei AR Engine ";
                }

            } catch (ARUnavailableServiceNotInstalledException e) {
                message = "Please install HuaweiARService.apk";
                exception = e;
            } catch (ARUnavailableServiceApkTooOldException e) {
                message = "Please update HuaweiARService.apk";
                exception = e;
            } catch (ARUnavailableClientSdkTooOldException e) {
                message = "Please update this app";
                exception = e;
            } catch (ARUnavailableDeviceNotCompatibleException e) {
                message = "This device does not support Huawei AR Engine ";
                exception = e;
            } catch (ARUnavailableEmuiNotCompatibleException e) {
                message = "Please update EMUI version";
                exception = e;
            } catch (ARUnavailableUserDeclinedInstallationException e) {
                message = "Please agree to install!";
                exception = e;
            } catch (ARUnSupportedConfigurationException e) {
                message = "The configuration is not supported by the device!";
                exception = e;
            } catch (Exception e) {
                message = "exception throwed";
                exception = e;
            }

            if (message != null) {
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
                Log.e(TAG, "Creating session", exception);
                if (mSession != null) {
                    mSession.stop();
                    mSession = null;
                }
                return;
            }

        }
        mSession.resume();
        mSurfaceView.onResume();
        mDisplayRotationHelper.onResume();
        lastInterval = (System.currentTimeMillis());

    }

    @Override
    protected void onPause() {
        Log.d(TAG, "onPause");
        super.onPause();
        if (mSession != null) {
            mDisplayRotationHelper.onPause();
            mSurfaceView.onPause();
            mSession.pause();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mSession != null) {
            mSession.stop();
            mSession = null;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] results) {
        if (!CameraPermissionHelper.hasPermission(this)) {
            Toast.makeText(this,
                    "This application needs camera permission.", Toast.LENGTH_LONG).show();

            finish();
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        Log.d(TAG, "onWindowFocusChanged");
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            // Standard Android full-screen functionality.
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }
    }

    private void onSingleTap(MotionEvent e) {
        // Queue tap if there is space. Tap is lost if queue is full.
        Log.d(TAG, "queue a motion event into mQueuedSingleTaps!");
        mQueuedSingleTaps.offer(e);
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        Log.d(TAG, "onSurfaceCreated");
        GLES20.glClearColor(0.1f, 0.1f, 0.1f, 1.0f);

        // Create the texture and pass it to ARCore session to be filled during update().
        mBackgroundRenderer.createOnGlThread(/*context=*/this);
        mBodySkeleton.createOnGlThread(this);
        mSkeletonConnection.createOnGlThread(this);
        mStandardRenderer.createOnGlThread(this);
        mBodySkeleton.setListener(new BodySkeletonRenderer.OnTextInfoChangeListener() {
            @Override
            public boolean textInfoChanged(String text, float positionX, float positionY) {
                showBodyTypeTextView(text, positionX, positionY);
                return true;
            }
        });
    }

    @Override
    public void onSurfaceChanged(GL10 unused, int width, int height) {
        this.width = width;
        this.height = height;
        Log.d(TAG, "onSurfaceChanged! [" + width + ", " + height + "]");
        GLES20.glViewport(0, 0, width, height);
        mDisplayRotationHelper.onSurfaceChanged(width, height);
    }

    @Override
    public void onDrawFrame(GL10 unused) {
        Log.d(TAG, "onDrawFrame");
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);

        if (mSession == null) {
            return;
        }
        mDisplayRotationHelper.updateSessionIfNeeded(mSession);
        try {
            mSession.setCameraTextureName(mBackgroundRenderer.getTextureId());
            ARFrame frame = mSession.update();
            ARCamera camera = frame.getCamera();
            float fpsResult = FPSCalculate();

            mBackgroundRenderer.draw(frame);
            Collection<ARBody> bodies = mSession.getAllTrackables(ARBody.class);

            Iterator<ARBody> it = bodies.iterator();
            while (it.hasNext()){
                ARBody arb = it.next();
                float[] bodyStruture = arb.getSkeletonPoint2D();
                if(arb.getTrackingState()==ARTrackable.TrackingState.TRACKING)
                {

                    int[] isExist = arb.getSkeletonPointIsExist2D();
                    String str = "";
                    int[] connect = arb.getBodySkeletonConnection();
//                    for(int i=0;i<connect.length;i++){
//                        str += String.valueOf(connect[i]) + ',';
//                    }
//                    Log.d("Eir","Connects" + str);

                    str = "";
                    int count = 0;

                    String str1 = "";
                    for (int i = 0; i < isExist.length ; i++) {
                        if (isExist[i]==1) {
                            count++;
                            str += String.valueOf(bodyStruture[3*i])+",";
                            str1 += String.valueOf(bodyStruture[3*i+1])+",";
                        }
                    }

                    ArrayList<double[]> vectorNow = new ArrayList<>();
                    ArrayList<double[]> vectorStd = new ArrayList<>();
                    for(int i=0;i<TestData.vector.length/2;i++){
                        if(isExist[TestData.vector[2*i]]==1 && isExist[TestData.vector[2*i+1]]==1){
                            double x = bodyStruture[3*TestData.vector[2*i]] - bodyStruture[3*TestData.vector[2*i+1]];
                            double y = bodyStruture[3*TestData.vector[2*i]+1] - bodyStruture[3*TestData.vector[2*i+1]+1];
                            double[] tmp = {x,y};
                            vectorNow.add(tmp);
                            x = TestData.pos1x[stdPoseNow][TestData.vector[2*i]] - TestData.pos1x[stdPoseNow][TestData.vector[2*i+1]];
                            y = TestData.pos1y[stdPoseNow][TestData.vector[2*i]] - TestData.pos1y[stdPoseNow][TestData.vector[2*i+1]];
                            double[] tmp1 = {x,y};
                            vectorStd.add(tmp1);
                            }
                    }
                    ArrayList<Double> anglesNow = new ArrayList<>();
                    ArrayList<Double> anglesStd = new ArrayList<>();
                    if(vectorStd.size()==18&&vectorNow.size()==18)
                    {
                        for(int i=0;i<TestData.angle.length/2;i++){
                            double x1=vectorNow.get(TestData.angle[2*i])[0],x2=vectorNow.get(TestData.angle[2*i+1])[0]
                                    ,y1=vectorNow.get(TestData.angle[2*i])[1],y2=vectorNow.get(TestData.angle[2*i+1])[1];
                            anglesNow.add((x1*x2+y1*y2)/(Math.sqrt(x1*x1+y1*y1)+Math.sqrt(x2*x2+y2*y2)));
                            x1=vectorStd.get(TestData.angle[2*i])[0];
                            x2=vectorStd.get(TestData.angle[2*i+1])[0];
                            y1=vectorStd.get(TestData.angle[2*i])[1];
                            y2=vectorStd.get(TestData.angle[2*i+1])[1];
                            anglesStd.add((x1*x2+y1*y2)/(Math.sqrt(x1*x1+y1*y1)+Math.sqrt(x2*x2+y2*y2)));
                        }
                        double err = 0;

                        for(int i=0;i<anglesNow.size();i++){
                            err+=anglesNow.get(i)-anglesStd.get(i);
                        }
                        if(Math.abs(err)<0.1){
                            if(type==2){
                                if(stdPoseNow<2){
                                    stdPoseNow+=1;
                                }
                            } else {
                                if(stdPoseNow<4){
                                    stdPoseNow+=1;
                                }
                            }
                        }
                        Log.d("Eir","error: "+ String.valueOf(err));
                    }


                    Log.d("Eir","x: "+str);
                    Log.d("Eir","y: "+str1);
                    double[] x_ = {
                            bodyStruture[3*0],bodyStruture[3*0+1],
                            bodyStruture[3*2],bodyStruture[3*2+1],
                            bodyStruture[3*5],bodyStruture[3*5+1]
                    };
                    Log.d("Eir",String.valueOf(count));
                    mStandardRenderer.updateData(arb,x_,width,height,1,stdPoseNow);

                } else {

                }
            }
            if(type!=3)
            {
                progressIndicator.setText(String.valueOf(stdPoseNow+1) + "/" + String.valueOf(5));
            } else {
                progressIndicator.setText(String.valueOf(stdPoseNow+1) + "/" + String.valueOf(3));
            }
            mBodySkeleton.updateData(bodies, width, height, fpsResult);
            mSkeletonConnection.updateData(bodies, width, height);


            // if not tracking, don't draw 3d objects
            if (camera.getTrackingState() != ARTrackable.TrackingState.TRACKING) {
                return;
            }

        } catch (Throwable t) {
            // Avoid crashing the application due to unhandled exceptions.
            Log.e(TAG, "Exception on the OpenGL thread", t);
        }
    }


    private void showBodyTypeTextView(final String text, final float positionX, final float
            positionY) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                cameraPoseTextView.setTextColor(Color.RED);
                cameraPoseTextView.setTextSize(15f);
                if (text != null) {
                    cameraPoseTextView.setText(text);
                    cameraPoseTextView.setPadding((int) positionX, (int) positionY, 0, 0);
                } else {
                    cameraPoseTextView.setText("");
                }
            }
        });
    }

    float FPSCalculate() {
        ++frames;
        long timeNow = System.currentTimeMillis();
//        Log.d(TAG, "FPSCalculate: frames=" + frames + ";timeNow=" + timeNow + ";lastInterval="
// + lastInterval
//                + ";lastInterval + updateInterval=" + (timeNow - lastInterval) / 1000.0f);
        if (((timeNow - lastInterval) / 1000) > updateInterval) {
            fps = (float) (frames / ((timeNow - lastInterval) / 1000.0f));
            frames = 0;
            lastInterval = timeNow;
            Log.d(TAG, "FPSCalculate: fps=" + fps);
        }
        return fps;
    }

}
