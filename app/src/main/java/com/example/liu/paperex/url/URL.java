package com.example.liu.paperex.url;

import java.util.Random;

/**
 * Created by liu on 16/8/10.
 */
public class URL {
    //最新
    public static final String MOSTNEW_URL = "http://bz.budejie.com/?typeid=2&ver=3.4.3&no_cry=1&client=android&c=wallPaper&a=wallPaperNew&index=1&size=30&bigid=0";
    //热门
    public static final String HOT_URL = "http://bz.budejie.com/?typeid=2&ver=3.4.3&no_cry=1&client=android&c=wallPaper&a=hotRecent&index=1&size=30&bigid=0";
    //随机
    public static final String RANDOM_URL = "http://bz.budejie.com/?typeid=2&ver=3.4.3&no_cry=1&client=android&c=wallPaper&a=random&bigid=0";
    //分类
    public static final String SORT_URL = "http://bz.budejie.com/?typeid=2&ver=3.4.3&no_cry=1&client=android&c=wallPaper&a=category";
    //搜索：热门搜索
    public static final String SEARCH_GV_URL = "http://bz.budejie.com/?typeid=2&ver=3.4.3&no_cry=1&client=android&c=search&a=hot&location=1";
    //搜索：滚动广告
    public static final String SEARCH_VIEWPAGER_URL = "http://bz.budejie.com/?typeid=2&ver=3.4.3&no_cry=1&client=android&c=topic&a=list&topictype=2&size=10";
    //搜索：列表内容
    public static final String SEARCH_LIST_URL = "http://bz.budejie.com/?typeid=2&ver=3.4.3&no_cry=1&client=android&c=search&a=hot&location=3";

    /**
     * 分类：加载第一页”最新“数据
     *
     * @param id 通过id获取不同类别的图片的网址
     *           id记录在主列表中的字段SecondCategoryList中
     * @return 网址
     */
    public static String getMostnewSort(int id) {
        String mostnew_Sort_URL = "http://bz.budejie.com/?typeid=2&ver=3.4.3&no_cry=1&client=android&c=wallPaper&a=wallPaperNew&index=1&size=60&bigid=" + id;
        return mostnew_Sort_URL;
    }

    /**
     * 分类：加载第一页”热门“数据
     *
     * @param id 通过id获取不同类别的图片的网址
     *           id记录在主列表中的字段SecondCategoryList中
     * @return 网址
     */
    public static String getHotSort(int id) {
        String hot_Sort_URL = "http://bz.budejie.com/?typeid=2&ver=3.4.3&no_cry=1&client=android&c=wallPaper&a=hotRecent&index=1&size=60&bigid=" + id;
        return hot_Sort_URL;
    }

    /**
     * 分类：加载第一页”随机“数据
     *
     * @param id 通过id获取不同类别的图片的网址
     *           id记录在主列表中的字段SecondCategoryList中
     * @return 网址
     */
    public static String getRandSort(int id) {
        String rand_Sort_URL = "http://bz.budejie.com/?typeid=2&ver=3.4.3&no_cry=1&client=android&c=wallPaper&a=random&bigid=" + id;
        return rand_Sort_URL;
    }


    /**
     * 最新加载更多时调用的方法
     *
     * @param oldURL 如果为空说明是推荐中的上下拉刷新
     *               不为空是分类中的上下拉刷新
     *               其中数据量不同，分类中随机请求100页中一页，推荐中随机请求1500页中一页
     * @return
     */
    public static String subMostnewURL(String oldURL, int page) {
        String[] strs;
        int rand = page;
        if (oldURL == null || "".equals(oldURL)) {
            rand = 1500;
            strs = MOSTNEW_URL.split("index=");
        } else {
            rand = 100;
            strs = oldURL.split("index=");
        }
        String later = strs[1];
        String newLater = later.substring(1);
        Random random = new Random();
        String new_MOSTNEW_URL = strs[0] + "index=" + random.nextInt(rand) + newLater;
        return new_MOSTNEW_URL;
    }

    /**
     * 热门加载更多时调用的方法
     *
     * @param oldURL 如果为空说明是推荐中的上下拉刷新
     *               不为空是分类中的上下拉刷新
     *               其中数据量不同，分类中随机请求100页中一页，推荐中随机请求1500页中一页
     * @return
     */
    public static String subHotURL(String oldURL, int page) {
        String[] strs;
        int rand;
        if (oldURL == null || "".equals(oldURL)) {
            rand = 1500;
            strs = HOT_URL.split("index=");
        } else {
            rand = 100;
            strs = oldURL.split("index=");
        }
        String later = strs[1];
        String newLater = later.substring(1);
        Random random = new Random();
        String new_HOT_URL = strs[0] + "index=" + random.nextInt(rand) + newLater;
        return new_HOT_URL;
    }

    /**
     * 通过topicID获取viewPager数据的请求网址
     *
     * @param topicID 专题的id
     * @return viewPager数据的请求网址
     */
    public static String getPagerURL(String topicID) {
        String base = "http://bz.budejie.com/?typeid=2&ver=3.4.3&no_cry=1&client=android&c=topic&a=detail&index=1&size=18&typeid=2&topicid=" + topicID;
        return base;
    }

    /**
     * 通过关键字获取ListView数据的请求网址
     *
     * @param keyWord 关键字
     * @return ListView数据的请求网址
     */
    public static String getListURL(String keyWord) {
        String base = "http://bz.budejie.com/?typeid=2&ver=3.4.3&no_cry=1&client=android&c=search&a=search&q=" + keyWord + "&p=1&s=30";
        return base;
    }

    /**
     * 用传入的网址处理显示第几页后返回新网址
     * 其中页数由传入的page随机产生
     *
     * @param oldURL 传入的网址
     * @param page   服务器上数据一共有多少页
     * @return 新的请求网址
     */
    public static String searchSecondURL(String oldURL, int page) {
        String[] strs = oldURL.split("&p=");
        String later = strs[1];
        String newLater = later.substring(1);
        Random random = new Random();
        String new_URL = strs[0] + "&p=" + random.nextInt(page) + newLater;
        return new_URL;
    }

}
