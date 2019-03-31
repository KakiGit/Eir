package hackton.health.eir.rendering;


import android.content.Context;
import android.opengl.GLES20;
import android.speech.tts.TextToSpeech;
import android.util.Log;

import com.huawei.hiar.ARBody;

import hackton.health.eir.R;

import java.nio.FloatBuffer;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import Jama.Matrix;
import hackton.health.eir.TestData;


public class StandardSkeletonRenderer {
    private static final String TAG = "StandardRenderer";

    private static final int BYTES_PER_FLOAT = Float.SIZE / 8;
    private static final int FLOATS_PER_POINT = 3;  // X,Y,Z,confidence.
    private static final int BYTES_PER_POINT = BYTES_PER_FLOAT * FLOATS_PER_POINT;
    private static final int INITIAL_BUFFER_POINTS = 150;

    private int mVbo;
    private int mVboSize;

    private int mProgramName;
    private int mPositionAttribute;
    private int mModelViewProjectionUniform;
    private int mColorUniform;
    private int mPointSizeUniform;

    private int mNumPoints = 0;
    private int mPointsLineNum = 0;
    private FloatBuffer mLinePoints;


    private TextToSpeech mTextToSpeech;
    private long lastTimestamp= new Date().getTime()-10000;
    // Keep track of the last point cloud rendered to avoid updating the VBO if point cloud
    // was not changed.
    private ARBody mLastBody = null;

    public StandardSkeletonRenderer() {
    }

    public void createOnGlThread(Context context) {
        ShaderUtil.checkGLError(TAG, "before create");

        int buffers[] = new int[1];
        GLES20.glGenBuffers(1, buffers, 0);
        mVbo = buffers[0];
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mVbo);

        mVboSize = INITIAL_BUFFER_POINTS * BYTES_PER_POINT;
        GLES20.glBufferData(GLES20.GL_ARRAY_BUFFER, mVboSize, null, GLES20.GL_DYNAMIC_DRAW);
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0);

        ShaderUtil.checkGLError(TAG, "buffer alloc");

        int vertexShader = ShaderUtil.loadGLShader(TAG, context,
                GLES20.GL_VERTEX_SHADER, R.raw.line_body_vertex);
        int passthroughShader = ShaderUtil.loadGLShader(TAG, context,
                GLES20.GL_FRAGMENT_SHADER, R.raw.passthrough_fragment);

        mProgramName = GLES20.glCreateProgram();
        GLES20.glAttachShader(mProgramName, vertexShader);
        GLES20.glAttachShader(mProgramName, passthroughShader);
        GLES20.glLinkProgram(mProgramName);
        GLES20.glUseProgram(mProgramName);

        ShaderUtil.checkGLError(TAG, "program");

        mPositionAttribute = GLES20.glGetAttribLocation(mProgramName, "a_Position");
        mColorUniform = GLES20.glGetUniformLocation(mProgramName, "u_Color");
        mModelViewProjectionUniform = GLES20.glGetUniformLocation(
                mProgramName, "u_ModelViewProjection");
        mPointSizeUniform = GLES20.glGetUniformLocation(mProgramName, "u_PointSize");

        ShaderUtil.checkGLError(TAG, "program  params");

        

    }

    /**
     * Updates the OpenGL buffer contents to the provided point.  Repeated calls with the same
     * point cloud will be ignored.
     */
    public void update() {//cwx556793


        ShaderUtil.checkGLError(TAG, "before update");

        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mVbo);


        // If the VBO is not large enough to fit the new point cloud, resize it.
        //mNumPoints = body.getSkeletonLinePointsNum();//5;
        mNumPoints = mPointsLineNum;//5;

        if (mNumPoints * BYTES_PER_POINT > mVboSize) {
            while (mNumPoints * BYTES_PER_POINT > mVboSize) {
                mVboSize *= 2;
            }
            GLES20.glBufferData(GLES20.GL_ARRAY_BUFFER, mVboSize, null, GLES20.GL_DYNAMIC_DRAW);
        }

        Log.d(TAG, "skeleton.getSkeletonLinePointsNum()" + mNumPoints);
        Log.d(TAG, "Skeleton Line Points: " + mLinePoints.toString());

        GLES20.glBufferSubData(GLES20.GL_ARRAY_BUFFER, 0, mNumPoints * BYTES_PER_POINT, mLinePoints);
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0);

        ShaderUtil.checkGLError(TAG, "after update");
    }

    /**
     * Renders the body Skeleton.
     */
    public void draw() {

        ShaderUtil.checkGLError(TAG, "Before draw");

        GLES20.glUseProgram(mProgramName);
        GLES20.glEnableVertexAttribArray(mPositionAttribute);
        GLES20.glEnableVertexAttribArray(mColorUniform);
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mVbo);
        GLES20.glLineWidth(18.0f);
        GLES20.glVertexAttribPointer(
                mPositionAttribute, 4, GLES20.GL_FLOAT, false, BYTES_PER_POINT, 0);
        GLES20.glUniform4f(mColorUniform, 127.0f / 255.0f, 255.0f / 255.0f, 212.0f / 255.0f, 1.0f);
        GLES20.glUniform1f(mPointSizeUniform, 100.0f);

        GLES20.glDrawArrays(GLES20.GL_LINES, 0, mNumPoints);
        GLES20.glDisableVertexAttribArray(mPositionAttribute);
        GLES20.glDisableVertexAttribArray(mColorUniform);
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0);

        ShaderUtil.checkGLError(TAG, "Draw");
    }

    public void updateData(ARBody arBody, double[] x_, float width, float height,int type,int stdPostNow) {

        calcSkeletonPoints(arBody, x_,width, height,type,stdPostNow);
        this.update();
        this.draw();

    }

    public boolean calcSkeletonPoints(ARBody arBody, double[] x_, float width, float height,int type,int stdPostNow) {
        this.mPointsLineNum = 0;
//        int connec[] = arBody.getBodySkeletonConnection();
        int connec[] = TestData.connection;
//        float coor[] = arBody.getSkeletonPoint2D();

        float linePoint[] = new float[connec.length * 3 * 2];
//        int isExist[] = arBody.getSkeletonPointIsExist2D ();
        Log.d("Eir","Standard Renderer");
        /***
         * head double shoulder    head-rsho-lsho
         */
        double[] hds;

        double[][] stdPosex,stdPosey;

        switch (type){
            case 0:
                stdPosex = TestData.pos1x;
                stdPosey = TestData.pos1y;
                break;
            case 1:
                stdPosex = TestData.pos2x;
                stdPosey = TestData.pos2y;
                break;
            case 2:
                stdPosex = TestData.pos3x;
                stdPosey = TestData.pos3y;
                break;
            default:
                stdPosex = TestData.pos1x;
                stdPosey = TestData.pos1y;
                break;
        }
        hds = new double[] {
                stdPosex[stdPostNow][0],stdPosey[stdPostNow][0],
                stdPosex[stdPostNow][2],stdPosey[stdPostNow][2],
                 stdPosex[stdPostNow][5],stdPosey[stdPostNow][5]
        };
        Matrix ori = Matrix.random(6,6);
/**
 *      x_   [xh,yh,xrs,yrs,xls,xls
 */

        for(int i=0;i<3;i++){

            ori.set(2*i,0,hds[2*i]);
            ori.set(2*i,1,hds[2*i+1]);
            ori.set(2*i,2,0);
            ori.set(2*i,3,0);
            ori.set(2*i,4,1);
            ori.set(2*i,5,0);

            ori.set(2*i+1,0,0);
            ori.set(2*i+1,1,0);
            ori.set(2*i+1,2,hds[2*i]);
            ori.set(2*i+1,3,hds[2*i+1]);
            ori.set(2*i+1,4,0);
            ori.set(2*i+1,5,1);

        }

        Matrix x = Matrix.random(6,1);
        for(int i=0;i<6;i++){
            x.set(i,0,x_[i]);
        }

        Matrix inv = ori.inverse();
        Matrix res = inv.times(x);

        double[][] best = {
                {res.get(0,0),res.get(1,0),res.get(4,0)},
                {res.get(2,0),res.get(3,0),res.get(5,0)},
                {0,0,1}
        };

        Matrix bestv = Matrix.constructWithCopy(best);
        ARBody.ARBodySkeletonType[] arBodySkeletonTypes = arBody.getBodySkeletonType();
        float[] bodyStructures = arBody.getSkeletonPoint2D();

        Map<Integer,Float> map = new HashMap<>();
        Map<Integer, float[]> stdMap = new HashMap<>();

        for (int j = 0; j < connec.length; j += 2) {
            float xf = (float) (
                    bestv.get(0,0)*stdPosex[0][connec[j]] +
                            bestv.get(0,1)*stdPosey[stdPostNow][connec[j]] +
                            bestv.get(0,2)*1
            );
            float yf = (float) (
                    bestv.get(1,0)*stdPosex[0][connec[j]] +
                            bestv.get(1,1)*stdPosey[stdPostNow][connec[j]] +
                            bestv.get(1,2)*1
            );

            float[] stdm = {xf,yf};
            stdMap.put(connec[j],stdm);
            float df = (xf-bodyStructures[3*connec[j]]) * (xf-bodyStructures[3*connec[j]]) +
                    (yf-bodyStructures[3*connec[j]+1]) * (yf-bodyStructures[3*connec[j]+1]);
            map.put(connec[j],df);
            linePoint[this.mPointsLineNum * 3] = xf;
            linePoint[this.mPointsLineNum * 3 + 1] = yf;
//                        TestData.pos1y[connec[j]]/height;
            linePoint[this.mPointsLineNum * 3 + 2] = 0;


            float xb = (float)(
                    bestv.get(0,0)*stdPosex[0][connec[j+1]] +
                            bestv.get(0,1)*stdPosey[stdPostNow][connec[j+1]] +
                            bestv.get(0,2)*1
            );

            float yb =(float) (
                    bestv.get(1,0)*stdPosex[0][connec[j+1]] +
                            bestv.get(1,1)*stdPosey[stdPostNow][connec[j+1]] +
                            bestv.get(1,2)*1
            );
            df = (xb-bodyStructures[3*connec[j+1]]) * (xb-bodyStructures[3*connec[j+1]]) +
                    (yb-bodyStructures[3*connec[j+1]+1]) * (yb-bodyStructures[3*connec[j+1]+1]);
            map.put(connec[j+1],df);
            float[] stdm1 = {xb,yb};
            stdMap.put(connec[j+1],stdm1);
            linePoint[this.mPointsLineNum * 3 + 3] = xb;
//                        TestData.pos1x[connec[j + 1]]/width;
            linePoint[this.mPointsLineNum * 3 + 4] = yb;
//                        TestData.pos1y[connec[j + 1]]/height;
            linePoint[this.mPointsLineNum * 3 + 5] = 0;

            this.mPointsLineNum += 2;

        }

        this.mLinePoints = FloatBuffer.wrap(linePoint);
        return true;
    }
}



