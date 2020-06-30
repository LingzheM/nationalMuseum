package cn.bupt.sse.nmp.result;

public class Result<T> {

    private Integer code;

    private T data;

    private String message;

    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<T>(data);
        return result;
    }

    public static <T> Result<T> error(CodeMsg codeMsg) {
        Result<T> result = new Result<T>(codeMsg);

        return result;
    }

    private Result(T data) {
        this.code = 200;
        this.message = "success";
        this.data = data;
    }

    private Result(CodeMsg codeMsg) {
        this.code = codeMsg.getCode();
        this.message = codeMsg.getMessge();
        this.data = null;
    }

}
