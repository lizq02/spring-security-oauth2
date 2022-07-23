package com.tortoise.base.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResultCode {
    SUCCESS(200, "操作成功"),
    FAILURE(400, "业务异常"),
    ERROR(500, "服务异常"),
    GLOBAL_PARAM_ERROR(540, "参数错误");

    private int code;
    private String msg;
}
