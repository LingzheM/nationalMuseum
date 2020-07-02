package cn.bupt.sse.nmp.result;

public class CodeMsg {

    private Integer code;

    private String message;

    public static final CodeMsg USER_NOT_EXIST = new CodeMsg(401, "该用户不存在");

    public static final CodeMsg PASSWORD_INCORRECT = new CodeMsg(401, "用户名或密码错误");

    public static final CodeMsg USER_PHONE_EXISTED = new CodeMsg(401, "该手机号已经注册");

    public static final CodeMsg UNAUTHORIZED = new CodeMsg(401, "Unauthorized");

    public CodeMsg(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
