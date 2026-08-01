package com.github.paohaijiao.banner.impl;

import com.github.paohaijiao.banner.JQuickBanner;
import com.github.paohaijiao.config.JQuickBannerConfig;
import com.github.paohaijiao.console.JConsole;

public class JQuickBannerImpl implements JQuickBanner {


    private static final JQuickBannerImpl INSTANCE = new JQuickBannerImpl();

    private JQuickBannerConfig config;

    private JConsole console = new JConsole();

    private JQuickBannerImpl() {
        this.config = new JQuickBannerConfig();
    }

    public static JQuickBannerImpl getInstance() {
        return INSTANCE;
    }
    public void setConfig(JQuickBannerConfig config) {
        this.config = config;
    }

    @Override
    public void printBanner( ) {
        if(!config.isEnabled()){
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

    public static void main(String[] args) {
        JQuickBanner banner=JQuickBannerImpl.getInstance();
        banner.printBanner();
    }
}
