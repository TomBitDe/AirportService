package util;

import java.util.Hashtable;
import java.util.Properties;
import java.util.Set;
import javax.ejb.Stateful;
import javax.ejb.Stateless;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;
import org.apache.log4j.Logger;

/**
 * Hides and simplifies the access to objects stored in the JNDI-tree.
 */
public class BeanLocator {
    private static final Logger LOG = Logger.getLogger(BeanLocator.class.getName());

    public static class GlobalJNDIName {

        private StringBuilder builder;
        private final static String GLOBAL_JNDI_PREFIX_NAME_KEY = "global.jndi.prefix";
        private final static String SEPARATOR = "/";
        private final static String PROPERTY_FILE = "/global.jndi";
        private final static String MODULE_NAME_KEY = "module.name";
        private final static String APP_NAME_KEY = "application.name";
        private Properties globalConfiguration;
        private final String globalJndiPrefixName;
        private String appName;
        private String moduleName;
        private String beanName;
        private String businessInterfaceName;

        public GlobalJNDIName() {
            this.builder = new StringBuilder();
            this.globalConfiguration = new Properties();
            try {
                this.globalConfiguration.load(this.getClass().getResourceAsStream(PROPERTY_FILE));
            }
            catch (Exception ex) {
                LOG.fatal("Cannot load configuration: " + ex);
            }
            this.globalJndiPrefixName = this.globalConfiguration.getProperty(GLOBAL_JNDI_PREFIX_NAME_KEY);
            this.appName = this.globalConfiguration.getProperty(APP_NAME_KEY);
            this.moduleName = this.globalConfiguration.getProperty(MODULE_NAME_KEY);
        }

        public GlobalJNDIName withAppName(String appName) {
            this.appName = appName;
            return this;
        }

        public GlobalJNDIName withModuleName(String moduleName) {
            this.moduleName = moduleName;
            return this;
        }

        public GlobalJNDIName withBeanName(String beanName) {
            this.beanName = beanName;
            return this;
        }

        public GlobalJNDIName withBeanName(Class beanClass) {
            return withBeanName(computeBeanName(beanClass));
        }

        public GlobalJNDIName withBusinessInterface(Class interfaze) {
            this.businessInterfaceName = interfaze.getName();
            return this;
        }

        String computeBeanName(Class beanClass) {
            Stateless stateless = (Stateless) beanClass.getAnnotation(Stateless.class);
            if (stateless != null && isNotEmpty(stateless.name())) {
                return stateless.name();
            }
            Stateful stateful = (Stateful) beanClass.getAnnotation(Stateful.class);
            if (stateful != null && isNotEmpty(stateful.name())) {
                return stateful.name();
            }
            return beanClass.getSimpleName();
        }

        private boolean isNotEmpty(String name) {
            return (name != null && !name.isEmpty());
        }

        public String asString() {
            if (globalJndiPrefixName != null && !globalJndiPrefixName.trim().isEmpty()) {
                this.builder.append(globalJndiPrefixName).append(SEPARATOR);
            }

            if (appName != null && !appName.trim().isEmpty()) {
                this.builder.append(appName).append(SEPARATOR);
            }

            if (moduleName != null && !moduleName.trim().isEmpty()) {
                this.builder.append(moduleName).append(SEPARATOR);
            }

            this.builder.append(beanName);

            if (businessInterfaceName != null && !businessInterfaceName.trim().isEmpty()) {
                this.builder.append("#").append(businessInterfaceName);
            }

            return this.builder.toString();
        }

        public <T> T locate(Class<T> clazz) {
            return BeanLocator.lookup(clazz, asString());
        }

        public Object locate() {
            return BeanLocator.lookup(asString());
        }
    }

    /**
     * Lookup for a bean reference
     *
     * @param <T>      the type of the ejb reference
     * @param clazz    the type (Business Interface or Bean Class)
     * @param jndiName the global JNDI name with the pattern:
     *                 java:global[/app-name]/module-name/bean-name#fully-qualified-interface-name
     *
     * @return the local or remote reference to the bean
     */
    public static <T> T lookup(Class<T> clazz, String jndiName) {
        Object bean = lookup(jndiName);
        return clazz.cast(PortableRemoteObject.narrow(bean, clazz));
    }

    /**
     * Lookup for a bean reference
     *
     * @param jndiName the global JNDI name with the pattern:
     *                 java:global[/app-name]/module-name/bean-name#fully-qualified-interface-name
     *
     * @return the local or remote reference to the bean
     */
    public static Object lookup(String jndiName) {
        Context context = null;
        try {
            context = new InitialContext();
            showJndiEnv(context.getEnvironment());
            return context.lookup(jndiName);
        }
        catch (NamingException ex) {
            throw new IllegalStateException("Cannot connect to bean: " + jndiName + " Reason: " + ex, ex.getCause());
        }
        catch (Exception e) {
            throw new IllegalStateException("Cannot connect to bean: " + jndiName + " Reason: " + e, e.getCause());
        }
        finally {
            try {
                if (context != null) {
                    context.close();
                }
            }
            catch (NamingException ex) {
                throw new IllegalStateException("Cannot close InitialContext. Reason: " + ex, ex.getCause());
            }
        }
    }

    /**
     * Show the used JNDI environment values
     *
     * @param env the JNDI environment
     */
    private static void showJndiEnv(Hashtable env) {
        LOG.debug("JNDI environment:");
        Set<Object> envKeys = env.keySet();
        for (Object o : envKeys) {
            LOG.debug("     " + o.toString() + "=" + env.get(o).toString());
        }
    }
}
