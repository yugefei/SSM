package com.tencent.seventeenShow.backend.conf;

/**
 * Created by Edward on 2016/8/10.
 */
public class ResultCode {
    public static final Long ERROR_DB_CODE = 22l;
    public static final Long ERROR_TOKEN_INVALID_CODE = 11l;
    public static final Long ERROR_DEFAULT_CODE = 555l;
    public static final Long OK_CODE = 0L;
    public static final Long ERROR_PARAMETER_WRONG = 33l;
    public static final Long ERROR_HTTP_METHOD_NOT_SUPPORTTED = 44l;
    public static final Long ERROR_OPERATION_FAILED = 55l;

    public static final Long ERROR_USER_MOBILE_OCCUPIED = 331l;
    public static final Long ERROR_USER_ID_NAME_NOTMATCHED = 332l;
    public static final Long ERROR_USER_STUDENT_REGISTERED = 333l;
    public static final Long ERROR_USER_LOGIN_FAILED_CODE = 334l;
    public static final Long MATCH_FAIL = 222l;
    public static final Long CLICK_LIKE_FAIL = 223l;
    public static final Long Click_DISLIKE_FAIL = 224l;
    public static final Long INCOMPLETE_FILLIN = 225l;

    public static final Long NOT_FRIST_LOGIN = 335l;
    public static final Long REPALY_ATTACK = 336l;
    public static final Long FALSIFY_DATA = 337l;
    public static final Long TOKEN_EXPIRED = 338l;

}
