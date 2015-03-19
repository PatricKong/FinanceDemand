package com.mercury.common.spring;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;


/**
 * HostPrecedingPropertyPlaceholderConfigurer
 *
 * Extends PropertyPlaceholderConfigurer to insert $hostname.property if the
 * property name starts with HOST sample properties file:
 *
 * jdbc.user=liveuser server.jdbc.url=jdbc:postgresql://db.host.com:5432/db
 * server.magic.file.location=/var/magicfile
 *
 * devel.jdbc.url=jdbc:postgresql://devel-db.host.com:5432/db
 * devel.magic.file.location=c:\\var\magicfile
 *
 * HOST.jdbc.url=jdbc:postgresql://default.host.com:1234/defaultdb
 *
 * my.property=a property referenced through a method besides
 * HostPrecedingPropertyPlaceholderConfigurer
 *
 * <bean id="propertyConfigurer"
 * class="com.util.spring.HostPrecedingPropertyPlaceholderConfigurer"> <property
 * name="location" value="classpath:config.properties" /> </bean>
 *
 * <bean id="dataSource" class="com.mchange.v2.c3p0.ComboPooledDataSource"
 * destroy-method="close"> <property name="driverClass"
 * value="${jdbc.driverClass}" /> <property name="jdbcUrl"
 * value="${HOST.jdbc.url}" /><!--Do a host lookup!--> <property name="user"
 * value="${jdbc.user}" /> <property name="password" value="${jdbc.password}" />
 * </bean>
 *
 *
 * @author Jeff Dwyer (blog) http://jdwyah.blogspot.com
 * @author Eero Raun (updated resolvePlaceholder)
 * @see http://jdwyah.blogspot.com/2006/12/updated-spring-configuration.html#c7193761915643235219
 */
public class HostPrecedingPropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer {

	private static final Logger LOG = Logger.getLogger(HostPrecedingPropertyPlaceholderConfigurer.class);

    protected String resolvePlaceholder(String placeholder, Properties props) {
        if (!placeholder.startsWith("HOST.")) {
            return props.getProperty(placeholder);
        }

        String hostname = "COULDNTGETHOSTNAME";

        try {
            hostname = InetAddress.getLocalHost().getHostName().toUpperCase();
        } catch (UnknownHostException uhe) {
            LOG.error("Error getting hostname!", uhe);
            return null;
        }

        String hostSpecific = placeholder.replaceFirst("HOST", hostname.toUpperCase());
        LOG.debug("Looking for property " + hostSpecific);
        return defaultValue(props.getProperty(hostSpecific), props.getProperty(placeholder));
    }

    protected <T> T defaultValue(T o, T defaultVal) {
        if (o == null) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Value missing; using default: " + defaultVal);
            }
            return defaultVal;
        }

        if (LOG.isDebugEnabled()) {
            LOG.debug("Object has value; using: " + o);
        }

        return o;
    }

}
