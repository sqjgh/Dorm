package com.example.dllo.dorm.left;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.dllo.dorm.MyUser;
import com.example.dllo.dorm.R;
import com.example.dllo.dorm.base.BaseActivity;
import com.example.dllo.dorm.base.Values;
import com.example.dllo.dorm.tools.circleimage.CircleImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;

/**
 * Created by SongQingJun on 16/12/7.
 */
public class IDSettingActivity extends BaseActivity implements View.OnClickListener {

    private CircleImageView icon;
    private EditText nickname;
    private Button btnIconPhone;
    private Button btnIconPhoto;
    private Button save;
    //拍照时的图片存储路径
    private static final String iconPath = Environment.getExternalStorageDirectory() + "/Image";//图片的存储目录
    private Bitmap bitmap;


    @Override
    protected int getLayout() {
        return R.layout.activity_setup_id;
    }

    @Override
    protected void initViews() {
        icon = bindView(R.id.setup_id_icon);
        nickname = bindView(R.id.setup_id_icon_nickname);
        btnIconPhone = bindView(R.id.setup_id_icon_phone);
        btnIconPhoto = bindView(R.id.setup_id_icon_photo);
        save = bindView(R.id.setup_id_icon_save);
        setClick(this, btnIconPhone, btnIconPhoto, save);
    }

    @Override
    protected void initData() {
        if (!Values.OBJECT_ID.equals("")){
            // 进来显示昵称
            initNickname();
        }
        Glide.with(this).load(Values.ICON_URL).into(icon);
    }

    private void initNickname() {
        BmobQuery<MyUser> query = new BmobQuery<MyUser>();
        query.getObject(Values.OBJECT_ID, new QueryListener<MyUser>() {

            @Override
            public void done(MyUser object, BmobException e) {
                if(e==null){
                    //获得昵称
                    object.getNickname();
                }else{
                    Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                }
            }

        });
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.setup_id_icon_phone:
                // 相册选择
                selectPic(v);
                break;
            case R.id.setup_id_icon_photo:
                // 拍照
                takePhoto(v);
                break;
            case R.id.setup_id_icon_save:
                // 保存
                if (bitmap != null){
                    UpLoadIcon();
                }
                String nicknameET = nickname.getText().toString();
                if (nicknameET != null){
                    nicknameUpDate(nicknameET);
                }
                break;
        }
    }

    private void nicknameUpDate(String str) {
        MyUser user = new MyUser();
        user.setNickname(str);
        user.update(Values.OBJECT_ID, new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if(e==null){
                    Log.i("bmob","更新成功");
                }else{
                    Log.i("bmob","更新失败："+e.getMessage()+","+e.getErrorCode());
                }
            }
        });



    }


    /**
     * 打开系统相册，并选择图片
     */
    public void selectPic(View view) {
        Intent intent = new Intent("android.intent.action.PICK");
        intent.setType("image/*");
        startActivityForResult(intent, 0);
    }

    /**
     * 选择拍照的图片
     */
    public void takePhoto(View view) {
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        startActivityForResult(intent, 1);
    }

    /**
     * 给拍的照片命名
     */
    public String createPhotoName() {
        //以系统的当前时间给图片命名
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        String fileName = format.format(date) + ".jpg";


        return fileName;
    }

    /**
     * 把拍的照片保存到SD卡
     */
    public void saveToSDCard(Bitmap bitmap) {
        //先要判断SD卡是否存在并且挂载
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File file = new File(iconPath);
            if (!file.exists()) {
                file.mkdirs();
            }
            FileOutputStream outputStream = null;
            try {
                outputStream = new FileOutputStream(createPhotoName());
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);//把图片数据写入文件
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } finally {
                if (outputStream != null) {
                    try {
                        outputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } else {
            Toast.makeText(this, "SD卡不存在", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 获取选择的图片
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {
            return;//当data为空的时候，不做任何处理
        }
        bitmap = null;
        if (requestCode == 0) {
            //获取从相册界面返回的缩略图
            bitmap = data.getParcelableExtra("data");
            if (bitmap == null) {//如果返回的图片不够大，就不会执行缩略图的代码，因此需要判断是否为null,如果是小图，直接显示原图即可
                try {
                    //通过URI得到输入流
                    InputStream inputStream = getContentResolver().openInputStream(data.getData());
                    //通过输入流得到bitmap对象
                    bitmap = BitmapFactory.decodeStream(inputStream);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        } else if (requestCode == 1) {
            bitmap = (Bitmap) data.getExtras().get("data");
            saveToSDCard(bitmap);
        }
        //将选择的图片设置到控件上
        icon.setImageBitmap(bitmap);
    }

/******************************************************/

    //上传头像
    private void UpLoadIcon() {
        final MyUser myUser = MyUser.getCurrentUser(MyUser.class); // 注意填 MyUser.class
        if (myUser == null) {
            Toast.makeText(this, "先登录", Toast.LENGTH_SHORT).show();
        } else { // 已经登录, 上传头像
            // 1拿到图片的mipmap
//            Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher);
            // 2存到硬盘里 getCacheDir() 是Android提供的缓存路径
            // 位置是包名/cache
            // 该方法是Context的方法, 可以使用Application的Context
            File cacheDir = getCacheDir();
            if (!cacheDir.exists()) {
                cacheDir.mkdir(); // 就创建这个文件夹
            }
            // 参数一路径 参数二文件名
            long time = System.currentTimeMillis(); // 文件名加上时间 为了防止文件名重复
            File iconFile = new File(cacheDir, myUser.getUsername() + time + ".png");
            if (!iconFile.exists()) {
                // 如果文件不存在 就创建一个新文件
                try {
                    iconFile.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            try {
                // 创建一个文件输出流
                FileOutputStream fileOutputStream = new FileOutputStream(iconFile);

                bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
                fileOutputStream.close();
                // 图片就存到了File里面
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            // 上传File
            final BmobFile bmobFile = new BmobFile(iconFile); // TODO iconFile
            // 上传
            bmobFile.uploadblock(new UploadFileListener() {
                @Override
                public void done(BmobException e) {
                    if (e == null) {
//                        Toast.makeText(MainActivity.this, "上传成功", Toast.LENGTH_SHORT).show();
                        // 拿到图片的url
                        String fileUrl = bmobFile.getFileUrl();
                        Log.d("MainActivity", fileUrl);
                        // 把图片的url存储到用户的表里
                        myUser.setIcon(fileUrl);
                        myUser.update(new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if (e == null) {
//                                    Toast.makeText(MainActivity.this, "存储url成功", Toast.LENGTH_SHORT).show();
                                } else {
//                                    Toast.makeText(MainActivity.this, "存储url失败", Toast.LENGTH_SHORT).show();
                                    Log.d("MainActivity", e.getMessage());
                                }
                            }
                        });
                    } else {
//                        Toast.makeText(MainActivity.this, "上传失败", Toast.LENGTH_SHORT).show();
                        Log.d("MainActivity", e.getMessage());
                    }

                }
            });
        }
    }
}
