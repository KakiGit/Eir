package hackton.health.eir;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Locale;

import me.digi.sdk.core.DigiMeClient;
import me.digi.sdk.core.SDKException;
import me.digi.sdk.core.SDKListener;
import me.digi.sdk.core.entities.CAAccounts;
import me.digi.sdk.core.entities.CAFileResponse;
import me.digi.sdk.core.entities.CAFiles;
import me.digi.sdk.core.internal.AuthorizationException;
import me.digi.sdk.core.session.CASession;

/**
 * Created by wanghongkuan on 2019/3/29.
 */

public class Enter extends AppCompatActivity implements SDKListener {
    Context mContext;
    Activity mActivity;
    ArrayList<String> mEvents;
    private String TAG = "Eir";
    private DigiMeClient dgmClient;
    private Handler mHandler;
    private Button digime;
    private TextView healthScore;
    public Enter() {
        super();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tstt);
        mContext=this;
        mActivity = this;
        ImageButton btn1 = findViewById(R.id.button4);
        ImageButton btn2 = findViewById(R.id.button5);
        ImageButton btn3 = findViewById(R.id.button6);
        digime = findViewById(R.id.digime);
        dgmClient = DigiMeClient.getInstance();
        healthScore = findViewById(R.id.healthscore);
        dgmClient.addListener(this);

        mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                dgmClient.authorize(mActivity,null);
            }
        };
        digime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mHandler.sendMessage(new Message());
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext,MainActivity.class);
                startActivity(intent);
            }
        });

        btn1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(mContext, TodoView.class);
                startActivity(intent);
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, ToDoList.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        dgmClient.getAuthManager().onActivityResult(requestCode, resultCode, data);

    }

    /**
     *
     * SDKListener overrides for DiGiMeClient
     */

    @Override
    public void sessionCreated(CASession session) {
        Log.d(TAG, "Session created with token " + session.getSessionKey());
    }

    @Override
    public void sessionCreateFailed(SDKException reason) {
        Log.d(TAG, reason.getMessage());
    }

    @Override
    public void authorizeSucceeded(CASession session) {
        Log.d(TAG, "Session created with token " + session.getSessionKey());
        DigiMeClient.getInstance().getFileList(null);
        digime.setVisibility(View.INVISIBLE);
    }

    @Override
    public void authorizeDenied(AuthorizationException reason) {
        Log.d(TAG, "Failed to authorize session; Reason " + reason.getThrowReason().name());

    }

    @Override
    public void authorizeFailedWithWrongRequestCode() {
        Log.d(TAG, "We received a wrong request code while authorization was in progress!");
    }

    @Override
    public void clientRetrievedFileList(CAFiles files) {
        //Fetch account metadata
        DigiMeClient.getInstance().getAccounts(null);
        for (final String fileId :
                files.fileIds) {
            //Fetch content for returned file IDs
            DigiMeClient.getInstance().getFileJSON(fileId, null);
        }
    }

    @Override
    public void clientFailedOnFileList(SDKException reason) {
        Log.d(TAG, "Failed to retrieve file list: " + reason.getMessage());
    }

    @Override
    public void contentRetrievedForFile(String fileId, CAFileResponse content) {
//        Log.d(TAG,fileId + ":" + content.fileContent);
    }

    @Override
    public void jsonRetrievedForFile(String fileId, JsonElement content) {
        try{
            JsonElement str = content.getAsJsonObject().get("fileContent").getAsJsonArray().get(0).getAsJsonObject().get("activitylevel");
            if(str!=null){
                str = str.getAsJsonArray();
                int count = 0;
                for(int i=0;i<((JsonArray) str).size();i++){
                    Log.d(TAG,fileId +" ::: " + ((JsonArray) str).get(i));
                    count+= (((JsonArray) str).get(i).getAsJsonObject().get("minutes").getAsInt());
                }
                Log.d(TAG,"exercises:" +" ::: " + count);
                TestData.getInstance().addExec(count);
                if(count<120){
                    healthScore.setText("Exec "+String.valueOf(count)+" min"+"\n<120 min this week\n more exercise!");
                } else  {
                    healthScore.setText("Exec "+String.valueOf(count)+" min"+"\n>120 min this week\n GOOD!");
                }
            }

        } catch (Exception e){

        }

        updateCounters();
    }

    @Override
    public void contentRetrieveFailed(String fileId, SDKException reason) {
        Log.d(TAG, "Failed to retrieve file content for file: " + fileId + "; Reason: " + reason);
        updateCounters();
    }

    @Override
    public void accountsRetrieved(CAAccounts accounts) {
    }

    @Override
    public void accountsRetrieveFailed(SDKException reason) {
        Log.d(TAG, "Failed to retrieve account details for session. Reason: " + reason);
    }

    private void updateCounters() {
    }
}
