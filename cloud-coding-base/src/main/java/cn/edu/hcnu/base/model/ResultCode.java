package cn.edu.hcnu.base.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author AICHEN
 * 响应码、提示信息
 */
@Getter
@AllArgsConstructor
public enum ResultCode {
    /**
     * 成功
     */
    SUCCESS(200, "成功"),

    ERROR(1000, "系统错误"),
    CLIENT_AUTHENTICATION_FAILED(1001, "客户端认证失败"),
    USERNAME_OR_PASSWORD_ERROR(1002, "用户名或密码错误"),
    UNSUPPORTED_GRANT_TYPE(1003, "不支持的认证模式"),
    NO_PERMISSION(1005, "无权限访问！"),
    INVALID_TOKEN(1004, "无效的token"),
    UNKOWN_ERROR(1006, "执行过程异常，请重试。"),
    PARAMS_ERROR(1007, "非法参数"),
    OBJECT_NULL(1008, "对象为空"),
    QUERY_NULL(1009, "查询结果为空"),
    REQUEST_NULL(1010, "请求参数为空"),

    UPLOAD_ERROR(1011, "上传失败"),

    MERGER_ERROR(1012, "合并失败"),


    UN_ERROR(1013, "未知失败"), ILLEGAL_PARAMETER(1014, "非法参数"), INDEX_DUP(1015, "主键冲突"),

    ACTIVITY_STATE_UPDATE_ERROR(1016, "活动状态变更失败"),

    ARRAIGNMENT_NOT_REPEAT(1017, "待审核状态不可重复提审"), ACTIVITY_REJECT_SUCCESS(1018, "活动审核拒绝完成"), ACTIVITY_PASS(1019, "活动审核通过完成"), BACK_TO_EDIT(1020, "活动审核撤销回到编辑中"), ACTIVITY_CLOSE(1021, "活动审核关闭完成"), ACTIVITY_NOT_OPEN(1022, "非关闭活动不可开启"), ARRAIGNMENT_UPDATE_NOT_DOING(1023, "待审核活动不可执行活动中变更"),


    CLOSE_NOT_ARRAIGNMENT(1024, "活动关闭不可提审"), CLOSE_NOT_PASS(1025, "活动关闭不可审核通过"), CLOSE_NOT_REFUSE(1026, "活动关闭不可审核拒绝"), CLOSE_NOT_REVOKE(1027, "活动关闭不可撤销审核"), CLOSE_NOT_CLOSE(1028, "活动关闭不可重复关闭"), CLOSE_TO_OPEN_SUCCESS(1029, "活动开启完成"), CLOSE_NOT_DOING(1030, "活动关闭不可变更活动中"),

    DOING_NOT_ARRAIGNMENT(1031, "活动中不可提审"), DOING_NOT_PASS(1032, "活动中不可审核通过"), DOING_NOT_REFUSE(1033, "活动中不可审核拒绝"), DOING_NOT_REVOKE(1034, "活动中不可撤销审核"), DOING_TO_CLOSE_SUCCESS(1035, "活动关闭成功"), DOING_NOT_OPEN(1036, "活动中不可开启"), DOING_NOT_DOING(1037, "活动中不可重复执行"), EDIT_TO_ARRAIGNMENT_SUCCESS(1038, "活动提审成功"), EDIT_NOT_PASS(1039, "编辑中不可审核通过"), EDIT_NOT_REFUSE(1040, "编辑中不可审核拒绝"), EDIT_NOT_REVOKE(1041, "编辑中不可撤销审核"), EDIT_TO_CLOSE_SUCCESS(1042, "活动关闭成功"), EDIT_NOT_OPEN(1043, "非关闭活动不可开启"), EDIT_NOT_DOING(1044, "编辑中活动不可执行活动中变更"), OPEN_NOT_ARRAIGNMENT(1045, "活动开启不可提审"),

    OPEN_NOT_PASS(1046, "活动开启不可审核通过"), OPEN_NOT_REFUSE(1047, "活动开启不可审核拒绝"), OPEN_NOT_REVOKE(1048, "活动开启不可撤销审核"),

    OPEN_TO_CLOSE_SUCCESS(1049, "活动关闭完成"),
    OPEN_TO_DOING_SUCCESS(1050, "活动变更活动中完成"),
    OPEN_NOT_OPEN(1051, "活动不可重复开启"),

    PASS_NOT_ARRAIGNMENT(1052, "已审核状态不可重复提审"),
    PASS_NOT_PASS(1053, "已审核状态不可重复审核"),
    PASS_TO_REFUSE_SUCCESS(1054, "活动审核拒绝完成"),
    PASS_NOT_REVOKE(1055, "审核通过不可撤销(可先拒绝审核)"),
    PASS_TO_CLOSE_SUCCESS(1056, "活动审核关闭完成"),
    PASS_NOT_OPEN(1057, "非关闭活动不可开启"),
    PASS_TO_DOING_SUCCESS(1058, "活动变更活动中完成"),
    REFUSE_NOT_ARRAIGNMENT(1059, "已审核状态不可重复提审"),
    REFUSE_NOT_PASS(1060, "已审核状态不可重复审核"),

    REFUSE_NOT_REFUSE(1061, "活动审核拒绝不可重复审核"),
    REFUSE_TO_REVOKE_SUCCESS(1062, "撤销审核完成"),
    REFUSE_NOT_CLOSE(1063, "活动审核关闭完成"),
    REFUSE_NOT_OPEN(1064, "非关闭活动不可开启"),
    REFUSE_NOT_DOING(1065, "审核拒绝不可执行活动为进行中"),
    ACTIVITY_STATE_NOT_USE(1066, "活动当前状态非可用"),
    TIME_ERROR(1067, "活动时间范围非可用"),
    AWARD_COUNT_ERROR(1068, "活动剩余库存非可用"),
    USER_COUNT_ERROR(1069, "个人领取次数非可用"),
    UPDATE_ERROR(1070, "更新失败"),
    LOSING_DRAW(1071, "丢失"),

    RULE_ERR(1072, "规则错误"),
    UPLOAD_SUCCESS(1073, "上传成功"),
    FLOW_LIMITING(1074, "接口限流"),
    PARAM_FLOW_LIMITING(1075, "热点参数限流"),
    DEGRADE_LIMITING(1076, "服务降级"),
    SYSTEM_BLOCK(1077, "触发系统保护规则"),
    AUTHORITY_LIMITING(1078, "授权规则不通过"),
    ACCOUNT_LOCK(1079, "账号被锁定"),
    NOT_FOUND(1080, "服务不可用"),
    ADD_URL_ERROR(1081, "添加url失败"),
    UPDATE_URL_ERROR(1082, "更新url失败"),
    DELETE_URL_ERROR(1083, "更新url失败"),
    PARAM_MISS(1084, "参数丢失"),
    GATEWAY_ERROR(1085, "网关异常"),
    NOT_FOUND_ERROR(1085, "参数不存在"),
    ADD_FEATURE_ERROR(1086, "添加功能失败"),
    UPDATE_FEATURE_ERROR(1087, "更新功能失败"),
    DELETE_FEATURE_ERROR(1088, "删除功能失败"),
    QUERY_FEATURE_ERROR(1089, "查询功能失败"),
    QUERY_ERROR(1090, "查询错误"),
    VALID_FAIL(1091, "参数校验失败"),
    ADD_FOLDER_ERROR(1092, "新建文件夹失败"),
    REMOVE_FOLDER_ERROR(1093, "删除文件夹失败"),
    UPDATE_FOLDER_ERROR(1094, "更新文件夹失败"),
    ADD_FILE_ERROR(1095, "创建文件失败"),
    REMOVE_FILE_ERROR(1096, "删除文件失败"),
    QUERY_FILE_ERROR(1097, "查询文件失败"),
    UPDATE_FILE_ERROR(1098, "更新文件失败"),
    PROGRAM_CREATE_ERROR(1099, "项目创建失败"),
    PROGRAM_UPDATE_ERROR(1100, "项目更新失败"),
    PROGRAM_QUERY_ERROR(1101, "项目查询失败"),
    PROGRAM_RUN_ERROR(1102, "项目运行失败"),
    FILE_UPLOAD_FAIL(1103, "文件上传失败"),
    FILE_CHECK_FAIL(1105, "文件检查失败"),
    DELETE_FILE_ERROR(1106, "删除文件失败"),
    ARTICLE_CREATE_FAIL(1107, "创建文章失败"),
    ARTICLE_UPDATE_FAIL(1108, "更新文章失败"),
    ARTICLE_DELETE_FAIL(1109, "删除文章失败"),
    ARTICLE_QUERY_FAIL(1110, "查询文章失败"),
    SAVE_COMMENT_ERROR(1111, "创建评论失败"),
    CHECK_FAIL(1112, "检验失败"),
    LOGOUT_FAIL(1113, "注销失败"),
    UPDATE_FAIL(1114, "重置失败"),
    UPDATE_ORGANIZATION_ERROR(1114, "更新组织失败"),
    DELETE_ORGANIZATION_ERROR(1115, "删除组织失败"),
    DELETE_ERROR(1116, "删除失败"),
    SAVE_ERROR(1117, "创建失败"),
    TOPIC_CREATE_FAIL(1118, "创建专栏失败");

    private final Integer code;

    private final String info;


}
