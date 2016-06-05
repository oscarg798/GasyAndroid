package rm.com.core.model.dto.utils;

/**
 * Created by oscargallon on 6/5/16.
 */

public interface IAsyncTaskExecutor {

    Object execute();

    void onExecuteComplete(Object object);

    void onExecuteFaliure(Exception e);
}
