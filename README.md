#Retrofit+RxJava+OkHttp封装

上篇文章介绍了Retrofit的简单使用，但是我们可以结合RxJava和OkHttp进一步封装，可以更便捷的使用。
下面我们一步一步来实现封装吧。
### 添加依赖
用到的包比较多，所以要添加的依赖也不少。
```java
    compile 'com.squareup.retrofit2:retrofit:2.0.1'
    compile 'com.squareup.retrofit2:converter-gson:2.0.1'
    compile 'com.squareup.retrofit2:adapter-rxjava:2.0.1'
    compile 'io.reactivex:rxandroid:1.1.0'
    compile 'io.reactivex:rxjava:1.1.2'
    compile 'com.squareup.okhttp3:okhttp:3.2.0'
    //logger
    compile 'com.orhanobut:logger:1.11'
    //retrofit   打印异常
    compile 'com.squareup.okhttp3:logging-interceptor:3.0.1'
```
由于使用了Lambda，需要启用 Java 8 语言功能和 Jack，请在模块层级的 build.gradle 文件的defaultConfig下输入以下内容：
```
jackOptions {
            enabled true
        }
        compileOptions {
            sourceCompatibility JavaVersion.VERSION_1_8
            targetCompatibility JavaVersion.VERSION_1_8
        }
```
[具体Lambda在Android中的配置](http://shesmile.cn/2017/06/04/Android-Studio下配置和使用Lambda/)
### 创建API接口
```java
public interface APIClient {
    @GET("repos/square/retrofit/contributors")
    Observable<List<ObjectEntity>> contributorsBySimpleGetCall();
}
```
### 封装方法
```java
public class NetRequest {
    public final static String TEMP_UPLOAD_IMAGE_PREFIX = "tmp";
    public final static int IMAGE_SIZE_THRESHOLD = 400 * 1024;//400K
    public final static String BASE_URL = "https://api.github.com";
    public final static String LINE_UP_URL = "https://api.github.com/";
    public final static String AFFAIR_URL = "https://api.github.com/";
    public static APIClient APIInstance;
    public static APIClient APIInstance2;
    public static Cache cache;

    static {
        try {
            cache = new Cache(MyApplication.context.getCacheDir(), 1 * 1024 * 1024);
        } catch (Exception e) {
            e.printStackTrace();
        }
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .cache(cache)
                .connectTimeout(40, TimeUnit.SECONDS)
                .addInterceptor(interceptor).build();
        Retrofit.Builder builder = new Retrofit.Builder()
                .client(okHttpClient)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io()))
                .addConverterFactory(GsonConverterFactory.create());
        APIInstance = builder.baseUrl(BASE_URL).build().create(APIClient.class);
        APIInstance2 = builder.baseUrl(AFFAIR_URL).build().create(APIClient.class);

    }
}
```
ObjectEntity实体类
```java
public class ObjectEntity implements Serializable {
    private String login;
    private int id;
    private String avatar_url;
    private String gravatar_id;
    private String url;
    private String html_url;
    private String followers_url;
    private String following_url;
    private String gists_url;
    private String starred_url;
    private String subscriptions_url;
    private String organizations_url;
    private String repos_url;
    private String events_url;
    private String received_events_url;
    private String type;
    private boolean site_admin;
    private int contributions;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }

    public String getGravatar_id() {
        return gravatar_id;
    }

    public void setGravatar_id(String gravatar_id) {
        this.gravatar_id = gravatar_id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getHtml_url() {
        return html_url;
    }

    public void setHtml_url(String html_url) {
        this.html_url = html_url;
    }

    public String getFollowers_url() {
        return followers_url;
    }

    public void setFollowers_url(String followers_url) {
        this.followers_url = followers_url;
    }

    public String getFollowing_url() {
        return following_url;
    }

    public void setFollowing_url(String following_url) {
        this.following_url = following_url;
    }

    public String getGists_url() {
        return gists_url;
    }

    public void setGists_url(String gists_url) {
        this.gists_url = gists_url;
    }

    public String getStarred_url() {
        return starred_url;
    }

    public void setStarred_url(String starred_url) {
        this.starred_url = starred_url;
    }

    public String getSubscriptions_url() {
        return subscriptions_url;
    }

    public void setSubscriptions_url(String subscriptions_url) {
        this.subscriptions_url = subscriptions_url;
    }

    public String getOrganizations_url() {
        return organizations_url;
    }

    public void setOrganizations_url(String organizations_url) {
        this.organizations_url = organizations_url;
    }

    public String getRepos_url() {
        return repos_url;
    }

    public void setRepos_url(String repos_url) {
        this.repos_url = repos_url;
    }

    public String getEvents_url() {
        return events_url;
    }

    public void setEvents_url(String events_url) {
        this.events_url = events_url;
    }

    public String getReceived_events_url() {
        return received_events_url;
    }

    public void setReceived_events_url(String received_events_url) {
        this.received_events_url = received_events_url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isSite_admin() {
        return site_admin;
    }

    public void setSite_admin(boolean site_admin) {
        this.site_admin = site_admin;
    }

    public int getContributions() {
        return contributions;
    }

    public void setContributions(int contributions) {
        this.contributions = contributions;
    }
}
```
### 接口调用
```java
public void getData() {
        NetRequest.APIInstance2.contributorsBySimpleGetCall()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(results -> {
                    Log.d("test","result size="+results.size());

                }, throwable -> {
                    if (throwable instanceof HttpException) {
                        Toast.makeText(MainActivity.this,"网络连接异常",Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity.this,"验证失败",Toast.LENGTH_SHORT).show();
                    }
                });

    }
```
### 效果图

![效果图](http://upload-images.jianshu.io/upload_images/267127-dfafcc9fc5cbf0e4.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
[源码地址](https://github.com/weihansheng/retrofitpackage)
###  总结
有了这样一个封装好的方法，我们在以后的项目中就可以很轻松地进行HTTP请求，也不必担心JSON解析的问题