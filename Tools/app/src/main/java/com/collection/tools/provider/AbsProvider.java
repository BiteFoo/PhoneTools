package com.collection.tools.provider;

import android.content.Context;

import java.util.List;

/**
 * Created by John.Lu on 2017/6/10.
 */

public interface  AbsProvider {

    void checkRollback(Context context);
    List<?> getList();

}
