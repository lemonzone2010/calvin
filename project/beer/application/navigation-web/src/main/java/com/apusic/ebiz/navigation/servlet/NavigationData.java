package com.apusic.ebiz.navigation.servlet;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.PropertyFilter;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.apusic.ebiz.model.navigation.ApplicationInfo;
import com.apusic.ebiz.model.navigation.Navigation;
import com.apusic.ebiz.model.user.Resource;
import com.apusic.ebiz.model.user.Role;
import com.apusic.ebiz.model.user.User;
import com.apusic.ebiz.navigation.service.ApplicationInfoService;
import com.apusic.ebiz.navigation.service.NavigationService;
import com.apusic.ebiz.smartorg.service.UserService;

public class NavigationData extends HttpServlet {

    private static final long serialVersionUID = -1085365287017074779L;

    private NavigationService navigationService;

    private ApplicationInfoService applicationInfoService;

    private UserService userService;

    private boolean isAdmin = false;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext servletContext = config.getServletContext();
        WebApplicationContext wac = WebApplicationContextUtils.getWebApplicationContext(servletContext);
        navigationService = wac.getBean(NavigationService.class);
        applicationInfoService = wac.getBean(ApplicationInfoService.class);
        userService = wac.getBean(UserService.class);
    }

    public void service(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //取出所有一级菜单
        List<Navigation> navigations = navigationService.findByLevel(1);
        //取出登录人角色
        Set<Role> roles = getUserRole();
        //取出登录人角色所拥有的所有资源
        Set<Resource> resourceAll = getAllResource(roles);
        //判断登录人是否是系统超级管理员
        isAdmin = isAdmin(roles);
        JSONArray navsjson = toJson(navigations, resourceAll);
        ApplicationInfo app = new ApplicationInfo();
        app.setStatus("Y");
        List<ApplicationInfo> apps = applicationInfoService.findByExample(app);
        JSONArray appsJson = JSONArray.fromObject(apps);
        JSONObject json = new JSONObject();
        json.put("nav", navsjson);
        json.put("app", appsJson);
        json.put("isAdmin", isAdmin);
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json.toString());
    }

    /**
     * 将数据转换成json对象，并且过滤掉没有权限的菜单
     * 
     * @param navigations
     * @return JSONArray
     */
    private JSONArray toJson(List<Navigation> navigations, Set<Resource> resourceAll) {
        JsonConfig config = new JsonConfig();
        config.setJsonPropertyFilter(new PropertyFilter() {
            public boolean apply(Object source, String name, Object value) {
                if ("id".equals(name) || "name".equals(name) || "url".equals(name) || "applicationId".equals(name)) { // 只取需要的属性
                    return false;
                }
                return true;
            }
        });
        JSONArray ja = new JSONArray();
        for (Navigation navigation : navigations) {
            ja.add(getJsonObject(navigation, config, resourceAll));
        }
        return ja;
    }

    /**
     * 递归调用，取得所有符合条件的孩子节点（非禁用，并且当前登录角色所拥有的菜单资源），转换成json对象
     * 
     * @param navigation
     * @param config
     * @param filter
     * @return JSONObject
     */
    private JSONObject getJsonObject(Navigation navigation, JsonConfig config, Set<Resource> resourceAll) {
        JSONObject json = JSONObject.fromObject(navigation, config);
        if (navigation.getChildrens().size() == 0) {
            return json;
        }
        JSONArray ja = new JSONArray();
        for (Navigation children : navigation.getChildrens()) {
            if ("N".equals(children.getStatus())) { // 判断是否禁用
                continue;
            } else if (StringUtils.isNotEmpty(children.getUrl()) && !isAdmin) { // 过滤菜单权限，如果没有配置角色资源，则不显示,管理员用户默认拥有全部权限
                if (!isAuthorization(children, resourceAll)) {// 如果是非授权资源，不显示
                    continue;
                }
            }
            JSONObject child = getJsonObject(children, config, resourceAll);
            ja.add(child);
        }
        json.put("childrens", ja);
        return json;
    }

    /**
     * 判断菜单url是否和角色拥有的资源匹配
     * @param children
     * @param resourceAll
     * @return
     */
    private boolean isAuthorization(Navigation children, Set<Resource> resourceAll) {
        boolean isAuth = false;
        for (Resource resource : resourceAll) {
            if (children.getUrl().endsWith(resource.getResValue())) { // 如果导航url和资源url匹配，授权资源，显示
                isAuth = true;
                continue;
            }
        }
        return isAuth;
    }

    /**
     * 根据角色取出所有资源
     * @param roles
     * @return
     */
    private Set<Resource> getAllResource(Set<Role> roles) {
        Set<Resource> resourceAll = new HashSet<Resource>();
        if (roles == null) {
            return resourceAll;
        }
        for (Role role : roles) {
            resourceAll.addAll(role.getResources());
        }
        return resourceAll;
    }

    /**
     * 取出登录人角色
     * @return
     */
    private Set<Role> getUserRole() {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        Set<Role> roles = new HashSet<Role>();
        if (StringUtils.isEmpty(name)) {
            return roles;
        }
        User user = userService.getUserByName(name);
        if (user == null) {
            return roles;
        }
        return roles = user.getRoles();
    }

    /**
     * 判断是否是系统管理员
     * 
     * @return
     */
    private boolean isAdmin(Set<Role> roles) {
        if (roles == null) {
            return false;
        }
        for (Role role : roles) {
            /*if (DataConstants.ROLE_SYSTEM_ADMIN.equals(role.getName())) {
                return true;
            }*/
        }
        return false;
    }
}
