package android.app;

import android.Manifest;
import android.content.Context;

import com.oasisfeng.condom.CondomKit;

import me.pqpo.librarylog4a.Log4a;

/**
 * Created by Trumeet on 2018/1/19.
 */

public class AppOpsKit implements CondomKit, CondomKit.SystemServiceSupplier {
    private static final String TAG = "AppOpsKit";

    @Override
    public void onRegister(CondomKitRegistry registry) {
        registry.addPermissionSpoof(Manifest.permission.GET_APP_OPS_STATS);
        registry.registerSystemService(Context.APP_OPS_SERVICE, this);
    }


    @Override public Object getSystemService(final Context context, final String name) {
        if (Context.APP_OPS_SERVICE.equals(name))
            return new CondomAppOpsManager(context);
        return null;
    }

    class CondomAppOpsManager extends AppOpsManagerExtender {
        CondomAppOpsManager (Context context) {
            super(context);
        }

        @Override
        public int checkOpNoThrow(int op, int uid, String packageName) {
            if (op == AppOpsManager.OP_POST_NOTIFICATION) {
                Log4a.d(TAG, "check post notification op: " + uid + ", " + packageName);
                // TODO: add a option is better.
                return AppOpsManager.MODE_ALLOWED;
            }
            return super.checkOpNoThrow(op, uid, packageName);
        }
    }
}
