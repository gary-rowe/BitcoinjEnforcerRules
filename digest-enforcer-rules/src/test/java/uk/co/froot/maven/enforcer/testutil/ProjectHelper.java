package uk.co.froot.maven.enforcer.testutil;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.maven.enforcer.rule.api.EnforcerRuleHelper;
import org.apache.maven.plugin.logging.Log;
import org.codehaus.plexus.PlexusContainer;
import org.codehaus.plexus.component.configurator.expression.ExpressionEvaluationException;
import org.codehaus.plexus.component.repository.exception.ComponentLookupException;

@SuppressWarnings("rawtypes")
public class ProjectHelper implements EnforcerRuleHelper {

    private final Log log = new LogHelper();
    private final Map<Object, Object> data = new HashMap<Object, Object>();

    public void set(final Object key, final Object value) {
        this.data.put(key, value);
    }

    private Object get(final Object key) {
        final Object value = this.data.get(key);
        if (value == null) {
            throw new IllegalArgumentException("Key " + key + " not found!");
        }
        return value;
    }

    @Override
    public Object evaluate(final String expression) throws ExpressionEvaluationException {
        return get(expression);
    }

    @Override
    public File alignToBaseDirectory(final File file) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Log getLog() {
        return this.log;
    }

    @Override
    public Object getComponent(final Class clazz) throws ComponentLookupException {
        return get(clazz);
    }

    @Override
    public Object getComponent(final String componentKey) throws ComponentLookupException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Object getComponent(final String role, final String roleHint) throws ComponentLookupException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Map getComponentMap(final String role) throws ComponentLookupException {
        throw new UnsupportedOperationException();
    }

    @Override
    public List getComponentList(final String role) throws ComponentLookupException {
        throw new UnsupportedOperationException();
    }

    @Override
    public PlexusContainer getContainer() {
        throw new UnsupportedOperationException();
    }

}
