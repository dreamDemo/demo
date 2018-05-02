package com.meitu.netlib.constraintdemo.expand.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sunyuxin on 2018/4/15.
 */

public class PicModelList extends SimpleModel {
    public List<SimpleModel> picModelList = new ArrayList<>();

    public PicModelList() {
        type = TYPE_LIST;
    }

    public static class PicModel extends SimpleModel {
        public int resId;

        public PicModel () {
            type = TYPE_PIC;
        }

        public PicModel(int resId) {
            this.resId = resId;
        }
    }
}
