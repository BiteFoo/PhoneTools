package com.collection.tools.entity;

/**
 * Created by John.Lu on 2017/6/14.
 *
 * 任务对象消息，
 * 提示信息，哪种行为动作
 */

public class TaskEntity {
    private String msg;//提示信息
    private int type;//什么样的行为动作

    public TaskEntity(String text,int action)
    {
        msg =text;
        type = action;
    }

    public String getMsg() {
        return msg;
    }

    public int getType() {
        return type;
    }
}
