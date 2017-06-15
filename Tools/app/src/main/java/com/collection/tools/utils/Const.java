package com.collection.tools.utils;

/**
 * Created by John.Lu on 2017/6/12.
 *
 * 失败码：
 * 1.INIT_FAILED：初始化资源失败，提示任务信息不正确，删除，
 * 2.unkwnon： 未知失败，包括： 网络关闭，不能用，提示打开网络重试；
 * 3.上传失败： 1.网络连接正常，传输失败，返回响应码 != 200，提示点击重新上传，在点击之后，设置标识符，表示这是第二次，无论如何都会删除应用
 *
 * 删除行为：删除本地缓存数据元，删除sharedpreference
 *
 */

public class Const {
    public  static final  int INIT=0x000;//初始化资源文件
    public  static  final  int UPLOAD=0x001;//开始收集数据并且上传
    public  static  final int   UNKWNON=0x002;//加密内容出错或者是TransManager为空的情况
    public  static  final  int  INIT_FAILED =0x003;//icon.png文件出错的情况
    public static final int AGAIN    =  0x005;//网络失败，提示用户重新开始后者是删除app
    public static  final  int UPLOAD_OK=0x010;//上传完成
    public static  final int UPLOAD_FAILED=0x11;//上传失败，重新尝试或者删除应用
    public static  final int INTERNET_FAILED    =0x110;//网络异常，提示设置网络，点击重新上传
    public  static final int UPDATE_UI=0x100;

}
