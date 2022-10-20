package com.bmsoft.toolkit.xxljob;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.bmsoft.toolkit.xxljob.annotation.XxlJobRegister;
import com.bmsoft.toolkit.xxljob.model.XxlJobGroup;
import com.bmsoft.toolkit.xxljob.model.XxlJobInfo;
import com.bmsoft.toolkit.xxljob.service.JobGroupService;
import com.bmsoft.toolkit.xxljob.service.JobInfoService;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.core.MethodIntrospector;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author : Hydra
 * @date: 2022/9/20 9:57
 * @version: 1.0
 */
@Component
public class XxlJobAutoRegister implements ApplicationListener<ApplicationReadyEvent>,
        ApplicationContextAware {

    private static final Log log = LogFactory.get();

    private ApplicationContext applicationContext;

    @Autowired
    private JobGroupService jobGroupService;

    @Autowired
    private JobInfoService jobInfoService;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        //注册执行器
        addJobGroup();
        //注册任务
        addJobInfo();
    }

    //自动注册执行器
    private void addJobGroup() {
        if (jobGroupService.preciselyCheck())
            return;

        if (jobGroupService.autoRegisterGroup())
            log.info("auto register xxl-job group success!");
    }

    private void addJobInfo() {
        List<XxlJobGroup> jobGroups = jobGroupService.getJobGroup();
        XxlJobGroup xxlJobGroup = jobGroups.get(0);

        String[] beanDefinitionNames = applicationContext.getBeanNamesForType(Object.class, false, true);
        for (String beanDefinitionName : beanDefinitionNames) {
            Object bean = applicationContext.getBean(beanDefinitionName);

            Map<Method, XxlJob> annotatedMethods = MethodIntrospector.selectMethods(bean.getClass(),
                    new MethodIntrospector.MetadataLookup<XxlJob>() {
                        @Override
                        public XxlJob inspect(Method method) {
                            return AnnotatedElementUtils.findMergedAnnotation(method, XxlJob.class);
                        }
                    });
            for (Map.Entry<Method, XxlJob> methodXxlJobEntry : annotatedMethods.entrySet()) {
                Method executeMethod = methodXxlJobEntry.getKey();
                XxlJob xxlJob = methodXxlJobEntry.getValue();

                //自动注册
                if (executeMethod.isAnnotationPresent(XxlJobRegister.class)) {
                    XxlJobRegister xxlJobRegister = executeMethod.getAnnotation(XxlJobRegister.class);
                    List<XxlJobInfo> jobInfo = jobInfoService.getJobInfo(xxlJobGroup.getId(), xxlJob.value());
                    if (!jobInfo.isEmpty()) {
                        // 因为是模糊查询，需要再判断一次
                        Optional<XxlJobInfo> first = jobInfo.stream()
                                .filter(xxlJobInfo -> xxlJobInfo.getExecutorHandler().equals(xxlJob.value()))
                                .findFirst();
                        if (first.isPresent())
                            continue;
                    }

                    XxlJobInfo xxlJobInfo = createXxlJobInfo(xxlJobGroup, xxlJob, xxlJobRegister);
                    Integer jobInfoId = jobInfoService.addJobInfo(xxlJobInfo);
                }
            }
        }
    }

    private XxlJobInfo createXxlJobInfo(XxlJobGroup xxlJobGroup, XxlJob xxlJob, XxlJobRegister xxlJobRegister) {
        XxlJobInfo xxlJobInfo = new XxlJobInfo();
        xxlJobInfo.setJobGroup(xxlJobGroup.getId());
        xxlJobInfo.setJobDesc(xxlJobRegister.jobDesc());
        xxlJobInfo.setAuthor(xxlJobRegister.author());
        xxlJobInfo.setScheduleType("CRON");
        xxlJobInfo.setScheduleConf(xxlJobRegister.cron());
        xxlJobInfo.setGlueType("BEAN");
        xxlJobInfo.setExecutorHandler(xxlJob.value());
        xxlJobInfo.setExecutorRouteStrategy(xxlJobRegister.executorRouteStrategy());
        xxlJobInfo.setMisfireStrategy("DO_NOTHING");
        xxlJobInfo.setExecutorBlockStrategy("SERIAL_EXECUTION");
        xxlJobInfo.setExecutorTimeout(0);
        xxlJobInfo.setExecutorFailRetryCount(0);
        xxlJobInfo.setGlueRemark("GLUE代码初始化");
        xxlJobInfo.setTriggerStatus(xxlJobRegister.triggerStatus());

        return xxlJobInfo;
    }

}
