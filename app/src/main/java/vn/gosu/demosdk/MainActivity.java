package vn.gosu.demosdk;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.billingclient.api.ProductDetails;
import com.android.billingclient.api.SkuDetails;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.share.Sharer;
import com.gosu.gstracking.GTrackingManger;
import com.gosu.sdk.DialogLoginID;
import com.gosu.sdk.FloatingView;
import com.gosu.sdk.Gosu;

import com.gosu.sdk.utils.GosuSDKConstant;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Random;
import com.gosu.sdk.DialogLoginID.OnLoginListener;
import com.gosu.sdk.DialogLoginID.OnLogoutListener;
import com.gosu.sdk.utils.GosuSDKUtils;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {
    private Gosu mGosu                  = null;
    private ScrollView layoutMain       = null;
    private RelativeLayout layoutIAP    = null;
    private Button btnReward            = null;
    private Button btnFloating          = null;

    public static MainActivity appActivity = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        appActivity = this;
        Gosu.getSharedInstance().showFloating18Plus(this);

        // init GOSU SDK
        Gosu.getSharedInstance().initialize( this);
        mGosu = Gosu.getSharedInstance();

        RelativeLayout rlScreen = (RelativeLayout) findViewById(R.id.rlScreen);
        rlScreen.addView(mGosu.addView(this));

        //mGosu.startTrackingAF(getApplication());
        //mGosu.trackUninstallAF();
        //get firebase message token
        getFirebaseToken();

        mGosu.firebaseSubscribeToTopic("topic_demo", new Gosu.OnFirebaseTopicListener() {
            @Override
            public void onSuccessful(String Message) {
                Log.i("T111", Message);
            }

            @Override
            public void onFailed(String Message) {

            }
        });
       mGosu.firebaseUnSubscribeToTopic("topic_demo", new Gosu.OnFirebaseTopicListener() {
            @Override
            public void onSuccessful(String Message) {
                Log.i("T111", Message);
            }

            @Override
            public void onFailed(String Message) {

            }
        });
        //test



        try {
            JSONObject jsonContent = new JSONObject();
            jsonContent.put("event", "name_event");

            JSONObject jsonRole = new JSONObject();

            jsonRole.put("name", "roleName");
            jsonRole.put("server", "ServerID");

            jsonContent.put("params", jsonRole);

            //Log.d("T111", jsonContent.toString());
            mGosu.trackingStartTrialEventCustomAF(this, jsonContent.toString());

            /*Log.d("T11", jsonRole.toString());
            mGosu.trackingEventOnFirebase("sdk_goiqua", jsonRole.toString());*/

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        printKeyHash(this);
        //endtest

        // init for activity
        final TextView tv_UID = (TextView) this.findViewById(R.id.txt_uID);
        tv_UID.setText("UserName: ");
        layoutMain = (ScrollView) findViewById(R.id.scrollView);
        layoutMain.setVisibility(View.GONE);

        layoutIAP = (RelativeLayout) findViewById(R.id.layout_iap);
        layoutIAP.setVisibility(View.GONE);

        // for showLogin SDK
        final Button btnVaoGame = (Button) findViewById(R.id.btnVaoGame);
        btnVaoGame.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                appActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mGosu.showLogin(new DialogLoginID.OnLoginListener() {
                            @Override
                            public void onLoginSuccessful(String UserId, String UserName, String accesstoken) {
                                tv_UID.setText("UserName: " + UserName);
                                btnVaoGame.setVisibility(View.GONE);
                                layoutMain.setVisibility(View.VISIBLE);
                                //
                                Log.d("Login==","uID: " + UserId + ", uName: " + UserName + ", token:" + accesstoken);

                            }

                            public void onBindIDSuccessful(String UserName) {
                                tv_UID.setText("UserName: " + UserName);
                            }

                            public void onLoginFailed() {}
                        });
                    }
                });


            }
        });

        //for Tracking Character
        findViewById(R.id.btn_gosutracking).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String serverID = "123";
                String roleID = "909484455";
                String roleName = "BigSDK";
                mGosu.gosuTrackingAppOpen(serverID, roleID, roleName);
            }
        });

        //for IAP
        findViewById(R.id.btn_payment_iap).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                loadItemDetails();
                layoutIAP.setVisibility(View.VISIBLE);
            }
        });

        //for Reward Ads
        btnReward = (Button) findViewById(R.id.btn_reward_ad);
        btnReward.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //mGosu.showGosuAds();
                mGosu.deleteAcount(new Gosu.OnDeleteAccountListener() {
                    @Override
                    public void onDeleteAccountSuccessful(String Message) {

                        mGosu.showConfirmDialog("Success!", Message);

                        btnVaoGame.setVisibility(View.VISIBLE);
                        layoutMain.setVisibility(View.GONE);
                    }

                    @Override
                    public void onDeleteAccountCancel() {

                    }

                    @Override
                    public void onDeleteAccountFailed(String Message) {
                        mGosu.showConfirmDialog("Error!", Message);
                    }
                });

            }
        });
        //btnReward.setVisibility(View.INVISIBLE);

        // for invite
        btnFloating = (Button) findViewById(R.id.btn_floating);
        btnFloating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {


                   if (FloatingView.instance == null) {
                        mGosu.showFloatingView();
                        btnFloating.setText("(5) Hide Floating");
                        btnFloating.setSelected(true);
                    } else {
                        mGosu.removeFloatingView();
                        btnFloating.setText("(5) Show Floating");
                        btnFloating.setSelected(false);
                    }
                } catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        });

        // for share image
        findViewById(R.id.btn_facebook_share).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            mGosu.shareLinkToFacebook("https://388.gsscdn.com/ShareFB.jpg", new FacebookCallback<Sharer.Result>(){
                                @Override
                                public void onSuccess(Sharer.Result o) {
                                    //Log.d("ShareFB", "Success");
                                    Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onCancel() {
                                    //Log.d("ShareFB", "Cancel");
                                    Toast.makeText(MainActivity.this, "Cancel", Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onError(FacebookException error) {
                                    //Log.d("ShareFB", "Fail");
                                    Toast.makeText(MainActivity.this, "Fail", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        // for share link
        findViewById(R.id.btn_facebook_share_link).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    mGosu.shareLinkToFacebook("https://play.google.com/store/apps/details?id=com.jwsvn.gg", new FacebookCallback<Sharer.Result>() {
                        @Override
                        public void onSuccess(Sharer.Result o) {
                            //Log.d("ShareFB", "Success");
                            Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onCancel() {
                            //Log.d("ShareFB", "Cancel");
                            Toast.makeText(MainActivity.this, "Cancel", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onError(FacebookException error) {
                            //Log.d("ShareFB", "Fail");
                            Toast.makeText(MainActivity.this, "Fail", Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        // for logOut
        findViewById(R.id.btn_logout).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mGosu.logOut(new OnLogoutListener() {
                    public void onLogoutSuccessful() {
                        btnVaoGame.setVisibility(View.VISIBLE);
                        layoutMain.setVisibility(View.GONE);
                        btnVaoGame.callOnClick();
                    }

                    public void onLogoutFailed() {
                    }
                });


            }
        });

        //////////// FOR IAP /////////////
        findViewById(R.id.btn_cancel_iap).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                layoutIAP.setVisibility(View.GONE);
            }
        });

        findViewById(R.id.btn_pk1).setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                layoutIAP.setVisibility(View.GONE);
                callIAP(1);
            }
        });

        findViewById(R.id.btn_pk2).setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                layoutIAP.setVisibility(View.GONE);
                callIAP(2);
            }
        });


        findViewById(R.id.btn_pk3).setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                layoutIAP.setVisibility(View.GONE);
                callIAP(3);
            }
        });
    }

    protected void callTrackingExample()
    {
        GTrackingManger.getInstance().trackingEvent("level_20");
        GTrackingManger.getInstance().trackingStartTrial();
        GTrackingManger.getInstance().trackingTutorialCompleted();
        GTrackingManger.getInstance().doneNRU(
                "server_id",
                "role_id",
                "Role Name"
        );
        String abc = null;
        GTrackingManger.getInstance().doneNRU(
                abc,
                abc,
                abc
        );
        try {
            String value = "{\"customer_id\":\"12345\"}";
            JSONObject jsonObject = new JSONObject(value);
        } catch (Exception e) {

        }

        /* custom event */
        GTrackingManger.getInstance().trackingEvent("level_20", abc);
        GTrackingManger.getInstance().trackingEvent("level_20", "{\"test\":\"abc\"}");
        GTrackingManger.getInstance().trackingEvent("level_20", "{\"customer_id\":\"12345\"}");
    }

    private void getFirebaseToken() {
        
    }

    ///////////// Load Item Detail on GG Store ///////////////
    private void loadItemDetails(){
        //load product detail
        try {
            List<ProductDetails> skuDetails = mGosu.getAllProductDetails();
            for(ProductDetails skuItem : skuDetails) {
                String title = skuItem.getTitle();
                String price = skuItem.getProductId();
                String orPrice = skuItem.getProductType();
                //....
                //Log.d("IAP_TAG", title + " - " + orPrice);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    ///////////// CAL IAP ////////////////
    private void callIAP(int type) {
        try {
            String orderid = String.valueOf(new Random().nextDouble());
            String orderInfo = "";
            String amount = "0";
            String sku = "";
            switch (type) {
                case 1:
                    sku = "vn.gosu.atomarena.100kc"; //GosuSDKConstant.iap_product_ids.get(0);// com.gosu.sdkdemo.p4
                    amount = "22000"; //"22000";
                    orderInfo = "Mua gói 1 nhận ngay 99 KNB";
                    break;
                case 2:
                    sku = GosuSDKConstant.iap_product_ids.get(1);// com.gosu.sdkdemo.p5
                    amount = "44000";
                    orderInfo = "Mua gói 2 nhận ngay 199 KNB";
                    break;
                case 3:
                    sku = GosuSDKConstant.iap_product_ids.get(2);// com.gosu.sdkdemo.p6
                    amount = "66000";
                    orderInfo = "Mua gói 3 nhận ngay 299 KNB";
                    break;
            }

            orderInfo = URLEncoder.encode(orderInfo, "UTF-8");
            String character    = "CharacterID";
            String extraInfo    = "Note";

            Context context = MainActivity.this;
            mGosu.payment_iap(context, GosuSDKConstant.username, sku,"s1", orderid, orderInfo, amount, character, extraInfo,
                    new Gosu.OnPaymentIAPListener() {

                        public void onPaymentSuccessful(String msg) {
                            mGosu.showConfirmDialog("Success!", msg);
                        }

                        @Override
                        public void onPaymentFailed(String msg, int errCode, String iapToken) {
                            mGosu.showConfirmDialog("Fail!", msg);
                        }
                    });
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


    }

    private void loadReward(){
        //use in by game
        mGosu.loadGosuAds(this, GosuSDKConstant.GosuAdsType_RewardVideo, GosuSDKConstant.GosuAdsScreen_Home, gosuAdsListener);
    }

    private Gosu.OnGosuAdsListener gosuAdsListener = new Gosu.OnGosuAdsListener() {
        @Override
        public void onGosuAdsLoaded(boolean isLoaded) {
            btnReward.setVisibility(isLoaded?View.VISIBLE:View.INVISIBLE);
        }

        @Override
        public void onGosuAdsCompleted(final int amount, final String trackID, final String message) {
            runOnUiThread(new Runnable() {
                public void run() {
                    mGosu.showConfirmDialog("GosuAds Finish!", message);
                }
            });
        }

        @Override
        public void onGosuAdsClick(final int amount, final String trackID, final String message) {
            runOnUiThread(new Runnable() {
                public void run() {
                    mGosu.showConfirmDialog("GosuAds Click!", message);
                }
            });
        }

        @Override
        public void onGosuAdsClose() {
            loadReward();
        }

        @Override
        public void onGosuAdsFailed(final String message) {
            runOnUiThread(new Runnable() {
                public void run() {
                    mGosu.showConfirmDialog("GosuAds Fail!", message);
                }
            });
        }
    };

    @Override
    protected void onPause() {
        super.onPause();
        if(mGosu != null)
            mGosu.gosuSDKOnPause();
        //check floating show or hide
        if(FloatingView.instance != null && !mGosu.mFloatingBtnCancelState)
            mGosu.removeFloatingView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(mGosu != null)
            mGosu.gosuSDKOnResume();
        //check floating show or hide
        if(FloatingView.instance != null && !mGosu.mFloatingBtnCancelState)
            mGosu.showFloatingView();
    }

    // We're being destroyed. It's important to dispose of the helper here!
    @Override
    public void onDestroy() {// very important
        if (mGosu != null)
            mGosu.gosuSDKOnDestroy();
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == GosuSDKConstant.IAP_RQ_CODE) {
            super.onActivityResult(requestCode, resultCode, data);
        } else if(requestCode == GosuSDKConstant.FLOATING_RQ_CODE){
            mGosu.checkResultForFloatingView(requestCode, resultCode, data);
        } else {
            mGosu.onActivityResult(requestCode, resultCode, data);
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if(FloatingView.instance != null)
            FloatingView.instance.checkOrientation(newConfig);
    }

    public static String printKeyHash(Activity context) {
        PackageInfo packageInfo;
        String key = null;
        try {
            //getting application package name, as defined in manifest
            String packageName = context.getApplicationContext().getPackageName();

            //Retriving package info
            packageInfo = context.getPackageManager().getPackageInfo(packageName,
                    PackageManager.GET_SIGNATURES);

            Log.e("Package Name=", context.getApplicationContext().getPackageName());

            for (android.content.pm.Signature signature : packageInfo.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                key = new String(Base64.encode(md.digest(), 0));

                // String key = new String(Base64.encodeBytes(md.digest()));
                Log.e("KeyHash=", key);
            }
        } catch (PackageManager.NameNotFoundException e1) {
            Log.e("Name not found", e1.toString());
        } catch (NoSuchAlgorithmException e) {
            Log.e("No such an algorithm", e.toString());
        } catch (Exception e) {
            Log.e("Exception", e.toString());
        }

        return key;
    }
}
