package com.hyvercode.springday.mail.config;

import lombok.extern.log4j.Log4j2;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.resource.loader.StringResourceLoader;
import org.apache.velocity.runtime.resource.util.StringResourceRepository;
import org.springframework.stereotype.Component;

import java.io.StringWriter;
import java.util.Map;

import static org.apache.velocity.runtime.RuntimeConstants.RESOURCE_LOADER;

@Log4j2
@Component
public class TemplatingEngine {

    public static final String RAW_TEMPLATE = "rawTemplate";
    private final VelocityEngine engine;

    public TemplatingEngine() {
        engine = new VelocityEngine();
        this.addVelocityProperties();
        engine.init();
    }

    public String transform(String rawNotificationTemplate, Map<String, Object> parameters) {

        VelocityContext context = this.getVelocityContext(rawNotificationTemplate, parameters);
        context.put("format", new TemplateFormatter());

        Template template = engine.getTemplate(RAW_TEMPLATE);

        StringWriter templateWriter = new StringWriter(rawNotificationTemplate.length());
        template.merge(context, templateWriter);

        return templateWriter.toString();
    }

    private VelocityContext getVelocityContext(String rawNotificationTemplate, Map<String, Object> parameters) {
        StringResourceRepository resourceRepository =
                (StringResourceRepository) engine.getApplicationAttribute(StringResourceLoader.REPOSITORY_NAME_DEFAULT);
        resourceRepository.putStringResource(RAW_TEMPLATE, rawNotificationTemplate);

        VelocityContext context = new VelocityContext(parameters);
        context.put("format", new TemplateFormatter());
        return context;
    }

    private void addVelocityProperties() {
        Velocity.addProperty("string.resource.loader.description", "Velocity StringResource loader");

        engine.addProperty(RESOURCE_LOADER, "string");
        engine.addProperty("string.resource.loader.repository.static", "false");
        engine.addProperty(
                "string.resource.loader.class",
                "org.apache.velocity.runtime.resource.loader.StringResourceLoader");
        engine.addProperty(
                "string.resource.loader.repository.class",
                "org.apache.velocity.runtime.resource.util.StringResourceRepositoryImpl");
        engine.setProperty(
                "runtime.log.logsystem.class",
                "org.apache.velocity.runtime.log.NullLogChute");
    }
}
