package _3_1_protobuf;

import com.google.protobuf.InvalidProtocolBufferException;
import com.zml.tank.domain.LoginRequest;

/**
 * Description:
 * User: zhumeilu
 * Date: 2017/9/5
 * Time: 16:22
 */
public class TestLoginRequest {

    private static byte[] encode(LoginRequest.LoginCommand command){
        return command.toByteArray();
    }
    private static LoginRequest.LoginCommand decode(byte[] body) throws InvalidProtocolBufferException {
        return LoginRequest.LoginCommand.parseFrom(body);
    }
    private static LoginRequest.LoginCommand createLoginCommand(){
        LoginRequest.LoginCommand.Builder builder = LoginRequest.LoginCommand.newBuilder();

        builder.setUsername("zml");

        return builder.build();
    }

    public static void main(String[] args) throws InvalidProtocolBufferException {
        LoginRequest.LoginCommand command = createLoginCommand();
        System.out.println("编码前："+command.toString());
        LoginRequest.LoginCommand command2 = decode(encode(command));
        System.out.println("编码后："+command2.toString());
        System.out.println("编码前后编码后是否相等："+command.equals(command2));
    }
}
