package com.xmu.common.enums;

/**
 * @author summer
 * @see <a href=""></a><br/>
 */
public enum ResponseCode {
    BAD_REQUEST(400,"BAD_REQUEST"),
    NOT_FOUND(404,"资源不存在"),
    OK(200, "OK"),
    INTERNAL_SERVER_ERROR(500, "INTERNAL_SERVER_ERROR"),
    USERNAME_OR_PASSWORD_ERROR(501, "用户名或密码错误"),
    USER_EXIST(502, "用户已存在"),
    USER_NOT_EXIST(503, "用户不存在"),
    EMPTY_USER_INFO(504,"用户信息为空"),
    PROBLEM_LIST_NOT_EXIST(505,"题单不存在"),
    PROBLEM_NOT_EXIST(506,"题目不存在"),
    ANNOUNCEMENT_NOT_EXIST(507,"公告不存在"),
    NOT_INITIAL_COMMENT_USER(508,"修改评论的用户和最初发表评论的用户不一致"),
    COMMON_NOT_EXIST(509,"评论不存在"),
    AUTHORIZATION_TOKEN_NOT_EXIST(510,"缺少token"),
    COMMENT_FROZEN(511,"评论已经被冻结"),
    NEW_REPLY_SHOULD_NOT_INCLUDE_ID(512,"新增评论不应该含有评论ID"),
    NOT_ADMIN(513,"不是管理员");
    private final int code;
    private final String msg;

    ResponseCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
