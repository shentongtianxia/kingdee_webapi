import com.google.gson.Gson;
import com.kingdee.bos.webapi.entity.RepoRet;
import com.kingdee.bos.webapi.sdk.K3CloudApi;

import static org.assertj.core.api.Fail.fail;

public class Test {

    public static void main(String[] args) {
        //注意 1：此处不再使用参数形式传入用户名及密码等敏感信息，改为在登录配置文件中设置。
//注意 2：必须先配置第三方系统登录授权信息后，再进行业务操作，详情参考各语言版本SDK介绍中的登录配置文件说明。
//读取配置，初始化SDK
        K3CloudApi client = new K3CloudApi();
//请求参数，要求为json字符串
        String jsonData = "{\"FormId\":\"\",\"FieldKeys\":\"\",\"FilterString\":[],\"OrderString\":\"\",\"TopRowCount\":0,\"StartRow\":0,\"Limit\":2000,\"SubSystemId\":\"\"}";
        try {
            //调用接口
            String resultJson = String.valueOf(client.executeBillQuery(jsonData));
            System.out.println("接口返回结果: " + resultJson);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }
}
