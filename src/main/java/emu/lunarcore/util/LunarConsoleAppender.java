package emu.lunarcore.util;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;
import ch.qos.logback.classic.PatternLayout;

import emu.lunarcore.LunarCore;

public class LunarConsoleAppender extends AppenderBase<ILoggingEvent> {
    protected PatternLayout layout;
    
    @Override
    public void start() {
        this.layout = new PatternLayout();
        this.layout.setContext(context);
        this.layout.setPattern("[%d{HH:mm:ss}] [%level] %msg%n");
        this.layout.start();
        super.start();
    }
    
    @Override
    public void stop() {
        super.stop();
        this.layout.stop();
    }
    
    @Override
    protected void append(ILoggingEvent event) {
        LunarCore.getLineReader().printAbove(layout.doLayout(event));
    }
}
