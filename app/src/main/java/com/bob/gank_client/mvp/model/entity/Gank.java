package com.bob.gank_client.mvp.model.entity;

import java.util.Date;

/**
 * Created by bob on 17-5-1.
 * 每种类型的干货数据的model(除妹子图)
 */

public class Gank extends BaseModel {
        public boolean used;
        public String type;
        public String url;
        public String who;
        public String desc;
        public Date updatedAt;
        public Date createdAt;
        public Date publishedAt;

        @Override
        public String toString() {
                return "Gank{" +
                        "used=" + used +
                        ", type='" + type + '\'' +
                        ", url='" + url + '\'' +
                        ", who='" + who + '\'' +
                        ", desc='" + desc + '\'' +
                        ", updatedAt=" + updatedAt +
                        ", createdAt=" + createdAt +
                        ", publishedAt=" + publishedAt +
                        '}';
        }
}
