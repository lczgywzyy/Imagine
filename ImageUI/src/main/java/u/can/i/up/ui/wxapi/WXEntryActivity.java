package u.can.i.up.ui.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.tencent.mm.sdk.openapi.BaseReq;
import com.tencent.mm.sdk.openapi.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import u.can.i.up.ui.R;
import u.can.i.up.utils.image.ShareUtils;

public class WXEntryActivity extends Activity implements IWXAPIEventHandler {
	private static long dataShareId;
	private IWXAPI api;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.wx_entry_activity);
		initData();
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
		initData();
	}

	private void initData() {
		api = WXAPIFactory.createWXAPI(this, ShareUtils.getMetaDataValue("WXAPPID", this));
		api.handleIntent(getIntent(), this);
	}

	@Override
	public void onReq(BaseReq req) {
	}

	@Override
	public void onResp(BaseResp resp) {
		String result = null;
		switch (resp.errCode) {
			case BaseResp.ErrCode.ERR_OK: {
				result = "分享成功";
			}
			break;
			case BaseResp.ErrCode.ERR_USER_CANCEL:
				result = "分享取消";
				break;
			case BaseResp.ErrCode.ERR_AUTH_DENIED:
				result = "分享被拒绝";
				break;
			default:
				result = "分享返回";
				break;
		}

		Toast.makeText(this, result, Toast.LENGTH_LONG).show();
		this.finish();
	}
}