package com.github.paohaijiao.banner.impl;

import com.github.paohaijiao.banner.JQuickBanner;
import com.github.paohaijiao.config.JQuickBannerConfig;
import com.github.paohaijiao.console.JConsole;

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

    @Override
    public void printBanner( ) {
        if(!config.isEnabled()){
            return;
        }
        if (!executed.compareAndSet(false, true)) {
            console.warn("⚠️ Banner has already been printed, skipping...");
            return;
        }
        console.info("╔════════════════════════════════════════════════════════════════════╗");
        console.info("║                                                                    ║");
        console.info("║          ██╗   ██████╗ ██╗   ██╗██╗ ██████╗██╗  ██╗                ║");
        console.info("║          ██║  ██╔═══██╗██║   ██║██║██╔════╝██║ ██╔╝                ║");
        console.info("║          ██║  ██║   ██║██║   ██║██║██║     █████╔╝                 ║");
        console.info("║          ██║  ██║▄▄ ██║██║   ██║██║██║     ██╔═██╗                 ║");
        console.info("║     ███████╗ ╚██████╔╝╚██████╔╝ ██║╚██████╗██║  ██╗                ║");
        console.info("║     ╚══════╝  ╚══▀▀═╝  ╚═════╝  ╚═╝ ╚═════╝╚═╝  ╚═╝                ║");
        console.info("║                                                                    ║");
        console.info("║              ⚡  JQUICK - 极速·简洁·现代  ⚡                           ║");
        console.info("║                                                                    ║");
        console.info("║     Version: 1.0.0          License: Apache 2.0                    ║");
        console.info("║     Author:  泡海椒          GitHub: @paohaijiao                    ║");
        console.info("║     Email:   goudingcheng@gmail.com                                ║");
        console.info("║     Star  us on GitHub!                                            ║");
        console.info("║     🚀 JQUICK Runtime Environment Initialized!                     ║");
        console.info("║                                                                    ║");
        console.info("╚════════════════════════════════════════════════════════════════════╝");
        console.debug("✓ JQUICK Runtime Environment initialized successfully!");
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
