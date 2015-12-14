package com.cruise.listviewscreenshot;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.ListView;
import android.widget.Toast;


/**
 * Created by cruise on 2015/11/29.
 * 为了不阻塞UI进程，放在后台执行。
 */
public class ScreenShotTask extends AsyncTask<Void, Void, Boolean> {

    private Context context;
    private ProgressDialog dialog;
    private String path;
    private ListView mListView;

    public ScreenShotTask(Context context, ListView listView) {
        this.context = context;
        this.mListView = listView;
    }

    @Override
    protected void onPreExecute() {
        dialog = new ProgressDialog(context);
        dialog.setCancelable(false);
        dialog.setMessage("正在生成图片，请稍等");
        dialog.show();

    }

    @Override
    protected Boolean doInBackground(Void... params) {
        try {
            path = ScreenShotUtil.savePic(ScreenShotUtil.compressImage(ScreenShotUtil.getBitmapByView(mListView)));
        } catch (Exception e) {
            path = null;
        }
        return path != null && !path.isEmpty();
    }

    @Override
    protected void onPostExecute(Boolean result) {
        dialog.hide();
        dialog.dismiss();
        if (result) {
            //// TODO: 截图成功后，进行接下来的操作，例如弹出社交分享的平台选择对话框等
            Toast.makeText(context, "图片保存路径" + getPath(), Toast.LENGTH_SHORT).show();
        }
    }

    public String getPath() {
        return path;
    }

}
