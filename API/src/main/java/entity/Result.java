package entity;

import java.io.Serializable;

public class Result<T> implements Serializable {


    private static final long serialVersionUID = 5283793014445351257L;

    private T data;

    private String msg;

    private boolean isSuccess;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    @Override
    public String toString() {
        return "Result{" +
                "data=" + data +
                ", msg='" + msg + '\'' +
                ", isSuccess=" + isSuccess +
                '}';
    }
}
