package com.bob.gank_client.mvp.model.entity;

import java.util.Date;

/**
 * Created by bob on 17-5-1.
 * 妹子图的model
 */

public class Meizi extends BaseModel { public boolean used;
        public String type;
        public String url;
        public String who;
        public String desc;
        public Date updatedAt;
        public Date createdAt;
        public Date publishedAt;

        @Override
        public String toString() {
                return "Meizi{" +
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
