package com.github.paohaijiao.banner.impl;

import com.github.paohaijiao.banner.JQuickBanner;
import com.github.paohaijiao.config.JQuickBannerConfig;
import com.github.paohaijiao.console.JConsole;

import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;

public class JQuickBannerImpl implements JQuickBanner {

    private static volatile JQuickBannerImpl instance;


    private JQuickBannerConfig config;

    private JConsole console = new JConsole();

    private AtomicBoolean executed = new AtomicBoolean(false);


    private JQuickBannerImpl() {
        this.config = new JQuickBannerConfig();
    }

    public static JQuickBannerImpl getInstance() {
        if (instance == null) {
            synchronized (JQuickBannerImpl.class) {
                if (instance == null) {
                    instance = new JQuickBannerImpl();
                }
            }
        }
        return instance;
    }
    public static JQuickBannerImpl getInstance(JQuickBannerConfig config) {
        if (instance == null) {
            synchronized (JQuickBannerImpl.class) {
                if (instance == null) {
                    instance = new JQuickBannerImpl();
                }
            }
        }
        return instance;
    }
    public void setConfig(JQuickBannerConfig config) {
        this.config = config;
    }
    public static JQuickBannerConfig loadFromProperties() {

        try (InputStream input = JQuickBannerConfig.class
                .getClassLoader()
                .getResourceAsStream("jquick-banner.properties")) {
            if (input != null) {
                JQuickBannerConfig config = new JQuickBannerConfig();
                Properties props = new Properties();
                props.load(input);
                config.setEnabled(Boolean.parseBoolean(props.getProperty("jquick.banner.enabled", "true")));
                config.setVersion(props.getProperty("jquick.banner.version", config.getVersion()));
                config.setAuthor(props.getProperty("jquick.banner.author", config.getAuthor()));
                config.setGithub(props.getProperty("jquick.banner.github", config.getGithub()));
                config.setEmail(props.getProperty("jquick.banner.email", config.getEmail()));
                config.setProjectName(props.getProperty("jquick.banner.project", config.getProjectName()));
                config.setSlogan(props.getProperty("jquick.banner.slogan", config.getSlogan()));
                return config;
            }
        } catch (Exception e) {

        }
        return null;
    }
    @Override
    public void printBanner( ) {
        if(!config.isEnabled()){
            return;
        }
        if (!executed.compareAndSet(false, true)) {
            console.warn("⚠️ Banner has already been printed, skipping...");
            return;
        }
        JQuickBannerConfig configFromClassPathFile=loadFromProperties();
        if (configFromClassPathFile != null) {
            config=configFromClassPathFile;
        }
        String version = config.getVersion() != null ? config.getVersion() : "1.0.0";
        String author = config.getAuthor() != null ? config.getAuthor() : "泡海椒";
        String github = config.getGithub() != null ? config.getGithub() : "@paohaijiao";
        String email = config.getEmail() != null ? config.getEmail() : "goudingcheng@gmail.com";
        String projectName = config.getProjectName() != null ? config.getProjectName() : "JQUICK";
        String slogan = config.getSlogan() != null ? config.getSlogan() : "极速·简洁·现代";
        console.info("╔════════════════════════════════════════════════════════════════════╗");
        console.info("║                                                                    ║");
        console.info("║          ██╗   ██████╗ ██╗   ██╗██╗ ██████╗██╗  ██╗                ║");
        console.info("║          ██║  ██╔═══██╗██║   ██║██║██╔════╝██║ ██╔╝                ║");
        console.info("║          ██║  ██║   ██║██║   ██║██║██║     █████╔╝                 ║");
        console.info("║          ██║  ██║▄▄ ██║██║   ██║██║██║     ██╔═██╗                 ║");
        console.info("║     ███████╗ ╚██████╔╝╚██████╔╝ ██║╚██████╗██║  ██╗                ║");
        console.info("║     ╚══════╝  ╚══▀▀═╝  ╚═════╝  ╚═╝ ╚═════╝╚═╝  ╚═╝                ║");
        console.info("║                                                                    ║");
        console.info("║              ⚡  " + projectName + " - " + slogan + "  ⚡                           ║");
        console.info("║                                                                    ║");
        console.info("║     Version: " + version + "          License: Apache 2.0                    ║");
        console.info("║     Author:  " + author + "          GitHub: " + github + "                    ║");
        console.info("║     Email:   " + email + "                                ║");
        console.info("║     Star  us on GitHub!                                            ║");
        console.info("║     🚀 " + projectName + " Runtime Environment Initialized!                     ║");
        console.info("║                                                                    ║");
        console.info("╚════════════════════════════════════════════════════════════════════╝");
        console.debug("✓ " + projectName + " Runtime Environment initialized successfully!");
        console.info(" ⚡ Ready to rock!");
        console.warn("⭐ Don't forget to star us: https://github.com/paohaijiao\n");
    }
    public void reset() {
        executed.set(false);
    }
    public static void main(String[] args) {
        JQuickBanner banner=JQuickBannerImpl.getInstance();
        banner.printBanner();
        banner.printBanner();
        banner.printBanner();
    }
}
