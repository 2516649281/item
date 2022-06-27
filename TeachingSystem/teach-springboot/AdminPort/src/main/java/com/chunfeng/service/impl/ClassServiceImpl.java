package com.chunfeng.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chunfeng.dao.ClassMapper;
import com.chunfeng.dao.LogMapper;
import com.chunfeng.entity.Class;
import com.chunfeng.entity.Log;
import com.chunfeng.service.IClassService;
import com.chunfeng.service.ex.addException.AddException;
import com.chunfeng.service.ex.deleteExcpption.DeleteException;
import com.chunfeng.service.ex.logException.LogAddErrorException;
import com.chunfeng.service.ex.logException.LogSelectErrorException;
import com.chunfeng.service.ex.logException.LogUpdateErrorException;
import com.chunfeng.service.ex.selectException.SelectSourceIsNullException;
import com.chunfeng.service.ex.updateException.UpdateException;
import com.chunfeng.util.JsonRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 班级业务层实现类
 */
@Service
@Transactional
public class ClassServiceImpl extends ServiceImpl<ClassMapper, Class> implements IClassService {

    /**
     * 班级持久层
     */
    @Autowired
    private ClassMapper classMapper;

    /**
     * 日志持久层
     */
    @Autowired
    private LogMapper logMapper;


    /**
     * 时间格式
     */
    @Value("${date.format}")
    private String dateFormat;

    /**
     * 查询所有班级
     *
     * @param current 页码
     * @param size    页长
     * @return JSON
     */
    @Cacheable(value = "class_page", key = "#current")
    @Override
    public JsonRequest<List<Class>> selectAllClass(Integer current, Integer size) {
        Page<Class> classPage = classMapper.selectPage(new Page<>(current, size), null);//获取所有班级
        if (classPage == null) {
            throw new SelectSourceIsNullException("未查询到数据!");
        }
        Long pageSize = classPage.getTotal();
        List<Class> classList = classPage.getRecords();//获取所有班级
        for (Class aClass : classList) {
            Log log = logMapper.selectById(aClass.getLogId());//拉取日志
            if (log == null) {
                throw new LogSelectErrorException("日志拉取失败!");
            }
            aClass.setLog(log);//添加
        }
        return new JsonRequest<>(200, "", classList, pageSize);
    }

    /**
     * 根据班级名查询所有班级
     *
     * @param current   页码
     * @param size      页长
     * @param className 班级名称
     * @return JSON
     */
    @Cacheable(value = "class_name", key = "#className+'_'+#current")
    @Override
    public JsonRequest<List<Class>> selectAllClassLikeName(Integer current, Integer size, String className) {
        Page<Class> classPage = classMapper.selectPage(
                new Page<>(current, size),//分页
                new LambdaQueryWrapper<Class>()
                        .like(Class::getClassName, className));//根据班级名查找
        if (classPage == null) {
            throw new SelectSourceIsNullException("未查询到数据!");
        }
        Long pageSize = classPage.getTotal();
        List<Class> classList = classPage.getRecords();//获取所有班级
        for (Class aClass : classList) {
            Log log = logMapper.selectById(aClass.getLogId());//拉取日志
            if (log == null) {
                throw new LogSelectErrorException("日志拉取失败!");
            }
            aClass.setLog(log);//添加
        }
        return new JsonRequest<>(200, "", classList, pageSize);
    }

    /**
     * 根据班级编号查询班级
     *
     * @param classId 班级编号
     * @return JSON
     */
    @CacheEvict(value = {"class_name", "class_page"}, allEntries = true)
    @Override
    public JsonRequest<Class> selectAllClassById(Long classId) {
        Class aClass = classMapper.selectById(classId);//根据班级编号查找
        if (aClass == null) {
            throw new SelectSourceIsNullException("未查询到数据!");
        }
        return new JsonRequest<>(200, "", aClass, null);
    }

    /**
     * 添加班级
     *
     * @param aClass 需提供:班级名称
     * @return JSON
     */
    @CacheEvict(value = {"class_name", "class_page"}, allEntries = true)
    @Override
    public JsonRequest<Integer> addClass(Class aClass) {
        Log log = new Log(new SimpleDateFormat(dateFormat).format(new Date()));//创建日志对象
        int logColumn = logMapper.insert(log);//添加日志
        if (logColumn < 1) {
            throw new LogAddErrorException("拉取日志失败!");
        }
        aClass.setLogId(log.getLogId());//获取已添加的日志id
        int column = classMapper.insert(aClass);
        if (column < 1) {
            throw new AddException("添加数据失败!");
        }
        return new JsonRequest<>(200, "", column, null);
    }

    /**
     * 修改班级
     *
     * @param aClass 需提供:班级编号,可提供:班级名称
     * @return JSON
     */
    @CacheEvict(value = {"class_name", "class_page"}, allEntries = true)
    @Override
    public JsonRequest<Integer> updateClassById(Class aClass) {
        Class classSource = classMapper.selectById(aClass.getClassId());//判断班级信息是否存在
        if (classSource == null) {
            throw new SelectSourceIsNullException("该数据不存在!");
        }
        Log log = new Log(classSource.getLogId(),
                new SimpleDateFormat(dateFormat).format(new Date()));//获取并修改时间
        int logColumn = logMapper.updateById(log);//拉取日志
        if (logColumn < 1) {
            throw new LogUpdateErrorException("拉取日志失败!");
        }
        int column = classMapper.updateById(aClass);
        if (column < 1) {
            throw new UpdateException("修改数据失败!");
        }
        return new JsonRequest<>(200, "", column, null);
    }

    /**
     * 删除或恢复班级
     *
     * @param classId 班级编号
     * @param index   操作指数(如果index值为true,则代表删除,反之代表恢复)
     * @return JSON
     */
    @CacheEvict(value = {"class_name", "class_page"}, allEntries = true)
    @Override
    public JsonRequest<Integer> deleteClassById(Long classId, Boolean index) {
        Class aClass = classMapper.selectById(classId);
        if (aClass == null) {
            throw new SelectSourceIsNullException("该数据不存在!");
        }
        int column = logMapper.updateById(new Log(aClass.getLogId(), index ? 1 : 0,
                new SimpleDateFormat(dateFormat).format(new Date())));
        if (column < 1) {
            throw new DeleteException("删除失败!");
        }
        return new JsonRequest<>(200, "", column, null);
    }
}
