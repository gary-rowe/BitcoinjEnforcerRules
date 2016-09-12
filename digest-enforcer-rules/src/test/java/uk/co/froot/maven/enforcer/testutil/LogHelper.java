package uk.co.froot.maven.enforcer.testutil;

import org.apache.maven.plugin.logging.Log;

public class LogHelper implements Log {

    @Override
    public boolean isDebugEnabled() {
        return true;
    }

    @Override
    public void debug(final CharSequence content) {
        System.out.println(content);
    }

    @Override
    public void debug(final CharSequence content, final Throwable error) {
        debug(content);
        debug(error);
    }

    @Override
    public void debug(final Throwable error) {
        error.printStackTrace(System.out);
    }

    @Override
    public boolean isInfoEnabled() {
        return true;
    }

    @Override
    public void info(final CharSequence content) {
        System.out.println(content);
    }

    @Override
    public void info(final CharSequence content, final Throwable error) {
        info(content);
        info(error);
    }

    @Override
    public void info(final Throwable error) {
        error.printStackTrace(System.out);
    }

    @Override
    public boolean isWarnEnabled() {
        return true;
    }

    @Override
    public void warn(final CharSequence content) {
        System.out.println(content);
    }

    @Override
    public void warn(final CharSequence content, final Throwable error) {
        warn(content);
        warn(error);
    }

    @Override
    public void warn(final Throwable error) {
        error.printStackTrace(System.out);
    }

    @Override
    public boolean isErrorEnabled() {
        return true;
    }

    @Override
    public void error(final CharSequence content) {
        System.err.println(content);
    }

    @Override
    public void error(final CharSequence content, final Throwable error) {
        error(content);
        error(error);
    }

    @Override
    public void error(final Throwable error) {
        error.printStackTrace(System.err);
    }

}
