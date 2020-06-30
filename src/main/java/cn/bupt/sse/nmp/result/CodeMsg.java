package cn.bupt.sse.nmp.result;

public class CodeMsg {

    private Integer code;

    private String message;

    public static final CodeMsg USER_NOT_EXIST = new CodeMsg(40001, "该用户不存在");

    public static final CodeMsg PASSWORD_INCORRECT = new CodeMsg(40002, "用户名或密码错误");

    private CodeMsg(Integer code, String message) {
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
