package zhaoq.hl.hlphonemallmanager.tasks;

/**
 * PACKAGE_NAME:zhaoq.hl.hlphonemallmanager.tasks
 * CREATE_BY:zhaoqiang
 * AUTHOR_EMAIL:zhaoq_hero@163.com
 * DATE: 2016/04/18  14:37
 */
public interface TaskCallBack {

    //异步任务的  回调接口类   数据为  回调回来的  数据对象
    public void taskFinished(TaskResult result);
}
