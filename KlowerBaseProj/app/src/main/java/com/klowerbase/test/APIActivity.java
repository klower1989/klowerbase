package com.klowerbase.test;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.util.Linkify;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.klower.titlebar.BaseException;
import com.klower.utilities.DialogUtils;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;

public class APIActivity extends ActionBarActivity implements OnClickListener {

	TextView result;

    private AsyncHttpClient mAsyncHttpClient;
	
	private ProgressDialog mProgressDialog;
	@Override
	protected void onCreateEqually(Bundle savedInstanceState)
			throws BaseException {
		super.onCreateEqually(savedInstanceState);
		mAsyncHttpClient = new AsyncHttpClient();
		setCustomContentView(R.layout.api);
		
		result = (TextView) findViewById(R.id.result);
		result.setAutoLinkMask(Linkify.ALL);
		findViewById(R.id.search_news_channel).setOnClickListener(this);
		findViewById(R.id.search_news_list).setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.search_news_channel:
			getNewsChannel();
            testJsonRequest();
			break;
		case R.id.search_news_list:
			getNewsList();
			break;
		}
	}


	static SimpleDateFormat mFormat = new SimpleDateFormat("yyyyMMddHHmmss");

	public static RequestParams getSearchParams() {
		RequestParams params = new RequestParams();
		params.put("showapi_appid", "929");
		params.put("showapi_sign", "simple_5db4a36afa2a488cb2ab03c35a3e1a3e");
		params.put("showapi_timestamp", mFormat.format(new Date()));
		return params;
	}
//
	private void getNewsChannel() {
		mProgressDialog = DialogUtils.showProgressDialog(this, null, "查询中...", false);
		mAsyncHttpClient.get(this, "http://route.showapi.com/109-34", null,
                getSearchParams(), new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] response) {
                        // TODO Auto-generated method stub
                        String content = new String(response);
                        if (mProgressDialog != null) {
                            mProgressDialog.dismiss();
                        }
                        result.setText(content);
                        try {
                            JSONObject jsonObject = new JSONObject(content);
                            if (jsonObject.getInt("showapi_res_code") == 0) {
                            } else {
                                Toast.makeText(APIActivity.this, "查询失败！",
                                        Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
                        if (mProgressDialog != null) {
                            mProgressDialog.dismiss();
                        }
                        Toast.makeText(APIActivity.this, "查询失败！",
                                Toast.LENGTH_SHORT).show();
                    }
                });
	}

	public static RequestParams getSearchNewsListParams() {
		RequestParams params = new RequestParams();
		params.put("showapi_appid", "929");
		params.put("showapi_sign", "simple_5db4a36afa2a488cb2ab03c35a3e1a3e");
		params.put("showapi_timestamp", mFormat.format(new Date()));
		params.put("channelId", "5572a10ab3cdc86cf39001eb");
		params.put("channelName", "娱乐最新");
		params.put("page", "1");
		return params;
	}

	private void getNewsList() {
		mProgressDialog = DialogUtils.showProgressDialog(this, null, "查询中...", false);
		mAsyncHttpClient.get(this, "http://route.showapi.com/109-35", null,
                getSearchNewsListParams(), new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] response) {
                        // TODO Auto-generated method stub
                        String content = new String(response);
                        if (mProgressDialog != null) {
                            mProgressDialog.dismiss();
                        }
                        result.setText(content);
                        try {
                            JSONObject jsonObject = new JSONObject(content);
                            if (jsonObject.getInt("showapi_res_code") == 0) {
                            } else {
                                Toast.makeText(APIActivity.this, "查询失败！",
                                        Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
                        if (mProgressDialog != null) {
                            mProgressDialog.dismiss();
                        }
                        Toast.makeText(APIActivity.this, "查询失败！",
                                Toast.LENGTH_SHORT).show();
                    }
                });
	}


    public static RequestParams getConfigParams(String token, String appVersion) {
        RequestParams params = new RequestParams();
        params.setUseJsonStreamer(true);
        JSONObject sendOb = new JSONObject();
        try {
            sendOb.put("token", token);
            JSONObject payload = new JSONObject();
            payload.put("appVersion", appVersion);
            sendOb.put("payload", payload);
            params.put("Json", sendOb.toString());
            Log.d("RequestParams", "Config request:" + sendOb.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return params;
    }

    private void testJsonRequest(){
        StringEntity mStringEntity = null;
        JSONObject sendOb = new JSONObject();
        try {
            sendOb.put("token", "");
            JSONObject payload = new JSONObject();
            payload.put("local", "CN");
            payload.put("scenicId", null);
            payload.put("pageIndex", 0);
            payload.put("pageSize", 5);
            sendOb.put("payload", payload);

            mStringEntity =new StringEntity(sendOb.toString(), "UTF-8");
            Log.d("RequestParams", "Config request:" + sendOb.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        mAsyncHttpClient.post(this, "http://112.124.55.66:8080/travel-logical-service/service/index/recommend/search",
                mStringEntity, "application/Json", new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
            Log.d("mAsyncHttpClient", "onSuccess onSuccess: "+ new String(bytes));
            }


            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                Log.d("mAsyncHttpClient", "onSuccess onSuccess: "+ new String(bytes));
            }
        });



//        mAsyncHttpClient.post(this, "url", null, getConfigParams(null,"1.0"),
//                RequestParams.APPLICATION_JSON, new AsyncHttpResponseHandler() {
//                    @Override
//                    public void onSuccess(int i, Header[] headers, byte[] bytes) {
//
//                    }
//
//                    @Override
//                    public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
//
//                    }
//                });
    }

}
