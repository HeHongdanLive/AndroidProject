package com.example.camera;

import java.io.File;

import com.linj.album.view.AlbumViewPager;
import com.linj.album.view.MatrixImageView.OnSingleTapListener;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/** 
* @ClassName: AlbumItemAty 
* @Description:���ͼƬ��ͼActivity ����ͼƬ�༭����
* @author LinJ
* @date 2015-1-12 ����5:18:25 
*  
*/
public class AlbumItemAty extends Activity implements OnClickListener,OnSingleTapListener{
	public final static String TAG="AlbumDetailAty";
	private String mSaveRoot;
	private AlbumViewPager mViewPager;//��ʾ��ͼ
	private ImageView mBackView;
	private ImageView mCameraView;
	private TextView mCountView;
	private View mHeaderBar,mBottomBar;
	private Button mDeleteButton;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.albumitem);

		mViewPager=(AlbumViewPager)findViewById(R.id.albumviewpager);
		mBackView=(ImageView)findViewById(R.id.header_bar_photo_back);
		mCameraView=(ImageView)findViewById(R.id.header_bar_photo_to_camera);
		mCountView=(TextView)findViewById(R.id.header_bar_photo_count);
		mHeaderBar=findViewById(R.id.album_item_header_bar);
		mBottomBar=findViewById(R.id.album_item_bottom_bar);
		mDeleteButton=(Button)findViewById(R.id.delete);
		
		mBackView.setOnClickListener(this);
		mCameraView.setOnClickListener(this);
		mCountView.setOnClickListener(this);
		mDeleteButton.setOnClickListener(this);
		
		mSaveRoot="test";
		mViewPager.setOnPageChangeListener(pageChangeListener);
		mViewPager.setOnSingleTapListener(this);
		String currentFileName=getIntent().getExtras().getString("path");
		if(currentFileName!=null){
			File file=new File(currentFileName);
			currentFileName=file.getName();
		}
			
		//��������ͼƬ���ز���
		mViewPager.loadAlbum(mSaveRoot,currentFileName,mCountView);
	}

	private OnPageChangeListener pageChangeListener=new OnPageChangeListener() {

		@Override
		public void onPageSelected(int position) {
			if(mViewPager.getAdapter()!=null){
				String text=(position+1)+"/"+mViewPager.getAdapter().getCount();
				mCountView.setText(text);
			}else {
				mCountView.setText("0/0");
			}
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onPageScrollStateChanged(int arg0) {
			// TODO Auto-generated method stub

		}
	};

	@Override
	public void onSingleTap() {
		if(mHeaderBar.getVisibility()==View.VISIBLE){
			AlphaAnimation animation=new AlphaAnimation(1, 0);
			animation.setDuration(300);
			mHeaderBar.startAnimation(animation);
			mBottomBar.startAnimation(animation);
			mHeaderBar.setVisibility(View.GONE);
			mBottomBar.setVisibility(View.GONE);
		}else {
			AlphaAnimation animation=new AlphaAnimation(0, 1);
			animation.setDuration(300);
			mHeaderBar.startAnimation(animation);
			mBottomBar.startAnimation(animation);
			mHeaderBar.setVisibility(View.VISIBLE);
			mBottomBar.setVisibility(View.VISIBLE);
		}	
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.header_bar_photo_back:
			startActivity(new Intent(this,AlbumAty.class));
			break;
		case R.id.header_bar_photo_to_camera:
			startActivity(new Intent(this,CameraAty.class));
			break;
		case R.id.delete:
			String result=mViewPager.deleteCurrentPath();
			if(result!=null)
				mCountView.setText(result);
			break;
		default:
			break;
		}
	}

	
}