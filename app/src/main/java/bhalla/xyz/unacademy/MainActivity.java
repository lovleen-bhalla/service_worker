package bhalla.xyz.unacademy;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    public static final String IMAGE_1 = "https://raw.githubusercontent.com/lovleen-bhalla/service_worker/master/image_one.jpeg";
    public static final String IMAGE_2 = "https://raw.githubusercontent.com/lovleen-bhalla/service_worker/master/image_two.jpeg";

    Button btnOne;
    Button btnTwo;
    ImageView imageView1;
    ImageView imageView2;

    ServiceWorker serviceWorker1 = new ServiceWorker("service_worker_1");
    ServiceWorker serviceWorker2 = new ServiceWorker("service_worker_2");

    OkHttpClient okHttpClient = new OkHttpClient().newBuilder().build();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnOne = findViewById(R.id.btn_one);
        btnOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchImage1AndSet();
            }
        });
        btnTwo = findViewById(R.id.btn_two);
        btnTwo.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                fetchImage2AndSet();
            }
        });
        btnTwo = findViewById(R.id.btn_two);
        imageView1 = findViewById(R.id.image_view_one);
        imageView2 = findViewById(R.id.image_view_two);

    }

    @Override
    protected void onDestroy() {
        serviceWorker1.destroy();
        serviceWorker2.destroy();
        super.onDestroy();
    }

    private void fetchImage1AndSet() {
        serviceWorker1.addTask(new Task<Bitmap>() {
            @Override
            public Bitmap onExecuteTask() {
                //Fetching image1 through okhttp
                Request request = new Request.Builder().url(IMAGE_1).build();
                Response response = null;
                try {
                    response = okHttpClient.newCall(request).execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                final Bitmap bitmap = BitmapFactory.decodeStream(response.body().byteStream());
                return bitmap;
            }

            @Override
            public void onTaskComplete(Bitmap result) {
                //Set bitmap to imageview
                imageView1.setImageBitmap(result);
            }
        });
    }

    private void fetchImage2AndSet() {
        serviceWorker2.addTask(new Task<Bitmap>() {
            @Override
            public Bitmap onExecuteTask() {
                //Fetching image1 through okhttp
                Request request = new Request.Builder().url(IMAGE_2).build();
                Response response = null;
                try {
                    response = okHttpClient.newCall(request).execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                final Bitmap bitmap = BitmapFactory.decodeStream(response.body().byteStream());
                return bitmap;
            }

            @Override
            public void onTaskComplete(Bitmap result) {
                imageView2.setImageBitmap(result);
            }
        });
    }
}

