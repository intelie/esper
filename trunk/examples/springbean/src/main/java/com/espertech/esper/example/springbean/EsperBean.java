package com.espertech.esper.example.springbean;

import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.EPRuntime;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.BeanNameAware;

public class EsperBean implements BeanNameAware, InitializingBean, DisposableBean
{
    private String engineURI;
    private EPServiceProvider epServiceProvider;

    private final Set<StatementBean> statementBeans = new LinkedHashSet<StatementBean>();

    public void setStatements(StatementBean... statementBeans)
    {
        for (StatementBean statementBean : statementBeans)
        {
            addStatement(statementBean);
        }
    }

    public void addStatement(StatementBean statementBean)
    {
        statementBeans.add(statementBean);
    }

    public void setBeanName(String name)
    {
        this.engineURI = name;
    }

    public void afterPropertiesSet() throws Exception
    {
        epServiceProvider = EPServiceProviderManager.getProvider(engineURI);
        for (StatementBean statementBean : statementBeans)
        {
            EPStatement epStatement = epServiceProvider.getEPAdministrator().createEPL(statementBean.getEPL());
            statementBean.setEPStatement(epStatement);
        }
    }

    public void destroy() throws Exception
    {
        epServiceProvider.destroy();
    }
}
