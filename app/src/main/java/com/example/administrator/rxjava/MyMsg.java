package com.example.administrator.rxjava;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import io.rong.common.ParcelUtils;
import io.rong.common.RLog;
import io.rong.imlib.MessageTag;
import io.rong.imlib.model.MessageContent;
import io.rong.imlib.model.UserInfo;

@SuppressLint("ParcelCreator")
@MessageTag(value="app:custom",flag = MessageTag.ISCOUNTED | MessageTag.ISPERSISTED)
public class MyMsg extends MessageContent {
    private static final String TAG = "MyMsg";

    private String userid;
    private String message;
    private String content;

    @Override
    public byte[] encode() {
        JSONObject jsonObject = new JSONObject();
        try{
            jsonObject.put("userid",userid);
            jsonObject.put("message",message);
            if(getJSONUserInfo()!=null){
                jsonObject.putOpt("user",getJSONUserInfo());
            }
        }catch (JSONException e){
            Log.e(TAG,e.getMessage());
        }
        try {
            return jsonObject.toString().getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public MyMsg(byte[] data) {
        String jsonStr = null;
        try {
            jsonStr = new String(data,"UTF-8");
            JSONObject jsonObject = new JSONObject(jsonStr);
            setUserid(jsonObject.getString("userid"));
            setMessage(jsonObject.getString("message"));
            if(getJSONUserInfo()!=null){
                setUserInfo(parseJsonToUserInfo(jsonObject.getJSONObject("user")));
                jsonObject.putOpt("user",getJSONUserInfo());
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            Log.e(TAG,e.getMessage());
        }
        try {
            JSONObject jsonObject = new JSONObject(jsonStr);
        } catch (JSONException e) {
            RLog.e(TAG,"JSONException",e);
            e.printStackTrace();
        }
    }

    public MyMsg(Parcel in){
        setUserid(ParcelUtils.readFromParcel(in));
        setMessage(ParcelUtils.readFromParcel(in));
        setUserInfo(ParcelUtils.readFromParcel(in, UserInfo.class));
    }

    public static MyMsg obtain(String userid, String message) {
        MyMsg msg = new MyMsg();
        msg.userid = userid;
        msg.message = message;
        return msg;
    }

    /**
     * 读取接口，目的是要从Parcel中构造一个实现了Parcelable的类的实例处理。
     */
    public static final Creator<MyMsg> CREATOR = new Creator<MyMsg>() {
        @Override
        public MyMsg createFromParcel(Parcel parcel) {
            return new MyMsg(parcel);
        }

        @Override
        public MyMsg[] newArray(int i) {
            return new MyMsg[i];
        }
    };

    /**
     * 描述了包含在 Parcelable 对象排列信息中的特殊对象的类型。
     *
     * @return 一个标志位，表明Parcelable对象特殊对象类型集合的排列。
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * 将类的数据写入外部提供的 Parcel 中。
     *
     * @param parcel  对象被写入的 Parcel。
     * @param i 对象如何被写入的附加标志。
     */
    @Override
    public void writeToParcel(Parcel parcel, int i) {
        ParcelUtils.writeToParcel(parcel,message);
        ParcelUtils.writeToParcel(parcel,userid);
        ParcelUtils.writeToParcel(parcel,getUserInfo());
    }

    public String getContent() {
        return content;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
