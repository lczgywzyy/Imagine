package u.can.i.up.ui.net;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.SoftReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import u.can.i.up.ui.beans.IHttpStatus;
import u.can.i.up.ui.utils.StringUtils;

public class HttpManager<T> extends AsyncTask<Integer, Integer, IHttpStatus> {


    private String url;

    private HttpType type;

    private HashMap<String, String> hashParam;

    private HttpURLConnection urlConnection;

    private static final String BOUNDARY = "--------a52f733b0e51d9a568fababbc8d3a518";

    private HashMap<String, SoftReference<Bitmap>> imgs;

    private File[] files;

    private Class<T> classT;


    public HttpManager(String url, HttpType type, HashMap<String, String> hashParam,Class<T> classT) {
        super();
        this.url = url;
        this.hashParam = hashParam;
        this.type = type;
        this.classT=classT;
    }

    /**
     * 图片+文字描述上传*
     */
    public HttpManager(String url,  HttpType type,HashMap<String, String> hashParam,Class<T> classT,HashMap<String, SoftReference<Bitmap>> images, File... files) {
        super();
        this.url = url;
        this.hashParam = hashParam;
        this.type = type;
        this.imgs = images;
        this.files = files;
        this.classT=classT;
    }

    public HttpManager(String url,HttpType type){
        super();
        this.url = url;
        this.type = type;
    }
    public HttpManager(String url,HttpType type,Class<T> classT){
        super();
        this.url = url;
        this.classT=classT;
        this.type = type;
    }

    public HttpManager(){

    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setType(HttpType type) {
        this.type = type;
    }

    public void setHashParam(HashMap<String, String> hashParam) {
        this.hashParam = hashParam;
    }

    public void setFiles(File[] files) {
        this.files = files;
    }

    public void setImgs(HashMap<String, SoftReference<Bitmap>> imgs) {
        this.imgs = imgs;
    }

    public void setClassT(Class<T> classT) {
        this.classT = classT;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(IHttpStatus s) {

        super.onPostExecute(s);
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onCancelled(IHttpStatus s) {
        super.onCancelled(s);
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }

    @Override
    protected IHttpStatus<T> doInBackground(Integer... params) {
        IHttpStatus<T> IHttpStatus = new IHttpStatus<>();
        try {
            switch (type) {
                case POST:
                    initConnectionPost();
                    break;
                case GET:
                    initConnectionGet();
                    break;
                default:
                    initConnectionPost();
                    return null;
            }
            if(TextUtils.isEmpty(url)){
                return null;
            }
            IHttpStatus.setHttpStatus(urlConnection.getResponseCode());
            switch (urlConnection.getResponseCode()) {
                case HttpURLConnection.HTTP_OK:

                    String mimeType = urlConnection.getHeaderField("Content-Type");

                    if (!StringUtils.isEmpty(mimeType)) {
                        InputStream netInput = urlConnection.getInputStream();
                        if (mimeType.contains("image/")) {
                            Bitmap bitmap = BitmapFactory.decodeStream(netInput);
                            IHttpStatus.setBitmap(bitmap);
                        } else if (mimeType.contains("/json")||mimeType.contains("text/html")) {
                            BufferedReader reader = new BufferedReader(new InputStreamReader(netInput));
                            StringBuilder sb = new StringBuilder();
                            String line = null;
                            while ((line = reader.readLine()) != null) {
                                sb.append(line + "\n");
                            }
                            String jsonStr = sb.toString();
                            /**转换为T**/
                            try {
                                T t = JSON.parseObject(jsonStr, classT);
                                IHttpStatus.setHttpObj(t);
                            }catch (Exception e){
                                IHttpStatus.setHttpStatus(-1);
                            }finally {
                                netInput.close();
                            }

                        }
                    }
                    break;

                default:
                    break;

            }

        } catch (IOException e) {
            IHttpStatus.setHttpStatus(-1);
        } finally {
            urlConnection.disconnect();
        }
        return IHttpStatus;
    }

    private void initConnection() throws IOException {
        urlConnection.setRequestProperty("connection", "Keep-Alive");
        urlConnection.setRequestProperty("Accept-Charset", "utf-8");
        urlConnection.setConnectTimeout(3000);

    }

    private void initConnectionPost() throws IOException {
        urlConnection = (HttpURLConnection) new URL(url).openConnection();
        urlConnection.setRequestMethod("POST");
        urlConnection.setDoInput(true);
        initConnection();
        if (imgs == null && files == null) {
            initParamsPost();
        } else {
            initParamPostMulti();
        }
    }

    private void initConnectionGet() throws IOException {
        if (hashParam != null) {
            StringBuilder buffer = new StringBuilder("?");
            Iterator iterator = hashParam.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry entry = (Map.Entry) iterator.next();
                String key = String.valueOf(entry.getKey());
                String val = String.valueOf(entry.getValue());
                buffer.append(key);
                buffer.append("=");
                buffer.append(val);
                if (iterator.hasNext()) {
                    buffer.append("&");
                }
            }
            url = url + buffer.toString();
        }
        urlConnection = (HttpURLConnection) new URL(url).openConnection();
        urlConnection.setRequestMethod("GET");
        initConnection();


    }

    /**
     * 提交表单*
     */
    private void initParamsPost() throws IOException {
        urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        StringBuilder buffer = new StringBuilder();
        Iterator iterator = hashParam.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();
            String key = String.valueOf(entry.getKey());
            String val = String.valueOf(entry.getValue());
            buffer.append(key);
            buffer.append("=");
            buffer.append(val);
            if (iterator.hasNext()) {
                buffer.append("&");
            }
        }
        urlConnection.getOutputStream().write(buffer.toString().getBytes());

    }

    private void initParamPostMulti() throws IOException {
        urlConnection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + BOUNDARY);

        /**text**/
        Iterator<Map.Entry<String, String>> iterator = hashParam.entrySet().iterator();
        StringBuilder buffer = new StringBuilder();
        while (iterator.hasNext()) {
            Map.Entry<String, String> entry = iterator.next();
            String key = String.valueOf(entry.getKey());
            String val = String.valueOf(entry.getValue());

            if (val == null || key == null) {
                continue;
            }
            buffer.append("\r\n").append("--").append(BOUNDARY).append("\r\n");
            buffer.append("Content-Disposition: form-data; name=\"" + key + "\"\r\n\r\n");
            buffer.append(val);


        }
        urlConnection.getOutputStream().write(buffer.toString().getBytes());
        /**图片**/
        if (imgs != null) {

            Iterator<Map.Entry<String, SoftReference<Bitmap>>> iteratorBitmap = imgs.entrySet().iterator();

            while (iteratorBitmap.hasNext()) {
                Map.Entry<String, SoftReference<Bitmap>> entry = iteratorBitmap.next();
                String key = String.valueOf(entry.getKey());
                Bitmap value = entry.getValue().get();

                if (key == null || value == null) {
                    continue;
                }

                String contentType = "image/png";
                StringBuilder strBuf = new StringBuilder();
                strBuf.append("\r\n").append("--").append(BOUNDARY).append("\r\n");
                strBuf.append("Content-Disposition: form-data; name=\"" + key + "\"; filename=\"" + key + ".png" + "\"\r\n");
                strBuf.append("Content-Type:" + contentType + "\r\n\r\n");

                urlConnection.getOutputStream().write(strBuf.toString().getBytes());

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                value.compress(Bitmap.CompressFormat.PNG, 100, baos);
                urlConnection.getOutputStream().write(baos.toByteArray());

            }
        }

        /**文件**/

        if (files!=null&&files.length != 0) {

            for (int i = 0; i < files.length; i++) {
                String contentType = "application/octet-stream";
                StringBuilder strBuf = new StringBuilder();
                strBuf.append("\r\n").append("--").append(BOUNDARY).append("\r\n");
                strBuf.append("Content-Disposition: form-data; name=\"" + files[i].getName() + "\"; filename=\"" + files[i].getName() + "\"\r\n");
                strBuf.append("Content-Type:" + contentType + "\r\n\r\n");

                urlConnection.getOutputStream().write(strBuf.toString().getBytes());

                DataInputStream in = new DataInputStream(
                        new FileInputStream(files[i]));
                int bytes;
                byte[] bufferOut = new byte[1024];
                while ((bytes = in.read(bufferOut)) != -1) {
                    urlConnection.getOutputStream().write(bufferOut, 0, bytes);
                }

                in.close();


            }
            byte[] endData = ("\r\n--" + BOUNDARY + "--\r\n").getBytes();
            urlConnection.getOutputStream().write(endData);
            urlConnection.getOutputStream().flush();
            urlConnection.getOutputStream().close();
        }


    }

    private void iniParamsGet() {
        if (hashParam != null) {
            StringBuilder buffer = new StringBuilder("?");
            Iterator iterator = hashParam.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry entry = (Map.Entry) iterator.next();
                String key = String.valueOf(entry.getKey());
                String val = String.valueOf(entry.getValue());
                buffer.append(key);
                buffer.append("=");
                buffer.append(val);
                if (iterator.hasNext()) {
                    buffer.append("&");
                }
            }
            url = url + buffer.toString();
        }
    }

    public enum HttpType {
        GET, POST
    }

}

