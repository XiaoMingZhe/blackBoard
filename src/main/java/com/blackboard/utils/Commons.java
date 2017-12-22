package com.blackboard.utils;
/*package com.soecode.lyf.utils;




import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

*//**
 * 主题公共函数
 * <p>
 * Created by 13 on 2017/2/21.
 *//*
@Component
public final class Commons {

//    private static ISiteService siteService;

    public static String THEME = "themes/default";

    private static final List EMPTY = new ArrayList(0);

    *//**
     * 判断分页中是否有数据
     *
     * @param paginator
     * @return
     *//*
    public static boolean is_empty(PageInfo paginator) {
        return paginator == null || (paginator.getList() == null) || (paginator.getList().size() == 0);
    }

    *//**
     * 网站链接
     *
     * @return
     *//*
//    public static String site_url() {
//        return site_url("");
//    }

    *//**
     * 返回网站链接下的全址
     *
     * @param sub 后面追加的地址
     * @return
     *//*
//    public static String site_url(String sub) {
//        return site_option("site_url") + sub;
//    }

    *//**
     * 网站标题
     *
     * @return
     *//*
//    public static String site_title() {
//        return site_option("site_title");
//    }

    *//**
     * 网站配置项
     *
     * @param key
     * @return
     *//*
//    public static String site_option(String key) {
//        return site_option(key, "");
//    }

    *//**
     * 网站配置项
     *
     * @param key
     * @param defalutValue 默认值
     * @return
     *//*
//    public static String site_option(String key, String defalutValue) {
//        if (StringUtils.isBlank(key)) {
//            return "";
//        }
//        String str = WebConst.initConfig.get(key);
//        if (StringUtils.isNotBlank(str)) {
//            return str;
//        } else {
//            return defalutValue;
//        }
//    }

    *//**
     * 截取字符串
     *
     * @param str
     * @param len
     * @return
     *//*
    public static String substr(String str, int len) {
        if (str.length() > len) {
            return str.substring(0, len);
        }
        return str;
    }

    *//**
     * 返回主题URL
     *
     * @return
     *//*
//    public static String theme_url() {
//        return site_url(Commons.THEME);
//    }

    *//**
     * 返回主题下的文件路径
     *
     * @param sub
     * @return
     *//*
//    public static String theme_url(String sub) {
//        return site_url(Commons.THEME + sub);
//    }

    *//**
     * 返回github头像地址
     *
     * @param email
     * @return
     *//*
    public static String gravatar(String email) {
        String avatarUrl = "https://github.com/identicons/";
        if (StringUtils.isBlank(email)) {
            email = "user@hanshuai.xin";
        }
        String hash = RcsUtils.MD5encode(email.trim().toLowerCase());
        return avatarUrl + hash + ".png";
    }

    *//**
     * 格式化unix时间戳为日期
     *
     * @param unixTime
     * @return
     *//*
    public static String fmtdate(Integer unixTime) {
        return fmtdate(unixTime, "yyyy-MM-dd");
    }

    *//**
     * 格式化unix时间戳为日期
     *
     * @param unixTime
     * @param patten
     * @return
     *//*
    public static String fmtdate(Integer unixTime, String patten) {
        if (null != unixTime && StringUtils.isNotBlank(patten)) {
            return DateKit.formatDateByUnixTime(unixTime, patten);
        }
        return "";
    }

    *//**
     * 显示分类
     *
     * @param categories
     * @return
     *//*
    public static String show_categories(String categories) throws UnsupportedEncodingException {
        if (StringUtils.isNotBlank(categories)) {
            String[] arr = categories.split(",");
            StringBuffer sbuf = new StringBuffer();
            for (String c : arr) {
                sbuf.append("<a href=\"/category/" + URLEncoder.encode(c, "UTF-8") + "\">" + c + "</a>");
            }
            return sbuf.toString();
        }
        return show_categories("默认分类");
    }

    *//**
     * 显示标签
     *
     * @param tags
     * @return
     *//*
    public static String show_tags(String tags) throws UnsupportedEncodingException {
        if (StringUtils.isNotBlank(tags)) {
            String[] arr = tags.split(",");
            StringBuffer sbuf = new StringBuffer();
            for (String c : arr) {
                sbuf.append("<a href=\"/tag/" + URLEncoder.encode(c, "UTF-8") + "\">" + c + "</a>");
            }
            return sbuf.toString();
        }
        return "";
    }

    *//**
     * 截取文章摘要
     *
     * @param value 文章内容
     * @param len   要截取文字的个数
     * @return
     *//*
    public static String intro(String value, int len) {
        int pos = value.indexOf("<!--more-->");
        if (pos != -1) {
            String html = value.substring(0, pos);
            return RcsUtils.htmlToText(RcsUtils.mdToHtml(html));
        } else {
            String text = RcsUtils.htmlToText(RcsUtils.mdToHtml(value));
            if (text.length() > len) {
                return text.substring(0, len);
            }
            return text;
        }
    }

    *//**
     * 显示文章内容，转换markdown为html
     *
     * @param value
     * @return
     *//*
    public static String article(String value) {
        if (StringUtils.isNotBlank(value)) {
            value = value.replace("<!--more-->", "\r\n");
            return RcsUtils.mdToHtml(value);
        }
        return "";
    }


    *//**
     * An :grinning:awesome :smiley:string &#128516;with a few :wink:emojis!
     * <p>
     * 这种格式的字符转换为emoji表情
     *
     * @param value
     * @return
     *//*
    public static String emoji(String value) {
//        return EmojiParser.parseToUnicode(value);
        return null;
    }

    *//**
     * 获取文章第一张图片
     *
     * @return
     *//*
    public static String show_thumb(String content) {
        content = RcsUtils.mdToHtml(content);
        if (content.contains("<img")) {
            String img = "";
            String regEx_img = "<img.*src\\s*=\\s*(.*?)[^>]*?>";
            Pattern p_image = Pattern.compile(regEx_img, Pattern.CASE_INSENSITIVE);
            Matcher m_image = p_image.matcher(content);
            if (m_image.find()) {
                img = img + "," + m_image.group();
                // //匹配src
                Matcher m = Pattern.compile("src\\s*=\\s*\'?\"?(.*?)(\'|\"|>|\\s+)").matcher(img);
                if (m.find()) {
                    return m.group(1);
                }
            }
        }
        return "";
    }

    private static final String[] ICONS = {"bg-ico-book", "bg-ico-game", "bg-ico-note", "bg-ico-chat", "bg-ico-code", "bg-ico-image", "bg-ico-web", "bg-ico-link", "bg-ico-design", "bg-ico-lock"};

    *//**
     * 显示文章图标
     *
     * @param cid
     * @return
     *//*
    public static String show_icon(int cid) {
        return ICONS[cid % ICONS.length];
    }

}
*/