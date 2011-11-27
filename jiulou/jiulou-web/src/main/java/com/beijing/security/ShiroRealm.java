package com.beijing.security;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.sql.DataSource;

import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.JdbcUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ShiroRealm extends JdbcRealm {
	
	private static final Logger log = LoggerFactory.getLogger(ShiroRealm.class);
	
    /**
     * 查询用户密码
     */
    private static final String DEFAULT_AUTHENTICATION_QUERY = "select password from users where username = ?";

    /**
     * 查询用户的角色
     */
    private static final String DEFAULT_USER_ROLES_QUERY = "select role_name from user_roles where username = ?";

    /**
     * 查询一个角色的权限
     */
    private static final String DEFAULT_PERMISSIONS_QUERY = "select permission from roles_permissions where role_name = ?";
    
    private boolean isPermissionsLookupEnabled = false;
	
	@Override
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
	
	@Override
    public void setAuthenticationQuery(String authenticationQuery) {
        this.authenticationQuery = DEFAULT_AUTHENTICATION_QUERY;
    }
	
	@Override
    public void setUserRolesQuery(String userRolesQuery) {
        this.userRolesQuery = DEFAULT_USER_ROLES_QUERY;
    }
	
	@Override
    public void setPermissionsQuery(String permissionsQuery) {
        this.permissionsQuery = DEFAULT_PERMISSIONS_QUERY;
    }
	
	@Override
    public void setPermissionsLookupEnabled(boolean permissionsLookupEnabled) {
        this.permissionsLookupEnabled = isPermissionsLookupEnabled;
    }
	
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

        UsernamePasswordToken upToken = (UsernamePasswordToken) token;
        String username = upToken.getUsername();
        
        // Null username is invalid
        if (username == null) {
            throw new AccountException("Null usernames are not allowed by this realm.");
        }        

        Connection conn = null;
        AuthenticationInfo info = null;
        
        try {
            conn = dataSource.getConnection();

            String password = getPasswordForUser(conn, username);

            if (password == null) {
                throw new UnknownAccountException("No account found for user [" + username + "]");
            }

            info = buildAuthenticationInfo(username, password.toCharArray());

        } catch (SQLException e) {
            final String message = "There was a SQL error while authenticating user [" + username + "]";
            if (log.isErrorEnabled()) {
                log.error(message, e);
            }

            // Rethrow any SQL errors as an authentication exception
            throw new AuthenticationException(message, e);
        } finally {
            JdbcUtils.closeConnection(conn);
        }

        return info;
    }

    protected AuthenticationInfo buildAuthenticationInfo(String username, char[] password) {
        return new SimpleAuthenticationInfo(username, password, getName());
    }

    private String getPasswordForUser(Connection conn, String username) throws SQLException {

        PreparedStatement ps = null;
        ResultSet rs = null;
        String password = null;
        
        try {
            ps = conn.prepareStatement(authenticationQuery);
            ps.setString(1, username);

            // Execute query
            rs = ps.executeQuery();

            // Loop over results - although we are only expecting one result, since usernames should be unique
            boolean foundResult = false;
            while (rs.next()) {

                // Check to ensure only one row is processed
                if (foundResult) {
                    throw new AuthenticationException("More than one user row found for user [" + username + "]. Usernames must be unique.");
                }

                password = rs.getString(1);

                foundResult = true;
            }
        } finally {
            JdbcUtils.closeResultSet(rs);
            JdbcUtils.closeStatement(ps);
        }

        return password;
    }

    /**
     * This implementation of the interface expects the principals collection to return a String username keyed off of
     * this realm's {@link #getName() name}
     *
     * @see #getAuthorizationInfo(org.apache.shiro.subject.PrincipalCollection)
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

        //null usernames are invalid
        if (principals == null) {
            throw new AuthorizationException("PrincipalCollection method argument cannot be null.");
        }

        String username = (String) getAvailablePrincipal(principals);

        Connection conn = null;
        Set<String> roleNames = new HashSet<String>();
        Set<String> permissions = new HashSet<String>();
        
        {
	        try {
	            conn = dataSource.getConnection();
	
	            // Retrieve roles and permissions from database
	            roleNames.addAll(getRoleNamesForUser(conn, username));
	            if (permissionsLookupEnabled) {
	                permissions.addAll(getPermissions(conn, username, roleNames));
	            }
	
	        } catch (SQLException e) {
	            final String message = "There was a SQL error while authorizing user [" + username + "]";
	            if (log.isErrorEnabled()) {
	                log.error(message, e);
	            }
	
	            // Rethrow any SQL errors as an authorization exception
	            throw new AuthorizationException(message, e);
	        } finally {
	            JdbcUtils.closeConnection(conn);
	        }
        }

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo(roleNames);
        info.setStringPermissions(permissions);
        return info;

    }

    protected Set<String> getRoleNamesForUser(Connection conn, String username) throws SQLException {
        
    	PreparedStatement ps = null;
        ResultSet rs = null;
        Set<String> roleNames = new LinkedHashSet<String>();
        
        try {
            ps = conn.prepareStatement(userRolesQuery);
            ps.setString(1, username);

            // Execute query
            rs = ps.executeQuery();

            // Loop over results and add each returned role to a set
            while (rs.next()) {

                String roleName = rs.getString(1);

                // Add the role to the list of names if it isn't null
                if (roleName != null) {
                    roleNames.add(roleName);
                } else {
                    if (log.isWarnEnabled()) {
                        log.warn("Null role name found while retrieving role names for user [" + username + "]");
                    }
                }
            }
        } finally {
            JdbcUtils.closeResultSet(rs);
            JdbcUtils.closeStatement(ps);
        }
        return roleNames;
    }

    protected Set<String> getPermissions(Connection conn, String username, Collection<String> roleNames) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Set<String> permissions = new LinkedHashSet<String>();
        
        try {
            for (String roleName : roleNames) {

                ps = conn.prepareStatement(permissionsQuery);
                ps.setString(1, roleName);

                // Execute query
                rs = ps.executeQuery();

                // Loop over results and add each returned role to a set
                while (rs.next()) {

                    String permissionString = rs.getString(1);

                    // Add the permission to the set of permissions
                    permissions.add(permissionString);
                }

            }
        } finally {
            JdbcUtils.closeResultSet(rs);
            JdbcUtils.closeStatement(ps);
        }

        return permissions;
    }
}
