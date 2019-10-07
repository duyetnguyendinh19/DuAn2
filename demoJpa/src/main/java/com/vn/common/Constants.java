package com.vn.common;

public interface Constants {

    public interface RestApiReturnCode {
        public int SUCCESS = 1;
        public int ERROR = 0;
        public int SYS_ERROR = -1;
        public int AUTH_ERROR = -2;
        public String SUCCESS_TXT = "success";
        public String ERROR_TXT = "errors";
        public String SYS_ERROR_TXT = "system errors";
        public String AUTH_ERROR_TXT = "auth error, back to login again";
    }

    public interface AuthRoles {
//        public String ROLE_ADMIN = "ROLE_ADMIN";
//        public String ROLE_FINANCE = "ROLE_FINANCE";
//        public String ROLE_USER = "ROLE_USER";
    }

    public interface AuthUserType {
        public byte TYPE_ADMIN = 1;
        public byte TYPE_ANOTHER = 2;
    }

    public interface Paging {
        public int SIZE = 20;
    }

    public String DEFAULT_USER_PASS = "123456a@";
    public String ISDELETE = "M";
}
