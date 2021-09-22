package com.okhome.connection.utils;

import android.view.View;

public interface RecyclerViewListener<T> {
    void onClick(View view, T data);
}
