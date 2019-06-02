package com.swjtu.shiro;

import org.apache.shiro.SecurityUtils;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

public class UserRealm extends AuthorizingRealm {

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken)
			throws AuthenticationException {
		// TODO Auto-generated method stub
		UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
		String username = "root";
		String password = "root";
		if (!username.equals(token.getUsername()))
			return null;
		return new SimpleAuthenticationInfo("", password, "");
	}

}
