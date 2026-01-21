package Constants;

public enum EndPoints {
    STORE("/store"),
    ACCOUNT("/account");
    public  final String url;
    EndPoints(String url) {this.url = url;}
}
