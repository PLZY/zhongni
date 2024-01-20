package com.zhongni.oauth.service.login;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zhongni.oauth.constants.OauthConstants;
import com.zhongni.oauth.entity.auth.RoleInfo;
import com.zhongni.oauth.entity.auth.UserRole;
import com.zhongni.oauth.entity.user.UserInfo;
import com.zhongni.oauth.enums.BusinessExceptionEnum;
import com.zhongni.oauth.exception.BusinessException;
import com.zhongni.oauth.service.auth.RoleInfoService;
import com.zhongni.oauth.service.auth.UserRoleService;
import com.zhongni.oauth.service.user.UserInfoService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DBUserDetailsService implements UserDetailsService {

    @Resource
    private UserInfoService userInfoService;

    @Resource
    private RoleInfoService roleInfoService;

    @Resource
    private UserRoleService userRoleService;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        UserInfo user = userInfoService.getOne(new LambdaQueryWrapper<UserInfo>().eq(UserInfo::getUserName, s));
        if(null == user)
        {
            throw new BusinessException(BusinessExceptionEnum.UN_RIGHT_NAME_AND_PASSWORD);
        }

        return new User(user.getUserName(), user.getPassword(), OauthConstants.ENABLE.equals(user.getUserStatus()),
                true, true, true,
                getAuthorities(user));
    }

    private Collection<? extends GrantedAuthority> getAuthorities(UserInfo user) {
        List<UserRole> userRoles = userRoleService.list(new LambdaQueryWrapper<UserRole>().eq(UserRole::getUserId, user.getId()));
        if(CollectionUtils.isEmpty(userRoles))
        {
            return Collections.singletonList(new SimpleGrantedAuthority("USER"));
        }

        List<SimpleGrantedAuthority> roleList = new ArrayList<>();
        List<RoleInfo> roleInfoList = roleInfoService.list(new LambdaQueryWrapper<RoleInfo>().
                in(RoleInfo::getId, userRoles.stream().map(UserRole::getRoleId).collect(Collectors.toList())));
        roleInfoList.forEach(item -> roleList.add(new SimpleGrantedAuthority(item.getRoleName())));
        return roleList;
    }
}
