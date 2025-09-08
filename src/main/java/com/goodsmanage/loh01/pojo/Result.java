package com.goodsmanage.loh01.pojo;
// 包声明，实体类包

/**
 * 统一响应结果封装类
 * 用于统一API响应格式，包含状态码、消息和数据
 */
public class Result {
    // 统一响应结果类，公共访问修饰符
    private Integer code ;//1 成功 , 0 失败
    // 响应状态码，1表示成功，0表示失败
    private String msg; //提示信息
    // 响应消息，用于描述操作结果
    private Object data; //数据 date
    // 响应数据，使用Object类型以支持任意数据类型

    public Result() {
        // 无参构造函数，用于创建空对象
    }
    public Result(Integer code, String msg, Object data) {
        // 全参构造函数，用于创建完整对象
        this.code = code;
        // 设置状态码
        this.msg = msg;
        // 设置消息
        this.data = data;
        // 设置数据
    }
    public Integer getCode() {
        // 获取状态码的getter方法
        return code;
    }
    public void setCode(Integer code) {
        // 设置状态码的setter方法
        this.code = code;
    }
    public String getMsg() {
        // 获取消息的getter方法
        return msg;
    }
    public void setMsg(String msg) {
        // 设置消息的setter方法
        this.msg = msg;
    }
    public Object getData() {
        // 获取数据的getter方法
        return data;
    }
    public void setData(Object data) {
        // 设置数据的setter方法
        this.data = data;
    }

    public static Result success(Object data){
        // 静态方法：创建成功响应，包含数据
        return new Result(1, "success", data);
        // 返回状态码1、成功消息和数据的Result对象
    }
    public static Result success(){
        // 静态方法：创建成功响应，不包含数据
        return new Result(1, "success", null);
        // 返回状态码1、成功消息和null数据的Result对象
    }
    public static Result error(String msg){
        // 静态方法：创建错误响应
        return new Result(0, msg, null);
        // 返回状态码0、错误消息和null数据的Result对象
    }

    @Override
    public String toString() {
        // 重写toString方法，用于调试和日志输出
        return "Result{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
        // 返回包含所有字段信息的字符串表示
    }
}