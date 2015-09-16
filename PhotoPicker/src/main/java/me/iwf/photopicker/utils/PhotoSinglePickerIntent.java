package me.iwf.photopicker.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import me.iwf.photopicker.PhotoPickerActivity;
import me.iwf.photopicker.PhotoSinglePickerActivity;

/**
 * Created by donglua on 15/7/2.
 */
public class PhotoSinglePickerIntent extends Intent {

  private PhotoSinglePickerIntent() {
  }

  private PhotoSinglePickerIntent(Intent o) {
    super(o);
  }

  private PhotoSinglePickerIntent(String action) {
    super(action);
  }

  private PhotoSinglePickerIntent(String action, Uri uri) {
    super(action, uri);
  }

  private PhotoSinglePickerIntent(Context packageContext, Class<?> cls) {
    super(packageContext, cls);
  }

  public PhotoSinglePickerIntent(Context packageContext) {
    super(packageContext, PhotoSinglePickerActivity.class);
  }

  public void setPhotoCount(int photoCount) {
    this.putExtra(PhotoSinglePickerActivity.EXTRA_MAX_COUNT, photoCount);
  }

  public void setShowCamera(boolean showCamera) {
    this.putExtra(PhotoSinglePickerActivity.EXTRA_SHOW_CAMERA, showCamera);
  }

  public void setShowGif(boolean showGif) {
    this.putExtra(PhotoSinglePickerActivity.EXTRA_SHOW_GIF, showGif);
  }

}
