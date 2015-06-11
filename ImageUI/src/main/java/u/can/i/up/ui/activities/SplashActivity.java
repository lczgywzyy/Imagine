package u.can.i.up.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import u.can.i.up.ui.R;


public class SplashActivity extends Activity {
    // Splash screen timer
    String now_playing, earned;
    private static int SPLASH_TIME_OUT = 1000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                Intent i = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(i);

                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);

//        /**
//         * Showing splashscreen while making network calls to download necessary
//         * data before launching the app Will use AsyncTask to make http call
//         */
//        new PrefetchData().execute();
    }


//    /**
//     * Async Task to make http call
//     */
//    private class PrefetchData extends AsyncTask<Void, Void, Void> {
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            // before making http calls
//
//        }
//
//        @Override
//        protected Void doInBackground(Void... arg0) {
//            /*
//             * Will make http call here This call will download required data
//             * before launching the app
//             * example:
//             * 1. Downloading and storing in SQLite
//             * 2. Downloading images
//             * 3. Fetching and parsing the xml / json
//             * 4. Sending device information to server
//             * 5. etc.,
//             */
//
//            JsonParser jsonParser = new JsonParser();
//            String json = jsonParser
//                    .getJSONFromUrl("http://api.androidhive.info/game/game_stats.json");
//
//            Log.e("Response: ", "> " + json);
//
//            if (json != null) {
//                try {
//                    JSONObject jObj = new JSONObject(json)
//                            .getJSONObject("game_stat");
//                    now_playing = jObj.getString("now_playing");
//                    earned = jObj.getString("earned");
//
//                    Log.e("JSON", "> " + now_playing + earned);
//
//                } catch (JSONException e) {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace();
//                }
//
//            }
//
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Void result) {
//            super.onPostExecute(result);
//            // After completing http call
//            // will close this activity and lauch main activity
//            Intent i = new Intent(SplashActivity.this, MainActivity.class);
//            i.putExtra("now_playing", now_playing);
//            i.putExtra("earned", earned);
//            startActivity(i);
//
//            // close this activity
//            finish();
//        }
//
//    }

}
