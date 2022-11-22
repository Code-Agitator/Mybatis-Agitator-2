package pers.agitator.mybatis.pasrsing;

import pers.agitator.mybatis.util.StrUtil;

/**
 * 解析sql中的 占位符
 */
public class GenericTokenParser {
    /**
     * 占位符开
     */
    private final String openToken;
    /**
     * 占位符闭
     */
    private final String closeToken;
    /**
     * 占位数据处理
     */
    private final TokenHandler handler;

    public GenericTokenParser(String openToken, String closeToken, TokenHandler handler) {
        this.openToken = openToken;
        this.closeToken = closeToken;
        this.handler = handler;
    }

    public String parse(String text) {
        if (StrUtil.isBlank(text)) {
            return "";
        }
        // search open token
        int start = text.indexOf(openToken);
        if (start == -1) {
            // 找不到占位符
            return text;
        }
        char[] src = text.toCharArray();
        int offset = 0;
        final StringBuilder builder = new StringBuilder();
        StringBuilder expression = null;
        do {
            if (start > 0 && src[start - 1] == '\\') {
                // 处理转义字符
                builder.append(src, offset, start - offset - 1).append(openToken);
                // 跳过当前占位符
                offset = start + openToken.length();
            } else {
                // 找到占位符开了 找闭的
                if (expression == null) {
                    expression = new StringBuilder();
                } else {
                    expression.setLength(0);
                }
                // 把占位符左边的东西先填上
                builder.append(src, offset, start - offset);
                offset = start + openToken.length();
                // 开始找占位符闭
                int end = text.indexOf(closeToken, offset);
                while (end > -1) {
                    // 找到占位符闭  同样的处理转义字符
                    if (end > offset && src[end - 1] == '\\') {
                        // 处理转义字符
                        builder.append(src, offset, end - offset - 1).append(closeToken);
                        // 跳过当前占位符
                        offset = end + closeToken.length();
                        // 下一个占位符闭
                        end = text.indexOf(closeToken, offset);
                    } else {
                        // 找到成对的占位闭了！
                        expression.append(src, offset, end - offset);
                        break;
                    }
                }
                if (end == -1) {
                    // 完全找不到占位符
                    builder.append(src, start, src.length - offset);
                    offset = src.length;
                } else {
                    // 解析占位数据
                    builder.append(handler.handleToken(expression.toString()));
                    offset = end + closeToken.length();
                }
            }
            // 找下一个占位符开
            start = text.indexOf(openToken, offset);
            // 直到找不到占位符开
        } while (start > -1);
        // 检查还有没有剩下
        if (offset < src.length) {
            builder.append(src, offset, src.length - offset);
        }
        return builder.toString();
    }
}
