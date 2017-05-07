package com.bob.gank_client.mvp.model;

import com.bob.gank_client.mvp.model.entity.Meizi;

import java.util.List;

/**
 * Created by bob on 17-5-1.
 * 妹子图界面返回的数据类型
 */

public class MeiziData extends BaseData {
        public List<Meizi> results;

        @Override
        public String toString() {
                return "MeiziData{" +
                        "results=" + results +
                        '}';
        }
}
