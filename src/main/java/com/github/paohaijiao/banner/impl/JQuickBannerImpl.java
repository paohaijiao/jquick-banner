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

    /** 标签区宽度：以最长标签 "Version" 为准，7 + 1 空格 = 8 */
    private static final int LABEL_WIDTH = 8;

    /** 信息区右侧 emoji 区宽度：1 空格 + emoji(2) + 1 空格 = 4 */
    private static final int RIGHT_ICON_WIDTH = 4;

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
        instance.setConfig(config);
        return instance;
    }

    public void setConfig(JQuickBannerConfig config) {
        this.config = config;
    }

    public static JQuickBannerConfig loadFromProperties() {
        try (InputStream input = JQuickBannerConfig.class.getClassLoader()
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
                config.setUrl(props.getProperty("jquick.banner.url", config.getUrl()));
                return config;
            }
        } catch (Exception e) {
            // ignore
        }
        return null;
    }

    @Override
    public void printBanner() {
        if (!config.isEnabled()) {
            return;
        }
        if (!executed.compareAndSet(false, true)) {
            console.warn("⚠️ Banner has already been printed, skipping...");
            return;
        }

        JQuickBannerConfig configFromClassPathFile = loadFromProperties();
        if (configFromClassPathFile != null) {
            config = configFromClassPathFile;
        }

        String version = config.getVersion() != null ? config.getVersion() : "1.0.0";
        String author = config.getAuthor() != null ? config.getAuthor() : "paohaijiao";
        String github = config.getGithub() != null ? config.getGithub() : "@paohaijiao";
        String email = config.getEmail() != null ? config.getEmail() : "goudingcheng@gmail.com";
        String projectName = config.getProjectName() != null ? config.getProjectName() : "JQUICK";
        String slogan = config.getSlogan() != null ? config.getSlogan() : "Fast · Simple · Modern";
        String url = config.getUrl() != null ? config.getUrl() : "https://www.jquick.org/";
        String githubUrl = config.getGithubUrl() != null ? config.getGithubUrl() : "https://github.com/paohaijiao";

        int innerWidth = 68;
        String top = "╔" + repeat("═", innerWidth) + "╗";
        String bottom = "╚" + repeat("═", innerWidth) + "╝";
        String empty = "║" + repeat(" ", innerWidth) + "║";
        int infoContentWidth = innerWidth - 2 - RIGHT_ICON_WIDTH;
        console.info(top);
        console.info(empty);
        // ASCII 艺术 LOGO（JQUICK），硬编码对齐，每行总宽 68
        console.info("║          ██╗ ██████╗ ██╗   ██╗██╗ ██████╗██╗  ██╗                  ║");
        console.info("║          ██║██╔═══██╗██║   ██║██║██╔════╝██║ ██╔╝                  ║");
        console.info("║          ██║██║   ██║██║   ██║██║██║     █████╔╝                   ║");
        console.info("║     ██   ██║██║   ██║██║   ██║██║██║     ██╔═██╗                   ║");
        console.info("║     ╚█████╔╝╚██████╔╝╚██████╔╝██║╚██████╗██║  ██╗                  ║");
        console.info("║      ╚════╝  ╚═════╝  ╚═════╝ ╚═╝ ╚═════╝╚═╝  ╚═╝                  ║");
        console.info(empty);
        console.info("║                ⚡ " + projectName + " - " + slogan + " ⚡                 ║");
        console.info(empty);

        // 信息区：标签冒号对齐，emoji 放行尾，不参与对齐
        console.info("║  " + buildInfoLine("Version", version, "🔖", infoContentWidth) + "║");
        console.info("║  " + buildInfoLine("Author",  author,  "👤", infoContentWidth) + "║");
        console.info("║  " + buildInfoLine("GitHub",  github,  "🐙", infoContentWidth) + "║");
        console.info("║  " + buildInfoLine("Email",   email,   "📧", infoContentWidth) + "║");
        console.info("║  " + buildInfoLine("Official URL",     url,     "🌐", infoContentWidth) + "║");
        console.info("║  " + buildInfoLine("Star us on GitHub:",     githubUrl,     "⭐", infoContentWidth) + "║");

        console.info(empty);
        console.info("║" + padRight(" 🚀 " + projectName + " Runtime Environment Initialized!", innerWidth) + "║");
        console.info(empty);
        console.info(bottom);

        console.debug("✅ " + projectName + " Runtime Environment initialized successfully!");
        console.info("⚡ Ready to rock!");
        console.warn("⭐ Don't forget to star us: " + githubUrl + "\n");
    }

    public void reset() {
        executed.set(false);
    }


    /**
     * 构造信息行：`Label : value` 对齐到 infoContentWidth，右侧留图标位
     */
    private static String buildInfoLine(String label, String value, String icon, int infoContentWidth) {
        String left = padRight(label, LABEL_WIDTH) + ": " + value;
        String padded = padRight(left, infoContentWidth);
        return padded + " " + icon + " ";
    }

    private static String repeat(String s, int count) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < count; i++) {
            sb.append(s);
        }
        return sb.toString();
    }

    /**
     * 将字符串居中到指定宽度（按显示宽度计算，CJK 与 emoji 算 2）
     */
    private static String center(String text, int width) {
        int textWidth = displayWidth(text);
        if (textWidth >= width) {
            return text;
        }
        int left = (width - textWidth) / 2;
        int right = width - textWidth - left;
        return repeat(" ", left) + text + repeat(" ", right);
    }

    /**
     * 将字符串左对齐并填充到指定宽度（按显示宽度计算）
     */
    private static String padRight(String text, int width) {
        int textWidth = displayWidth(text);
        if (textWidth >= width) {
            return text;
        }
        return text + repeat(" ", width - textWidth);
    }

    /**
     * 计算显示宽度：CJK、全角、emoji 算 2，其余算 1。
     */
    private static int displayWidth(String text) {
        int width = 0;
        for (int i = 0; i < text.length(); ) {
            int cp = text.codePointAt(i);
            if (isWide(cp)) {
                width += 2;
            } else {
                width += 1;
            }
            i += Character.charCount(cp);
        }
        return width;
    }

    /**
     * 判断码点是否为宽字符（占 2 列）
     */
    private static boolean isWide(int cp) {
        if (cp >= 0x4E00 && cp <= 0x9FFF)   return true;  // CJK Unified Ideographs
        if (cp >= 0x3000 && cp <= 0x303F)   return true;  // CJK Symbols and Punctuation
        if (cp >= 0xFF00 && cp <= 0xFFEF)   return true;  // Halfwidth and Fullwidth Forms
        if (cp >= 0x1F300 && cp <= 0x1FAFF) return true;  // Emoji
        if (cp >= 0x2600  && cp <= 0x27BF)  return true;  // Misc symbols / Dingbats
        if (cp >= 0x2B00  && cp <= 0x2BFF)  return true;  // Arrows
        return false;
    }

    public static void main(String[] args) {
        JQuickBanner banner = JQuickBannerImpl.getInstance();
        banner.printBanner();
        banner.printBanner();
        banner.printBanner();
    }
}