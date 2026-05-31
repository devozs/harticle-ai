package com.devozs.components.common.utils;

import java.io.File;
import java.io.FileNotFoundException;

public class JobFileUtils {
    private JobFileUtils(){}

    private static final String JOB_FILE_LOCATION = "/opt/job_manifest";
    private static final String ENGINE_MANIFEST = "engine.yaml";
    private static final String EVENT_TRACER_MANIFEST = "kube-events-tracer.yaml";
    private static final String EVENT_TRACER_CONFIG_MANIFEST = "kube-events-tracer-application.yaml";
    private static final String SLASH = "/";

    private static File getFile(String manifestFile) throws FileNotFoundException{
        String filePath = JOB_FILE_LOCATION + SLASH + manifestFile;
        File file = new File(filePath);
        if(!file.exists())
            throw new FileNotFoundException(String.format("Couldn't locate file %s", filePath));
        return file;
    }

    public static File getEngineFile() throws FileNotFoundException{
        return getFile(ENGINE_MANIFEST);
    }

    public static File getEventTracerFile() throws FileNotFoundException{
        return getFile(EVENT_TRACER_MANIFEST);
    }

    public static File getEventTracerKubeConfigFile() throws FileNotFoundException{
        return getFile(EVENT_TRACER_CONFIG_MANIFEST);
    }


}
