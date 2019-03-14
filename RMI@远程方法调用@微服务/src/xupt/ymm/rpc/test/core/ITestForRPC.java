package xupt.ymm.rpc.test.core;

import java.util.List;

import xupt.ymm.rpc.model.UserModel;

public interface ITestForRPC {
	UserModel getUserById(String id);
	UserModel getUserById(UserModel userModel);
	UserModel getUserById();
	List<UserModel> getUserList();
}
