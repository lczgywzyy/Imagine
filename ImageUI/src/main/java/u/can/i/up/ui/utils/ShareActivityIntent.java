package u.can.i.up.ui.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import me.iwf.photopicker.PhotoPickerActivity;

/**
 * Created by dongfeng on 15/9/23
 * @sumary 区分分享界面中，串珠搭配还是新组串珠.
 */
public class ShareActivityIntent extends Intent {

  public static final int TYPE_IMAGE_COLLOCATE=0;//串珠搭配结果图

  public static final int TYPE_PICKER_CUT=1;//相册

  public static final int TYPE_PICKER_BG=2;//自带背景

  public static final int TYPE_PICKER_ALL=3;//相册+自带背景




  private ShareActivityIntent() {
  }

  private ShareActivityIntent(Intent o) {
    super(o);
  }

  private ShareActivityIntent(String action) {
    super(action);
  }

  private ShareActivityIntent(String action, Uri uri) {
    super(action, uri);
  }

  private ShareActivityIntent(Context packageContext, Class<?> cls) {
    super(packageContext, cls);
  }

  public ShareActivityIntent(Context packageContext) {
    super(packageContext, PhotoPickerActivity.class);
  }

  public void setPhotoCount(int photoCount) {
    this.putExtra(PhotoPickerActivity.EXTRA_MAX_COUNT, photoCount);
  }

  public void setShowCamera(boolean showCamera) {
    this.putExtra(PhotoPickerActivity.EXTRA_SHOW_CAMERA, showCamera);
  }

  public void setShowGif(boolean showGif) {
    this.putExtra(PhotoPickerActivity.EXTRA_SHOW_GIF, showGif);
  }

}
