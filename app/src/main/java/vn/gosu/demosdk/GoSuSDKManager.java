package vn.gosu.demosdk;

import android.app.Activity;
import android.widget.Toast;

import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.share.Sharer;
import com.gosu.sdk.Gosu;
import com.gosu.sdk.utils.GosuSDKConstant;

public class GoSuSDKManager {

    private static GoSuSDKManager goSuSDKManager;

    private Activity activity;
    private Gosu gosu = null;

    public static GoSuSDKManager getInstance() {
        if (goSuSDKManager == null) {
            goSuSDKManager = new GoSuSDKManager();
        }
        return goSuSDKManager;
    }

    public void init(Activity activity) {
        this.activity = activity;
        Gosu.getSharedInstance().initialize(activity, "m379.oqPvcfYcDu5XmUSul9JHiu");
        gosu = Gosu.getSharedInstance();
    }

    public void pay() {
        activity.runOnUiThread(new Runnable() {
            public void run() {

            }
        });
        gosu.payment_iap(activity, GosuSDKConstant.username, "productID", "serverId", "orderID", "orderInfo", "", "character", "extraInfo", new Gosu.OnPaymentIAPListener() {
            @Override
            public void onPaymentSuccessful(java.lang.String msg) {

                gosu.showConfirmDialog("Success!", msg);

            }

            @Override
            public void onPaymentFailed(java.lang.String s, int i, java.lang.String msg) {

                gosu.showConfirmDialog("Fail!", msg);

            }
        });

    }
}
