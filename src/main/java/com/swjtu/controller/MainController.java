package com.swjtu.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ibm.watson.developer_cloud.assistant.v1.Assistant;
import com.ibm.watson.developer_cloud.assistant.v1.model.Context;
import com.ibm.watson.developer_cloud.assistant.v1.model.InputData;
import com.ibm.watson.developer_cloud.assistant.v1.model.MessageOptions;
import com.ibm.watson.developer_cloud.assistant.v1.model.MessageResponse;
import com.ibm.watson.developer_cloud.service.security.IamOptions;
import com.swjtu.common.Info;
import com.swjtu.common.Result;

@RestController
public class MainController {

	private Assistant service = null;
	private Context context = null;

	{
		IamOptions iamOptions = new IamOptions.Builder().apiKey(Info.apikey).build();
		service = new Assistant("2018-08-30", iamOptions);
		service.setEndPoint(Info.Url);
	}


	@GetMapping("/ans")
	public Result ans(@RequestParam("node") String dialogNode) {
		InputData input = new InputData.Builder(dialogNode).build();
		MessageOptions options = new MessageOptions.Builder(Info.workspaceId).input(input).context(context).build();
		MessageResponse response = service.message(options).execute();
		context = response.getContext();
		return Result.ok(response);
	}

	@PostMapping("login")
	public Result login(String username, String password) {
		Subject subject = SecurityUtils.getSubject();

		UsernamePasswordToken token = new UsernamePasswordToken(username, password);
		try {
			subject.login(token);
			return Result.ok("success login");
		} catch (UnknownAccountException e) {
			return Result.build(403, "error username");
		} catch (IncorrectCredentialsException e) {
			return Result.build(403, "error password");
		}
	}
	
	@GetMapping("toLogin")
	public Result toLogin() {
		return Result.build(403, "need login");
	}
}
