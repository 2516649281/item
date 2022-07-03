package com.chunfeng.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chunfeng.controller.FileClientController;
import com.chunfeng.dao.AdminMapper;
import com.chunfeng.dao.StudentMapper;
import com.chunfeng.dao.TeacherMapper;
import com.chunfeng.dao.UserMapper;
import com.chunfeng.entity.*;
import com.chunfeng.service.ILogService;
import com.chunfeng.service.IRedisService;
import com.chunfeng.service.IUserService;
import com.chunfeng.service.ex.addException.AddException;
import com.chunfeng.service.ex.addException.AddSourceIsExistException;
import com.chunfeng.service.ex.logException.updateException.LogUpdateErrorException;
import com.chunfeng.service.ex.selectException.SelectException;
import com.chunfeng.service.ex.selectException.SelectSourceIsNullException;
import com.chunfeng.service.ex.updateException.UpdateException;
import com.chunfeng.util.JwtTokenUtil;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import org.springframework.web.multipart.MultipartFile;

import java.lang.Class;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

/**
 * 账号管理公共业务层实现类
 */
@Service
@Transactional
@Log4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    /**
     * 用户管理持久层
     */
    @Autowired(required = false)
    private UserMapper userMapper;

    /**
     * 日志持久层
     */
    @Autowired(required = false)
    private ILogService logService;

    /**
     * 学生持久层
     */
    @Autowired(required = false)
    private StudentMapper studentMapper;

    /**
     * 教师持久层
     */
    @Autowired(required = false)
    private TeacherMapper teacherMapper;

    /**
     * 管理员持久层
     */
    @Autowired(required = false)
    private AdminMapper adminMapper;

    /**
     * redis业务层
     */
    @Autowired(required = false)
    private IRedisService<User> redisService;

    /**
     * fegin客户端
     */
    @Autowired(required = false)
    private FileClientController fileClientController;

    /**
     * 时间格式
     */
    @Value("${date.format}")
    private String dateFormat;


    /**
     * 用户登录
     *
     * @param user 用户信息:账号,密码
     * @return JSON
     */
    @Override
    public JsonRequest<User> login(User user) {
        //根据用户名查询密码及盐值
        User userSource = userMapper.selectOne(new LambdaQueryWrapper<User>()
                .eq(User::getUserName, user.getUserName()));//用户名查询
        //如果使用用户名查不到密码和盐值或者密码与数据库中的密码不匹配,则判定用户或密码错误
        if (userSource.getUserId() == null || !userSource.getUserPassword().equals(getPassword(user.getUserPassword(), userSource.getUserSalt()))) {
            throw new SelectSourceIsNullException("登录失败!");
        }
        //判断日志表中的逻辑删除字段是否为1,即判断用户是否已被注销
        getLog(userSource.getLogId());
        //判断用户信息是否为空
        if (userSource.getUserIndex() != 0) {
            switch (userSource.getUserIdentity()) {
                //如果是学生
                case 0:
                    getIdentity(studentMapper, userSource);
                    break;
                //如果是老师
                case 1:
                    getIdentity(teacherMapper, userSource);
                    break;
                //如果是管理员
                case 2:
                    getIdentity(adminMapper, userSource);
                    break;
            }
        }
        User user1 = new User();
        user1.setUserIdentity(userSource.getUserIdentity());//放入用户身份信息
        user1.setToken(new JwtTokenUtil<>().createToken(userSource));//放入token
        redisService.set("user", user1);//将user存入redis
        return new JsonRequest<>(user1, null);
    }

    /**
     * 用户注册
     *
     * @param user 用户信息:账号,密码
     * @return JSON
     */
    @Override
    public JsonRequest<Integer> register(User user) {
        Log log = new Log(new SimpleDateFormat(dateFormat).format(new Date()));//创建日志对象
        logService.insertLog(log);//添加日志
        user.setLogId(log.getLogId());//返回日志编号
        User userSource = userMapper.selectOne(new LambdaQueryWrapper<User>()
                .eq(User::getUserName, user.getUserName()));
        if (userSource != null) {
            throw new AddSourceIsExistException("该用户已存在!");
        }
        String salt = UUID.randomUUID().toString().toUpperCase();//随机生成盐值
        user.setUserSalt(salt);//将盐值存放在数据库中
        user.setUserPassword(getPassword(user.getUserPassword(), salt));//加密密码
        int column = userMapper.insert(user);
        if (column < 1) {
            throw new AddException("注册失败!");
        }
        return new JsonRequest<>(column);
    }

    /**
     * 修改用户信息
     *
     * @param user 用户信息:编号,密码
     * @return JSON
     */
    @Override
    public JsonRequest<Integer> updateUser(User user) {
        User userSource = userMapper.selectById(user.getUserId());//获取用户信息
        if (userSource == null) {
            throw new SelectSourceIsNullException("该用户不存在!");
        }
        Log log = new Log(userSource.getLogId(), new SimpleDateFormat(dateFormat).format(new Date()));//获取并修改时间
        int logColumn = logService.updateLogById(log).getData();//拉取日志
        if (logColumn < 1) {
            throw new LogUpdateErrorException("拉取日志失败!");
        }
        String password = getPassword(user.getUserPassword(), userSource.getUserSalt());//加密用户输入的密码
        if (!password.equals(userSource.getUserPassword())) {//验证密码
            throw new SelectException("密码错误!");
        }
        if (!user.getUserPassword().equals("")) {
            user.setUserPassword(getPassword(user.getNewUserPassword(), userSource.getUserSalt()));//新密码
        }
        int column = userMapper.updateById(user);//修改
        if (column < 1) {
            throw new UpdateException("修改账号失败!");
        }
        return new JsonRequest<>(column);
    }

    /**
     * 注销
     *
     * @param userId 用户编号
     * @return JSON
     */
    @Override
    public JsonRequest<Integer> deleteUser(Long userId) {
        User user = userMapper.selectById(userId);
        if (user == null) {
            throw new SelectSourceIsNullException("用户信息不存在!");
        }
        int column = logService.updateLogById(new Log(user.getLogId(), 1, new SimpleDateFormat(dateFormat).format(new Date()))).getData();
        if (column < 1) {
            throw new UpdateException("注销用户失败!");
        }
        return new JsonRequest<>(column);
    }

    /**
     * 根据用户编号上传头像
     *
     * @param file   头像文件
     * @param userId 用户编号
     * @return JSON
     */
    @Override
    public JsonRequest<Integer> headerUpload(MultipartFile file, Long userId) {
        JsonRequest<Map<Long, Integer>> jsonRequest;
        JsonRequest<Integer> jsonRequest1;
        int column;
        User user = new User();
        user.setUserId(userId);
        User userSource = userMapper.selectById(userId);
        if (userSource == null) {
            throw new SelectSourceIsNullException("用户信息不存在!");
        }
        Log log = new Log(userSource.getLogId(), new SimpleDateFormat(dateFormat).format(new Date()));//获取并修改时间
        int logColumn = logService.updateLogById(log).getData();//拉取日志
        if (logColumn < 1) {
            throw new LogUpdateErrorException("拉取日志失败!");
        }
        byte[] bytes;
        try {
            bytes = file.getBytes();
        } catch (Exception e) {
            throw new UpdateException("头像上传失败!");
        }
        //删除原来的头像
        jsonRequest1 = fileClientController.deleteFile(Long.valueOf(userSource.getUserHeader()));
        if (jsonRequest1 != null && (jsonRequest1.getStatue() == 200 || jsonRequest1.getStatue() == 9002)) {
            //发送请求至文件服务层
            jsonRequest = fileClientController.loadFile(bytes, Math.toIntExact(file.getSize()), file.getOriginalFilename());
            //将用户头像存入数据库
            for (Long aLong : jsonRequest.getData().keySet()) {
                user.setUserHeader(aLong.toString());
            }
            column = userMapper.updateById(user);
            if (column < 1) {
                throw new UpdateException("头像上传失败!");
            }
        } else {
            throw new UpdateException("头像上传失败!");
        }
        return new JsonRequest<>(column);
    }

    /**
     * 绑定身份信息
     *
     * @param userId    用户编号
     * @param userIndex 身份编号
     * @return JSON
     */
    @Override
    public JsonRequest<Integer> BindIdentity(Long userId, Long userIndex) {
        User user = userMapper.selectById(userId);//获取用户信息
        if (user == null) {
            throw new SelectSourceIsNullException("该用户不存在!");
        }
        user.setUserIndex(userIndex);
        switch (user.getUserIdentity()) {
            case 0://学生
                Student student = studentMapper.selectById(userIndex);
                if (student == null) {
                    throw new SelectSourceIsNullException("没有该学生信息!");
                }
                break;
            case 1://教师
                Teacher teacher = teacherMapper.selectById(userIndex);
                if (teacher == null) {
                    throw new SelectSourceIsNullException("没有该教师信息!");
                }
                break;
            case 2://管理员
                Admin admin = adminMapper.selectById(userIndex);
                if (admin == null) {
                    throw new SelectSourceIsNullException("没有该管理员信息!");
                }
                break;
        }
        int column = userMapper.updateById(user);
        if (column < 1) {
            throw new UpdateException("绑定失败!");
        }
        return new JsonRequest<>(column);
    }

    /**
     * 加密密码
     *
     * @param password 原始密码
     * @param salt     盐值
     * @return 加密后的新密码
     */
    public String getPassword(String password, String salt) {
        //加密10次
        for (int i = 0; i < 10; i++) {
            //MD5加密算法
            password = DigestUtils.md5DigestAsHex((salt + password + salt).getBytes()).toUpperCase();//重组
        }
        return password;
    }

    /**
     * 检查日志信息
     *
     * @param id 日志编号
     * @return Boolean 判断用户是否被删除
     */
    public Boolean getLog(Long id) {
        Log log = logService.selectLogById(id).getData();
        if (log == null) {
            throw new SelectSourceIsNullException("拉取日志失败!");
        }
        return log.getDeleted() != 1;
    }

    /**
     * 获取身份信息并验证
     *
     * @param mapper mapper接口对象
     * @param user   账号对象
     * @param <T>    学生?教师?管理员?
     */
    public <T> void getIdentity(BaseMapper<T> mapper, User user) {
        try {
            //获取身份信息
            T t = mapper.selectById(user.getUserIndex());
            Class<?> aClass = t.getClass();
            Field logIdField = aClass.getDeclaredField("logId");
            //暴力破解私有修饰
            logIdField.setAccessible(true);
            Long logId = (Long) logIdField.get(t);
            if (getLog(logId)) {
                user.setUser(t);
            }
        } catch (Exception e) {
            log.info("反射捕获到异常:" + e.getMessage());
            e.printStackTrace();
        }
    }
}
