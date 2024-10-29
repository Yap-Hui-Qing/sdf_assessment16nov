package task01;

public class App {
    
    private String appName;
    private String appCat;
    private Double rating;
    
    @Override
    public String toString() {
        return "App [appName=" + appName + ", appCat=" + appCat + ", rating=" + rating + "]";
    }

    public App(String appName, String appCat, Double rating) {
        this.appName = appName;
        this.appCat = appCat;
        this.rating = rating;
    }
    
    public String getAppName() {
        return appName;
    }
    public String getAppCat() {
        return appCat;
    }
    public Double getRating() {
        return rating;
    }

}
