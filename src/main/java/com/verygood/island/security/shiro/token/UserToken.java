package com.verygood.island.security.shiro.token;

import com.verygood.island.entity.User;
import lombok.Data;
import org.apache.shiro.authc.UsernamePasswordToken;

import java.io.Serializable;

/**
 * @Description: $
 * @Param: $
 * @return: $
 * @author: SheledonPeng
 * @Date: $
 */
@Data
public class UserToken extends UsernamePasswordToken implements Serializable {

    private User user;

    private String username;

    private char[] password;

    private static final long serialVersionUID = 4812793519945855483L;

    @Override
    public Object getPrincipal() {
        return getUser();
    }

    @Override
    public Object getCredentials() {
        return "ok";
    }

    public UserToken(String username, String password) {
        this.username = username;
        this.password = password.toCharArray();
    }

}
