package com.quange.model;

/**
 * 糗百数据模型
 * 
 * @author 泉哥
 * 
 *	"format": "image",
    "image": "app115966071.jpg",
    "published_at": 1460617202,
    "tag": "",
    "user": {
        "avatar_updated_at": 1460049671,
        "uid": 31513072,
        "last_visited_at": 1459389574,
        "created_at": 1459389574,
        "state": "active",
        "last_device": "ios_9.3.1",
        "role": "n",
        "login": "吃鸡翅嘛",
        "id": 31513072,
        "icon": "20160407172111.jpg"
    },
    "image_size": {
        "s": [
            220,
            293,
            11878
        ],
        "m": [
            500,
            666,
            48097
        ]
    },
    "id": 115966071,
    "votes": {
        "down": -12,
        "up": 95
    },
    "created_at": 1460612510,
    "content": "再见了，我的青春",
    "state": "publish",
    "comments_count": 3,
    "allow_comment": true,
    "share_count": 0,
    "type": "fresh"
 */
public class GQiuBaiModel {
	public int id;
	public String format;
	public String content;
	public String image;
	public String pic_url;
	public String high_url;
	public GQiuBaiUserModel user;
	public GQiuBaiImageSizeModel image_size;
	public static class GQiuBaiUserModel {
		public int id;
		public String login;
		public int uid;
		public String icon;
		
	}
	
	public static class GQiuBaiImageSizeModel {
		
		public int[] m ;
		public int[] s ;
	
		
	}
	
}
