package com.example.musicandroid;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import com.example.musicandroid.base.BaseActivity;
import com.example.musicandroid.base.BasePresenter;
import com.example.musicandroid.base.BaseView;
import com.example.musicandroid.myhome.MyHomePresenter;
import com.example.musicandroid.myhome.list.MyhomeListActivity;
import com.example.musicandroid.nav.LoginActivity;
import com.example.musicandroid.play.PlayActivity;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends BaseActivity implements BaseView {

    public static final int TAKE_PHOTO = 1;
    public static final int CHOOSE_PHOTO = 2;

    private MyHomePresenter myHomePresenter;

    private ViewAdapter adapter;
    private ViewPager viewPager;

    private DrawerLayout drawerLayout;
    private Uri imageUri;
    private CircleImageView avatar;


    //全局歌名，歌手，是否正在播放，已播放的时长，未播放时长，
    public static String globalSongName, globalSinger,  globalPlayTime, globalAllplayTime;
    public static boolean globalIsplaying = false;
    public static int globalProgress , globalCurrentPosition,globalAllProgress ;
    public static ImageView globalAvatar;

    //public String globalSinger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

        TabLayout tabLayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.Vp_findFragment);
        //初始化适配器
        adapter = new ViewAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        //给viewPaper设置事件监听
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            //页面滚动事件
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            //页面选择事件
            @Override
            public void onPageSelected(int position) {
                //设置对应集合中的Fragment
                viewPager.setCurrentItem(position);
            }

            //页面滚动状态改变事件
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        //调用setupWithViewPager()方法，则使用TabLayout.addtab()方法无效，TabLayout会清除之前添加的所有tabs，
        // 并将根据Viewpager的页数添加Tab，Tab标题为对应页通过getPageTitle()返回的文本
        tabLayout.setupWithViewPager(viewPager);

    }

    @Override
    public BasePresenter createPresenter() {
        return new MyHomePresenter();
    }

    @Override
    public BaseView createView() {
        return this;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar,menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        Intent intent;

        switch (item.getItemId()){
            case R.id.search_home:{
                Toast.makeText(this,"查找",Toast.LENGTH_SHORT).show();
                //跳转到“查找”
                intent=new Intent(MainActivity.this, SearchActivity.class);
                startActivity(intent);
                break;
            }

            case android.R.id.home:{
                drawerLayout.openDrawer(GravityCompat.START);
                break;
            }
            default:

        }

        return true;
    }


    public void showPopMenu(View view){
        PopupMenu popupMenu = new PopupMenu(this,view);
        popupMenu.getMenuInflater().inflate(R.menu.avatar_menu,popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.avatar_take_photo:
                        takePhoto();
                        break;

                    case R.id.avatar_from_album:
                        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                        } else {
                            openAlbum();
                        }

                        break;
                }

                return false;
            }
        });

        //关闭删除菜单
        popupMenu.setOnDismissListener(new PopupMenu.OnDismissListener() {
            @Override
            public void onDismiss(PopupMenu menu) {
                //Toast.makeText(getApplicationContext(), "删除成功", Toast.LENGTH_SHORT).show();
            }
        });
        popupMenu.show();
    }


    //拍照
    public void takePhoto(){
        File image = new File(getExternalCacheDir(),"avatar");

        try {
            if (image.exists()){
                image.delete();
            }

            image.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (Build.VERSION.SDK_INT >= 24){
            imageUri = FileProvider.getUriForFile(MainActivity.this,
                    getPackageName() + ".fileprovider",image);
        }else
            imageUri = Uri.fromFile(image);

        if (ContextCompat.checkSelfPermission(MainActivity.this,
                android.Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
            //先判断有没有权限 ，没有就在这里进行权限的申请
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{android.Manifest.permission.CAMERA},1);

        }else {
            //说明已经获取到摄像头权限了 想干嘛干嘛
            Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            startActivityForResult(intent, TAKE_PHOTO);

            Log.d("nnn", "  name=  "+ getPackageName() + ".fileprovider");
        }

    }


    private void openAlbum() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent, CHOOSE_PHOTO);
    }


    /*在这里设置拍照的头像avatar*/
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case TAKE_PHOTO:
                if (resultCode == RESULT_OK) {
                    try {
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                        avatar.setImageBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(this, "未拍照", Toast.LENGTH_SHORT).show();
                }
                break;
            case CHOOSE_PHOTO:
                if (resultCode == RESULT_OK) {
                    if (Build.VERSION.SDK_INT >= 19) {
                        handleImageOnKitkat(data);
                    } else {
                        handleImageBeforeKitkat(data);
                    }
                } else {
                    Toast.makeText(this, "未选择图片", Toast.LENGTH_SHORT).show();
                }


            default:
                break;

        }
    }


    private void handleImageBeforeKitkat(Intent data) {
        Uri uri = data.getData();
        String imagePath = getImagePath(uri, null);
        displayImage(imagePath);
    }

    @TargetApi(19)
    private void handleImageOnKitkat(Intent data) {
        String imagePath = null;
        Uri uri = data.getData();
        if (DocumentsContract.isDocumentUri(this, uri)) {
            String docId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docId.split(":")[1];
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                imagePath = getImagePath(contentUri, null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            imagePath = getImagePath(uri, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            imagePath = uri.getPath();
        }
        displayImage(imagePath);
    }

    /*在这里设置从相册选择的图片*/
    private void displayImage(String imagePath) {
        if (imagePath != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            avatar.setImageBitmap(bitmap);
        } else {
            Toast.makeText(this, "图片加载错误！", Toast.LENGTH_SHORT).show();
        }
    }

    private String getImagePath(Uri externalContentUri, String selection) {
        String path = null;
        Cursor cursor = getContentResolver().query(externalContentUri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openAlbum();
                } else {
                    Toast.makeText(this, "申请权限被拒绝", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }



/*
 *初始化toolbar 和 滑动菜单
 *
 */
    @SuppressLint("ClickableViewAccessibility")
    public void initView() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        }
        drawerLayout = findViewById(R.id.base_drawer_layout);

        NavigationView navigationView = findViewById(R.id.my_nav_view);
        navigationView.setCheckedItem(R.id.login_nav);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                drawerLayout.closeDrawers();

                switch (menuItem.getItemId()){

                    case R.id.login_nav:
                        Intent intent=new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(intent);

                        break;
                    case R.id.me_nav:

                        break;
                    case R.id.friends_nav:

                        break;
                    case R.id.skin_nav:

                        break;
                    case R.id.setting_nav:

                        break;
                    case R.id.exit_nav:

                        break;


                }

                return true;
            }
        });



        //设置头像  拍照/相册选择
        View headerLayout = navigationView.getHeaderView(0);
        avatar = headerLayout.findViewById(R.id.avatar);
        avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopMenu(view);//弹出菜单  选择拍照/从相册选择照片当头像
            }
        });



        RelativeLayout relativeLayout = findViewById(R.id.bottom_home);

        CircleImageView play = findViewById(R.id.bottom_play);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("kkk", "  000 play");
            }
        });


        relativeLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        Intent intent = new Intent(MainActivity.this, PlayActivity.class);
                        startActivity(intent);
                        Log.d("kkk", "  000 R.id.bottom_home");
                        break;
                    case MotionEvent.ACTION_UP:
                        break;
                    case MotionEvent.ACTION_MOVE:
                        break;
                }
                return true;
            }
        });

    }



}
