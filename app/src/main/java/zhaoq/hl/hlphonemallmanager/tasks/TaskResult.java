package zhaoq.hl.hlphonemallmanager.tasks;

/**
 * PACKAGE_NAME:zhaoq.hl.hlphonemallmanager.tasks
 * CREATE_BY:zhaoqiang
 * AUTHOR_EMAIL:zhaoq_hero@163.com
 * DATE: 2016/04/18  14:32
 * 所有 异步任务  返回的结果类
 */
public final class TaskResult {

    //异步  任务标识
    public static int task_id;

//    异步任务   返回结果的状态码
    public static int result_status = -1 ;//默认  返回  -1没有返回数据

    // 默认  返回的  数据对象
    public static Object data = null;

}
