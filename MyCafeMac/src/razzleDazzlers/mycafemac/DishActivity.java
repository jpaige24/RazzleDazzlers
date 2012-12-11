package razzleDazzlers.mycafemac;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;

import com.sun.jndi.toolkit.url.Uri;

import razzleDazzlers.ratecafemac.R;
import razzleDazzlers.util.DishArrayAdapter;
import razzleDazzlers.util.Server;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.view.ViewPager.LayoutParams;
import android.text.format.Time;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

public class DishActivity extends Activity implements OnClickListener {

	private static final int REQUEST_PICK_FILE = 1;
	private static final int TAKE_A_PICTURE = 2;
	private TextView mFilePathTextView;
	private Button mStartActivityButton;

	String name;
	String date;
	String device;
	float r;
	private String filePath = "";
	android.net.Uri imageUri;
	Bitmap bm1;
	Bitmap bm2;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dish);

		mStartActivityButton = (Button) findViewById(R.id.button1);
		mStartActivityButton.setOnClickListener(this);
		mFilePathTextView = (TextView) findViewById(R.id.filepath);
		Button submitButton = (Button) findViewById(R.id.button2);
		submitButton.setOnClickListener(this);
		Button takePicture = (Button) findViewById(R.id.button3);
		takePicture.setOnClickListener(this);

		date = getIntent().getStringExtra("date");
		device = getIntent().getStringExtra("device");

		String dishName = getIntent().getStringExtra("dishName");
		this.name = dishName;
		String dishDescription = getIntent().getStringExtra("dishDescription");
		float rating = getIntent().getFloatExtra("dishRating", 0);
		//float ratingblue = getIntent().getFloatExtra("avgRating", 0);
		//RatingBar ratingBarblue = (RatingBar) findViewById(R.id.DishInfo_ratingBarblue);
		final RatingBar ratingBar = (RatingBar) findViewById(R.id.DishInfo_ratingBar);
		ratingBar.setRating(rating);
		
		/* if (rating < 1) {
			ratingBarblue.setRating(ratingblue);
			ratingBar.setVisibility(View.GONE);
		} else {
			ratingBar.setRating(rating);
			ratingBarblue.setVisibility(View.GONE);
		} */
		
		RatingBar miniBar = (RatingBar) findViewById(R.id.DishInfo_miniBar);
        float miniRating = getIntent().getFloatExtra("avgRating", 0);
        miniBar.setRating(miniRating);

		TextView dishNameView = (TextView) findViewById(R.id.DishInfo_name);
		dishNameView.setText(dishName);

		TextView dishDescriptionView = (TextView) findViewById(R.id.DishInfo_description);
		dishDescriptionView.setText(dishDescription);

		RetrievePhoto rp = new RetrievePhoto();
		rp.execute();

/*		ratingBarblue.setOnTouchListener(new OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				int action = event.getAction() & MotionEvent.ACTION_MASK;
				if (action == MotionEvent.ACTION_DOWN || action == MotionEvent.ACTION_UP) {
					float x = event.getX();
					float y = event.getY();
					System.out.println("****" + x + "****" + y);
					float star = (float) 0.0;
					if (x < 56)
						star = (float) 1.0;
					if (x > 69 && x < 122)
						star = (float) 2.0;
					if (x > 134 && x < 188)
						star = (float) 3.0;
					if (x > 199 && x < 253)
						star = (float) 4.0;
					if (x > 264)
						star = (float) 5.0;
					if (star > 0) {
						v.setVisibility(View.GONE);
						ratingBar.setVisibility(View.VISIBLE);
						ratingBar.setRating(star);
						// System.out.println(((RatingBar) v).getRating());
						TextView text = (TextView) ((View) v.getParent())
								.findViewById(R.id.DishInfo_name);
						// System.out.println(text.getText().toString());
						name = text.getText().toString();
						r = ratingBar.getRating();
						Thread t = new Thread() {
							public void run() {
								submitRating(name, device, r, date,
										DishActivity.this);
							}
						};
						t.start();
					}
				}
				return false;
			}
		});
		ratingBarblue.setOnRatingBarChangeListener(new RatingBarListener(name, device));
*/

		ratingBar.setOnTouchListener(new OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				int action = event.getAction() & MotionEvent.ACTION_MASK;
				if (action == MotionEvent.ACTION_DOWN || action == MotionEvent.ACTION_UP) {
					float x = event.getX();
					float y = event.getY();
					System.out.println("****" + x + "****" + y);
					float star = (float) 0.0;
					if (x < 56)
						star = (float) 1.0;
					if (x > 69 && x < 122)
						star = (float) 2.0;
					if (x > 134 && x < 188)
						star = (float) 3.0;
					if (x > 199 && x < 253)
						star = (float) 4.0;
					if (x > 264)
						star = (float) 5.0;
					if (star > 0) {
						((RatingBar) v).setRating(star);
						// System.out.println(((RatingBar) v).getRating());
						TextView text = (TextView) ((View) v.getParent())
								.findViewById(R.id.DishInfo_name);
						// System.out.println(text.getText().toString());
						name = text.getText().toString();
						r = ((RatingBar) v).getRating();
						Thread t = new Thread() {
							public void run() {
								submitRating(name, device, r, date,
										DishActivity.this);
							}
						};
						t.start();
					}
				}
				return false;
			}
		});
		ratingBar.setOnRatingBarChangeListener(new RatingBarListener(name, device));
	}

	private class RetrievePhoto extends AsyncTask<String, Void, String> {

		ArrayList photos = new ArrayList();
		ImageView image1 = new ImageView(DishActivity.this);
		ImageView image2 = new ImageView(DishActivity.this);

		@Override
		protected void onPreExecute() {
			LinearLayout linearLayout = (LinearLayout)findViewById(R.id.dishLayout);
			//image1.setImageResource(R.drawable.loading);
			LinearLayout.LayoutParams vp = 
				    new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			image1.setLayoutParams(vp); 
			image1.setAdjustViewBounds(true);
			image1.setScaleType(ScaleType.CENTER_INSIDE);
			image1.setPadding(10, 10, 0, 10);
			image1.setMaxHeight(500);
			linearLayout.addView(image1);
			//image2.setImageResource(R.drawable.loading);
			image2.setLayoutParams(vp); 
			image2.setAdjustViewBounds(true);
			image2.setScaleType(ScaleType.CENTER_INSIDE);
			image2.setPadding(10, 10, 0, 10);
			image2.setMaxHeight(500);
			linearLayout.addView(image2);
		}

		@Override
		protected String doInBackground(String... arg0) {
			Server serv = new Server(DishActivity.this);
			photos = serv.retrievePhoto(name);
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			if (photos.size() > 0) {
				bm1 = BitmapFactory.decodeStream((InputStream) photos
						.get(0));
				/*ImageView iv1 = (ImageView) findViewById(R.id.photo1);
				iv1.setScaleType(ScaleType.CENTER_INSIDE);
				iv1.setPadding(10, 10, 0, 0);
				iv1.setImageBitmap(bm1);*/
				image1.setImageBitmap(bm1);
				if (photos.size() > 1) {
					bm2 = BitmapFactory
							.decodeStream((InputStream) photos.get(1));
					/*ImageView iv2 = (ImageView) findViewById(R.id.photo2);
					iv2.setScaleType(ScaleType.CENTER_INSIDE);
					iv2.setPadding(10, 10, 0, 0);
					iv2.setImageBitmap(bm2);*/
					image2.setImageBitmap(bm2);
				}
			}
		}

	}

	public static void submitRating(String dishName, String deviceID,
			float rating, String date, Context context) {

		Server serv = new Server(context);
		if (serv.check(deviceID, date, dishName)) {
			serv.update(deviceID, date, dishName, rating);
		} else {
			serv.insert(deviceID, date, dishName, rating);
		}
		System.out.println("rating--->" + rating);
	}

	@Override
	protected void onPause() {
		super.onPause();
		Toast.makeText(this, "Refreshing CafeMac ratings...",
				Toast.LENGTH_SHORT).show();
	}

	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.button1:
			// Create a new Intent for the file picker activity
			Intent intent = new Intent(this, FilePickerActivity.class);

			// Set the initial directory to be the sdcard
			// intent.putExtra(FilePickerActivity.EXTRA_FILE_PATH,
			// Environment.getExternalStorageDirectory());

			// Show hidden files
			// intent.putExtra(FilePickerActivity.EXTRA_SHOW_HIDDEN_FILES,
			// true);

			// Only make .png files visible
			ArrayList<String> extensions = new ArrayList<String>();
			extensions.add(".png");
			extensions.add(".jpg");
			extensions.add(".bmp");
			intent.putExtra(FilePickerActivity.EXTRA_ACCEPTED_FILE_EXTENSIONS,
					extensions);

			// Start the activity
			startActivityForResult(intent, REQUEST_PICK_FILE);
			break;
		case R.id.button2:
			System.out.println("submit button");
			if (filePath.length() > 1) {
				SubmitPhoto sp = new SubmitPhoto();
				sp.execute();
			}
			break;
		case R.id.button3:
			Boolean isSDPresent = android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
			if(isSDPresent){
				String fileName = "new-photo-name.jpg";
				ContentValues values = new ContentValues();
				values.put(MediaStore.Images.Media.TITLE, fileName);
				values.put(MediaStore.Images.Media.DESCRIPTION,
						"Image capture by camera");
				imageUri = getContentResolver().insert(
						MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
				Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				takePicture.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
				startActivityForResult(takePicture, TAKE_A_PICTURE);
			}else{
				Toast.makeText(DishActivity.this,
						"Please Insert an SD-Card.", Toast.LENGTH_SHORT)
						.show();	
			}
			break;
		}
	}

	private class SubmitPhoto extends AsyncTask<String, Void, String> {

		Boolean submitPhoto = false;

		@Override
		protected void onPreExecute() {
		}

		@Override
		protected String doInBackground(String... arg0) {
			if (submitPhoto(date, name, filePath, DishActivity.this)) {
				submitPhoto = true;
				System.out.println("boolean" + submitPhoto);
			}
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			if (submitPhoto) {
				Toast.makeText(DishActivity.this,
						"Your photo has been submitted", Toast.LENGTH_SHORT)
						.show();
			} else {
				Toast.makeText(
						DishActivity.this,
						"Sorry, submission failed: poor connection/file size too big.",
						Toast.LENGTH_SHORT).show();
			}
		}

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
			switch (requestCode) {
			case REQUEST_PICK_FILE:
				if (data.hasExtra(FilePickerActivity.EXTRA_FILE_PATH)) {
					// Get the file path
					File f = new File(
							data.getStringExtra(FilePickerActivity.EXTRA_FILE_PATH));

					// Set the file path text view
					mFilePathTextView.setText("File path£º " + f.getPath());

					filePath = f.getPath();
				}
				break;
			case TAKE_A_PICTURE:
				takePicture tp = new takePicture();
				tp.execute();
				break;
			}
		}
	}
	
	private class takePicture extends AsyncTask<String, Void, String> {
		
		File f;

		@Override
		protected String doInBackground(String... arg0) {
			f = convertImageUriToFile(imageUri, DishActivity.this);
			return null;
		}
		
		@Override
		protected void onPostExecute(String result) {
			mFilePathTextView.setText("File path£º " + f.getPath());
			filePath = f.getPath();
		}

	}

	public static boolean submitPhoto(String date, String dishName,
			String filePath, Context context) {

		Server serv = new Server(context);
		if (serv.submitPhoto(date, dishName, filePath)) {
			System.out.println("*Photo submitted");
			return true;
		} else {
			return false;
		}
	}

	public static File convertImageUriToFile(android.net.Uri imageUri, Activity activity) {
		Cursor cursor = null;
		try {
			String[] proj = { MediaStore.Images.Media.DATA,
					MediaStore.Images.Media._ID,
					MediaStore.Images.ImageColumns.ORIENTATION };
			cursor = activity.managedQuery(imageUri, proj, // Which columns to
															// return
					null, // WHERE clause; which rows to return (all rows)
					null, // WHERE clause selection arguments (none)
					null); // Order-by clause (ascending by name)
			int file_ColumnIndex = cursor
					.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
			int orientation_ColumnIndex = cursor
					.getColumnIndexOrThrow(MediaStore.Images.ImageColumns.ORIENTATION);
			if (cursor.moveToFirst()) {
				String orientation = cursor.getString(orientation_ColumnIndex);
				return new File(cursor.getString(file_ColumnIndex));
			}
			return null;
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}
	}

	private class RatingBarListener implements RatingBar.OnRatingBarChangeListener{
		
		public String dishName = "";
		public String deviceID = "";
		
		public RatingBarListener(String dishName, String device){
			this.dishName = dishName;
			this.deviceID = device;
		}
		  
        public void onRatingChanged(RatingBar ratingBar, float rating,  
                boolean fromUser) {
        	
        	Time today = new Time(Time.getCurrentTimezone());
        	today.setToNow();
        	final String date = Integer.toString(today.month) + Integer.toString(today.monthDay) + Integer.toString(today.year);
        	
            ratingBar.setRating(rating);
    		Thread t = new Thread(){
    			public void run(){
    				submitRating(dishName, device, r, date, DishActivity.this);
    			}
    		};
    		t.start();
        }  
    }
	
	public void onDestroy() {
	    super.onDestroy();
	    if (bm1 != null) bm1.recycle();
	    if (bm2 != null) bm2.recycle();
	}
}
