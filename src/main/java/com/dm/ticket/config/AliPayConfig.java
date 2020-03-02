package com.dm.ticket.config;


// TODO: 2020/3/2 记得修改所有参数
public class AliPayConfig {

    // 应用ID
    public static String APP_ID = "2016101715569";

    // 商户私钥
    public static String APP_PRIVATE_KEY = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjA" +
            "gEAAoIBAQCB7N4rg+Wurp+xGt+t5rEHUROSMDFPqP09r8mXOX4ZIBGHmy0yrXekB7pzvEVFf+K5PO3" +
            "bKuY3F606HypQCQAg/NfvOKwbCDbHPAwz789jqStLlJFOCwaMZKrtSYKGMgTxZ6ysDMFGOgTFkzqVNIv6" +
            "5ga6rKQjEPxWedY8rmNVlFvIz0Ws3puL9qo3VSkuRCIk0v50gxtbCsFGcc5QVG7iyoS8wJgkyXjZNILNJIgqUBYg" +
            "AmeE7WUdcCOMqmxlm3qDrD5/W8tbWfwXYMhi69a1qNcs1gu6ETbZcqC9cSLIafo5AAekIbp77xXHe/O5P8UIMS" +
            "W4/BCi72BlGaM/Fz3BAgMBAAECggEAV9jof1O3QN0hbIZXJyYMHH5dVULwlIQWdlcdXvqooCRmteHeNZ3Ef0wabZ" +
            "am388QZV8lFEN1VVrU7a+U1HjBdYyAFGelQP0ndYMSBY0CSi7TMHkMHy7nDDF1tPalJorNGiWQWDD" +
            "A4979jEB68Y/VNZvR6kpie3/jCTC6UAV4v8ESUYWtBE0rNaDITiD9RzouY55oLX763bVPpwmftazFJ" +
            "4fVRAi8IF7yZzxBm4a6jV9Hw8E3RUEu46dk3NvETgOODSDBsboZQ/K1pbMYnyXiRdLXpyFPhJt" +
            "EpefGr0T2D97rYypENaprj9IuNTD+rVqDKIueQcJMI3IYQKBgQDQwiUhUaLJLbfWiwbxuQlJh4UeWoVCSX" +
            "9E45/hNUUHOMZTOGz/6+QFbqPx9s4TgJ7U+eEj3YOuQJtPs4HB2Q28LdkyssdJfrauXqsh/+3RyfmL" +
            "TSlzM4jA4PdnQay9qdI2yx7kz465vA9uiNr4uQjTBvAZd4kfaZZH0smIfwKBgQCfU70M0FIx/a7QAmjhRe5" +
            "DeK2/TPb5Vl4gva+yxUOgewFAkQ14oumuGClcj7EEhVCZFPFA5oBQeawCDXgnjbUw9ZQYc/KJ9bHUaKIQhOA" +
            "oWyJklledL6xw7jLgTvVwcJLkLWeA8PCc8uJEu7A7OyjlZMM8zs0GLMus0JoZvwKBgBL9tncDgDWhDRpdZ3JSDQ" +
            "A9beTHA1x2ZzeTJZZNTExN4+zqMzBM+19rI0r90t1/U/O4D7qvsCy9GB+gDQEBKYrC/jbiTOIJ1YtgFapjPlpHeBEf" +
            "uK3thRnP1F8HmDoyhU5zxoXpkJPsNeW7sAO0O4do/d6EwQ7wCu9qoZtDF8ZtAoGAQbjvMQv1+/0jUVbNILAS" +
            "Gu767QC4mB3ScjsM8PeCb/6Ng67SGcNKNz0rOwIiluJ/07Mqof4nRNH+N4vMHnQyQbI3XrKUXGAGZaKsHNF5" +
            "zj36c4ZA619LKVdD8Wcd2MH2yRnVUxDcdegCO6b9qcOR0eDvadYjw9e0gj0b1ak0CgYEAuswzTbRWZKhz98q98W" +
            "UAdv2W0MtYEH2g946jPzetj81+FnOb3rXs8j2TVKvfyD176H3Xgo/eofhc/eOPlBair4D8Ph6bwPb2jD2nG3i" +
            "8ICYtmvumRfwapeUPg1bHOp/TJSuysRYWW2D2ife6aSm9Qrwn5A0KP58ioRKVo2LvK8s=";

    // 支付宝公钥
    public static String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAA8AMIIBCgKCAQEAgjJ6r8QffmG1DlFmw9sFZ+ibUiUYyvsQlDVcQFwaL6ojotVK12i6OngJCB5i72zo7Mr6Lstw3vcQMVS0UCqkUGTR3Y5zVGll49lgxxIBVktLPSO9sxzwH81C93nnmdIytTsk6pF4tk/jRWX1VsRhQun3eEbIzW+UaN2Fckqb/C09lh0TnqeGkpw9AqvVDiR+b0id55KQ4bo2DuRZx+d670cZenrfHQAKJVirOn87sykh9TNZBVfTGlb1uEbQNL9oQDwQE8HPvYA5lak6Wh5KJlAuwHHBGP55DAw6eIhIWtCrflTg/alPbz1ASjr/t+gFOXC3ccFZX1KQIDAQAB";

    // 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问，
    public static String NOTIFY_URL = "http://localhost:8080/order/notify";

    // 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String RETURN_URL = "http://localhost:8080/order/return";

    // 签名方式
    public static String SIGN_TYPE = "RSA2";

    // 字符编码格式
    public static String CHARSET = "utf-8";

    // 支付宝网关
    public static String GATEWAY_URL = "https://openapi.alipaydev.com/gateway.do";
}
